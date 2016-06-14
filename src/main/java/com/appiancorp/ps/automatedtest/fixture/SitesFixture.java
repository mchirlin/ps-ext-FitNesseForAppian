package com.appiancorp.ps.automatedtest.fixture;

import com.appiancorp.ps.automatedtest.site.Site;
import com.appiancorp.ps.automatedtest.site.SitePage;

public class SitesFixture extends TempoFixture {

  public SitesFixture() {
    super();
  }

  /**
   * Navigate to Appian Site.<br>
   * <br>
   * Example: <code>| navigate to site | SITE_NAME |</code>
   * 
   * @param siteUrl
   *          Url of Appian site
   */
  public void navigateToSite(String siteUrl) {
    Site.getInstance(settings).navigateTo(siteUrl);
  }

  /**
   * Navigate to Appian Site Page <br>
   * <br>
   * Example: <code>| navigate to site | SITE_URL | page | PAGE_URL | </code>
   * 
   * @param siteUrl
   *          Url of Appian site
   * @param pageUrl
   *          Url of Page
   */
  public void navigateToSitePage(String siteUrl, String pageUrl) {
    SitePage.getInstance(settings).navigateTo(siteUrl, pageUrl);
  }

  /**
   * Navigate to Appian Site Page.<br>
   * <br>
   * Example: <code>| navigate to site | SITE_NAME |</code>
   * 
   * @param site
   *          Name of Appian site
   */
  public void clickOnSitePage(String sitePage) {
    SitePage.getInstance(settings).waitFor(sitePage);
    SitePage.getInstance(settings).click(sitePage);
  }

}
