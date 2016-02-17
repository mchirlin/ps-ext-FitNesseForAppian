package com.appiancorp.ps.automatedtest.common;

import java.io.InputStream;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Settings {
    private static final Logger LOG = Logger.getLogger(Settings.class);
    private static List<AppianVersion> appianVersions;
    private static List<Version> allVersions;
    private static List<AppianLocale> appianLocales;
   
    private WebDriver driver;
    private String masterWindowHandle;
    private String url;
    private static Version version = new Version("7.10");
    private String locale;
    private int timeoutSeconds = 10;
    private int notPresentTimeoutSeconds = 1;
    private Date startDatetime = new Date();
    private int refreshTimes = 5;
    private int attemptTimes = 3;
    
    private String dateFormat = "M/d/yyyy";
    private String dateDisplayFormat = "MMM d, yyyy";
    private String timeFormat = "h:mm aa";
    private String timeDisplayFormat = "h:mm aa";
    private String datetimeFormat = "M/d/yyyy h:mm aa";
    private String datetimeDisplayFormat = "MMM d, yyyy, h:mm aa";
    
    private String dataSourceName = null;
    
    private String screenshotPath = "C:\\AutomatedTesting\\screenshots\\";
    private Boolean takeErrorScreenshots = false;
    private Boolean stopOnError = false;

    @JsonCreator
    public Settings(
          @JsonProperty("appianVersions") List<AppianVersion> appianVersions,
          @JsonProperty("appianLocales") List<AppianLocale> appianLocales) {
        Settings.appianVersions = appianVersions;
        Settings.appianLocales = appianLocales;
        allVersions = new ArrayList<Version>();
        for (AppianVersion av : appianVersions) {
            allVersions.add(av.getVersion());
        }
    }

    public static List<AppianVersion> getAppianVersions() {
        return appianVersions;
    }
    
    public static List<AppianLocale> getAppianLocales() {
        return appianLocales;
    }

    public static String getByConstant(String constant) {
        Integer index = Version.getBestIndexFromList(getVersion(), allVersions);
        AppianVersion appianVersion = appianVersions.get(index);
        String byConstant;
        
        if (appianVersion.getVersion().match(getVersion()) <= 1) {
            throw new IllegalArgumentException(String.format("%s is not a recognized version", getVersion()));
        }
        
        while (index >= 0) {
            byConstant = appianVersion.getByConstant(constant);
            if (byConstant != null) return byConstant;
            index--;
            appianVersion = appianVersions.get(index);
        }
        
        return null;
    }
    
    public static Settings initialize() {
        InputStream in = null;
        try {
            in = Settings.class.getResource("/metadata.json").openStream();
            return new ObjectMapper().readValue(in, Settings.class);
            
        } catch (Exception e) {
            LOG.error("Error processing metadata.json resource", e);
        } finally {
            IOUtils.closeQuietly(in);
        }
        
        return null;
    }
    
    public void setDriver(WebDriver d) {
        driver = d;
        this.setMasterWindowHandle(d.getWindowHandle());
    }
    
    public WebDriver getDriver() {
        return driver;
    }
    
    public void setUrl(String u) {
        url = u;
    }
    public String getUrl() {
        return url;
    }
    
    public static void setVersion(String v) {
        version = new Version(v);
    }
    
    public static Version getVersion() {
        return version;
    }

    public void setLocale(String l) {
        for (AppianLocale al : Settings.getAppianLocales()) {
            if (al.getLocale().equals(l)) {
                setDateFormat(al.getDateFormat());
                setDateDisplayFormat(al.getDateDisplayFormat());
                setTimeFormat(al.getTimeFormat());
                setTimeDisplayFormat(al.getTimeDisplayFormat());
                setDatetimeFormat(al.getDatetimeFormat());
                setDatetimeDisplayFormat(al.getDatetimeDisplayFormat());
            }
        }
    }
    
    public String getLocale() {
        return locale;
    }
    
    public void setDateFormat(String df) {
        dateFormat = df;
    }
   
    public String getDateFormat() {
        return dateFormat;
    }
    
    public void setDateDisplayFormat(String df) {
        dateDisplayFormat = df;
    }
    
    public String getDateDisplayFormat() {
        return dateDisplayFormat;
    }
    
    public void setTimeFormat(String tf) {
        timeFormat = tf;
    }
    
    public String getTimeFormat() {
        return timeFormat;
    }
    
    public void setTimeDisplayFormat(String tf) {
        timeDisplayFormat = tf;
    }
    
    public String getTimeDisplayFormat() {
        return timeDisplayFormat;
    }
    
    public void setDatetimeFormat (String dtf) {
        datetimeFormat = dtf;
    }
    
    public String getDatetimeFormat() {
        return datetimeFormat;
    }
    
    public void setDatetimeDisplayFormat (String dtf) {
        datetimeDisplayFormat = dtf;
    }
    
    public String getDatetimeDisplayFormat() {
        return datetimeDisplayFormat;
    }
    
    public void setTimeoutSeconds(int t) {
        timeoutSeconds = t;
    }
    
    public int getTimeoutSeconds() {
        return timeoutSeconds;
    }  
    
    public void setNotPresentTimeoutSeconds(int t) {
        notPresentTimeoutSeconds = t;
    }
    
    public int getNotPresentTimeoutSeconds() {
        return notPresentTimeoutSeconds;
    }
    
    public void setStartDatetime(Date s) {
        startDatetime = s;
    }  
    
    public Date getStartDatetime() {
        return startDatetime;
    }
    
    public void setMasterWindowHandle(String w) {
        masterWindowHandle = w;
    }
    
    public String getMasterWindowHandle() {
        return masterWindowHandle;
    }
    
    public void setDataSourceName(String ds) {
        dataSourceName = ds;
    }
    
    public String getDataSourceName() {
        return this.dataSourceName;
    }
    
    public void setAttemptTimes(Integer at) {
        attemptTimes = at;
    }
    
    public Integer getAttemptTimes() {
        return this.attemptTimes;
    }
    
    public void setRefreshTimes(Integer rt) {
        refreshTimes = rt;
    }
    
    public Integer getRefreshTimes() {
        return this.refreshTimes;
    }
    
    public void setScreenshotPath(String sp) {
        screenshotPath = sp;
    }
    
    public String getScreenshotPath() {
        return this.screenshotPath;
    }
    
    public void setTakeErrorScreenshots(Boolean es) {
        takeErrorScreenshots = es;
    }
    
    public Boolean isTakeErrorScreenshots() {
        return this.takeErrorScreenshots;
    }
    
    public void setStopOnError(Boolean es) {
        stopOnError = es;
    }
    
    public Boolean isStopOnError() {
        return this.stopOnError;
    }
}