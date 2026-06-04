package org.unexpected.slience.batch.application.exception;

public class BatchServiceNotFound extends RuntimeException {
    public BatchServiceNotFound(String message) {
        super(message);
    }
}
