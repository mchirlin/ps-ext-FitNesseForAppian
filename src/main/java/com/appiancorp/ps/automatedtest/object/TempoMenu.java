package com.appiancorp.ps.automatedtest.object;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.appiancorp.ps.automatedtest.common.Settings;
import com.appiancorp.ps.automatedtest.exception.ExceptionBuilder;

public class TempoMenu extends AppianObject {

  private static final Logger LOG = Logger.getLogger(TempoMenu.class);
  private static final String XPATH_ABSOLUTE_MENU_LINK = Settings.getByConstant("xpathAbsoluteMenuLink");

  public static void click(String tempoMenu, Settings s) {
    if (LOG.isDebugEnabled()) LOG.debug("CLICK [" + tempoMenu + "]");

    try {
      WebElement element = s.getDriver().findElement(By.xpath(String.format(XPATH_ABSOLUTE_MENU_LINK, tempoMenu)));
      element.click();
      waitForWorking(s);
    } catch (Exception e) {
      throw ExceptionBuilder.build(e, s, "Tempo Menu", tempoMenu);
    }
  }

  public static void waitFor(String tempoMenu, Settings s) {
    if (LOG.isDebugEnabled()) LOG.debug("WAIT FOR [" + tempoMenu + "]");

    try {
      (new WebDriverWait(s.getDriver(), s.getTimeoutSeconds())).until(ExpectedConditions.presenceOfElementLocated(By.xpath(String.format(
        XPATH_ABSOLUTE_MENU_LINK, tempoMenu))));
      WebElement element = s.getDriver().findElement(By.xpath(String.format(XPATH_ABSOLUTE_MENU_LINK, tempoMenu)));
      scrollIntoView(element, true, s);
    } catch (Exception e) {
      throw ExceptionBuilder.build(e, s, "Tempo Menu", tempoMenu);
    }
  }
}
