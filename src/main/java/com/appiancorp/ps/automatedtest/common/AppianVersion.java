package com.appiancorp.ps.automatedtest.common;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AppianVersion{
  private Version version;
  private List<ByConstant> byConstants;

  @JsonCreator
  public AppianVersion(
    @JsonProperty("version") Version version,
    @JsonProperty("hotfixes") List<ByConstant> byConstants) {
    this.version = version;
    this.byConstants = byConstants;
  }

  public Version getVersion() {
      return this.version;
  }
  
  public List<ByConstant> getByConstants() {
    return this.byConstants;
  }
}