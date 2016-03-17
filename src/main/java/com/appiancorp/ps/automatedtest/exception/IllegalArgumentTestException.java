package com.appiancorp.ps.automatedtest.exception;

@SuppressWarnings("serial")
public class IllegalArgumentTestException extends RuntimeException {

  public IllegalArgumentTestException(String... vals) {
    super("message:<<Invalid argument: " + String.join(" - ", vals) + ">>");
  }
}
