package com.appiancorp.ps.automatedtest.exception;

@SuppressWarnings("serial")
public class GenericTestException extends RuntimeException {

  public GenericTestException(String... vals) {
    super("message:<<An error has occurred: " + String.join(" - ", vals) + ">>");
  }
}
