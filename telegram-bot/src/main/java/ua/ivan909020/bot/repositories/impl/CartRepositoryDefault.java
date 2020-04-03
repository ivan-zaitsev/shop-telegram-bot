package ua.ivan909020.bot.repositories.impl;

import ua.ivan909020.bot.domain.models.CartItem;
import ua.ivan909020.bot.repositories.CartRepository;
import ua.ivan909020.bot.utils.ClonerUtils;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class CartRepositoryDefault implements CartRepository {

    private AtomicInteger lastCartItemId = new AtomicInteger();
    private Map<Long, List<CartItem>> cartItems = new HashMap<>();

    private Map<Long, Integer> cartPageNumbers = new HashMap<>();

    @Override
    public void saveCartItem(Long chatId, CartItem cartItem) {
        cartItems.computeIfAbsent(chatId, orderItems -> new ArrayList<>());
        cartItem.setId(lastCartItemId.incrementAndGet());
        cartItems.get(chatId).add(ClonerUtils.cloneObject(cartItem));
    }

    @Override
    public void updateCartItem(Long chatId, CartItem cartItem) {
        cartItems.computeIfAbsent(chatId, cartItems -> new ArrayList<>());
        List<CartItem> receivedCartItems = cartItems.get(chatId);
        IntStream.range(0, receivedCartItems.size())
                .filter(i -> cartItem.getId().equals(receivedCartItems.get(i).getId()))
                .findFirst()
                .ifPresent(i -> receivedCartItems.set(i, ClonerUtils.cloneObject(cartItem)));
    }

    @Override
    public void deleteCartItem(Long chatId, Integer cartItemId) {
        cartItems.computeIfAbsent(chatId, cartItems -> new ArrayList<>());
        List<CartItem> receivedCartItems = cartItems.get(chatId);
        receivedCartItems.stream()
                .filter(cartItem -> cartItem.getId().equals(cartItemId))
                .findFirst()
                .ifPresent(receivedCartItems::remove);
    }

    @Override
    public CartItem findCartItemByChatIdAndProductId(Long chatId, Integer productId) {
        cartItems.computeIfAbsent(chatId, cartItems -> new ArrayList<>());
        return cartItems.get(chatId).stream()
                .filter(cartItem -> cartItem.getProduct().getId().equals(productId))
                .findFirst()
                .map(ClonerUtils::cloneObject)
                .orElse(null);
    }

    @Override
    public List<CartItem> findAllCartItemsByChatId(Long chatId) {
        cartItems.computeIfAbsent(chatId, cartItems -> new ArrayList<>());
        return cartItems.get(chatId).stream()
                .map(ClonerUtils::cloneObject)
                .collect(Collectors.toList());
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

}
