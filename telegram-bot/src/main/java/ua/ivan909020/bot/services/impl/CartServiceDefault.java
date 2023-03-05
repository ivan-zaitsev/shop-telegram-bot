package ua.ivan909020.bot.services.impl;

import java.util.List;

import ua.ivan909020.bot.exceptions.ValidationException;
import ua.ivan909020.bot.models.domain.CartItem;
import ua.ivan909020.bot.repositories.CartRepository;
import ua.ivan909020.bot.repositories.memory.CartRepositoryDefault;
import ua.ivan909020.bot.services.CartService;

public class CartServiceDefault implements CartService {

    private static final CartService INSTANCE = new CartServiceDefault();

    private final CartRepository repository = new CartRepositoryDefault();

    private CartServiceDefault() {
    }

    public static CartService getInstance() {
        return INSTANCE;
    }

    @Override
    public void saveCartItem(Long chatId, CartItem cartItem) {
        if (chatId == null) {
            throw new IllegalArgumentException("ChatId should not be NULL");
        }
        if (cartItem == null) {
            throw new IllegalArgumentException("CartItem should not be NULL");
        }
        if (cartItem.getProduct() == null) {
            throw new ValidationException("Product of CartItem should not be NULL");
        }

        repository.saveCartItem(chatId, cartItem);
    }

    @Override
    public void updateCartItem(Long chatId, CartItem cartItem) {
        if (chatId == null) {
            throw new IllegalArgumentException("ChatId should not be NULL");
        }
        if (cartItem == null) {
            throw new IllegalArgumentException("CartItem should not be NULL");
        }
        if (cartItem.getProduct() == null) {
            throw new ValidationException("Product of CartItem should not be NULL");
        }

        repository.updateCartItem(chatId, cartItem);
    }

    @Override
    public void deleteCartItem(Long chatId, Integer cartItemId) {
        if (chatId == null) {
            throw new IllegalArgumentException("ChatId should not be NULL");
        }
        if (cartItemId == null) {
            throw new IllegalArgumentException("CartItemId should not be NULL");
        }

        repository.deleteCartItem(chatId, cartItemId);
    }

    @Override
    public CartItem findCartItemByChatIdAndProductId(Long chatId, Integer productId) {
        if (chatId == null) {
            throw new IllegalArgumentException("ChatId should not be NULL");
        }
        if (productId == null) {
            throw new IllegalArgumentException("ProductId should not be NULL");
        }

        return repository.findCartItemByChatIdAndProductId(chatId, productId);
    }

    @Override
    public List<CartItem> findAllCartItemsByChatId(Long chatId) {
        if (chatId == null) {
            throw new IllegalArgumentException("ChatId should not be NULL");
        }

        return repository.findAllCartItemsByChatId(chatId);
    }

    @Override
    public void deleteAllCartItemsByChatId(Long chatId) {
        if (chatId == null) {
            throw new IllegalArgumentException("ChatId should not be NULL");
        }

        repository.deleteAllCartItemsByChatId(chatId);
    }

    @Override
    public Integer findPageNumberByChatId(Long chatId) {
        if (chatId == null) {
            throw new IllegalArgumentException("ChatId should not be NULL");
        }

        return repository.findPageNumberByChatId(chatId);
    }

    @Override
    public void updatePageNumberByChatId(Long chatId, Integer pageNumber) {
        if (chatId == null) {
            throw new IllegalArgumentException("ChatId should not be NULL");
        }
        if (pageNumber == null) {
            throw new IllegalArgumentException("PageNumber should not be NULL");
        }

        repository.updatePageNumberByChatId(chatId, pageNumber);
    }

    @Override
    public long calculateTotalPrice(List<CartItem> cartItems) {
        if (cartItems == null) {
            throw new IllegalArgumentException("CartItems should not be NULL");
        }

        long totalPrice = 0;
        for (CartItem cartItem : cartItems) {
            totalPrice += cartItem.getTotalPrice();
        }

        return totalPrice;
    }

}
