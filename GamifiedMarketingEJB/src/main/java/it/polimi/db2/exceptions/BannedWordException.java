package it.polimi.db2.exceptions;

public class BannedWordException extends Exception {
    private static final long serialVersionUID = 1L;

    public BannedWordException(String message) {
        super(message);
    }
}
