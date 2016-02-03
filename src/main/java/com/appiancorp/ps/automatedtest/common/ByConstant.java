package com.appiancorp.ps.automatedtest.common;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ByConstant {
    private String name;
    private String by;
    
    @JsonCreator
    public ByConstant(
      @JsonProperty("name") String name,
      @JsonProperty("by") String by) {
      this.name = name;
      this.by = by;
    }
    
    public String getName() {
        return this.name;
    }
    
    public String getBy() {
        return this.by;
    }
    
    @Override
    public String toString() {
        return "{name: \"" + this.name +  "\", by: \"" + this.by + "\"}";
    }
}