package com.appiancorp.ps.automatedtest.test;

import org.apache.log4j.Logger;
import org.junit.BeforeClass;

import com.appiancorp.ps.automatedtest.fixture.SitesFixture;

public abstract class AbstractTest {

  private static final Logger LOG = Logger.getLogger(AbstractTest.class);
  public static SitesFixture fixture;

  protected static String TEST_BROWSER = System.getenv("browser") == null ? "FIREFOX" : System.getenv("browser");
  protected static String TEST_SITE_VERSION = System.getenv("version") == null ? "16.2" : System.getenv("version");
  protected static String TEST_SITE_URL;
  protected static String TEST_SITE_LOCALE = "en_US";
  protected static String TEST_ROLE = "role.basic_user";
  protected static String TEST_USERNAME = "test.user";
  protected static String TEST_PASSWORD = "password3";
  protected static Integer TEST_TIMEOUT = 10;
  static {
    switch (TEST_SITE_VERSION) {
      case "7.10":
        TEST_SITE_URL = "https://fitnesse-710.appianci.net/suite/";
        break;
      case "7.11":
        TEST_SITE_URL = "https://fitnesse-711.appianci.net/suite/";
        break;
      case "16.1":
        TEST_SITE_URL = "https://fitnesse-161.appianci.net/suite/";
        break;
      case "16.2":
        TEST_SITE_URL = "https://ps-sandbox1.appiancloud.com/suite/";
        break;
      case "16.3":
        TEST_SITE_URL = "https://fitnesse-163.appianci.net/suite/";
        break;
      default:
        TEST_SITE_URL = "https://ps-sandbox1.appiancloud.com/suite/";
    }
  }

  @BeforeClass
  public static void setUp() throws Exception {
    LOG.debug("Setting up Fixture");

    fixture = new SitesFixture();
  }

  public static boolean atLeastVersion(Double version) {
    return Double.parseDouble(TEST_SITE_VERSION) >= version;
  }

  public static boolean isBrowser(String browser) {
    return TEST_BROWSER.equals(browser);
  }
}
