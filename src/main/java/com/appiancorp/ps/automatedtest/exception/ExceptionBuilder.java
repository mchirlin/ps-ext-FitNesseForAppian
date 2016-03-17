package com.appiancorp.ps.automatedtest.exception;

import org.apache.log4j.Logger;

import com.appiancorp.ps.automatedtest.common.Settings;
import com.appiancorp.ps.automatedtest.object.Screenshot;

public class ExceptionBuilder {
  private static final Logger LOG = Logger.getLogger(ExceptionBuilder.class);

  public static RuntimeException build(Exception e, Settings s, String... vals) {
    LOG.error(String.join(" - ", vals), e);

    try {
      if (s.isTakeErrorScreenshots()) {
        Screenshot.takeScreenshot(String.format("%3d", s.getErrorNumber()), s);
        s.setErrorNumber(s.getErrorNumber() + 1);
      }
    } catch (Exception exception) {
      LOG.error("Screenshot error", e);
    }

    if (s.isStopOnError()) {
      s.getDriver().quit();
    }

    String clazz = e.getClass().getCanonicalName();

    switch (clazz) {
      case "org.openqa.selenium.NoSuchElementException":
        if (s.isStopOnError()) {
          return new ObjectNotFoundStopTestException(vals);
        } else {
          return new ObjectNotFoundTestException(vals);
        }

      case "org.openqa.selenium.TimeoutException":
        if (s.isStopOnError()) {
          return new TimeoutStopTestException(vals);
        } else {
          return new TimeoutTestException(vals);
        }

      case "org.openqa.selenium.StaleElementReferenceException":
        if (s.isStopOnError()) {
          return new StaleElementStopTestException(vals);
        } else {
          return new StaleElementTestException(vals);
        }

      case "java.lang.IllegalArgumentException":
        if (s.isStopOnError()) {
          return new IllegalArgumentStopTestException(vals);
        } else {
          return new IllegalArgumentTestException(vals);
        }

      case "org.openqa.selenium.remote.UnreachableBrowserException":
        if (s.isStopOnError()) {
          return new UnreachableBrowserStopTestException(vals);
        } else {
          return new UnreachableBrowserTestException(vals);
        }

      default:
        if (s.isStopOnError()) {
          return new GenericStopTestException(vals);
        } else {
          return new GenericTestException(vals);
        }
    }
  }
}
