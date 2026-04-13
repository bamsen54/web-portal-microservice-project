package com.simon.wigellpadel.exception;

public class BookingDoesNotExistException extends RuntimeException {
    public BookingDoesNotExistException(String message) {
        super(message);
    }
    public BookingDoesNotExistException(Long id) {
        super("Booking with id: " + id + " does not exist");
    }
}
