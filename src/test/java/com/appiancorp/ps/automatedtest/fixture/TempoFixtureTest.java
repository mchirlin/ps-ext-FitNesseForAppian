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
      tFixture.setTimeoutSecondsTo(10);
      tFixture.setAppianVersionTo("16.1");
      tFixture.setAppianLocaleTo("en_GB");
      
      tFixture.loginWithUsernameAndPassword("michael.chirlin@appian.com", "password1");
    }
    
    @Test
    public void testTempoFixture() throws Exception {
        assertTrue(tFixture.clickOnMenu("News"));
        assertTrue(tFixture.searchFor("News Search"));
        assertTrue(tFixture.clickOnMenu("Reports"));
        assertTrue(tFixture.searchFor("Report Search"));
        assertTrue(tFixture.clickOnMenu("Records"));
        assertTrue(tFixture.clickOnRecordType("Automated Testing Records"));
        assertTrue(tFixture.searchFor("Records Search"));
        assertTrue(tFixture.clickOnMenu("Tasks"));
    }
    
    @AfterClass
    public static void tearDown() throws Exception {
        tFixture.logout();
        tFixture.tearDownSeleniumWebDriver();
    }
}