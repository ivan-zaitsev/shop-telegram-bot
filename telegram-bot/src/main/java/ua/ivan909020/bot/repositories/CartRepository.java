package ua.ivan909020.bot.repositories;

import ua.ivan909020.bot.domain.models.CartItem;

import java.util.List;

public interface CartRepository {

    void saveCartItem(Long chatId, CartItem cartItem);

    void updateCartItem(Long chatId, CartItem cartItem);

    void deleteCartItem(Long chatId, Integer cartItemId);

    CartItem findCartItemByChatIdAndProductId(Long chatId, Integer productId);

    List<CartItem> findAllCartItemsByChatId(Long chatId);

    void deleteAllCartItemsByChatId(Long chatId);

    void setPageNumber(Long chatId, Integer pageNumber);

    Integer findPageNumberByChatId(Long chatId);

}
