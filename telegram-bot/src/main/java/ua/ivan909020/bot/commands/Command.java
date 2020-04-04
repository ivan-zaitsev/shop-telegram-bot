package ua.ivan909020.bot.commands;

public interface Command<T> {

    void execute(T t);

}
