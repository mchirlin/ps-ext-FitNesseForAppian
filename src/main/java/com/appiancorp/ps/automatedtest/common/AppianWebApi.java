package com.appiancorp.ps.automatedtest.common;

import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;

import com.appiancorp.ps.automatedtest.exception.ExceptionBuilder;

public class AppianWebApi {

  protected Settings settings;

  public static AppianWebApi getInstance(Settings settings) {
    return new AppianWebApi(settings);
  }

  public AppianWebApi(Settings settings) {
    this.settings = settings;
  }

  public String callWebApi(String webApi, String username, String password) {
    HttpURLConnection conn = null;
    try {
      URL url = new URL(settings.getUrl() + "/webapi/" + webApi);
      conn = (HttpURLConnection) url.openConnection();
      conn.setDoOutput(false);
      conn.setRequestMethod("GET");
      conn.setRequestProperty("Content-Type", "application/json");
      byte[] encoded = Base64.encodeBase64(new String(username + ":" + password).getBytes());
      conn.setRequestProperty("Authorization", "Basic " + new String(encoded));
      String result = IOUtils.toString(conn.getInputStream());

      if (conn.getResponseCode() != 200) {
        throw new Exception("Web API request failed: " + result);
      } else {
        return result;
      }
    } catch (Exception e) {
      throw ExceptionBuilder.build(e, settings, "Call Web API");
    } finally {
      if (conn != null) conn.disconnect();
    }
  }
}
