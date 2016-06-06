package com.appiancorp.ps.automatedtest.test;

import org.apache.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;

public class AbstractDataTest extends AbstractLoginTest {

  private static final Logger LOG = Logger.getLogger(AbstractDataTest.class);
  private static boolean isSetUp = false;
  protected static String randString;
  protected static Integer randInt;
  protected static Double randDecimal;

  @BeforeClass
  public static void setUpData() throws Exception {
    if (!isSetUp) {
      LOG.debug("Submitting new Record");

      fixture.clickOnMenu("Actions");
      fixture.clickOnAction("Automated Testing Input");

      randString = fixture.getRandomString(5);
      randInt = fixture.getRandomIntegerFromTo(0, 9);
      randDecimal = fixture.getRandomDecimalFromToWith(1.0, 2.0, 4);
      fixture.populateFieldWith("Title", new String[] { randString });
      fixture.populateFieldWith("Quantity", new String[] { randInt.toString() });
      fixture.populateFieldWith("Price", new String[] { randDecimal.toString() });
      fixture.populateFieldWith("Start Date", new String[] { "2016-02-04 02:00" });

      fixture.clickOnButton("Submit");

      isSetUp = true;
    }
  }

  @AfterClass
  public static void tearDownData() throws Exception {
  }
}
