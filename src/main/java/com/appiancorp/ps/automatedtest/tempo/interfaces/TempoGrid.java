package com.appiancorp.ps.automatedtest.tempo.interfaces;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.appiancorp.ps.automatedtest.common.AppianObject;
import com.appiancorp.ps.automatedtest.common.Settings;
import com.appiancorp.ps.automatedtest.properties.Container;
import com.appiancorp.ps.automatedtest.properties.WaitFor;

public class TempoGrid extends AppianObject implements WaitFor, Container {

  @SuppressWarnings("unused")
  private static final Logger LOG = Logger.getLogger(TempoGrid.class);
  protected static final String XPATH_ABSOLUTE_GRID_BY_INDEX = Settings.getByConstant("xpathAbsoluteGridByIndex");
  protected static final String XPATH_ABSOLUTE_GRID_BY_LABEL = Settings.getByConstant("xpathAbsoluteGridByLabel");
  protected static final String XPATH_ABSOLUTE_GRID_BY_LABEL_INDEX = "(" + XPATH_ABSOLUTE_GRID_BY_LABEL + ")[%2$d]";

  public static TempoGrid getInstance(Settings settings) {
    return new TempoGrid(settings);
  }

  protected TempoGrid(Settings settings) {
    super(settings);
  }

  @Override
  public WebElement getWebElement(String... params) {
    return settings.getDriver().findElement(By.xpath(getXpath(params)));
  }

  @Override
  public String getXpath(String... params) {
    String gridName = params[0];

    WebElement grid = null;
    if (isFieldIndex(gridName)) {
      int gNum = getIndexFromFieldIndex(gridName);
      String gName = getFieldFromFieldIndex(gridName);
      if (StringUtils.isBlank(gName)) {
        grid = settings.getDriver().findElement(By.xpath(xpathFormat(XPATH_ABSOLUTE_GRID_BY_INDEX, gNum)));
      } else {
        grid = settings.getDriver().findElement(By.xpath(xpathFormat(XPATH_ABSOLUTE_GRID_BY_LABEL_INDEX, gName, gNum)));
      }
    } else {
      grid = settings.getDriver().findElement(By.xpath(xpathFormat(XPATH_ABSOLUTE_GRID_BY_LABEL, gridName)));
    }

    scrollIntoView(grid);
    return getXpathLocator(grid);
  }

  @Override
  public void waitFor(String... params) {
    (new WebDriverWait(settings.getDriver(), settings.getTimeoutSeconds())).until(ExpectedConditions.presenceOfElementLocated(By.xpath(getXpath(params))));
  }
}