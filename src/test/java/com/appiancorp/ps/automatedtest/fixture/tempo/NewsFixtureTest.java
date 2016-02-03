package com.appiancorp.ps.automatedtest.fixture.tempo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.appiancorp.ps.automatedtest.fixture.TempoFixture;

public class NewsFixtureTest {    
    
    private static TempoFixture tFixture;
    private static String randString;
    
    @BeforeClass
    public static void setUp() throws Exception {
      tFixture = new TempoFixture();
      
      tFixture.setupSeleniumWebDriverWithBrowser("FIREFOX");
      tFixture.setAppianUrlTo("https://apacdemo.appiancloud.com/suite");
      tFixture.setTimeoutSecondsTo("10");
      tFixture.setAppianVersionTo("16.1");
      
      tFixture.setDateFormatStringTo("dd/MM/yyyy");
      tFixture.setTimeFormatStringTo("HH:mm");
      tFixture.setDateDisplayFormatStringTo("MMM d yyyy");
      tFixture.setTimeDisplayFormatStringTo("HH:mm");
      
      tFixture.loginWithUsernameAndPassword("michael.chirlin@appian.com", "password1");
      
      tFixture.clickOnMenu("Actions");
      tFixture.clickOnAction("Automated Testing Input");
      
      randString = tFixture.getRandomString(5);
      tFixture.populateFieldWith("Text Field Test", new String[]{randString});
      tFixture.populateFieldWith("Integer Field Test", new String[]{"5"});
      tFixture.populateFieldWith("Decimal Field Test", new String[]{"123.45"});
      tFixture.populateFieldWith("Date and Time Test", new String[]{"04/02/2016 02:00"});
      
      tFixture.clickOnButton("Submit");
    }
    
    /** 
     * Whether or not these tests work is highly dependent on the current news feed status in the test site.
     * @throws Exception
     */
    @Test
    public void testNewsFixture() throws Exception {
        tFixture.clickOnMenu("News");        
        assertTrue(tFixture.verifyNewsFeedContainingTextIsPresent("Automated test message: " + randString));
        assertTrue(tFixture.verifyNewsFeedContainingTextIsNotPresent("Not present"));
        //TODO assertTrue(tFixture.toggleMoreInfoForNewsFeedContainingText("A test message: " + randString"));
        //TODO assertTrue(tFixture.verifyNewsFeedContainingTextAndMoreInfoWithLabelAndValueIsPresent("A test message: " + randString, "Total Hours", "42.0"));
        //TODO assertTrue(tFixture.verifyNewsFeedContainingTextTaggedWithIsPresent("A test message: " + randString, "536877593"));
        //TODO assertTrue(tFixture.verifyNewsFeedContainingTextCommentedWithIsPresent(A test message" + randString, randString));
        assertEquals(tFixture.getRegexFromNewsFeedContainingText(": [a-zA-Z0-9]{5}", "Automated test message: " + randString), ": " + randString);
        //TODO assertEquals(tFixture.getRegexFromNewsFeedContainingTextCommentedWith("", "", ""), "");
    }
    
    @Test 
    public void testClickOnRecordTag() throws Exception {
        tFixture.clickOnMenu("News");
        assertTrue(tFixture.clickOnNewsFeedRecordTag("Automated test message: " + randString, randString));
    }
    
    @AfterClass
    public static void tearDown() throws Exception {
        tFixture.logout();
        tFixture.tearDownSeleniumWebDriver();
    }
}