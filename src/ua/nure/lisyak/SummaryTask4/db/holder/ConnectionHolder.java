package ua.nure.lisyak.SummaryTask4.db.holder;

import java.sql.Connection;

/**
 * Holds a {@link Connection}
 */
public interface ConnectionHolder {

    /**
     * Get a connection.
     * @return connection
     */
    Connection get();

    /**
     * Sets a connection
     * @param connection connection to set
     */
    void set(Connection connection);

    /**
     * Removes a connection
     */
    void remove();

}
