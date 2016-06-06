package com.appiancorp.ps.automatedtest.object;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.appiancorp.ps.automatedtest.common.Settings;
import com.appiancorp.ps.automatedtest.exception.ExceptionBuilder;
import com.google.common.base.Strings;

public class TempoLinkField extends AppianObject {

  private static final Logger LOG = Logger.getLogger(TempoLinkField.class);
  private static final String XPATH_ABSOLUTE_LINK_FIELD = Settings.getByConstant("xpathAbsoluteLinkField");
  private static final String XPATH_ABSOLUTE_LINK_FIELD_INDEX = "(" + XPATH_ABSOLUTE_LINK_FIELD + ")[%2$d]";

  public static WebElement getLink(String linkName, Settings s) {
    if (isFieldIndex(linkName)) {
      int lNum = getIndexFromFieldIndex(linkName);
      String lName = getFieldFromFieldIndex(linkName);
      return s.getDriver().findElement(By.xpath(String.format(XPATH_ABSOLUTE_LINK_FIELD_INDEX, lName, lNum)));
    } else {
      return s.getDriver().findElement(By.xpath(String.format(XPATH_ABSOLUTE_LINK_FIELD, linkName)));
    }

  }

  public static String getLinkURL(String linkName, Settings s) {
    WebElement link = getLink(linkName, s);
    String linkURL = link.getAttribute("href");
    return linkURL;
  }

  public static void click(String linkName, Settings s) {
    if (LOG.isDebugEnabled()) LOG.debug("CLINK LINK [" + linkName + "]");

    try {
      WebElement link = getLink(linkName, s);
      clickElement(link, s);
    } catch (Exception e) {
      throw ExceptionBuilder.build(e, s, "Click Link", linkName);
    }
  }

  public static void waitFor(String linkName, Settings s) {
    if (LOG.isDebugEnabled()) LOG.debug("WAIT FOR LINK [" + linkName + "]");

    try {
      if (isFieldIndex(linkName)) {
        int lNum = getIndexFromFieldIndex(linkName);
        String lName = getFieldFromFieldIndex(linkName);
        (new WebDriverWait(s.getDriver(), s.getTimeoutSeconds())).until(ExpectedConditions.presenceOfElementLocated(By.xpath(String.format(
          XPATH_ABSOLUTE_LINK_FIELD_INDEX, lName, lNum))));
      } else {
        (new WebDriverWait(s.getDriver(), s.getTimeoutSeconds())).until(ExpectedConditions.presenceOfElementLocated(By.xpath(String.format(
          XPATH_ABSOLUTE_LINK_FIELD, linkName))));
      }
    } catch (Exception e) {
      throw ExceptionBuilder.build(e, s, "Wait for Link", linkName);
    }
  }

  public static boolean containsURLValue(String linkName, String linkURLValue, Settings s) {
    String linkURLText = getLinkURL(linkName, s);

    if (LOG.isDebugEnabled())
      LOG.debug("READ ONLY FIELD COMPARISON : Link field URL value [" + linkURLText + "] compared to Entered value [" + linkURLValue + "]");
    return (linkURLText.contains(linkURLValue) && !Strings.isNullOrEmpty(linkURLValue));
  }
}
