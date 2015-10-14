package com.appiancorp.ps.automatedtest.fixture;

import fitlibrary.exception.FitLibraryException;

@SuppressWarnings("serial")
public class MissingObjectException extends FitLibraryException {

    public MissingObjectException(String objectType, String objectName) {
        super("Unable to locate " + objectType + ": " + objectName);
    }
    
    public MissingObjectException(String objectName) {
        super("Unable to locate " + objectName);
    }
}
