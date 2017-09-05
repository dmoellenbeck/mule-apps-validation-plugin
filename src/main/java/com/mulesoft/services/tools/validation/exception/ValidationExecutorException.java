package com.mulesoft.services.tools.validation.exception;

/**
 * Custom exception to manage Validation Executor errors.
 *
 */
public class ValidationExecutorException extends Exception {

    private static final long serialVersionUID = 6255669670745578121L;

    /**
     * @param message
     */
    public ValidationExecutorException(String message) {
        super(message);
    }

}
