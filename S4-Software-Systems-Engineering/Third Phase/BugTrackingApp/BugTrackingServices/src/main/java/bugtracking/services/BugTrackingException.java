package bugtracking.services;

public class BugTrackingException extends Exception {
    public BugTrackingException() {
    }

    public BugTrackingException(String message) {
        super(message);
    }

    public BugTrackingException(String message, Throwable cause) {
        super(message, cause);
    }
}
