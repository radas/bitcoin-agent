package cz.kavan.radek.agent.bitcoin.errorhandling.exception;

public class AuthRequestException extends RuntimeException {

    private static final long serialVersionUID = -7605921959568123978L;

    public AuthRequestException() {
        super();
    }

    public AuthRequestException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public AuthRequestException(String message, Throwable cause) {
        super(message, cause);
    }

    public AuthRequestException(String message) {
        super(message);
    }

    public AuthRequestException(Throwable cause) {
        super(cause);
    }

}
