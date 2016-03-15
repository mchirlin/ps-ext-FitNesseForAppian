package com.appiancorp.ps.automatedtest.fixture.tempo;

import static org.junit.Assert.assertTrue;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.appiancorp.ps.automatedtest.fixture.TempoFixtureTest;

public class TasksFixtureTest extends TempoFixtureTest {    

    @BeforeClass
    public static void setUpTasks() throws Exception {
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
        
        assertTrue(tFixture.clickOnTaskReport("Task Report"));
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
    public static void tearDownTasks() throws Exception {
        tFixture.clickOnMenu("Tasks");
        tFixture.clickOnTask("Input Automated Test Data");
        tFixture.clickOnButton("Cancel");
    }
}