package ua.ivan909020.bot.repositories.hibernate;

import java.util.function.Function;

import org.hibernate.Session;

@FunctionalInterface
public interface TransactionFunction<T> extends Function<Session, T> {

}
