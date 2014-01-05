package repository.architecture;

/**
 * Exceptions thrown by the {@link ReflectionHelper} helper class
 *
 * @author stibe
 * @since 1.0.0
 */
public class ReflectionHelperException extends Exception {

    public ReflectionHelperException() {
    }

    public ReflectionHelperException(String message) {
        super(message);
    }

    public ReflectionHelperException(String message, Throwable cause) {
        super(message, cause);
    }

    public ReflectionHelperException(Throwable cause) {
        super(cause);
    }

    public ReflectionHelperException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
