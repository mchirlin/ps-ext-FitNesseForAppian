package com.appiancorp.ps.automatedtest.object;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.appiancorp.ps.automatedtest.common.Settings;
import com.appiancorp.ps.automatedtest.exception.ExceptionBuilder;

public class TempoForm extends AppianObject {

  private static final Logger LOG = Logger.getLogger(TempoForm.class);
  private static final String XPATH_ABSOLUTE_FORM_TITLE = Settings.getByConstant("xpathAbsoluteFormTitle");
  private static final String XPATH_ABSOLUTE_FORM_INSTRUCTIONS = Settings.getByConstant("xpathAbsoluteFormInstructions");

  public static String getFormTitle(Settings s) {
    if (LOG.isDebugEnabled()) LOG.debug("GET FORM TITLE");

    try {
      WebElement element = s.getDriver().findElement(By.xpath(String.format(XPATH_ABSOLUTE_FORM_TITLE)));
      return element.getText();
    } catch (Exception e) {
      throw ExceptionBuilder.build(e, s, "Get Form Title");
    }
  }

  public static void waitForTitle(Settings s) {
    if (LOG.isDebugEnabled()) LOG.debug("WAIT FOR FORM TITLE");

    try {
      (new WebDriverWait(s.getDriver(), s.getTimeoutSeconds())).until(ExpectedConditions.presenceOfElementLocated(By.xpath(String.format(XPATH_ABSOLUTE_FORM_TITLE))));
      WebElement element = s.getDriver().findElement(By.xpath(String.format(XPATH_ABSOLUTE_FORM_TITLE)));
      scrollIntoView(element, true, s);
    } catch (Exception e) {
      throw ExceptionBuilder.build(e, s, "Wait for Title");
    }
  }

  public static String getFormInstructions(Settings s) {
    if (LOG.isDebugEnabled()) LOG.debug("GET FORM INSTRUCTIONS");

    try {
      WebElement element = s.getDriver().findElement(By.xpath(String.format(XPATH_ABSOLUTE_FORM_INSTRUCTIONS)));
      return element.getText();
    } catch (Exception e) {
      throw ExceptionBuilder.build(e, s, "Get Form Instructions");
    }
  }

  public static void waitForInstructions(Settings s) {
    if (LOG.isDebugEnabled()) LOG.debug("WAIT FOR FORM INSTRUCTIONS");

    try {
      (new WebDriverWait(s.getDriver(), s.getTimeoutSeconds())).until(ExpectedConditions.presenceOfElementLocated(By.xpath(String.format(XPATH_ABSOLUTE_FORM_INSTRUCTIONS))));
      WebElement element = s.getDriver().findElement(By.xpath(String.format(XPATH_ABSOLUTE_FORM_INSTRUCTIONS)));
      scrollIntoView(element, true, s);
    } catch (Exception e) {
      throw ExceptionBuilder.build(e, s, "Wait for Form Instructions");
    }
  }
}
