package ua.nure.lisyak.SummaryTask4.util;

import java.io.Serializable;

/**
 * A process process result. Useful for passing between requests.
 */
public final class ProcessResult implements Serializable {

    /**
     * Success of the the process
     */
    private boolean succeeded;

    /**
     * Result message
     */
    private String message;

    /**
     * Returns {@code true} if result is successful, {@code false} otherwise.
     *
     * @return {@code true} if result is successful, {@code false} otherwise
     */
    public boolean isSucceeded() {
        return succeeded;
    }

    /**
     * Set result success
     *
     * @param succeeded {@code true} if result is successful, {@code false} otherwise
     */
    public void setSucceeded(boolean succeeded) {
        this.succeeded = succeeded;
    }

    /**
     * Gets result message.
     *
     * @return result message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets result message.
     *
     * @param message message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }

}
