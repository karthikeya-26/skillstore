package org.karthik.skillstore.exceptions;

public class InternalServerErrorExcetion extends RuntimeException {
    public InternalServerErrorExcetion(String message) {
        super(message);
    }

    public InternalServerErrorExcetion(String message, Throwable cause) {
        super(message, cause);
    }

    public InternalServerErrorExcetion(Throwable cause) {
        super(cause);
    }
}
