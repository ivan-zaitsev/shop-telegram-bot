package ua.ivan909020.bot.models.domain;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class ClientOrder implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private List<CartItem> cartItems;
    private String clientName;
    private String phoneNumber;
    private String city;
    private String address;

    public long calculateTotalPrice() {
        long totalPrice = 0;
        for (CartItem cartItem : cartItems) {
            totalPrice += cartItem.getTotalPrice();
        }
        return totalPrice;
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public int hashCode() {
        return Objects.hash(cartItems, clientName, phoneNumber, city, address);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        ClientOrder other = (ClientOrder) obj;
        return Objects.equals(cartItems, other.cartItems) &&
                Objects.equals(clientName, other.clientName) &&
                Objects.equals(phoneNumber, other.phoneNumber) &&
                Objects.equals(city, other.city) &&
                Objects.equals(address, other.address);
    }

    @Override
    public String toString() {
        return "ClientOrder [cartItems=" + cartItems +
                ", clientName=" + clientName +
                ", phoneNumber=" + phoneNumber +
                ", city=" + city +
                ", address=" + address + "]";
    }

}
