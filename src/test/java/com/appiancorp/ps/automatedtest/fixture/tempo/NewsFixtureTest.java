package com.appiancorp.ps.automatedtest.fixture.tempo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.appiancorp.ps.automatedtest.fixture.TempoFixtureTest;

public class NewsFixtureTest extends TempoFixtureTest {

  private static String randString;
  private static Integer randInt;
  private static Double randDecimal;

  @BeforeClass
  public static void setUpNews() throws Exception {
    tFixture.clickOnMenu("Actions");
    tFixture.clickOnAction("Automated Testing Input");

    randString = tFixture.getRandomString(5);
    randInt = tFixture.getRandomIntegerFromTo(0, 9);
    randDecimal = tFixture.getRandomDecimalFromToWith(1.0, 2.0, 4);
    tFixture.populateFieldWith("Title", new String[] { randString });
    tFixture.populateFieldWith("Quantity", new String[] { randInt.toString() });
    tFixture.populateFieldWith("Price", new String[] { randDecimal.toString() });
    tFixture.populateFieldWith("Start Date", new String[] { "2016-02-04 02:00" });

    tFixture.clickOnButton("Submit");
  }

  @Test
  public void testClickOnRecordTag() throws Exception {
    tFixture.clickOnMenu("News");
    assertTrue(tFixture.verifyNewsFeedContainingTextTaggedWithIsPresent(randString, randString));
    tFixture.clickOnNewsFeedRecordTag(randString, randString);
  }

  @Test
  public void testVerifyFeed() {
    tFixture.clickOnMenu("News");
    assertTrue(tFixture.verifyNewsFeedContainingTextIsPresent(randString));
    assertTrue(tFixture.verifyNewsFeedContainingTextIsNotPresent("Not present"));
  }

  @Test
  public void testNewsItemComments() {
    tFixture.clickOnMenu("News");
    assertTrue(tFixture.verifyNewsFeedContainingTextCommentedWithIsPresent(randString, "Comment"));
  }

  @Test
  public void testMoreInfo() {
    tFixture.clickOnMenu("News");
    tFixture.toggleMoreInfoForNewsFeedContainingText(randString);
    assertTrue(tFixture.verifyNewsFeedContainingTextAndMoreInfoWithLabelAndValueIsPresent(randString, "Label", "Value"));
  }

  @Test
  public void testRegex() throws Exception {
    tFixture.clickOnMenu("News");
    assertEquals(tFixture.getRegexGroupFromNewsFeedContainingText("\\[([a-zA-Z0-9]{5})\\]", 1, randString), randString);
    assertEquals(tFixture.getRegexGroupFromNewsFeedContainingTextCommentedWith("\\[([0-9])\\]", 1, randString, "Comment"),
      randInt.toString());
  }

  @Test
  public void testSearch() throws Exception {
    tFixture.clickOnMenu("News");
    tFixture.searchFor(randString);
    assertTrue(tFixture.verifyNewsFeedContainingTextIsPresent(randString));
  }

  @AfterClass
  public static void tearDownNews() throws Exception {
    tFixture.clickOnMenu("News");
    // tFixture.deleteNewsPost(randString); //Only for admin accounts
  }
}
