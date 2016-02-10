package com.appiancorp.ps.automatedtest.fixture.tempo;

import static org.junit.Assert.assertTrue;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.appiancorp.ps.automatedtest.fixture.TempoFixture;

public class ReportsFixtureTest {    
    
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
    public void testReportsFixture() throws Exception {
        tFixture.clickOnMenu("Reports");
        
        assertTrue(tFixture.verifyReportIsPresent("Automated Test Report"));
        assertTrue(tFixture.verifyReportIsNotPresent("Not Report"));
        assertTrue(tFixture.clickOnReport("Automated Test Report"));
    }
    
    @Test
    public void testSearch() throws Exception {
        assertTrue(tFixture.clickOnMenu("Reports"));
        assertTrue(tFixture.searchFor("Automated Test Report"));
        assertTrue(tFixture.verifyReportIsPresent("Automated Test Report"));
    }
    
    @AfterClass
    public static void tearDown() throws Exception {
        tFixture.logout();
        tFixture.tearDownSeleniumWebDriver();
    }
}