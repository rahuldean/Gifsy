package com.godhc.gifsy.models;

public class ApplicationError {
    String errorId;
    String message;
    String internalMessage;

    public ApplicationError(String message) {
        this.message = message;
        this.errorId = java.util.UUID.randomUUID().toString();
    }

    public ApplicationError(String message, String internalMessage) {
        this.message = message;
        this.internalMessage = internalMessage;
        this.errorId = java.util.UUID.randomUUID().toString();
    }

    public String getErrorId() {
        return errorId;
    }

    public String getMessage() {
        return message;
    }

    public String getInternalMessage() {
        return internalMessage;
    }
}
