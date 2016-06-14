package com.appiancorp.ps.automatedtest.properties;

public interface Refreshable extends WaitForReturn {

  public void refreshAndWaitFor(String... params);
}
