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
    }
    
    @Test
    public void testClickOnRecordItemRelatedActionShortcut() throws Exception {
        tFixture.clickOnMenu("Records");
        
        assertTrue(tFixture.clickOnRecordType("Automated Testing Records"));
        assertTrue(tFixture.clickOnRecordTypeUserFilter("Past"));
        assertTrue(tFixture.clickOnRecord(randString));
        assertTrue(tFixture.clickOnRecordRelatedAction("AUT Data Input Test"));
        
        tFixture.clickOnButton("Cancel");
    }    
    
    @AfterClass
    public static void tearDown() throws Exception {
        tFixture.clickOnMenu("News");
        tFixture.deleteNewsPost(randString);
        tFixture.logout();
        tFixture.tearDownSeleniumWebDriver();
    }
}