package ua.nure.lisyak.SummaryTask4.db.holder;

import java.sql.Connection;

/**
 * Provides thread-safe implementation of {@link ua.nure.lisyak.SummaryTask4.db.holder.ConnectionHolder}
 * interface.
 * @see ThreadLocal
 */
public class ThreadLocalConnectionHolder implements ConnectionHolder {

    private final ThreadLocal<Connection> connectionHolder = new ThreadLocal<>();

    @Override
    public Connection get() {
        return connectionHolder.get();
    }

    @Override
    public void set(Connection connection) {
        connectionHolder.set(connection);
    }

    @Override
    public void remove() {
        connectionHolder.remove();
    }
}
