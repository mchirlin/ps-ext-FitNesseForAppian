package com.appiancorp.ps.automatedtest.exception;

@SuppressWarnings("serial")
public class ObjectNotFoundTestException extends RuntimeException {

  public ObjectNotFoundTestException(String... vals) {
    super("message:<<Object not found: " + String.join(" - ", vals) + ">>");
  }
}
