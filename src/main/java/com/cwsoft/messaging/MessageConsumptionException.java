package com.cwsoft.messaging;

public class MessageConsumptionException extends RuntimeException {
    public MessageConsumptionException(String message, Throwable cause) {
        super(message, cause);
    }
}
