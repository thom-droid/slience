package org.unexpected.slience.reservation.application.exception;

public class UpdateFailureException extends RuntimeException {
    public UpdateFailureException(String message) {
        super(message);
    }

    public UpdateFailureException(String message, Throwable cause) {
        super(message, cause);
    }

    public UpdateFailureException(int expected, int actual) {
        super("expected " + expected + ", actual " + actual);
    }
}
