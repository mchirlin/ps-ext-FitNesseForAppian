package com.appiancorp.ps.automatedtest.tempo.interfaces;

import org.openqa.selenium.WebElement;

import com.appiancorp.ps.automatedtest.common.Settings;

public class TempoField extends AbstractTempoField {

  public static TempoField getInstance(Settings settings) {
    return new TempoField(settings);
  }

  private TempoField(Settings settings) {
    super(settings);
  }

  @Override
  public void populate(WebElement fieldLayout, String... params) throws Exception {
    // TODO Auto-generated method stub

  }

  @Override
  public boolean contains(WebElement fieldLayout, String... params) throws Exception {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public String capture(WebElement fieldLayout, String... params) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void clear(WebElement fieldLayout, String... params) {
    // TODO Auto-generated method stub

  }
}