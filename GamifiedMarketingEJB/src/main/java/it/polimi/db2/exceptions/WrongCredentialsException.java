package it.polimi.db2.exceptions;

public class WrongCredentialsException extends Exception {
    private static final long serialVersionUID = 1L;

    public WrongCredentialsException(String message) {
        super(message);
    }
}
