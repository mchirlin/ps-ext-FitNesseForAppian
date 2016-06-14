package com.appiancorp.ps.automatedtest.properties;

public interface WaitForMultiple extends WaitFor {

  public void waitForMultiple(String[] values, String... params);
}
