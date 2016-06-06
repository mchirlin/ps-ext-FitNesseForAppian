package com.appiancorp.ps.automatedtest.properties;

import org.openqa.selenium.WebElement;

public interface Container {

  public WebElement getWebElement(String... params);
}
