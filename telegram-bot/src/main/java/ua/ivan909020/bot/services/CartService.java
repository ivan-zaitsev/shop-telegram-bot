package ua.ivan909020.bot.services;

import java.util.List;

import ua.ivan909020.bot.models.domain.CartItem;

public interface CartService {

    void saveCartItem(Long chatId, CartItem cartItem);

    void updateCartItem(Long chatId, CartItem cartItem);

    void deleteCartItem(Long chatId, Integer cartItemId);

    CartItem findCartItemByChatIdAndProductId(Long chatId, Integer productId);

    List<CartItem> findAllCartItemsByChatId(Long chatId);

    void deleteAllCartItemsByChatId(Long chatId);

    void updatePageNumberByChatId(Long chatId, Integer pageNumber);

    Integer findPageNumberByChatId(Long chatId);

    long calculateTotalPrice(List<CartItem> cartItems);

}
