package com.appiancorp.ps.automatedtest.fixture.tempo;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.appiancorp.ps.automatedtest.fixture.TempoFixtureTest;

public class ActionsFixtureTest extends TempoFixtureTest {

  @Test
  public void testActionsFixture() throws Exception {
    tFixture.clickOnMenu("Actions");

    assertTrue(tFixture.verifyActionIsPresent("Automated Testing"));
    assertTrue(tFixture.verifyActionIsNotPresent("Not Automated Testing"));
    tFixture.clickOnAction("Automated Testing");

    tFixture.clickOnButton("Cancel");
  }

  @Test
  public void testClickOnAppFilter() throws Exception {
    tFixture.clickOnMenu("Actions");
    tFixture.clickOnApplicationFilter("Automated");
  }
}