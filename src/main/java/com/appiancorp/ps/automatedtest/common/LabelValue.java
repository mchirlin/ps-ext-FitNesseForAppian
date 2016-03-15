package com.appiancorp.ps.automatedtest.common;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class LabelValue {
  private String label;
  private String value;

  @JsonCreator
  public LabelValue(
    @JsonProperty("label") String label,
    @JsonProperty("value") String value) {
    this.label = label;
    this.value = value;
  }

  public String getLabel() {
    return this.label;
  }

  public String getValue() {
    return this.value;
  }

  @Override
  public String toString() {
    return "{label: \"" + this.label + "\", value: \"" + this.value + "\"}";
  }
}