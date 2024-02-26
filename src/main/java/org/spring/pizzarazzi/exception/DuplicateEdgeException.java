package org.spring.pizzarazzi.exception;

public class DuplicateEdgeException extends RuntimeException {
    public DuplicateEdgeException() {
        super();
    }

    public DuplicateEdgeException(String message, Throwable cause) {
        super(message, cause);
    }

    public DuplicateEdgeException(String message) {
        super(message);
    }

    public DuplicateEdgeException(Throwable cause) {
        super(cause);
    }
}
