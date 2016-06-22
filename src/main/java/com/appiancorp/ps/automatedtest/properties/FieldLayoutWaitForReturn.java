package com.appiancorp.ps.automatedtest.properties;

import org.openqa.selenium.WebElement;

public interface FieldLayoutWaitForReturn extends FieldLayoutWaitFor {

  public boolean waitForReturn(int timeout, WebElement fieldLayout, String... params);

  public boolean waitForReturn(WebElement fieldLayout, String... params);
}
