package ua.ivan909020.bot.repositories.hibernate;

import org.hibernate.Session;

import java.util.function.Consumer;

@FunctionalInterface
public interface TransactionVoidFunction extends Consumer<Session> {

}
