package com.appiancorp.ps.automatedtest.tempo.interfaces;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.appiancorp.ps.automatedtest.common.AppianObject;
import com.appiancorp.ps.automatedtest.common.Settings;
import com.appiancorp.ps.automatedtest.exception.ExceptionBuilder;
import com.appiancorp.ps.automatedtest.properties.Captureable;
import com.appiancorp.ps.automatedtest.properties.WaitForMultiple;

public class TempoFieldValidation extends AppianObject implements WaitForMultiple, Captureable {

  private static final Logger LOG = Logger.getLogger(TempoFieldValidation.class);
  public static final String XPATH_RELATIVE_FIELD_VALIDATION_MESSAGE_SPECIFIC_VALUE = Settings.getByConstant("xpathRelativeFieldValidationMessageSpecificValue");
  public static final String XPATH_RELATIVE_FIELD_VALIDATION_MESSAGE = Settings.getByConstant("xpathRelativeFieldValidationMessage");

  public static TempoFieldValidation getInstance(Settings settings) {
    return new TempoFieldValidation(settings);
  }

  protected TempoFieldValidation(Settings settings) {
    super(settings);
  }

  @Override
  public String getXpath(String... params) {
    String fieldName = getParam(0, params);
    String validationMessage = getParam(1, params);

    return "(" + TempoField.getInstance(settings).getXpath(fieldName) + ")" +
      xpathFormat(XPATH_RELATIVE_FIELD_VALIDATION_MESSAGE_SPECIFIC_VALUE, validationMessage);
  }

  public String capture(String... params) {
    WebElement fieldLayout = settings.getDriver().findElement(By.xpath(TempoField.getInstance(settings).getXpath(params)));
    List<String> values = new ArrayList<String>();

    for (WebElement a : fieldLayout.findElements(By.xpath(XPATH_RELATIVE_FIELD_VALIDATION_MESSAGE))) {
      values.add(a.getText());
    }

    return String.join(",", values);
  }

  public void waitForMultiple(String[] validationMessages, String... params) {
    String fieldName = getParam(0, params);

    try {
      for (String validationMessage : validationMessages) {
        waitFor(fieldName, validationMessage);
      }
    } catch (Exception e) {
      throw ExceptionBuilder.build(e, settings, "Get field value", fieldName);
    }
  }

  @SuppressWarnings("unused")
  @Override
  public void waitFor(String... params) {
    String fieldName = getParam(0, params);
    String validationMessage = getParam(1, params);

    if (LOG.isDebugEnabled()) LOG.debug("WAIT FOR VALIDATION [" + validationMessage + "]");

    (new WebDriverWait(settings.getDriver(), settings.getTimeoutSeconds())).until(ExpectedConditions.presenceOfElementLocated(By.xpath(
      getXpath(params))));
  }
}
