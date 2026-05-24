package org.unexpected.slience.sync.application.exception;

public class SyncFailedException extends RuntimeException {
    public SyncFailedException(String message) {
        super(message);
    }
}
