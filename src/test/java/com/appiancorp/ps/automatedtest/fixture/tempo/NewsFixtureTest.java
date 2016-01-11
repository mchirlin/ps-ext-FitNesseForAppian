package com.appiancorp.ps.automatedtest.fixture.tempo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.appiancorp.ps.automatedtest.fixture.tempo.TempoFixture;

public class NewsFixtureTest {    
    
    private static TempoFixture tFixture;
    private static NewsFixture nFixture;
    
    @BeforeClass
    public static void setUp() throws Exception {
      tFixture = new TempoFixture();
      nFixture = new NewsFixture();
      
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
        
        assertTrue(nFixture.verifyNewsFeedContainingTextIsPresent("A timesheet"));

        assertTrue(nFixture.verifyNewsFeedContainingTextIsNotPresent("Not present"));
        
        assertTrue(nFixture.toggleMoreInfoForNewsFeedContainingText("A timesheet"));
        
        assertTrue(nFixture.verifyNewsFeedContainingTextAndMoreInfoWithLabelAndValueIsPresent("A timesheet", "Total Hours", "42.0"));
         
        assertTrue(nFixture.verifyNewsFeedContainingTextTaggedWithIsPresent("A New Investigation", "536877593"));
        
        assertTrue(nFixture.verifyNewsFeedContainingTextCommentedWithIsPresent("A New Investigation", "Planning completed."));
        
        assertEquals(nFixture.getRegexFromNewsFeedContainingText("[0-9]{2}/[0-9]{2}/[0-9]{4}", "A timesheet"), "09/01/2016");
        
        assertEquals(nFixture.getRegexFromNewsFeedContainingTextCommentedWith("assigned to [A-z ]+", "A New Investigation", "Planning completed."), "assigned to Ray Croxon");
    }
    
    @AfterClass
    public static void tearDown() throws Exception {
        tFixture.logout();
        tFixture.tearDownSeleniumWebDriver();
    }
}