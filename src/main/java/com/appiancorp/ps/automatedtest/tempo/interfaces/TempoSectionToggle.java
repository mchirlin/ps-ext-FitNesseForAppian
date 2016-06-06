package com.appiancorp.ps.automatedtest.tempo.interfaces;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.appiancorp.ps.automatedtest.common.Settings;
import com.appiancorp.ps.automatedtest.exception.ExceptionBuilder;
import com.appiancorp.ps.automatedtest.properties.Clickable;

public class TempoSectionToggle extends TempoSection implements Clickable {

  private static final Logger LOG = Logger.getLogger(TempoSectionToggle.class);

  protected static final String XPATH_RELATIVE_SECTION_TOGGLE = Settings.getByConstant("xpathRelativeSectionToggle");

  public static TempoSectionToggle getInstance(Settings settings) {
    return new TempoSectionToggle(settings);
  }

  protected TempoSectionToggle(Settings settings) {
    super(settings);
  }

  public void click(String... params) {
    String sectionName = params[0];

    if (LOG.isDebugEnabled()) LOG.debug("EXPAND SECTION [" + sectionName + "]");

    try {
      WebElement section = getWebElement(sectionName);
      WebElement expand = section.findElement(By.xpath(XPATH_RELATIVE_SECTION_TOGGLE));
      clickElement(expand);
    } catch (Exception e) {
      throw ExceptionBuilder.build(e, settings, "Expand Section", sectionName);
    }
  }
}
