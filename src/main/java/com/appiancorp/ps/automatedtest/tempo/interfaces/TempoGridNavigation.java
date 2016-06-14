package com.appiancorp.ps.automatedtest.tempo.interfaces;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.appiancorp.ps.automatedtest.common.Settings;
import com.appiancorp.ps.automatedtest.exception.ExceptionBuilder;
import com.appiancorp.ps.automatedtest.properties.Clickable;

public class TempoGridNavigation extends TempoGrid implements Clickable {

  private static final Logger LOG = Logger.getLogger(TempoGridNavigation.class);
  private static final String XPATH_RELATIVE_GRID_FIRST_PAGE_LINK = Settings.getByConstant("xpathRelativeGridFirstPageLink");
  private static final String XPATH_RELATIVE_GRID_PREVIOUS_PAGE_LINK = Settings.getByConstant("xpathRelativeGridPreviousPageLink");
  private static final String XPATH_RELATIVE_GRID_NEXT_PAGE_LINK = Settings.getByConstant("xpathRelativeGridNextPageLink");
  private static final String XPATH_RELATIVE_GRID_LAST_PAGE_LINK = Settings.getByConstant("xpathRelativeGridLastPageLink");
  protected static final String XPATH_RELATIVE_GRID_PAGING_LABEL = Settings.getByConstant("xpathRelativeGridPagingLabel");

  public static TempoGridNavigation getInstance(Settings settings) {
    return new TempoGridNavigation(settings);
  }

  protected TempoGridNavigation(Settings settings) {
    super(settings);
  }

  public void click(String... params) {
    String gridName = params[0];
    String navOption = params[1];

    if (LOG.isDebugEnabled()) LOG.debug("CLICK GRID [" + gridName + "] NAVIGATION [" + navOption + "]");

    try {
      navOption = navOption.toLowerCase();

      WebElement grid = getWebElement(params);
      WebElement link = null;

      switch (navOption) {
        case "first":
          link = grid.findElement(By.xpath(XPATH_RELATIVE_GRID_FIRST_PAGE_LINK));
          break;
        case "next":
          link = grid.findElement(By.xpath(XPATH_RELATIVE_GRID_NEXT_PAGE_LINK));
          break;
        case "previous":
          link = grid.findElement(By.xpath(XPATH_RELATIVE_GRID_PREVIOUS_PAGE_LINK));
          break;
        case "last":
          link = grid.findElement(By.xpath(XPATH_RELATIVE_GRID_LAST_PAGE_LINK));
          break;
        default:
          throw new IllegalArgumentException("Invalid navigation option");
      }
      clickElement(link);
    } catch (Exception e) {
      throw ExceptionBuilder.build(e, settings, "Click Navigation option", navOption);
    }
  }
}