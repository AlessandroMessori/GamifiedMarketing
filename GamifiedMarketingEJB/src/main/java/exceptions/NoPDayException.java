package exceptions;

public class NoPDayException extends Exception {
    private static final long serialVersionUID = 1L;

    public NoPDayException(String message) {
        super(message);
    }
}
