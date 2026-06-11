package org.unexpected.slience.user.application.exception;

public class NoUserFoundException extends RuntimeException {
    public NoUserFoundException(String message) {
        super(message);
    }
    public NoUserFoundException(Long id) {
        super("No user found for id: " + id);
    }
}
