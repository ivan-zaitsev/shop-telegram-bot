package ua.ivan909020.bot.repositories.hibernate;

import java.util.function.Consumer;

import org.hibernate.Session;

@FunctionalInterface
public interface TransactionVoidFunction extends Consumer<Session> {

}
