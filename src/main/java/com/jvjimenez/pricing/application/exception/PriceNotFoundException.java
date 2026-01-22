package com.jvjimenez.pricing.application.exception;

public class PriceNotFoundException extends RuntimeException {

    public PriceNotFoundException(String message) {
        super("Price not found for query: " + message);
    }
}
