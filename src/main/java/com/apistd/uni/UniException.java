package com.apistd.uni;

public class UniException extends RuntimeException {
    public String code;
    public String message;
    public String requestId;

    public UniException(final String message) {
        super(message);
        this.message = message;
    }

    public UniException(final String message, final String code) {
        super("[" + code + "] " + message);
        this.message = message;
        this.code = code;
    }

    public UniException(final String message, final String code, final String requestId) {
        super("[" + code + "] " + message);
        this.message = message;
        this.code = code;
        this.requestId = requestId;
    }
}
