package cz.kavan.radek.agent.bitcoin.errorhandling.exception;

public class AgentDataAccessException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public AgentDataAccessException(Throwable cause) {
        super(cause);
    }

}
