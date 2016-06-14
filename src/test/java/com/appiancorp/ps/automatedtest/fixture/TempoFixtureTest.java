package com.appiancorp.ps.automatedtest.fixture;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import com.appiancorp.ps.automatedtest.exception.IllegalArgumentTestException;
import com.appiancorp.ps.automatedtest.test.AbstractDataTest;

public class TempoFixtureTest extends AbstractDataTest {

  @Test
  public void testNews() {
    // Setup
    fixture.clickOnMenu("News");

    // Verify Post
    assertTrue(fixture.verifyNewsFeedContainingTextIsPresent(randString));
    assertTrue(fixture.verifyNewsFeedContainingTextIsNotPresent("Not present"));

    // Verify Comment
    assertTrue(fixture.verifyNewsFeedContainingTextCommentedWithIsPresent(randString, "Comment"));

    // Verify More Info
    fixture.toggleMoreInfoForNewsFeedContainingText(randString);
    assertTrue(fixture.verifyNewsFeedContainingTextAndMoreInfoWithLabelAndValueIsPresent(randString, "Label", "Value"));

    // TODO Verify Posted At

    // Regex
    assertEquals(fixture.getRegexGroupFromNewsFeedContainingText("\\[([a-zA-Z0-9]{5})\\]", 1, randString), randString);
    assertEquals(fixture.getRegexGroupFromNewsFeedContainingTextCommentedWith("\\[([0-9])\\]", 1, randString, "Comment"),
      randInt.toString());

    // Search
    fixture.searchFor(randString);

    // Record Tag
    assertTrue(fixture.verifyNewsFeedContainingTextTaggedWithIsPresent(randString, randString));
    fixture.clickOnNewsFeedRecordTag(randString, randString);
  }

  @Test
  public void testRecordList() throws Exception {
    // Setup
    fixture.clickOnMenu("Records");
    fixture.clickOnRecordType("Automated Testing Records");

    // Verify Presence
    assertTrue(fixture.verifyRecordIsPresent(randString));
    assertTrue(fixture.verifyRecordIsNotPresent("Invalid"));

    // Record Type Filter
    fixture.verifyRecordTypeUserFilterIsPresent("Past");
    fixture.clickOnRecordTypeUserFilter("Past");

    // Regex
    assertEquals(fixture.getRegexGroupFromRecordNameContainingText("([a-zA-Z0-9]{5})", 1, randString), randString);

    // Click
    fixture.clickOnRecord(randString + "[1]");

    // Related Action View
    fixture.clickOnRecordView("Related Actions");
    assertTrue(fixture.verifyRecordRelatedActionIsPresent("AUT Data Input Test"));
    assertTrue(fixture.verifyRecordRelatedActionIsNotPresent("Not present"));
    fixture.clickOnRecordRelatedAction("AUT Data Input Test");
    fixture.clickOnButton("Cancel");

    // Related Action Shortcut
    fixture.clickOnRecordView("Summary");
    // TODO Configure verify related action is present to work with shortcuts
    // assertTrue(fixture.verifyRecordRelatedActionIsPresent("AUT Data Input Test"));
    // assertTrue(fixture.verifyRecordRelatedActionIsNotPresent("Not present"));
    fixture.clickOnRecordRelatedAction("AUT Data Input Test");
    fixture.clickOnButton("Cancel");
  }

  @Test
  public void testRecordGrid() throws Exception {
    // Setup
    fixture.clickOnMenu("Records");
    fixture.clickOnRecordType("Automated Test Grid[1]");

    // Verify Presence
    fixture.verifyRecordIsPresent(randString + "[1]");

    // Sort
    fixture.sortRecordGridByColumn("Title");

    // Navigation
    fixture.clickOnRecordGridNavigation("Next");
    fixture.clickOnRecordGridNavigation("Previous");
    fixture.clickOnRecordGridNavigation("Last");
    fixture.clickOnRecordGridNavigation("First");
    try {
      fixture.clickOnRecordGridNavigation("Invalid");
      fail("Should have thrown illegal argument exception");
    } catch (IllegalArgumentTestException e) {
    }

    // Search
    fixture.searchFor(randString);

    // Click
    fixture.clickOnRecord(randString + "[1]");
  }

  @Test
  public void testTask() throws Exception {
    // Setup
    fixture.clickOnMenu("Actions");
    fixture.clickOnAction("All Fields");
    fixture.clickOnMenu("Tasks");

    // Verify Presence
    assertTrue(fixture.verifyTaskIsPresent("All Fields Task"));
    assertTrue(fixture.verifyTaskIsNotPresent("Not present"));

    // Regex
    assertEquals(fixture.getRegexGroupFromTaskNameContainingText("([a-zA-Z0-9]{0,5})", 1, "All Fields"), "All");

    // Deadline
    assertTrue(fixture.verifyTaskHasDeadlineOf("All Fields Task", "1h"));

    // Task Report
    fixture.clickOnTaskReport("Task Report");

    // Click
    fixture.clickOnMenu("Tasks");
    fixture.clickOnTask("All Fields Task[1]");
    fixture.clickOnButton("Cancel");
  }

  @Test
  public void testReport() throws Exception {
    // Setup
    fixture.clickOnMenu("Reports");

    // Search
    fixture.searchFor("Automated Test Report");

    // Verify Presence
    assertTrue(fixture.verifyReportIsPresent("Automated Test Report[1]"));
    assertTrue(fixture.verifyReportIsNotPresent("Not Report"));

    // Click
    fixture.clickOnReport("Automated Test Report[1]");
  }

  @Test
  public void testAction() throws Exception {
    // Setup
    fixture.clickOnMenu("Actions");

    // Verify Presence
    assertTrue(fixture.verifyActionIsPresent("Automated Testing[1]"));
    assertTrue(fixture.verifyActionIsNotPresent("Not Automated Testing"));

    // Click
    fixture.clickOnApplicationFilter("Automated");
    fixture.clickOnAction("Automated Testing[1]");
    fixture.clickOnButton("Cancel");
  }
}
