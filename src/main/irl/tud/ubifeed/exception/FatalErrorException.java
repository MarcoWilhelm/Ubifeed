package irl.tud.ubifeed.exception;

/**
 * FatalErrorException constructs a new FatalErrorException with or without message, cause,
 * enableSuppression and writableStackTrace.
 * 
 * @author Pierre, Raphaël
 *
 */
public class FatalErrorException extends RuntimeException {
  private static final long serialVersionUID = 1L;


  /**
   * Constructs a new FatalErrorException with null as its detail message. The cause is not
   * initialized, and may subsequently be initialized by a call to
   * Throwable.initCause(java.lang.Throwable).
   */
  public FatalErrorException() {

  }

  /**
   * Constructs a new FatalErrorException with the specified detail message.
   * 
   * @param message Message of the exception
   */
  public FatalErrorException(String message) {
    super(message);

  }

  /**
   * Constructs a new FatalErrorException with the specified cause.
   * 
   * @param cause Cause thrown by exception
   */
  public FatalErrorException(Throwable cause) {
    super(cause);
  }

  /**
   * Constructs a new FatalErrorException with the specified detail message and cause.
   * 
   * @param message Message of exception
   * @param cause Cause Thrown by exception
   */
  public FatalErrorException(String message, Throwable cause) {
    super(message, cause);
  }


  /**
   * Class Constructor. Constructs a new FatalErrorException with the specified detail message,
   * cause, suppression enabled or disabled, and writable stack trace enabled or disabled.
   * 
   * @param message Message of exception
   * @param cause Cause Thrown by exception
   * @param enableSuppression Whether or not suppression is enabled or disabled
   * @param writableStackTrace Whether or not the stack trace should be writable
   */
  public FatalErrorException(String message, Throwable cause, boolean enableSuppression,
      boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }



}
