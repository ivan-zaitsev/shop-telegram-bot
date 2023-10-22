package ua.ivanzaitsev.bot.repositories;

import java.util.List;

import ua.ivanzaitsev.bot.models.domain.CartItem;

public interface CartRepository {

    void saveCartItem(Long chatId, CartItem cartItem);

    void updateCartItem(Long chatId, CartItem cartItem);

    void deleteCartItem(Long chatId, Integer cartItemId);

    CartItem findCartItemByChatIdAndProductId(Long chatId, Integer productId);

    List<CartItem> findAllCartItemsByChatId(Long chatId);

    void deleteAllCartItemsByChatId(Long chatId);

    void updatePageNumberByChatId(Long chatId, Integer pageNumber);

    Integer findPageNumberByChatId(Long chatId);

}
