package com.appiancorp.ps.automatedtest.exception;

@SuppressWarnings("serial")
public class StopTestException extends RuntimeException {

    public StopTestException(String...vals) {
        super("message:<<Fatal error: " + String.join("- ", vals) + ">>");
    }
}
