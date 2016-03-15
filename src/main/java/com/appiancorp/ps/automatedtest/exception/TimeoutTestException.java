package com.appiancorp.ps.automatedtest.exception;

@SuppressWarnings("serial")
public class TimeoutTestException extends RuntimeException {

  public TimeoutTestException(String... vals) {
    super("message:<<Timeout period reached: " + String.join(" - ", vals) + ">>");
  }
}
