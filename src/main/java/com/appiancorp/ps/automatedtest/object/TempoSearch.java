package com.appiancorp.ps.automatedtest.object;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.appiancorp.ps.automatedtest.common.Settings;
import com.appiancorp.ps.automatedtest.exception.ExceptionBuilder;

public class TempoSearch extends AppianObject {

  private static final Logger LOG = Logger.getLogger(TempoRecordType.class);
  private static final String XPATH_ABSOLUTE_SEARCH_FIELD = Settings.getByConstant("xpathAbsoluteSearchField");

  public static void searchFor(String searchValue, Settings s) {
    if (LOG.isDebugEnabled()) LOG.debug("SEARCH FOR [" + searchValue + "]");

    try {
      WebElement fieldLayout = s.getDriver().findElement(By.xpath(XPATH_ABSOLUTE_SEARCH_FIELD));

      fieldLayout.clear();
      fieldLayout.sendKeys(searchValue);
      fieldLayout.sendKeys(Keys.ENTER);

      waitForWorking(s);
    } catch (Exception e) {
      throw ExceptionBuilder.build(e, s, "Tempo Search", searchValue);
    }
  }

  public static void waitFor(String type, Settings s) {
    if (LOG.isDebugEnabled()) LOG.debug("WAIT FOR");

    try {
      (new WebDriverWait(s.getDriver(), s.getTimeoutSeconds())).until(ExpectedConditions.visibilityOfElementLocated(By.xpath(String.format(XPATH_ABSOLUTE_SEARCH_FIELD))));
      WebElement element = s.getDriver().findElement(By.xpath(String.format(XPATH_ABSOLUTE_SEARCH_FIELD)));
      scrollIntoView(element, false, s);
    } catch (Exception e) {
      throw ExceptionBuilder.build(e, s, "Tempo Search");
    }
  }
}
