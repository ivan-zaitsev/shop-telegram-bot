package ua.ivanzaitsev.bot.models.domain;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

public class ClientAction implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private final Command command;
    private final String action;
    private final LocalDateTime createdTime = LocalDateTime.now();

    public ClientAction(Command command, String action) {
        this.command = command;
        this.action = action;
    }

    public Command getCommand() {
        return command;
    }

    public String getAction() {
        return action;
    }

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ClientAction that = (ClientAction) o;
        return Objects.equals(command, that.command) &&
                Objects.equals(action, that.action) &&
                Objects.equals(createdTime, that.createdTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(command, action, createdTime);
    }

    @Override
    public String toString() {
        return "ClientAction [command=" + command +
                ", action=" + action +
                ", createdTime=" + createdTime + "]";
    }

}
