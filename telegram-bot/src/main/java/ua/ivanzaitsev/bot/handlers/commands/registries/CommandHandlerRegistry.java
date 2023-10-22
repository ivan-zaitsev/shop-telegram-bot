package ua.ivanzaitsev.bot.handlers.commands.registries;

import java.util.List;

import ua.ivanzaitsev.bot.handlers.CommandHandler;
import ua.ivanzaitsev.bot.models.domain.Command;

public interface CommandHandlerRegistry {

    void setCommandHandlers(List<CommandHandler> commandHandlers);

    CommandHandler find(Command command);

}
