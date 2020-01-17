package ua.ivan909020.bot.domain.models;

import java.time.LocalDateTime;
import java.util.Objects;


public class ClientAction {

    private final String action;
    private final LocalDateTime createdTime;

    public ClientAction(String action, LocalDateTime createdTime) {
        this.action = action;
        this.createdTime = createdTime;
    }

    public String getAction() {
        return action;
    }

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClientAction that = (ClientAction) o;
        return Objects.equals(action, that.action) &&
                Objects.equals(createdTime, that.createdTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(action, createdTime);
    }

    @Override
    public String toString() {
        return "ClientAction{" +
                "action='" + action + '\'' +
                ", createdTime=" + createdTime +
                '}';
    }

}
