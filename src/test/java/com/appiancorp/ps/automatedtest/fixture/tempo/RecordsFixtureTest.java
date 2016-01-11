package com.appiancorp.ps.automatedtest.fixture.tempo;

import static org.junit.Assert.assertTrue;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.appiancorp.ps.automatedtest.fixture.tempo.TempoFixture;

public class RecordsFixtureTest {    
    
    private static TempoFixture tFixture;
    private static RecordsFixture rFixture;
    
    @BeforeClass
    public static void setUp() throws Exception {
      tFixture = new TempoFixture();
      rFixture = new RecordsFixture();
      
      tFixture.setupSeleniumWebDriverWithBrowser("FIREFOX");
      tFixture.setAppianUrlTo("https://apacdemo.appiancloud.com/suite");
      tFixture.setTimeoutSecondsTo("10");
      
      tFixture.loginWithUsernameAndPassword("michael.chirlin@appian.com", "password1");
    }
    
    @Test
    public void testRecordsFixture() throws Exception {
        tFixture.clickOnMenu("Records");
        
        assertTrue(rFixture.clickOnRecordList("Users"));
        
        assertTrue(rFixture.verifyRecordListFacetOptionIsPresent("Active"));
        
        assertTrue(rFixture.clickOnRecordListFacetOption("Active"));
        
        assertTrue(rFixture.verifyRecordItemIsPresent("Michael Chirlin"));

        assertTrue(rFixture.verifyRecordItemIsNotPresent("Michael Churlish"));
        
        assertTrue(rFixture.clickOnRecordItem("Michael Chirlin"));
        
        assertTrue(rFixture.clickOnRecordItemFacet("Related Actions"));
        
        //TODO Add verifyRecordItemRelatedActionIsPresent
        
        assertTrue(rFixture.verifyRecordItemRelatedActionIsNotPresent("Not a related action"));
    }
    
    @AfterClass
    public static void tearDown() throws Exception {
        tFixture.logout();
        tFixture.tearDownSeleniumWebDriver();
    }
}