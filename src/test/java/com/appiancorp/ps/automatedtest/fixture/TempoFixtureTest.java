package com.appiancorp.ps.automatedtest.fixture;

import org.junit.AfterClass;
import org.junit.BeforeClass;

public class TempoFixtureTest {

    protected static TempoFixture tFixture;
    
    @BeforeClass
    public static void setUp() throws Exception {
      tFixture = new TempoFixture();
      
      tFixture.setupSeleniumWebDriverWithBrowser("FIREFOX");
      tFixture.setAppianUrlTo("https://ps-sandbox1.appiancloud.com/suite/");
      tFixture.setTimeoutSecondsTo(20);
      tFixture.setAppianVersionTo("16.1");
      tFixture.setAppianLocaleTo("en_US");
      tFixture.loginWithUsernameAndPassword("test.user", "password1");
    }
    
    @AfterClass
    public static void tearDown() throws Exception {
        tFixture.logout();
        tFixture.tearDownSeleniumWebDriver();
    }
}