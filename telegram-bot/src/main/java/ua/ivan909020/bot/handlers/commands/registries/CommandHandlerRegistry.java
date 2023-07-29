package ua.ivan909020.bot.handlers.commands.registries;

import java.util.List;

import ua.ivan909020.bot.handlers.CommandHandler;
import ua.ivan909020.bot.models.domain.Command;

public interface CommandHandlerRegistry {

    void setCommandHandlers(List<CommandHandler> commandHandlers);

    CommandHandler find(Command command);

}
