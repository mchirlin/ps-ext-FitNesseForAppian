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
      
      tFixture.loginWithUsernameAndPassword("michael.chirlin@appian.com", "password1");
    }
    
    @Test
    public void testClickOnTask() throws Exception {
        tFixture.clickOnMenu("Tasks");
        
        assertTrue(tFixture.clickOnTask("Associate Receipts"));
    }
    
    @Test
    public void testClickOnTaskReport() throws Exception {
        tFixture.clickOnMenu("Tasks");
        
        assertTrue(tFixture.clickOnTaskReport("My Current Tasks"));
    }
    
    @Test
    public void testVerifyTaskIsPresent() throws Exception {
        tFixture.clickOnMenu("Tasks");
        
        assertTrue(tFixture.verifyTaskIsPresent("Associate Receipts"));
        assertTrue(tFixture.verifyTaskIsNotPresent("Not a task"));
    }
    
    @Test
    public void testVerifyTaskHasDeadlineOf() throws Exception {
        tFixture.clickOnMenu("Tasks");
        
        // TODO Create task with deadline
    }
    
    @AfterClass
    public static void tearDown() throws Exception {
        tFixture.logout();
        tFixture.tearDownSeleniumWebDriver();
    }
}