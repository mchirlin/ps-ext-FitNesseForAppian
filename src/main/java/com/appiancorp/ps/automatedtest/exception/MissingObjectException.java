package com.appiancorp.ps.automatedtest.exception;

@SuppressWarnings("serial")
public class MissingObjectException extends RuntimeException {
    
    public MissingObjectException(String...vals) {
        super("message:<<Missing Object: " + String.join("- ", vals) + ">>");
    }
}
