package com.apistd.uni;

public class UniException extends RuntimeException {
    public String code;
    public String message;
    public String requestId;

    /**
     * Create a new Uni Exception.
     *
     * @param message exception message
     */
    public UniException(final String message) {
        super(message);
        this.message = message;
    }

    /**
     * Create a new Uni Exception.
     *
     * @param message exception message
     * @param code exception code
     */
    public UniException(final String message, final String code) {
        super("[" + code + "] " + message);
        this.message = message;
        this.code = code;
    }

    /**
     * Create a new Uni Exception.
     *
     * @param message exception message
     * @param code exception code
     * @param requestId exception request ID
     */
    public UniException(final String message, final String code, final String requestId) {
        super("[" + code + "] " + message);
        this.message = message;
        this.code = code;
        this.requestId = requestId;
    }
}
