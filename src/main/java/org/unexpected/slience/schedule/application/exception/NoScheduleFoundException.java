package org.unexpected.slience.schedule.application.exception;

public class NoScheduleFoundException extends RuntimeException {
    public NoScheduleFoundException(String message) {
        super(message);
    }
    public NoScheduleFoundException(Long id) {
        super("No schedule found for id: " + id);
    }
}
