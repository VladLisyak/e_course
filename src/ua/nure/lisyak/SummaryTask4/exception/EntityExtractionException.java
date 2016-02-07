package ua.nure.lisyak.SummaryTask4.exception;

public class EntityExtractionException extends RuntimeException {
    public EntityExtractionException(String description, Throwable e) {
        super(description, e);
    }
}
