package ua.ivan909020.bot.repositories.hibernate;

import org.hibernate.Session;

import java.util.function.Function;

@FunctionalInterface
public interface TransactionFunction<T> extends Function<Session, T> {

}
