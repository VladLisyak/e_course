package ua.nure.lisyak.SummaryTask4.exception;

public class DataAccessException extends RuntimeException {
    public DataAccessException(String description, Throwable e) {
        super(description, e);
    }
}
