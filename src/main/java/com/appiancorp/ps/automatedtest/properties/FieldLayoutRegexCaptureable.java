package com.appiancorp.ps.automatedtest.properties;

import org.openqa.selenium.WebElement;

public interface FieldLayoutRegexCaptureable {

  public String regexCapture(WebElement fieldLayout, String regex, Integer group, String... params) throws Exception;
}
