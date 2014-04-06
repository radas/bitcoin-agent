package cz.kavan.radek.agent.bitcoin.errorhandling.exception;

public class BitcoinAgentException extends RuntimeException {

    private static final long serialVersionUID = 8547335086842826516L;

    public BitcoinAgentException() {
    }

    public BitcoinAgentException(String message) {
        super(message);
    }

    public BitcoinAgentException(Throwable cause) {
        super(cause);
    }

    public BitcoinAgentException(String message, Throwable cause) {
        super(message, cause);
    }

    public BitcoinAgentException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
