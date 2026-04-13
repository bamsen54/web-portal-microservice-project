package com.simon.wigellpadel.exception;

public class TimeMachineDoesNotExistException extends RuntimeException {
    public TimeMachineDoesNotExistException(String message) {
        super(message);
    }
    public TimeMachineDoesNotExistException() {
        super("Time machines do not exist, yet");
    }
}
