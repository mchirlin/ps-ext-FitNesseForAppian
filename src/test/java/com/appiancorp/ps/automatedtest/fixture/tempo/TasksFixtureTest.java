package com.appiancorp.ps.automatedtest.fixture.tempo;

import static org.junit.Assert.assertTrue;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.appiancorp.ps.automatedtest.fixture.TempoFixture;

public class TasksFixtureTest {    
    
    private static TempoFixture tFixture;

    @BeforeClass
    public static void setUp() throws Exception {
      tFixture = new TempoFixture();
      
      tFixture.setupSeleniumWebDriverWithBrowser("FIREFOX");
      tFixture.setAppianUrlTo("https://apacdemo.appiancloud.com/suite");
      tFixture.setTimeoutSecondsTo("10");
      tFixture.setAppianVersionTo("16.1");
      tFixture.setAppianLocaleTo("en_GB");
      
      tFixture.loginWithUsernameAndPassword("michael.chirlin@appian.com", "password1");
      
      tFixture.clickOnMenu("Actions");
      tFixture.clickOnAction("Automated Testing Input");
    }
    
    @Test
    public void testClickOnTask() throws Exception {
        tFixture.clickOnMenu("Tasks");
        
        assertTrue(tFixture.clickOnTask("Input Automated Test Data"));
    }
    
    @Test
    public void testClickOnTaskReport() throws Exception {
        tFixture.clickOnMenu("Tasks");
        
        assertTrue(tFixture.clickOnTaskReport("My Current Tasks"));
    }
    
    @Test
    public void testVerifyTaskIsPresent() throws Exception {
        tFixture.clickOnMenu("Tasks");
        
        assertTrue(tFixture.verifyTaskIsPresent("Input Automated Test Data"));
        assertTrue(tFixture.verifyTaskIsNotPresent("Not present"));
    }
    
    @Test
    public void testVerifyTaskHasDeadlineOf() throws Exception {
        tFixture.clickOnMenu("Tasks");
        
        assertTrue(tFixture.verifyTaskHasDeadlineOf("Input Automated Test Data", "1h"));
    }
    
    @AfterClass
    public static void tearDown() throws Exception {
        tFixture.clickOnMenu("Tasks");
        tFixture.clickOnTask("Input Automated Test Data");
        tFixture.clickOnButton("Cancel");
        tFixture.logout();
        tFixture.tearDownSeleniumWebDriver();
    }
}