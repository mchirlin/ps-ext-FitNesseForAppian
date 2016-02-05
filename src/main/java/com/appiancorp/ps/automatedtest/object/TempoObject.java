package com.appiancorp.ps.automatedtest.object;

import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.time.DateUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.appiancorp.ps.automatedtest.common.AppianLocale;
import com.appiancorp.ps.automatedtest.common.Metadata;
import com.appiancorp.ps.automatedtest.common.Version;

public class TempoObject {
    
    private static final Logger LOG = Logger.getLogger(TempoObject.class);
    
    protected static WebDriver driver;
    protected static String masterWindowHandle;
    protected static String url;
    protected static Version version = new Version("7.10");
    protected static String locale;
    protected static int timeoutSeconds;
    protected static Date startDatetime;
    protected static int refreshTimes = 5;
    protected static int attemptTimes = 3;
    
    public static final String DATE_ENTRY_FORMAT = "yyyy-MM-dd";
    public static final String TIME_ENTRY_FORMAT = "HH:mm";
    public static final String DATETIME_ENTRY_FORMAT = DATE_ENTRY_FORMAT + " " + TIME_ENTRY_FORMAT;
    protected static String dateFormat = "M/d/yyyy";
    protected static String dateDisplayFormat = "MMM d, yyyy";
    protected static String timeFormat = "h:mm aa";
    protected static String timeDisplayFormat = "h:mm aa";
    protected static String datetimeFormat = "M/d/yyyy h:mm aa";
    protected static String datetimeDisplayFormat = "MMM d, yyyy, h:mm aa";
    
    private static final String XPATH_WORKING = "//div[@class = 'appian-indicator-message']";
    
    private static final Pattern INDEX_PATTERN = Pattern.compile("(.*)?\\[([0-9]+)\\]");
    
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
                d = DateUtils.parseDate(dateTimeString.substring(0, plusLocation).trim(), DATETIME_ENTRY_FORMAT);
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
        
        return new SimpleDateFormat(getDatetimeDisplayFormat()).format(d);
    }
    
    public static Date parseDate(String datetimeString) throws ParseException {
        return DateUtils.parseDateStrictly(datetimeString, new String[] {
                DATE_ENTRY_FORMAT,
                DATETIME_ENTRY_FORMAT,
                dateFormat,
                dateDisplayFormat,
                datetimeFormat,
                datetimeDisplayFormat
        });
    }
    
    public static String parseVariable(String variable) {
        if (isDateCalculation(variable)) variable = calculateDate(variable);
        
        return variable;
    }
    
    public static String runExpression(String expression) {
        try {
            String servletUrl = url + "/plugins/servlet/appianautomatedtest?operation=runExpression&expression=" + URLEncoder.encode(expression, "UTF-8");
            
            String returnVal = "";
            
            // Open new tab
            ((JavascriptExecutor)driver).executeScript("window.open('" + servletUrl + "','_blank');");
            
            // Switch to tab
            Set<String> handles = driver.getWindowHandles();
            String popupHandle = "";
            for (String handle : handles) {
                if (!handle.equals(masterWindowHandle)) popupHandle = handle;
            }
            driver.switchTo().window(popupHandle);
            
            (new WebDriverWait(driver, timeoutSeconds)).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//pre")));
            returnVal = driver.findElement(By.xpath("//pre")).getText();
            
            LOG.debug("'" + expression + "' equals '" + returnVal + "'");
            
            // Close tab
            driver.close();
            
            Thread.sleep(500);
            driver.switchTo().window(masterWindowHandle);
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
    
    public static void setVersion(String v) {
        version = new Version(v);
    }
    
    public static Version getVersion() {
        return version;
    }
    
    public static void setLocale(String l) {
        locale = l;
        for (AppianLocale al : Metadata.getAppianLocales()) {
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
    
    public static String getLocale() {
        return locale;
    }
    
    public static void setDateFormat(String df) {
        dateFormat = df;
    }
   
    public static String getDateFormat() {
        return dateFormat;
    }
    
    public static void setDateDisplayFormat(String df) {
        dateDisplayFormat = df;
    }
    
    public static String getDateDisplayFormat() {
        return dateDisplayFormat;
    }
    
    public static void setTimeFormat(String tf) {
        timeFormat = tf;
    }
    
    public static String getTimeFormat() {
        return timeFormat;
    }
    
    public static void setTimeDisplayFormat(String tf) {
        timeDisplayFormat = tf;
    }
    
    public static String getTimeDisplayFormat() {
        return timeDisplayFormat;
    }
    
    public static void setDatetimeFormat (String dtf) {
        datetimeFormat = dtf;
    }
    
    public static String getDatetimeFormat() {
        return datetimeFormat;
    }
    
    public static void setDatetimeDisplayFormat (String dtf) {
        datetimeDisplayFormat = dtf;
    }
    
    public static String getDatetimeDisplayFormat() {
        return datetimeDisplayFormat;
    }
    
    public static void setTimeoutSeconds(int t) {
        timeoutSeconds = t;
    }
    public static int getTimeoutSeconds() {
        return timeoutSeconds;
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
    
    public static boolean waitForWorking() {
        try {
            Thread.sleep(100);
            (new WebDriverWait(driver, timeoutSeconds)).until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(XPATH_WORKING)));
            Thread.sleep(200);
        } catch (Exception e) {
            return false;
        }
        
        LOG.debug("Working done");
        return true;
    }
    
    public static void scrollIntoView(WebElement webElement, Boolean alignToTop ) {
        // Have to manually scroll element into view because Tempo header covers the action link for long action lists
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView("+alignToTop.toString()+");", webElement);
        waitForWorking();
    }
    
    public static void scrollIntoView(WebElement webElement) {
        scrollIntoView(webElement, false);
    }
    
    public static void unfocus() {
        driver.findElement(By.xpath("//html")).click();
        waitForWorking();
    }
    
    public static boolean isFieldIndex(String fieldNameIndex) {        
        return INDEX_PATTERN.matcher(fieldNameIndex).matches();
    }
    
    public static String getFieldFromFieldIndex(String fieldNameIndex) {
        Matcher m = INDEX_PATTERN.matcher(fieldNameIndex);
        if (m.find()) {
            return m.group(1);
        } else {
            return "";
        }
    }
    
    public static int getIndexFromFieldIndex(String fieldNameIndex) {
        Matcher m = INDEX_PATTERN.matcher(fieldNameIndex);
        if (m.find()) {
            return Integer.parseInt(m.group(2));
        } else {
            return 1;
        } 
    }
    
    public static String getXpathLocator(WebElement element) {
        Pattern p = Pattern.compile(".*xpath: (.*)");
        Matcher m = p.matcher(element.toString());
        
        if (m.find()) {
            return m.group(1).substring(0, m.group(1).length() - 1);
        } else {
            return null;
        }        
    }
    
    public static String getRegexResults(String regex, String text) {
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(text.toString());
        
        if (m.find()) {
            LOG.debug("Regex results: " + m.group(0));
            return m.group(0);
        } else {
            return "";
        } 
    }
}
