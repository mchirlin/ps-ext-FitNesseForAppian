package com.appiancorp.ps.automatedtest.fields;

import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TempoObject {
    
    private static final Logger LOG = Logger.getLogger(TempoObject.class);
    
    protected static WebDriver driver;
    protected static String masterWindowHandle;
    protected static String url;
    protected static int timeOutSeconds;
    protected static Date startDatetime;
    protected static int refreshTimes = 10;
    
    public static final String DATE_FORMAT_STRING = "M/dd/yyyy";
    public static final String TIME_FORMAT_STRING = "hh:mm aaa";
    public static final String DATETIME_FORMAT_STRING = DATE_FORMAT_STRING + " " + TIME_FORMAT_STRING;
    public static final String DATE_DISPLAY_FORMAT_STRING = "MMM dd, yyyy";
    public static final String TIME_DISPLAY_FORMAT_STRING = "h:mm aaa";
    public static final String DATETIME_DISPLAY_FORMAT_STRING = DATE_DISPLAY_FORMAT_STRING + ", " + TIME_DISPLAY_FORMAT_STRING;
    
    public static boolean isDateCalculation(String dateTimeString) {
        dateTimeString = dateTimeString.replaceAll("\\s", "");
        return dateTimeString.matches("([0-9]{2}/[0-9]{2}/[0-9]{4}([0-9]{2}:[0-9]{2}(AM|PM))?)?[+-][0-9]+(minute(s)?|hour(s)?|day(s)?)");
    }
    
    public static String calculateDate(String dateTimeString) {
        dateTimeString = dateTimeString.trim();
        int plusLocation = dateTimeString.indexOf("+");
        Date d;
        
        if (plusLocation > 0 ) {
            try {
                d = DateUtils.parseDate(dateTimeString.substring(0, plusLocation).trim(), DATETIME_FORMAT_STRING);
            } catch (ParseException e) {
                d = startDatetime;
            }
        } else {
            d = startDatetime;
        }
        
        int addValue = Integer.parseInt(dateTimeString.substring(plusLocation, dateTimeString.length()).replaceAll("[^0-9]", ""));
        if (dateTimeString.contains("minute")) {
            d = DateUtils.addMinutes(d, addValue);
        } else if (dateTimeString.contains("hour")) {
            d = DateUtils.addHours(d, addValue);
        } else if (dateTimeString.contains("day")) {
            d = DateUtils.addDays(d, addValue);
        }
        
        return new SimpleDateFormat(DATETIME_DISPLAY_FORMAT_STRING).format(d);
    }
    
    public static String parseVariable(String variable) {
        if (isDateCalculation(variable)) variable = calculateDate(variable);
        
        return variable;
    }
    
    public static String runExpression(String expression) {
        try {
            String servletUrl = url + "/plugins/servlet/appianautomatedtest?operation=runExpression&expression=" + URLEncoder.encode(expression, "UTF-8");
            
            String returnVal = "";
            
            // Open servlet window
            new Actions(driver).sendKeys(Keys.chord(Keys.CONTROL, Keys.TAB)).perform();
            
            driver.get(servletUrl);
            (new WebDriverWait(driver, timeOutSeconds)).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//pre")));
            returnVal = driver.findElement(By.xpath("//pre")).getText();
            
            // Go back to test window
            new Actions(driver).sendKeys(Keys.chord(Keys.CONTROL, Keys.SHIFT, Keys.TAB)).perform();
            
            Thread.sleep(500);
            driver.switchTo().defaultContent();
            
            return returnVal;        
        } catch (Exception e) {
            LOG.error(e.getMessage());
        }
        return null;
    }
    
    public static void setDriver(WebDriver d) {
        driver = d;
    }
    public static WebDriver getDriver() {
        return driver;
    }
    
    public static void setUrl(String u) {
        url = u;
    }
    public static String getUrl() {
        return url;
    }
    
    public static void setTimeoutSeconds(int t) {
        timeOutSeconds = t;
    }
    public static int getTimeOutSeconds() {
        return timeOutSeconds;
    }  
    
    public static void setStartDatetime(Date s) {
        startDatetime = s;
    }  
    public static Date getStartDatetime() {
        return startDatetime;
    }
    
    public static void setMasterWindowHandle(String w) {
        masterWindowHandle = w;
    }
    public static String getMasterWindowHandle() {
        return masterWindowHandle;
    }
}
