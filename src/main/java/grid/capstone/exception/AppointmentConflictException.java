package grid.capstone.exception;

/**
 * @author Javaughn Stephenson
 * @since 11/07/2023
 */

public class AppointmentConflictException extends RuntimeException{

    public AppointmentConflictException() {
        super();
    }

    public AppointmentConflictException(String message) {
        super(message);
    }

    public AppointmentConflictException(String message, Throwable cause) {
        super(message, cause);
    }

    public AppointmentConflictException(Throwable cause) {
        super(cause);
    }

    protected AppointmentConflictException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
