package com.appiancorp.ps.automatedtest.exception;

@SuppressWarnings("serial")
public class StaleElementTestException extends RuntimeException {

  public StaleElementTestException(String... vals) {
    super("message:<<Element is no longer attached to the DOM: " + String.join(" - ", vals) + ">>");
  }
}
