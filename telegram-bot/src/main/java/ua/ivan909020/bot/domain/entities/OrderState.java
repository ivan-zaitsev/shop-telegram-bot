package ua.ivan909020.bot.domain.entities;

public enum OrderState {

    WAITING("Waiting"),
    PROCESSED("Processed"),
    COMPLETED("Completed");

    private String value;

    OrderState(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
