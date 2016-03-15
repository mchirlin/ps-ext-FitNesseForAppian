package com.appiancorp.ps.automatedtest.fixture;

import org.junit.AfterClass;
import org.junit.BeforeClass;

public class TempoFixtureTest extends FixtureTest {

    protected static TempoFixture tFixture;
    
    @BeforeClass
    public static void setUp() throws Exception {
      tFixture = new TempoFixture();
      
      tFixture.setupSeleniumWebDriverWithBrowser(TEST_BROWSER);
      tFixture.setAppianUrlTo(TEST_SITE_URL);
      tFixture.setTimeoutSecondsTo(TEST_TIMEOUT);
      tFixture.setAppianVersionTo(TEST_SITE_VERSION);
      tFixture.setAppianLocaleTo(TEST_SITE_LOCALE);
      tFixture.loginWithUsernameAndPassword(TEST_USERNAME, TEST_PASSWORD);
    }
    
    @AfterClass
    public static void tearDown() throws Exception {
        tFixture.logout();
        tFixture.tearDownSeleniumWebDriver();
    }
}