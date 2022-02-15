package ua.ivan909020.bot.commands;

public interface CommandSequence<T> extends Command<T> {

    void doPreviousCommand(T t);

    void doNextCommand(T t);

}
