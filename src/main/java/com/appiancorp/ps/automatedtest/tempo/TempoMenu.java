package com.appiancorp.ps.automatedtest.tempo;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.appiancorp.ps.automatedtest.common.AppianObject;
import com.appiancorp.ps.automatedtest.common.Settings;
import com.appiancorp.ps.automatedtest.exception.ExceptionBuilder;
import com.appiancorp.ps.automatedtest.properties.Clickable;
import com.appiancorp.ps.automatedtest.properties.WaitFor;

public class TempoMenu extends AppianObject implements WaitFor, Clickable {

  private static final Logger LOG = Logger.getLogger(TempoMenu.class);
  private static final String XPATH_ABSOLUTE_MENU_LINK = Settings.getByConstant("xpathAbsoluteMenuLink");

  public static TempoMenu getInstance(Settings settings) {
    return new TempoMenu(settings);
  }

  private TempoMenu(Settings settings) {
    super(settings);
  }

  @Override
  public String getXpath(String... params) {
    String tempoMenu = getParam(0, params);

    return xpathFormat(XPATH_ABSOLUTE_MENU_LINK, tempoMenu);
  }

  @Override
  public void click(String... params) {
    String tempoMenu = getParam(0, params);

    if (LOG.isDebugEnabled()) LOG.debug("CLICK [" + tempoMenu + "]");

    try {
      WebElement menu = settings.getDriver().findElement(By.xpath(getXpath(params)));
      clickElement(menu);
    } catch (Exception e) {
      throw ExceptionBuilder.build(e, settings, "Tempo Menu", tempoMenu);
    }
  }

  @Override
  public void waitFor(String... params) {
    String tempoMenu = getParam(0, params);

    if (LOG.isDebugEnabled()) LOG.debug("WAIT FOR [" + tempoMenu + "]");

    try {
      (new WebDriverWait(settings.getDriver(), settings.getTimeoutSeconds())).until(ExpectedConditions.presenceOfElementLocated(By.xpath(getXpath(params))));
    } catch (Exception e) {
      throw ExceptionBuilder.build(e, settings, "Tempo Menu", tempoMenu);
    }
  }
}
