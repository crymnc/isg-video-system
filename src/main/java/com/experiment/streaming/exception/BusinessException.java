package com.experiment.streaming.exception;

public final class BusinessException extends RuntimeException {

    private static final long serialVersionUID = 51514312562L;

    public BusinessException() {
        super();
    }

    public BusinessException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public BusinessException(final String message) {
        super(message);
    }

    public BusinessException(final Throwable cause) {
        super(cause);
    }

}
