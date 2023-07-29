package ua.ivan909020.bot.models.domain;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

import ua.ivan909020.bot.models.entities.Product;

public class CartItem implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Integer id;
    private Product product;
    private int quantity;

    public CartItem() {
    }

    public CartItem(Product product, Integer quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Long getTotalPrice() {
        return quantity * product.getPrice();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CartItem cartItem = (CartItem) o;
        return Objects.equals(id, cartItem.id) && 
                Objects.equals(product, cartItem.product) && 
                Objects.equals(quantity, cartItem.quantity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, product, quantity);
    }

    @Override
    public String toString() {
        return "CartItem [id=" + id +
                ", product=" + product +
                ", quantity=" + quantity + "]";
    }

}
