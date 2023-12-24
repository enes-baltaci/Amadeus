package com.example.amadeus.exception;

public class AirportNotFoundException extends RuntimeException {
    public AirportNotFoundException(Long id) {
        super("Airport with ID " + id + " not found.");
    }
}
