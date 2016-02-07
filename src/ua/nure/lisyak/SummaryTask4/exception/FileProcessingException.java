package ua.nure.lisyak.SummaryTask4.exception;

public class FileProcessingException extends RuntimeException {

    public FileProcessingException(String description, Throwable e) {
        super(description, e);
    }

    public FileProcessingException(String s) {
        super(s);
    }

    public FileProcessingException(Throwable e) {
        super(e);
    }
}
