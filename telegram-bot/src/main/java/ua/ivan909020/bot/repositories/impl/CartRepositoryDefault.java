package ua.ivan909020.bot.repositories.impl;

import ua.ivan909020.bot.domain.models.CartItem;
import ua.ivan909020.bot.repositories.CartRepository;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class CartRepositoryDefault implements CartRepository {

    private AtomicInteger lastCartItemId = new AtomicInteger();
    private Map<Long, List<CartItem>> cartItems = new HashMap<>();

    private Map<Long, Integer> cartPageNumbers = new HashMap<>();

    @Override
    public void saveCartItem(Long chatId, CartItem cartItem) {
        cartItems.computeIfAbsent(chatId, orderItems -> new ArrayList<>());
        cartItem.setId(lastCartItemId.incrementAndGet());
        cartItems.get(chatId).add(copy(cartItem));
    }

    @Override
    public void updateCartItem(Long chatId, CartItem cartItem) {
        cartItems.computeIfAbsent(chatId, cartItems -> new ArrayList<>());
        Optional<CartItem> receivedCartItem = cartItems.get(chatId).stream()
                .filter(item -> cartItem.getId().equals(item.getId()))
                .findFirst();
        receivedCartItem.ifPresent(item -> item.setQuantity(cartItem.getQuantity()));
    }

    @Override
    public void deleteCartItem(Long chatId, Integer cartItemId) {
        cartItems.computeIfAbsent(chatId, cartItems -> new ArrayList<>());
        cartItems.get(chatId).stream()
                .filter(cartItem -> cartItem.getId().equals(cartItemId))
                .findFirst().ifPresent(receivedCartItem -> cartItems.get(chatId).remove(receivedCartItem));
    }

    @Override
    public CartItem findCartItemByChatIdAndProductId(Long chatId, Integer productId) {
        cartItems.computeIfAbsent(chatId, cartItems -> new ArrayList<>());
        return cartItems.get(chatId).stream()
                .filter(cartItem -> cartItem.getProduct().getId().equals(productId))
                .findFirst().map(this::copy).orElse(null);
    }

    @Override
    public List<CartItem> findAllCartItemsByChatId(Long chatId) {
        cartItems.computeIfAbsent(chatId, cartItems -> new ArrayList<>());
        return cartItems.get(chatId).stream()
                .map(this::copy).collect(Collectors.toList());
    }

    @Override
    public void deleteAllCartItemsByChatId(Long chatId) {
        cartItems.remove(chatId);
    }

    @Override
    public void setPageNumber(Long chatId, Integer pageNumber) {
        cartPageNumbers.put(chatId, pageNumber);
    }

    @Override
    public Integer findPageNumberByChatId(Long chatId) {
        return cartPageNumbers.getOrDefault(chatId, 0);
    }

    private CartItem copy(CartItem cartItem) {
        if (cartItem == null) {
            return null;
        }
        CartItem copiedCartItem = new CartItem();
        copiedCartItem.setId(cartItem.getId());
        copiedCartItem.setProduct(cartItem.getProduct());
        copiedCartItem.setQuantity(cartItem.getQuantity());
        return copiedCartItem;
    }

}
