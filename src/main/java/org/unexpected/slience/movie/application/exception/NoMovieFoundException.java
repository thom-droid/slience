package org.unexpected.slience.movie.application.exception;

public class NoMovieFoundException extends RuntimeException {
    public NoMovieFoundException(String message) {
        super(message);
    }
    public NoMovieFoundException(Long id) {
        super("No movie found with id: " + id);
    }
}
