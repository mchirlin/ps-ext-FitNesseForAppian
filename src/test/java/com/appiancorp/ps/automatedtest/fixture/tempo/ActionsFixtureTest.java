package com.appiancorp.ps.automatedtest.fixture.tempo;

import static org.junit.Assert.assertTrue;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.appiancorp.ps.automatedtest.fixture.tempo.TempoFixture;

public class ActionsFixtureTest {    
    
    private static TempoFixture tFixture;
    private static ActionsFixture aFixture;
    private static InterfaceFixture iFixture;
    
    @BeforeClass
    public static void setUp() throws Exception {
      tFixture = new TempoFixture();
      aFixture = new ActionsFixture();
      iFixture = new InterfaceFixture();
      
      tFixture.setupSeleniumWebDriverWithBrowser("FIREFOX");
      tFixture.setAppianUrlTo("https://apacdemo.appiancloud.com/suite");
      tFixture.setTimeoutSecondsTo("10");
      
      tFixture.loginWithUsernameAndPassword("michael.chirlin@appian.com", "password1");
    }
    
    @Test
    public void testActionsFixture() throws Exception {
        tFixture.clickOnMenu("Actions");
        
        assertTrue(aFixture.clickOnAction("Automated Testing"));
        
        iFixture.clickOnButton("Cancel");
        
        assertTrue(aFixture.verifyActionCompleted());
    }
    
    @AfterClass
    public static void tearDown() throws Exception {
        tFixture.logout();
        tFixture.tearDownSeleniumWebDriver();
    }
}