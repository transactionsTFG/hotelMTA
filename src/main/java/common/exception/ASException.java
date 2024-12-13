package common.exception;

import jakarta.ejb.ApplicationException;

@ApplicationException(rollback = true)
public class ASException extends Exception {
    private static final long serialVersionUID = 5067456836242850281L;

    public ASException(String message) {
        super(message);
    }
}