package com.appiancorp.ps.automatedtest.fixture;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.appiancorp.ps.automatedtest.exception.IllegalArgumentTestException;

public class BaseFixtureTest extends FixtureTest {

  private static BaseFixture bFixture;

  @BeforeClass
  public static void setUp() throws Exception {
    bFixture = new TempoFixture();
  }

  @Test
  public void testSetAppianUrlTo() throws Exception {
    bFixture.setAppianUrlTo(TEST_SITE_URL);
    assertEquals(bFixture.getSettings().getUrl(), TEST_SITE_URL);
  }

  @Test
  public void testSetStartDatetime() throws Exception {
    bFixture.setStartDatetime();
  }

  @Test
  public void testSetAppianLocaleTo() throws Exception {
    bFixture.setAppianLocaleTo("en_GB");
    assertEquals(bFixture.getSettings().getDateFormat(), "dd/MM/yyyy");
    assertEquals(bFixture.getSettings().getDateDisplayFormat(), "d MMM yyyy");
    assertEquals(bFixture.getSettings().getTimeFormat(), "HH:mm");
    assertEquals(bFixture.getSettings().getTimeDisplayFormat(), "HH:mm");
    assertEquals(bFixture.getSettings().getDatetimeFormat(), "dd/MM/yyyy HH:mm");
    assertEquals(bFixture.getSettings().getDatetimeDisplayFormat(), "d MMM yyyy HH:mm");

    bFixture.setAppianLocaleTo("en_US");
    assertEquals(bFixture.getSettings().getDateFormat(), "M/d/yyyy");
    assertEquals(bFixture.getSettings().getDateDisplayFormat(), "MMM d, yyyy");
    assertEquals(bFixture.getSettings().getTimeFormat(), "h:mm aa");
    assertEquals(bFixture.getSettings().getTimeDisplayFormat(), "h:mm aa");
    assertEquals(bFixture.getSettings().getDatetimeFormat(), "M/d/yyyy h:mm aa");
    assertEquals(bFixture.getSettings().getDatetimeDisplayFormat(), "MMM d, yyyy, h:mm aa");
  }

  @Test
  public void testSetTimeoutSecondsTo() throws Exception {
    bFixture.setTimeoutSecondsTo(10);
    assertEquals(bFixture.getSettings().getTimeoutSeconds(), 10);
  }

  @Test
  public void testSetScreenshotPathTo() throws Exception {
    bFixture.setScreenshotPathTo("C:\\AutomatedTesting\\screenshots\\");
  }

  @Test
  public void testOpen() throws Exception {
    bFixture.setupSeleniumWebDriverWithBrowser(TEST_BROWSER);
    assertTrue(bFixture.open("http://google.com"));
    bFixture.tearDownSeleniumWebDriver();
  }

  @Test
  public void testLoginIntoWithUsernameAndPassword() throws Exception {
    bFixture.setupSeleniumWebDriverWithBrowser(TEST_BROWSER);
    bFixture.loginIntoWithUsernameAndPassword(TEST_SITE_URL, TEST_USERNAME, TEST_PASSWORD);
    bFixture.tearDownSeleniumWebDriver();
  }

  @Test
  public void testLoginWithUsernameAndPassword() throws Exception {
    bFixture.setupSeleniumWebDriverWithBrowser(TEST_BROWSER);
    bFixture.setAppianUrlTo(TEST_SITE_URL);
    bFixture.loginWithUsernameAndPassword(TEST_USERNAME, TEST_PASSWORD);
    bFixture.tearDownSeleniumWebDriver();
  }

  @Test
  public void testLoginWithTermsWithUsernameAndPassword() throws Exception {
    // TODO Find test site with terms
  }

  @Test
  public void testWaitForSecondsMinutesHours() {
    // TODO Create test case
  }

  @Test
  public void testWaitForWorking() {
    // TODO Create test case
  }

  @Test
  public void testGetRandomString() {
    assertTrue(bFixture.getRandomString(7).length() == 7);
  }

  @Test
  public void testGetRandomIntFromTo() {
    int randomInt = bFixture.getRandomIntegerFromTo(0, 10);
    assertTrue((randomInt < 10) && (randomInt >= 0));

    try {
      bFixture.getRandomIntegerFromTo(10, 0);
      fail("Should have thrown illegal argument exception");
    } catch (IllegalArgumentTestException e) {
    }
  }

  @Test
  public void testGetRandomDecimalFromTo() {
    double randomDec = bFixture.getRandomDecimalFromTo(0, 10);
    assertTrue((randomDec < 10) && (randomDec >= 0));

    try {
      bFixture.getRandomDecimalFromTo(10, 0);
      fail("Should have thrown illegal argument exception");
    } catch (IllegalArgumentTestException e) {
    }
  }

  @Test
  public void testGetRandomDecimalFromToWith() {
    double randomDec = bFixture.getRandomDecimalFromToWith(0.1, 10.01, 6);
    assertTrue((randomDec < 10.01) && (randomDec >= 0.1));

    try {
      bFixture.getRandomDecimalFromToWith(10, 0, 6);
      fail("Should have thrown illegal argument exception");
    } catch (IllegalArgumentTestException e) {
    }
  }

  @AfterClass
  public static void tearDown() throws Exception {

  }
}