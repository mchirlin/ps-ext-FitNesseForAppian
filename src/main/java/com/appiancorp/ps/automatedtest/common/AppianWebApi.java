package com.appiancorp.ps.automatedtest.common;

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import com.appiancorp.ps.automatedtest.exception.ExceptionBuilder;

public class AppianWebApi {

  protected Settings settings;

  public static AppianWebApi getInstance(Settings settings) {
    return new AppianWebApi(settings);
  }

  public AppianWebApi(Settings settings) {
    this.settings = settings;
  }

  public String callWebApi(String webApi, String body, String username, String password) {
    HttpURLConnection conn = null;
    try {
      URL url = new URL(settings.getUrl() + "/webapi/" + webApi);
      conn = (HttpURLConnection) url.openConnection();
      byte[] encoded = Base64.encodeBase64(new String(username + ":" + password).getBytes());
      conn.setRequestProperty("Authorization", "Basic " + new String(encoded));

      if (StringUtils.isNotBlank(body)) {
        conn.setDoOutput(true);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "text/plain; charset=UTF-8");
        DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
        wr.write(body.getBytes());
        wr.flush();
        wr.close();
      } else {
        conn.setRequestMethod("GET");
        conn.setDoOutput(false);
      }

      if (conn.getResponseCode() != 200) {
        throw new Exception("Web API request failed:" + conn.getResponseCode());
      } else {
        return IOUtils.toString(conn.getInputStream());
      }
    } catch (Exception e) {
      throw ExceptionBuilder.build(e, settings, "Call Web API");
    } finally {
      if (conn != null) conn.disconnect();
    }
  }
}
