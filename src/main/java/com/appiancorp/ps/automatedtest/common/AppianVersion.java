package com.appiancorp.ps.automatedtest.common;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AppianVersion{
  private Version version;
  private List<LabelValue> byConstants;

  @JsonCreator
  public AppianVersion(
    @JsonProperty("version") Version version,
    @JsonProperty("byConstants") List<LabelValue> byConstants) {
    this.version = version;
    this.byConstants = byConstants;
  }

  public Version getVersion() {
      return this.version;
  }
  
  public String getByConstant(String name) {
      for (LabelValue by : this.byConstants) {
          if (by.getLabel().equals(name)) {
              return by.getValue();
          }
      }
      
      return null;
  }
}