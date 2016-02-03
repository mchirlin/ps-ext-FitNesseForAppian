package com.appiancorp.ps.automatedtest.fixture.tempo;

import static org.junit.Assert.assertTrue;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.appiancorp.ps.automatedtest.fixture.TempoFixture;

public class ActionsFixtureTest {    
    
    private static TempoFixture tFixture;
    
    @BeforeClass
    public static void setUp() throws Exception {
      tFixture = new TempoFixture();
      
      tFixture.setupSeleniumWebDriverWithBrowser("FIREFOX");
      tFixture.setAppianUrlTo("https://apacdemo.appiancloud.com/suite");
      tFixture.setTimeoutSecondsTo("10");
      tFixture.setAppianVersionTo("16.1");
      
      tFixture.setDateFormatStringTo("dd/MM/yyyy");
      tFixture.setTimeFormatStringTo("HH:mm");
      tFixture.setDateDisplayFormatStringTo("MMM d yyyy");
      tFixture.setTimeDisplayFormatStringTo("HH:mm");
      
      tFixture.loginWithUsernameAndPassword("michael.chirlin@appian.com", "password1");
    }
    
    @Test
    public void testActionsFixture() throws Exception {
        tFixture.clickOnMenu("Actions");
        
        assertTrue(tFixture.verifyActionIsPresent("Automated Testing"));

        assertTrue(tFixture.verifyActionIsNotPresent("Not Automated Testing"));
        
        assertTrue(tFixture.clickOnAction("Automated Testing"));
        
        tFixture.clickOnButton("Cancel");
        
        assertTrue(tFixture.verifyActionCompleted());
    }
    
    @AfterClass
    public static void tearDown() throws Exception {
        tFixture.logout();
        tFixture.tearDownSeleniumWebDriver();
    }
}