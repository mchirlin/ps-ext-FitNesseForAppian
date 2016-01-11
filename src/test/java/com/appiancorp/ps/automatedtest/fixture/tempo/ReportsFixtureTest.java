package com.appiancorp.ps.automatedtest.fixture.tempo;

import static org.junit.Assert.assertTrue;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.appiancorp.ps.automatedtest.fixture.tempo.TempoFixture;

public class ReportsFixtureTest {    
    
    private static TempoFixture tFixture;
    private static ReportsFixture rFixture;
    
    @BeforeClass
    public static void setUp() throws Exception {
      tFixture = new TempoFixture();
      rFixture = new ReportsFixture();
      
      tFixture.setupSeleniumWebDriverWithBrowser("FIREFOX");
      tFixture.setAppianUrlTo("https://apacdemo.appiancloud.com/suite");
      tFixture.setTimeoutSecondsTo("10");
      
      tFixture.loginWithUsernameAndPassword("michael.chirlin@appian.com", "password1");
    }
    
    @Test
    public void testReportsFixture() throws Exception {
        tFixture.clickOnMenu("Reports");
        
        assertTrue(rFixture.verifyReportIsPresent("Orders Dashboard"));
        
        assertTrue(rFixture.verifyReportIsNotPresent("Not Orders Dashboard"));

        assertTrue(rFixture.clickOnReport("Orders Dashboard"));
    }
    
    @AfterClass
    public static void tearDown() throws Exception {
        tFixture.logout();
        tFixture.tearDownSeleniumWebDriver();
    }
}