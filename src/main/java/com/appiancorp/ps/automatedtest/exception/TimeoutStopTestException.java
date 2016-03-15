package com.appiancorp.ps.automatedtest.exception;

@SuppressWarnings("serial")
public class TimeoutStopTestException extends ObjectNotFoundTestException {

  public TimeoutStopTestException(String... vals) {
    super(vals);
  }
}
