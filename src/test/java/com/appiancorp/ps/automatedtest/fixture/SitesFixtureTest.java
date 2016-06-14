package com.appiancorp.ps.automatedtest.fixture;

import org.junit.Test;

import com.appiancorp.ps.automatedtest.test.AbstractLoginTest;

public class SitesFixtureTest extends AbstractLoginTest {

  @Test
  public void testClickOnSitePage() throws Exception {
    fixture.navigateToSite("automated-test-site");
    fixture.clickOnSitePage("Data Input");
  }

  @Test
  public void testNavigateToSitePage() throws Exception {
    fixture.navigateToSitePage("automated-test-site", "data-input");
  }

  @Test
  public void testNavigateToSite() throws Exception {
    fixture.navigateToSite("automated-test-site");
  }
}
