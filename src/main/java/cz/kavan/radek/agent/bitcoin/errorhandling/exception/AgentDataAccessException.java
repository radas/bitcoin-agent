package cz.kavan.radek.agent.bitcoin.errorhandling.exception;

public class AgentDataAccessException extends RuntimeException {

    private static final long serialVersionUID = 964427152103323999L;

    public AgentDataAccessException() {
        super();
    }

    public AgentDataAccessException(String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public AgentDataAccessException(String message, Throwable cause) {
        super(message, cause);
    }

    public AgentDataAccessException(String message) {
        super(message);
    }

    public AgentDataAccessException(Throwable cause) {
        super(cause);
    }

}
