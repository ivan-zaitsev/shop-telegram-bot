package ua.ivan909020.bot.models.entities;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "clients")
public class Client implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "clients_seq")
    @SequenceGenerator(name = "clients_seq", sequenceName = "clients_id_seq", allocationSize = 1)
    private Integer id;

    @Column(name = "chat_id", unique = true, nullable = false)
    private Long chatId;

    @Column
    private String name;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column
    private String city;

    @Column
    private String address;

    @Column(name = "is_active", nullable = false)
    private boolean active;

    public Client() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Client client = (Client) o;
        return active == client.active && 
                Objects.equals(id, client.id) && 
                Objects.equals(chatId, client.chatId) && 
                Objects.equals(name, client.name) && 
                Objects.equals(phoneNumber, client.phoneNumber) && 
                Objects.equals(city, client.city) && 
                Objects.equals(address, client.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, chatId, name, phoneNumber, city, address, active);
    }

    @Override
    public String toString() {
        return "Client [id=" + id + 
                ", chatId=" + chatId + 
                ", name=" + name + 
                ", phoneNumber=" + phoneNumber + 
                ", city=" + city + 
                ", address=" + address + 
                ", active=" + active + "]";
    }

}
