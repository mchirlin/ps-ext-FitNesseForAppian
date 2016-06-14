package com.appiancorp.ps.automatedtest.exception;

import org.apache.log4j.Logger;

import com.appiancorp.ps.automatedtest.common.Screenshot;
import com.appiancorp.ps.automatedtest.common.Settings;

public class ExceptionBuilder {
  private static final Logger LOG = Logger.getLogger(ExceptionBuilder.class);

  public static RuntimeException build(Exception e, Settings s, String... vals) {
    LOG.error(String.join(" - ", vals), e);

    try {
      if (s.isTakeErrorScreenshots()) {
        Screenshot.getInstance(s).capture(String.format("%3d", s.getErrorNumber()));
        s.setErrorNumber(s.getErrorNumber() + 1);
      }
    } catch (Exception exception) {
      LOG.error("Screenshot error", e);
    }

    if (s.isStopOnError()) {
      s.getDriver().quit();
    }

    switch (e.getClass().getCanonicalName()) {
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

      case "com.appiancorp.ps.automatedtest.exception.WaitForWorkingTestException":
        if (s.isStopOnError()) {
          return new WaitForWorkingStopTestException(vals);
        } else {
          return new WaitForWorkingTestException(vals);
        }

      default:
        if (s.isStopOnError()) {
          return new GenericStopTestException(vals);
        } else {
          e.printStackTrace();
          return new GenericTestException(vals);
        }
    }
  }
}
