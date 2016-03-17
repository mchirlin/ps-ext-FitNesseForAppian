package com.appiancorp.ps.automatedtest.object;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import com.appiancorp.ps.automatedtest.common.Settings;
import com.google.common.base.Throwables;

public class Screenshot extends AppianObject {

  private static final Logger LOG = Logger.getLogger(AppianObject.class);

  public static void takeScreenshot(String fileName, Settings s) {
    File srcFile = ((TakesScreenshot) s.getDriver()).getScreenshotAs(OutputType.FILE);
    try {
      FileUtils.copyFile(srcFile, new File(s.getScreenshotPath() + fileName + ".png"));
    } catch (IOException e) {
      LOG.error(Throwables.getStackTraceAsString(e));
      throw new RuntimeException(e.getMessage());
    }
  }
}
