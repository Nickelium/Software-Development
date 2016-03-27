package CustomExceptions;

/**
 * Created by Tom on 2/03/16.
 */
public class ReportErrorToUserException extends Exception {
    public ReportErrorToUserException() {
        super();
    }

    public ReportErrorToUserException(String message) {
        super(message);
    }

    public ReportErrorToUserException(String message, Throwable cause) {
        super(message, cause);
    }

    public ReportErrorToUserException(Throwable cause) {
        super(cause);
    }
}
