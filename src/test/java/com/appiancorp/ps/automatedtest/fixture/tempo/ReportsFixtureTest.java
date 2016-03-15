package com.appiancorp.ps.automatedtest.fixture.tempo;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.appiancorp.ps.automatedtest.fixture.TempoFixtureTest;

public class ReportsFixtureTest extends TempoFixtureTest {

  @Test
  public void testReportsFixture() throws Exception {
    tFixture.clickOnMenu("Reports");

    assertTrue(tFixture.verifyReportIsPresent("Automated Test Report"));
    assertTrue(tFixture.verifyReportIsNotPresent("Not Report"));
    tFixture.clickOnReport("Automated Test Report");
  }

  @Test
  public void testSearch() throws Exception {
    tFixture.clickOnMenu("Reports");
    tFixture.searchFor("Automated Test Report");
    assertTrue(tFixture.verifyReportIsPresent("Automated Test Report"));
  }
}