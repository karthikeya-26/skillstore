package org.karthik.skillstore.exceptions;

public class NotAuthorizedException extends RuntimeException {
    public NotAuthorizedException(String message) {
        super(message);
    }
    public NotAuthorizedException(String message, Throwable cause) {
        super(message, cause);
    }
    public NotAuthorizedException(Throwable cause) {
        super(cause);
    }
}
