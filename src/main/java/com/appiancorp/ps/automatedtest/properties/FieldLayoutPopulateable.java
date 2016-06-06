package com.appiancorp.ps.automatedtest.properties;

import org.openqa.selenium.WebElement;

public interface FieldLayoutPopulateable {

  public void populate(WebElement fieldLayout, String... params) throws Exception;
}
