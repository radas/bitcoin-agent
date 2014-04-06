package cz.kavan.radek.agent.bitcoin.errorhandling.resttemplate;

public class BitstampClientException extends RuntimeException {

    private static final long serialVersionUID = -1098750829283264830L;

    public BitstampClientException() {
    }

    public BitstampClientException(String message) {
        super(message);
    }

    public BitstampClientException(Throwable cause) {
        super(cause);
    }

    public BitstampClientException(String message, Throwable cause) {
        super(message, cause);
    }

    public BitstampClientException(String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
