package com.appiancorp.ps.automatedtest.fixture.tempo;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.appiancorp.ps.automatedtest.exception.IllegalArgumentTestException;
import com.appiancorp.ps.automatedtest.fixture.TempoFixtureTest;

public class RecordsFixtureTest extends TempoFixtureTest {

  private static String randString;

  @BeforeClass
  public static void setUpRecords() throws Exception {
    tFixture.clickOnMenu("Actions");
    tFixture.clickOnAction("Automated Testing Input");

    randString = tFixture.getRandomString(5);
    tFixture.populateFieldWith("Title", new String[] { randString });
    tFixture.populateFieldWith("Quantity", new String[] { "5" });
    tFixture.populateFieldWith("Price", new String[] { "123.45" });
    tFixture.populateFieldWith("Start Date", new String[] { "2010-01-01 02:00" });

    tFixture.clickOnButton("Submit");
  }

  @Test
  public void testRecordsUserFilter() throws Exception {
    tFixture.clickOnMenu("Records");

    tFixture.clickOnRecordType("Automated Testing Records");
    assertTrue(tFixture.verifyRecordTypeUserFilterIsPresent("Past"));
    tFixture.clickOnRecordTypeUserFilter("Past");
    assertTrue(tFixture.verifyRecordIsPresent(randString));
    assertTrue(tFixture.verifyRecordIsNotPresent("Not present"));
  }

  @Test
  public void testClickOnRecordItemRelatedActions() throws Exception {
    tFixture.clickOnMenu("Records");

    tFixture.clickOnRecordType("Automated Testing Records");
    tFixture.clickOnRecordTypeUserFilter("Past");
    tFixture.clickOnRecord(randString);

    // Related Action View
    tFixture.clickOnRecordView("Related Actions");
    assertTrue(tFixture.verifyRecordRelatedActionIsPresent("AUT Data Input Test"));
    assertTrue(tFixture.verifyRecordRelatedActionIsNotPresent("Not present"));
    tFixture.clickOnRecordRelatedAction("AUT Data Input Test");
    tFixture.clickOnButton("Cancel");

    // Related Action Shortcut
    tFixture.clickOnRecordView("Summary");
    // TODO Configure verify related action is present to work with shortcuts
    // assertTrue(tFixture.verifyRecordRelatedActionIsPresent("AUT Data Input Test"));
    // assertTrue(tFixture.verifyRecordRelatedActionIsNotPresent("Not present"));
    tFixture.clickOnRecordRelatedAction("AUT Data Input Test");
    tFixture.clickOnButton("Cancel");
  }

  @Test
  public void testSearch() throws Exception {
    tFixture.clickOnMenu("Records");
    tFixture.clickOnRecordType("Automated Testing Records");
    tFixture.searchFor(randString);
    assertTrue(tFixture.verifyRecordIsPresent(randString));
  }

  @Test
  public void testRecordGridList() throws Exception {
    tFixture.clickOnMenu("Records");
    tFixture.clickOnRecordType("Automated Test Grid");

    tFixture.sortRecordGridByColumn("Title");
    tFixture.sortRecordGridByColumn("Quantity");
    tFixture.sortRecordGridByColumn("Price");
    tFixture.sortRecordGridByColumn("Start Date");

    tFixture.clickOnRecordGridNavigation("Next");
    tFixture.clickOnRecordGridNavigation("Previous");
    tFixture.clickOnRecordGridNavigation("Last");
    tFixture.clickOnRecordGridNavigation("First");

    try {
      tFixture.clickOnRecordGridNavigation("Invalid");
      fail("Should have thrown illegal argument exception");
    } catch (IllegalArgumentTestException e) {
    }

    tFixture.searchFor(randString);
    assertTrue(tFixture.verifyRecordIsPresent(randString));
    assertTrue(tFixture.verifyRecordIsNotPresent("not " + randString));
    tFixture.clickOnRecord(randString);
  }

  @Test
  public void testRecordsTypeByNameIndex() throws Exception {
    tFixture.clickOnMenu("Records");
    tFixture.clickOnRecordType("Auto[1]");

    tFixture.clickOnMenu("Records");
    tFixture.clickOnRecordType("Auto[2]");
  }

  @AfterClass
  public static void tearDownRecords() throws Exception {
    tFixture.clickOnMenu("News");
    // TODO Configure delete news post to handle non admins
    // tFixture.deleteNewsPost(randString);
  }
}