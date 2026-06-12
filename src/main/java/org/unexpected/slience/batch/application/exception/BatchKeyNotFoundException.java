package org.unexpected.slience.batch.application.exception;

public class BatchKeyNotFoundException extends RuntimeException {

    public BatchKeyNotFoundException(Long batchId) {
        super("no batch found by id :: " + batchId);
    }

}
