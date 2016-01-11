package com.appiancorp.ps.automatedtest.fixture.tempo;

import static org.junit.Assert.assertTrue;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.appiancorp.ps.automatedtest.fixture.TempoFixture;

public class RecordsFixtureTest {    
    
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
    public void testRecordsFixture() throws Exception {
        tFixture.clickOnMenu("Records");
        
        assertTrue(tFixture.clickOnRecordList("Users"));
        
        assertTrue(tFixture.verifyRecordListFacetOptionIsPresent("Active"));
        
        assertTrue(tFixture.clickOnRecordListFacetOption("Active"));
        
        assertTrue(tFixture.verifyRecordItemIsPresent("Michael Chirlin"));

        assertTrue(tFixture.verifyRecordItemIsNotPresent("Michael Churlish"));
        
        assertTrue(tFixture.clickOnRecordItem("Michael Chirlin"));
        
        assertTrue(tFixture.clickOnRecordItemFacet("Related Actions"));
        
        //TODO Add verifyRecordItemRelatedActionIsPresent
        
        assertTrue(tFixture.verifyRecordItemRelatedActionIsNotPresent("Not a related action"));
    }
    
    @AfterClass
    public static void tearDown() throws Exception {
        tFixture.logout();
        tFixture.tearDownSeleniumWebDriver();
    }
}