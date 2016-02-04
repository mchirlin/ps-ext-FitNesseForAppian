package com.appiancorp.ps.automatedtest.fixture;

import static org.junit.Assert.assertTrue;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class TempoFixtureTest {

    private static TempoFixture tFixture;
    
    @BeforeClass
    public static void setUp() throws Exception {
      tFixture = new TempoFixture();
      
      tFixture.setupSeleniumWebDriverWithBrowser("FIREFOX");
      tFixture.setAppianUrlTo("https://apacdemo.appiancloud.com/suite");
      tFixture.setTimeoutSecondsTo("10");
      
      tFixture.loginWithUsernameAndPassword("michael.chirlin@appian.com", "password1");
    }
    
    @Test
    public void testTempoFixture() throws Exception {
        assertTrue(tFixture.clickOnMenu("News"));
        assertTrue(tFixture.populateSearchWith("Test Text"));
        assertTrue(tFixture.clickOnMenu("Reports"));
        assertTrue(tFixture.populateSearchWith("dashboard"));
        assertTrue(tFixture.clickOnMenu("Records"));
        //TODO create a test case for populating search for record with a search term.  Unable to test b/c apacdemo is on 16.1
        assertTrue(tFixture.clickOnMenu("Tasks"));
    }
    
    @AfterClass
    public static void tearDown() throws Exception {
        tFixture.logout();
        tFixture.tearDownSeleniumWebDriver();
    }
}