package com.appiancorp.ps.automatedtest.properties;

import org.openqa.selenium.WebElement;

public interface FieldLayoutCaptureable {

  public String capture(WebElement fieldLayout, String... params);
}
