package br.com.william.handlers;

import java.io.Serializable;

public class NotFoundException extends RuntimeException implements Serializable {
    private static final long serialVersionUID = 1L;

    public NotFoundException() {
    }

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
