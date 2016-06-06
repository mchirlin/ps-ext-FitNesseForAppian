package com.appiancorp.ps.automatedtest.fixture.tempo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.appiancorp.ps.automatedtest.fixture.TempoFixtureTest;

public class TasksFixtureTest extends TempoFixtureTest {

  @BeforeClass
  public static void setUpTasks() throws Exception {
    tFixture.clickOnMenu("Actions");
    tFixture.clickOnAction("All Fields");
  }

  @Test
  public void testClickOnTask() throws Exception {
    tFixture.clickOnMenu("Tasks");

    tFixture.clickOnTask("Input Interface Test");
  }

  @Test
  public void testClickOnTaskReport() throws Exception {
    tFixture.clickOnMenu("Tasks");

    tFixture.clickOnTaskReport("Task Report");
  }

  @Test
  public void testVerifyTaskIsPresent() throws Exception {
    tFixture.clickOnMenu("Tasks");

    assertTrue(tFixture.verifyTaskIsPresent("Input Interface Test"));
    assertTrue(tFixture.verifyTaskIsNotPresent("Not present"));
  }

  @Test
  public void testVerifyTaskHasDeadlineOf() throws Exception {
    tFixture.clickOnMenu("Tasks");

    assertTrue(tFixture.verifyTaskHasDeadlineOf("Input Interface Test", "1h"));
  }

  @Test
  public void testGetTaskRegex() throws Exception {
    tFixture.clickOnMenu("Tasks");
    assertEquals(tFixture.getRegexGroupFromTaskNameContainingText("([a-zA-Z0-9]{5})", 1, "Input Automated Test Data"), "Input");
  }

  @AfterClass
  public static void tearDownTasks() throws Exception {
    tFixture.clickOnMenu("Tasks");
    tFixture.clickOnTask("Input Interface Test");
    tFixture.clickOnButton("Cancel");
  }
}