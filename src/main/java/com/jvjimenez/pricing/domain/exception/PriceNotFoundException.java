package com.jvjimenez.pricing.domain.exception;

public class PriceNotFoundException extends RuntimeException {

    public PriceNotFoundException(String message) {
        super("Price not found for query: " + message);
    }
}