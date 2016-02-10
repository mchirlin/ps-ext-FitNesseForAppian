package com.appiancorp.ps.automatedtest.fixture.tempo;

import static org.junit.Assert.assertTrue;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.appiancorp.ps.automatedtest.fixture.TempoFixture;

public class RecordsFixtureTest {    
    
    private static TempoFixture tFixture;
    private static String randString;
    
    @BeforeClass
    public static void setUp() throws Exception {
      tFixture = new TempoFixture();

      tFixture.setupSeleniumWebDriverWithBrowser("FIREFOX");
      tFixture.setAppianUrlTo("https://apacdemo.appiancloud.com/suite");
      tFixture.setTimeoutSecondsTo("15");
      tFixture.setAppianVersionTo("16.1");
      tFixture.setAppianLocaleTo("en_GB");;
      
      tFixture.loginWithUsernameAndPassword("michael.chirlin@appian.com", "password1");
      
      tFixture.clickOnMenu("Actions");
      tFixture.clickOnAction("Automated Testing Input");
      
      randString = tFixture.getRandomString(5);
      tFixture.populateFieldWith("Text Field Test", new String[]{randString});
      tFixture.populateFieldWith("Integer Field Test", new String[]{"5"});
      tFixture.populateFieldWith("Decimal Field Test", new String[]{"123.45"});
      tFixture.populateFieldWith("Date and Time Test", new String[]{"2010-01-01 02:00"});
      
      tFixture.clickOnButton("Submit");
    }
    
    @Test
    public void testRecordsFixture() throws Exception {
        tFixture.clickOnMenu("Records");
        
        assertTrue(tFixture.clickOnRecordType("Automated Testing Records"));
        assertTrue(tFixture.verifyRecordTypeUserFilterIsPresent("Past"));
        assertTrue(tFixture.clickOnRecordTypeUserFilter("Past"));
        assertTrue(tFixture.verifyRecordIsPresent(randString));
        assertTrue(tFixture.verifyRecordIsNotPresent("Not present"));
        assertTrue(tFixture.clickOnRecord(randString));
        assertTrue(tFixture.clickOnRecordView("Related Actions"));
        assertTrue(tFixture.verifyRecordRelatedActionIsPresent("AUT Data Input Test"));
        assertTrue(tFixture.verifyRecordRelatedActionIsNotPresent("Not present"));
        
        tFixture.waitForSeconds("1");
    }
    
    @Test
    public void testClickOnRecordItemRelatedActionShortcut() throws Exception {
        tFixture.clickOnMenu("Records");
        
        assertTrue(tFixture.clickOnRecordType("Automated Testing Records"));
        assertTrue(tFixture.clickOnRecordTypeUserFilter("Past"));
        assertTrue(tFixture.clickOnRecord(randString));
        assertTrue(tFixture.clickOnRecordRelatedAction("AUT Data Input Test"));
        
        tFixture.waitForSeconds("1");
    }
    
    @Test
    public void testRecordGridList() throws Exception {
        tFixture.clickOnMenu("Records");
        tFixture.clickOnRecordType("Automated Test Grid");
        
        assertTrue(tFixture.sortRecordGridByColumn("Test Text"));
        assertTrue(tFixture.sortRecordGridByColumn("Test Int"));
        assertTrue(tFixture.sortRecordGridByColumn("Test Decimal"));
        assertTrue(tFixture.sortRecordGridByColumn("Test Date Time"));
        
        assertTrue(tFixture.clickOnRecordGridNaviation("Next"));
        assertTrue(tFixture.clickOnRecordGridNaviation("Previous"));
        assertTrue(tFixture.clickOnRecordGridNaviation("Last"));
        assertTrue(tFixture.clickOnRecordGridNaviation("First"));
        
        assertTrue(tFixture.searchFor(randString));
        assertTrue(tFixture.verifyRecordIsPresent(randString));
        assertTrue(tFixture.verifyRecordIsNotPresent("not "+ randString));
        assertTrue(tFixture.clickOnRecord(randString));
    }
    
    @AfterClass
    public static void tearDown() throws Exception {
        tFixture.logout();
        tFixture.tearDownSeleniumWebDriver();
    }
}