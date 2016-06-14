package com.appiancorp.ps.automatedtest.test;

import org.apache.log4j.Logger;
import org.junit.BeforeClass;

import com.appiancorp.ps.automatedtest.fixture.SitesFixture;

public abstract class AbstractTest {

  private static final Logger LOG = Logger.getLogger(AbstractTest.class);
  public static SitesFixture fixture;

  protected static String TEST_BROWSER = "FIREFOX";
  protected static String TEST_SITE_URL = "https://ps-sandbox1.appiancloud.com/suite/";
  protected static String TEST_SITE_VERSION = "16.2";
  protected static String TEST_SITE_LOCALE = "en_US";
  protected static String TEST_ROLE = "role.basic_user";
  protected static String TEST_USERNAME = "test.user";
  protected static String TEST_PASSWORD = "password2";
  protected static Integer TEST_TIMEOUT = 10;

  @BeforeClass
  public static void setUp() throws Exception {
    LOG.debug("Setting up Fixture");

    fixture = new SitesFixture();
  }
}
