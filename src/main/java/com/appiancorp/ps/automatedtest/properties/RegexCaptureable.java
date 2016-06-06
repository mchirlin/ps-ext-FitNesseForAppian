package com.appiancorp.ps.automatedtest.properties;

public interface RegexCaptureable {

  public String regexCapture(String regex, Integer group, String... params);
}
