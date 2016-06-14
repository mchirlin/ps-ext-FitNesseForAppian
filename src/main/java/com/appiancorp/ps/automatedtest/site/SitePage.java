package com.appiancorp.ps.automatedtest.site;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.appiancorp.ps.automatedtest.common.AppianObject;
import com.appiancorp.ps.automatedtest.common.Settings;
import com.appiancorp.ps.automatedtest.exception.ExceptionBuilder;
import com.appiancorp.ps.automatedtest.properties.Clickable;
import com.appiancorp.ps.automatedtest.properties.Navigateable;
import com.appiancorp.ps.automatedtest.properties.WaitFor;

public class SitePage extends AppianObject implements WaitFor, Clickable, Navigateable {

  private static final Logger LOG = Logger.getLogger(SitePage.class);
  private static final String XPATH_ABSOLUTE_SITE_PAGE_LINK = Settings.getByConstant("xpathAbsoluteSitePageLink");

  public static SitePage getInstance(Settings settings) {
    return new SitePage(settings);
  }

  private SitePage(Settings settings) {
    super(settings);
  }

  @Override
  public String getXpath(String... params) {
    String sitePage = params[0];

    return xpathFormat(XPATH_ABSOLUTE_SITE_PAGE_LINK, sitePage);
  }

  @Override
  public void click(String... params) {
    String sitePage = params[0];

    if (LOG.isDebugEnabled()) LOG.debug("CLICK [" + sitePage + "]");

    try {
      WebElement menu = settings.getDriver().findElement(By.xpath(getXpath(params)));
      clickElement(menu);
    } catch (Exception e) {
      throw ExceptionBuilder.build(e, settings, "Site Page", sitePage);
    }
  }

  @Override
  public void waitFor(String... params) {
    String sitePage = params[0];

    if (LOG.isDebugEnabled()) LOG.debug("WAIT FOR [" + sitePage + "]");

    try {
      (new WebDriverWait(settings.getDriver(), settings.getTimeoutSeconds())).until(ExpectedConditions.presenceOfElementLocated(By.xpath(getXpath(params))));
    } catch (Exception e) {
      throw ExceptionBuilder.build(e, settings, "Site Page", sitePage);
    }
  }

  @Override
  public void navigateTo(String... params) {
    String siteUrl = params[0];
    String pageUrl = params[1];

    if (LOG.isDebugEnabled()) LOG.debug("NAVIGATE TO SITE [" + siteUrl + "] PAGE [" + pageUrl + "]");

    String url = settings.getUrl() + "/sites/" + siteUrl + "/page/" + pageUrl;
    settings.getDriver().get(url);
  }
}
