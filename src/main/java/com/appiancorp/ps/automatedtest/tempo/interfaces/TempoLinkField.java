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
import com.appiancorp.ps.automatedtest.properties.Clickable;

public class TempoLinkField extends AppianObject implements Clickable, Captureable {

  private static final Logger LOG = Logger.getLogger(TempoLinkField.class);
  private static final String XPATH_ABSOLUTE_LINK_FIELD = Settings.getByConstant("xpathAbsoluteLinkField");
  private static final String XPATH_ABSOLUTE_LINK_FIELD_INDEX = "(" + XPATH_ABSOLUTE_LINK_FIELD + ")[%2$d]";

  public static TempoLinkField getInstance(Settings settings) {
    return new TempoLinkField(settings);
  }

  protected TempoLinkField(Settings settings) {
    super(settings);
  }

  @Override
  public String getXpath(String... params) {
    String linkName = getParam(0, params);

    if (isFieldIndex(linkName)) {
      int lNum = getIndexFromFieldIndex(linkName);
      String lName = getFieldFromFieldIndex(linkName);
      return xpathFormat(XPATH_ABSOLUTE_LINK_FIELD_INDEX, lName, lNum);
    } else {
      return xpathFormat(XPATH_ABSOLUTE_LINK_FIELD, linkName);
    }
  }

  @Override
  public void waitFor(String... params) {
    String linkName = getParam(0, params);

    if (LOG.isDebugEnabled()) LOG.debug("WAIT FOR LINK [" + linkName + "]");

    try {
      (new WebDriverWait(settings.getDriver(), settings.getTimeoutSeconds())).until(ExpectedConditions.presenceOfElementLocated(By.xpath(getXpath(params))));
    } catch (Exception e) {
      throw ExceptionBuilder.build(e, settings, "Wait for Link", linkName);
    }
  }

  @Override
  public String capture(String... params) {
    WebElement link = settings.getDriver().findElement(By.xpath(getXpath(params)));
    String linkURL = link.getAttribute("href");
    return linkURL;
  }

  @Override
  public void click(String... params) {
    String linkName = getParam(0, params);

    if (LOG.isDebugEnabled()) LOG.debug("CLINK LINK [" + linkName + "]");

    try {
      WebElement link = settings.getDriver().findElement(By.xpath(getXpath(params)));
      clickElement(link);
    } catch (Exception e) {
      throw ExceptionBuilder.build(e, settings, "Click Link", linkName);
    }
  }
}
