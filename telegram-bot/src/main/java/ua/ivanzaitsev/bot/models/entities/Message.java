package ua.ivanzaitsev.bot.models.entities;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import ua.ivanzaitsev.bot.models.domain.MessagePlaceholder;

@Entity
@Table(name = "messages")
public class Message implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "messages_seq")
    @SequenceGenerator(name = "messages_seq", sequenceName = "messages_id_seq", allocationSize = 1)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(length = 4096, nullable = false)
    private String text;

    public Message() {
    }

    public void applyPlaceholder(MessagePlaceholder placeholder) {
        text = text.replace(placeholder.getPlaceholder(), placeholder.getReplacement().toString());
    }

    public void removeTextBetweenPlaceholder(String placeholderName) {
        text = text.replaceAll(placeholderName + "(?s).*" + placeholderName, "");
    }

    public void removeAllPlaceholders() {
        text = text.replaceAll("%.*%", "");
    }

    public String buildText() {
        removeAllPlaceholders();
        return text;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Message message = (Message) o;
        return Objects.equals(id, message.id) && 
                Objects.equals(name, message.name) && 
                Objects.equals(description, message.description) && 
                Objects.equals(text, message.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, text);
    }

    @Override
    public String toString() {
        return "Message [id=" + id + 
                ", name=" + name + 
                ", description=" + description + 
                ", text=" + text + "]";
    }

}
