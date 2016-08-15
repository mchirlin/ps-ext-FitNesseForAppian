package com.appiancorp.ps.automatedtest.test;

import org.apache.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;

public class AbstractLoginTest extends AbstractTest {

  private static final Logger LOG = Logger.getLogger(AbstractLoginTest.class);

  @BeforeClass
  public static void setUpLogin() throws Exception {
    LOG.debug("Setting Up Login");

    fixture.setupWithBrowser(TEST_BROWSER);
    fixture.setAppianUrlTo(TEST_SITE_URL);
    fixture.setTimeoutSecondsTo(TEST_TIMEOUT);
    fixture.setAppianVersionTo(TEST_SITE_VERSION);
    fixture.setAppianLocaleTo(TEST_SITE_LOCALE);
    fixture.loginWithUsername(TEST_USERNAME);
  }

  @AfterClass
  public static void tearDown() throws Exception {
    fixture.logout();
    fixture.tearDown();
  }
}
