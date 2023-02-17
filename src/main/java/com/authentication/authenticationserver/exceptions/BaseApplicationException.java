package com.authentication.authenticationserver.exceptions;


import com.authentication.authenticationserver.enums.ExceptionCode;

/**
 * The type Base application exception.
 */
@SuppressWarnings("serial")
public class BaseApplicationException extends RuntimeException {
  /**
   * The Detailed message.
   */
  private final String detailedMessage;
  /**
   * The Error code.
   */
  private ExceptionCode errorCode;

  /**
   * Instantiates a new Base application exception.
   *
   * @param message the message
   */
  public BaseApplicationException(final String message) {
    super(message);
    this.detailedMessage = message;
  }

  /**
   * Instantiates a new Base application exception.
   *
   * @param message the message
   * @param code    the code
   */
  public BaseApplicationException(final String message, final ExceptionCode code) {
    super(message);
    this.detailedMessage = message;
    this.errorCode = code;
  }

  /**
   * Gets detailed message.
   *
   * @return the detailed message
   */
  public String getDetailedMessage() {
    return detailedMessage;
  }

  /**
   * Gets error code.
   *
   * @return the error code
   */
  public ExceptionCode getErrorCode() {
    return errorCode;
  }
}
