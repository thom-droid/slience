package org.unexpected.slience.batch.application.exception;

public class BatchFailedException extends RuntimeException {
    public BatchFailedException(String message) {
        super(message);
    }
}
