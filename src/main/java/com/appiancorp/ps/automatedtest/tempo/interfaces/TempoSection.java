package com.appiancorp.ps.automatedtest.tempo.interfaces;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.appiancorp.ps.automatedtest.common.AppianObject;
import com.appiancorp.ps.automatedtest.common.Settings;
import com.appiancorp.ps.automatedtest.exception.ExceptionBuilder;
import com.appiancorp.ps.automatedtest.properties.Container;
import com.appiancorp.ps.automatedtest.properties.WaitFor;

public class TempoSection extends AppianObject implements
  Container, WaitFor {

  public static TempoSection getInstance(Settings settings) {
    return new TempoSection(settings);
  }

  protected TempoSection(Settings settings) {
    super(settings);
  }

  @SuppressWarnings("unused")
  private static final Logger LOG = Logger.getLogger(TempoSection.class);

  protected static final String XPATH_ABSOLUTE_SECTION_LAYOUT = Settings.getByConstant("xpathAbsoluteSectionLayout");

  @Override
  public String getXpath(String... params) {
    String sectionName = params[0];

    return xpathFormat(XPATH_ABSOLUTE_SECTION_LAYOUT, sectionName);
  }

  @Override
  public WebElement getWebElement(String... params) {
    return settings.getDriver().findElement(By.xpath(getXpath(params)));
  }

  @Override
  public void waitFor(String... params) {
    if (params.length == 1) {
      String sectionName = params[0];

      try {
        (new WebDriverWait(settings.getDriver(), settings.getTimeoutSeconds())).until(ExpectedConditions.presenceOfElementLocated(By.xpath(getXpath(params))));
      } catch (Exception e) {
        throw ExceptionBuilder.build(e, settings, "Wait for Section", sectionName);
      }
    }
  }
}
