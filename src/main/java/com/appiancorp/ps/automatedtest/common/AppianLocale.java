package com.appiancorp.ps.automatedtest.common;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AppianLocale{
  private String locale;
  private String dateFormat;
  private String dateDisplayFormat;
  private String timeFormat;
  private String timeDisplayFormat;
  private String datetimeFormat;
  private String datetimeDisplayFormat;

  @JsonCreator
  public AppianLocale(
    @JsonProperty("locale") String locale,
    @JsonProperty("dateFormat") String dateFormat,
    @JsonProperty("dateDisplayFormat") String dateDisplayFormat,
    @JsonProperty("timeFormat") String timeFormat,
    @JsonProperty("timeDisplayFormat") String timeDisplayFormat,
    @JsonProperty("datetimeFormat") String datetimeFormat,
    @JsonProperty("datetimeDisplayFormat") String datetimeDisplayFormat) {
    this.locale = locale;
    this.dateFormat = dateFormat;
    this.dateDisplayFormat = dateDisplayFormat;
    this.timeFormat = timeFormat;
    this.timeDisplayFormat = timeDisplayFormat;
    this.datetimeFormat = datetimeFormat;
    this.datetimeDisplayFormat = datetimeDisplayFormat;
  }

  public String getLocale() {
      return this.locale;
  }
  
  public String getDateFormat() {
      return this.dateFormat;
  }
  
  public String getDateDisplayFormat() {
      return this.dateDisplayFormat;
  }

  public String getTimeFormat() {
      return this.timeFormat;
  }

  public String getTimeDisplayFormat() {
      return this.timeDisplayFormat;
  }
  
  public String getDatetimeFormat() {
      return this.datetimeFormat;
  }
  
  public String getDatetimeDisplayFormat() {
      return this.datetimeDisplayFormat;
  }
}