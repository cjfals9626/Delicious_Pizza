package org.spring.pizzarazzi.exception;

public class DuplicateDoughException extends RuntimeException {
    public DuplicateDoughException() {
        super();
    }

    public DuplicateDoughException(String message, Throwable cause) {
        super(message, cause);
    }

    public DuplicateDoughException(String message) {
        super(message);
    }

    public DuplicateDoughException(Throwable cause) {
        super(cause);
    }
}
