package org.picon.exception;

public class BusinessException extends RuntimeException {
    public BusinessException() {
        super();
    }

    public BusinessException(String s) {
        super(s);
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }

    public BusinessException(Throwable cause) {
        super(cause);
    }
}
