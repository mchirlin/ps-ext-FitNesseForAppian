package com.appiancorp.ps.automatedtest.tempo.interfaces;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.appiancorp.ps.automatedtest.common.AppianObject;
import com.appiancorp.ps.automatedtest.common.Settings;
import com.appiancorp.ps.automatedtest.exception.ExceptionBuilder;
import com.appiancorp.ps.automatedtest.properties.Container;
import com.appiancorp.ps.automatedtest.properties.FieldLayoutCaptureable;
import com.appiancorp.ps.automatedtest.properties.FieldLayoutClearable;
import com.appiancorp.ps.automatedtest.properties.FieldLayoutPopulateable;
import com.appiancorp.ps.automatedtest.properties.FieldLayoutVerifiable;
import com.appiancorp.ps.automatedtest.properties.WaitForReturn;

public abstract class AbstractTempoField extends AppianObject implements Container, FieldLayoutPopulateable, FieldLayoutVerifiable,
  FieldLayoutCaptureable, FieldLayoutClearable, WaitForReturn {

  public static final String XPATH_ABSOLUTE_FIELD_LAYOUT_LABEL = Settings.getByConstant("xpathAbsoluteFieldLayoutLabel");
  public static final String XPATH_ABSOLUTE_FIELD_LAYOUT_INDEX = Settings.getByConstant("xpathAbsoluteFieldLayoutIndex");
  public static final String XPATH_ABSOLUTE_FIELD_LAYOUT_LABEL_INDEX = "(" + XPATH_ABSOLUTE_FIELD_LAYOUT_LABEL + ")[%2$d]";

  protected AbstractTempoField(Settings settings) {
    super(settings);
  }

  @Override
  public WebElement getWebElement(String... params) {
    return settings.getDriver().findElement(By.xpath(getXpath(params)));
  }

  @Override
  public String getXpath(String... params) {
    String fieldName = params[0];

    if (isFieldIndex(fieldName)) {
      int index = getIndexFromFieldIndex(fieldName);
      String name = getFieldFromFieldIndex(fieldName);
      if (StringUtils.isBlank(name)) {
        return xpathFormat(XPATH_ABSOLUTE_FIELD_LAYOUT_INDEX, index);
      } else {
        return xpathFormat(XPATH_ABSOLUTE_FIELD_LAYOUT_LABEL_INDEX, name, index);
      }

    } else {
      return xpathFormat(XPATH_ABSOLUTE_FIELD_LAYOUT_LABEL, fieldName);
    }
  }

  @Override
  public void waitFor(String... params) {
    String fieldName = params[0];

    try {
      (new WebDriverWait(settings.getDriver(), settings.getTimeoutSeconds())).until(ExpectedConditions.visibilityOfElementLocated(By.xpath(getXpath(params))));
    } catch (Exception e) {
      throw ExceptionBuilder.build(e, settings, "Wait for Field", fieldName);
    }
  }

  @Override
  public boolean waitForReturn(int timeout, String... params) {
    String fieldName = params[0];

    try {
      (new WebDriverWait(settings.getDriver(), timeout)).until(ExpectedConditions.visibilityOfElementLocated(By.xpath(getXpath(params))));
      return true;
    } catch (TimeoutException e) {
      return false;
    } catch (Exception e) {
      throw ExceptionBuilder.build(e, settings, "Wait for Field", fieldName);
    }
  }

  @Override
  public boolean waitForReturn(String... params) {
    return waitForReturn(settings.getTimeoutSeconds(), params);
  }
}
