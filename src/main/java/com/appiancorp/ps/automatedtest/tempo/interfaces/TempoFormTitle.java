package com.appiancorp.ps.automatedtest.tempo.interfaces;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.appiancorp.ps.automatedtest.common.AppianObject;
import com.appiancorp.ps.automatedtest.common.Settings;
import com.appiancorp.ps.automatedtest.exception.ExceptionBuilder;
import com.appiancorp.ps.automatedtest.properties.Captureable;
import com.appiancorp.ps.automatedtest.properties.RegexCaptureable;
import com.appiancorp.ps.automatedtest.properties.WaitFor;

public class TempoFormTitle extends AppianObject implements WaitFor, Captureable, RegexCaptureable {

  private static final Logger LOG = Logger.getLogger(TempoFormTitle.class);
  private static final String XPATH_ABSOLUTE_FORM_TITLE = Settings.getByConstant("xpathAbsoluteFormTitle");

  public static TempoFormTitle getInstance(Settings settings) {
    return new TempoFormTitle(settings);
  }

  private TempoFormTitle(Settings settings) {
    super(settings);
  }

  @Override
  public String getXpath(String... params) {
    return XPATH_ABSOLUTE_FORM_TITLE;
  }

  public String capture(String... params) {
    if (LOG.isDebugEnabled()) LOG.debug("GET FORM TITLE");

    try {
      WebElement element = settings.getDriver().findElement(By.xpath(getXpath(params)));
      return element.getText();
    } catch (Exception e) {
      throw ExceptionBuilder.build(e, settings, "Get Form Title");
    }
  }

  public String regexCapture(String regex, Integer group, String... params) {
    if (LOG.isDebugEnabled()) LOG.debug("REGEX FOR FORM TITLE [" + regex + "]");

    try {
      String text = settings.getDriver().findElement(By.xpath(getXpath(params))).getText();
      if (LOG.isDebugEnabled()) LOG.debug("TITLE VALUE [" + text + "]");
      return getRegexResults(regex, group, text);
    } catch (Exception e) {
      throw ExceptionBuilder.build(e, settings, "Title regex", regex);
    }
  }

  public void waitFor(String... params) {
    if (LOG.isDebugEnabled()) LOG.debug("WAIT FOR FORM TITLE");

    try {
      (new WebDriverWait(settings.getDriver(), settings.getTimeoutSeconds())).until(ExpectedConditions.presenceOfElementLocated(By.xpath(getXpath(params))));
    } catch (Exception e) {
      throw ExceptionBuilder.build(e, settings, "Wait for Title");
    }
  }
}
