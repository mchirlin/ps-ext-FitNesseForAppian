package com.appiancorp.ps.automatedtest.fixture.tempo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.appiancorp.ps.automatedtest.fixture.TempoFixture;

public class NewsFixtureTest {    
    
    private static TempoFixture tFixture;
    
    @BeforeClass
    public static void setUp() throws Exception {
      tFixture = new TempoFixture();
      
      tFixture.setupSeleniumWebDriverWithBrowser("FIREFOX");
      tFixture.setAppianUrlTo("https://apacdemo.appiancloud.com/suite");
      tFixture.setTimeoutSecondsTo("10");
      
      tFixture.loginWithUsernameAndPassword("michael.chirlin@appian.com", "password1");
    }
    
    /** 
     * Whether or not these tests work is highly dependent on the current news feed status in the test site.
     * @throws Exception
     */
    @Test
    public void testNewsFixture() throws Exception {
        tFixture.clickOnMenu("News");
        
        assertTrue(tFixture.verifyNewsFeedContainingTextIsPresent("A timesheet"));

        assertTrue(tFixture.verifyNewsFeedContainingTextIsNotPresent("Not present"));
        
        assertTrue(tFixture.toggleMoreInfoForNewsFeedContainingText("A timesheet"));
        
        assertTrue(tFixture.verifyNewsFeedContainingTextAndMoreInfoWithLabelAndValueIsPresent("A timesheet", "Total Hours", "42.0"));
         
        assertTrue(tFixture.verifyNewsFeedContainingTextTaggedWithIsPresent("A New Investigation", "536877593"));
        
        assertTrue(tFixture.verifyNewsFeedContainingTextCommentedWithIsPresent("A New Investigation", "Planning completed."));
        
        assertEquals(tFixture.getRegexFromNewsFeedContainingText("[0-9]{2}/[0-9]{2}/[0-9]{4}", "A timesheet"), "09/01/2016");
        
        assertEquals(tFixture.getRegexFromNewsFeedContainingTextCommentedWith("assigned to [A-z ]+", "A New Investigation", "Planning completed."), "assigned to Ray Croxon");
    }
    
    @AfterClass
    public static void tearDown() throws Exception {
        tFixture.logout();
        tFixture.tearDownSeleniumWebDriver();
    }
}