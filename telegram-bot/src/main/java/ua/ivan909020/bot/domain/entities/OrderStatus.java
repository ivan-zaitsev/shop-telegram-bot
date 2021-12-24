package ua.ivan909020.bot.domain.entities;

import java.io.Serializable;

public enum OrderStatus implements Serializable {

    WAITING("Waiting"),
    PROCESSED("Processed"),
    COMPLETED("Completed"),
    CANCELED("Canceled");

    private final String value;

    OrderStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
