package com.appiancorp.ps.automatedtest.test;

import org.apache.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;

public class AbstractInterfaceTest extends AbstractLoginTest {

  private static final Logger LOG = Logger.getLogger(AbstractInterfaceTest.class);
  private static boolean isSetUp = false;

  @BeforeClass
  public static void setUpInterface() throws Exception {
    if (!isSetUp) {
      LOG.debug("Opening 'All Fields' Form");

      fixture.clickOnMenu("Actions");
      fixture.clickOnAction("All Fields");

      isSetUp = true;
    }
  }

  @AfterClass
  public static void tearDownInterface() throws Exception {
  }
}