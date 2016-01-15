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
      tFixture.setTimeoutSecondsTo("15");
      
      tFixture.loginWithUsernameAndPassword("michael.chirlin@appian.com", "password1");
    }
    
    @Test
    public void testRecordsFixture() throws Exception {
        tFixture.clickOnMenu("Records");
        
        assertTrue(tFixture.clickOnRecordList("Orders"));
        assertTrue(tFixture.verifyRecordListFacetOptionIsPresent("Open"));
        assertTrue(tFixture.clickOnRecordListFacetOption("Open"));
        assertTrue(tFixture.verifyRecordItemIsPresent("Tarkin Construction"));
        assertTrue(tFixture.verifyRecordItemIsNotPresent("Markin Construction"));
        assertTrue(tFixture.clickOnRecordItem("Tarkin Construction"));
        assertTrue(tFixture.clickOnRecordItemFacet("Related Actions"));
        assertTrue(tFixture.verifyRecordItemRelatedActionIsPresent("Update Order Status"));
        assertTrue(tFixture.verifyRecordItemRelatedActionIsNotPresent("Not a related action"));
        
        tFixture.waitForSeconds("1");
    }
    
    @Test
    public void testClickOnRecordItemRelatedActionShortcut() throws Exception {
        tFixture.clickOnMenu("Records");
        
        assertTrue(tFixture.clickOnRecordList("Orders"));
        assertTrue(tFixture.clickOnRecordListFacetOption("Open"));
        assertTrue(tFixture.clickOnRecordItem("Tarkin Construction"));
        assertTrue(tFixture.clickOnRecordItemRelatedAction("Update Order Status"));
        
        tFixture.waitForSeconds("1");
    }
    
    @AfterClass
    public static void tearDown() throws Exception {
        tFixture.logout();
        tFixture.tearDownSeleniumWebDriver();
    }
}