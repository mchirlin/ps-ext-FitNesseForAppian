package com.appiancorp.ps.automatedtest.exception;

@SuppressWarnings("serial")
public class WaitForWorkingTestException extends RuntimeException {

  public WaitForWorkingTestException(RuntimeException e) {
    super(e);
  }

  public WaitForWorkingTestException(String... vals) {
    super("message:<<Working... remained for longer than timeout period, investigate performance and test timeout parameter.>>");
  }
}
