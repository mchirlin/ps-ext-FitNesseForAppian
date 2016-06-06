package com.appiancorp.ps.automatedtest.properties;

public interface WaitForReturn extends WaitFor {

  public boolean waitForReturn(int timeout, String... params);

  public boolean waitForReturn(String... params);

}
