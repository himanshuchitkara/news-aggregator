package org.sapient.newsaggregator.exception;

public class NewsAggregatorException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public NewsAggregatorException(String message) {
        super(message);
    }

    public NewsAggregatorException(Throwable cause) {
        super(cause);
    }

    public NewsAggregatorException(String message, Throwable cause) {
        super(message, cause);
    }
}
