package com.appiancorp.ps.automatedtest.fixture;

import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;

import com.appiancorp.ps.automatedtest.test.AbstractLoginTest;

public class SitesFixtureTest extends AbstractLoginTest {

  @Before
  public void beforeSitesFixtureTest() {
    Assume.assumeTrue(atLeastVersion(16.1));
  }

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

  @Test
  public void testRecordGridView() throws Exception {
    Assume.assumeTrue(atLeastVersion(16.2));
    fixture.navigateToSite("automated-test-site");
    fixture.clickOnSitePage("Data Input");
    String randString = fixture.getRandomString(5);
    Integer randInt = fixture.getRandomIntegerFromTo(0, 9);
    Double randDecimal = fixture.getRandomDecimalFromToWith(1.0, 2.0, 4);
    fixture.populateFieldWith("Title", new String[] { randString });
    fixture.populateFieldWith("Quantity", new String[] { randInt.toString() });
    fixture.populateFieldWith("Price", new String[] { randDecimal.toString() });
    fixture.populateFieldWith("Start Date", new String[] { "2016-02-04 02:00" });
    fixture.clickOnButton("Submit");

    fixture.clickOnSitePage("Automated Test Grid");
    fixture.clickOnRecordGridNavigation("Last");
    fixture.clickOnRecordGridNavigation("Previous");
    fixture.clickOnRecordGridNavigation("Next");
    fixture.clickOnRecordGridNavigation("First");
    fixture.searchFor("rYZZk");
    fixture.clickOnRecord("rYZZk");

    fixture.clickOnRecordRelatedAction("AUT Data Input Test");
    fixture.clickOnButton("Cancel");

    fixture.clickOnRecordView("Related Actions");
    fixture.clickOnRecordRelatedAction("AUT Data Input Test");
    fixture.clickOnButton("Cancel");

  }

}
