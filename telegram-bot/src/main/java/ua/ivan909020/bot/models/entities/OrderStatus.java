package ua.ivan909020.bot.models.entities;

import java.io.Serial;
import java.io.Serializable;

public enum OrderStatus implements Serializable {

    WAITING("Waiting"),
    PROCESSED("Processed"),
    COMPLETED("Completed"),
    CANCELED("Canceled");
    
    @Serial
    private static final long serialVersionUID = 1L;

    private final String value;

    OrderStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
