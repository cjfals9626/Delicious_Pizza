package org.spring.pizzarazzi.exception;

public class DuplicateToppingException extends RuntimeException {
    public DuplicateToppingException() {
        super();
    }

    public DuplicateToppingException(String message, Throwable cause) {
        super(message, cause);
    }

    public DuplicateToppingException(String message) {
        super(message);
    }

    public DuplicateToppingException(Throwable cause) {
        super(cause);
    }
}
