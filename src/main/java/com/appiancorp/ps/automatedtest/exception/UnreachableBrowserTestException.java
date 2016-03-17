package com.appiancorp.ps.automatedtest.exception;

@SuppressWarnings("serial")
public class UnreachableBrowserTestException extends RuntimeException {

  public UnreachableBrowserTestException(String... vals) {
    super("message:<<Browser is unreachable, please restart test>>");
  }
}
