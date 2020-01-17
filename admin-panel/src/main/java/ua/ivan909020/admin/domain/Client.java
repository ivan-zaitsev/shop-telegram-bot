package ua.ivan909020.admin.domain;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "clients")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "clients_seq")
    @SequenceGenerator(name = "clients_seq", sequenceName = "clients_id_seq", allocationSize = 1)
    private Integer id;

    @Column(name = "chat_id", unique = true, nullable = false)
    private Long chatId;

    @Column
    @Length(max = 255, message = "Name too long (more than 255 characters)")
    private String name;

    @Column(name = "phone_number")
    @Length(max = 255, message = "Phone number too long (more than 255 characters)")
    private String phoneNumber;

    @Column
    @Length(max = 255, message = "City too long (more than 255 characters)")
    private String city;

    @Column
    @Length(max = 255, message = "Address to long (more than 255 characters)")
    private String address;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return Objects.equals(id, client.id) &&
                Objects.equals(chatId, client.chatId) &&
                Objects.equals(name, client.name) &&
                Objects.equals(phoneNumber, client.phoneNumber) &&
                Objects.equals(city, client.city) &&
                Objects.equals(address, client.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, chatId, name, phoneNumber, city, address);
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", chatId=" + chatId +
                ", name='" + name + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", city='" + city + '\'' +
                ", address='" + address + '\'' +
                '}';
    }

}
