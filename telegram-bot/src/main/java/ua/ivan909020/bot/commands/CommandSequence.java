package ua.ivan909020.bot.commands;

public interface CommandSequence<T> extends Command<T> {

    void executePrevious(T t);

    void executeNext(T t);

}
