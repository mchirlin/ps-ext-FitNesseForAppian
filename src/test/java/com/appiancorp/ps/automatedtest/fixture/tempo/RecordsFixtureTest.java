package com.appiancorp.ps.automatedtest.fixture.tempo;

import static org.junit.Assert.assertTrue;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.appiancorp.ps.automatedtest.fixture.TempoFixtureTest;

public class RecordsFixtureTest extends TempoFixtureTest{    
    
    private static String randString;
    
    @BeforeClass
    public static void setUpRecords() throws Exception {
      tFixture.clickOnMenu("Actions");
      tFixture.clickOnAction("Automated Testing Input");
      
      randString = tFixture.getRandomString(5);
      tFixture.populateFieldWith("Title", new String[]{randString});
      tFixture.populateFieldWith("Quantity", new String[]{"5"});
      tFixture.populateFieldWith("Price", new String[]{"123.45"});
      tFixture.populateFieldWith("Start Date", new String[]{"2010-01-01 02:00"});
      
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
    
    @Test
    public void testSearch() throws Exception {
        assertTrue(tFixture.clickOnMenu("Records"));
        assertTrue(tFixture.clickOnRecordType("Automated Testing Records"));
        assertTrue(tFixture.searchFor(randString));
        assertTrue(tFixture.verifyRecordIsPresent(randString));   
    }
    
    @Test
    public void testRecordGridList() throws Exception {
        tFixture.clickOnMenu("Records");
        tFixture.clickOnRecordType("Automated Test Grid");
        
        assertTrue(tFixture.sortRecordGridByColumn("Title"));
        assertTrue(tFixture.sortRecordGridByColumn("Quantity"));
        assertTrue(tFixture.sortRecordGridByColumn("Price"));
        assertTrue(tFixture.sortRecordGridByColumn("Start Date"));
        
        assertTrue(tFixture.clickOnRecordGridNaviation("Next"));
        assertTrue(tFixture.clickOnRecordGridNaviation("Previous"));
        assertTrue(tFixture.clickOnRecordGridNaviation("Last"));
        assertTrue(tFixture.clickOnRecordGridNaviation("First"));
        
        assertTrue(tFixture.searchFor(randString));
        assertTrue(tFixture.verifyRecordIsPresent(randString));
        assertTrue(tFixture.verifyRecordIsNotPresent("not "+ randString));
        assertTrue(tFixture.clickOnRecord(randString));
    }
    @Test
    public void testRecordsTypeByNameIndex() throws Exception {
        tFixture.clickOnMenu("Records");
        assertTrue(tFixture.clickOnRecordType("Auto[1]"));
        
        tFixture.clickOnMenu("Records");
        assertTrue(tFixture.clickOnRecordType("Auto[2]"));
    }
    @AfterClass
    public static void tearDownRecords() throws Exception {
        tFixture.clickOnMenu("News");
//        tFixture.deleteNewsPost(randString);
    }
}