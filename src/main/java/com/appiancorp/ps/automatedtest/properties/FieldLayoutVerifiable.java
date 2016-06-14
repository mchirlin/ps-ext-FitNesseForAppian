package com.appiancorp.ps.automatedtest.properties;

import org.openqa.selenium.WebElement;

public interface FieldLayoutVerifiable {

  public boolean contains(WebElement fieldLayout, String... params) throws Exception;
}
