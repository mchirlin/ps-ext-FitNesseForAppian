package com.appiancorp.ps.automatedtest.object;

import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TempoFileUploadField extends TempoField {

    private static final Logger LOG = Logger.getLogger(TempoFileUploadField.class);
    private static final String XPATH_ABSOLUTE_LABEL = "//label[contains(text(),'%s')]/parent::span/following-sibling::div/descendant::input";
    private static final String XPATH_RELATIVE_INPUT = ".//input[contains(@class, 'gwt-FileUpload')]";
    private static final String XPATH_RELATIVE_FILE = ".//span[contains(@class, 'filename')]";
    private static final String XPATH_RELATIVE_FILENAME = XPATH_RELATIVE_FILE + "/span[contains(text(), '%s')]";
    private static final String XPATH_RELATIVE_REMOVE = ".//a[starts-with(text(), 'Remove')]";
    private static final Pattern FILENAME_PATTERN = Pattern.compile("(.*) \\(.*\\)");
   
    public static boolean populate(WebElement fieldLayout, String fieldValue) {
        WebElement fileUpload = fieldLayout.findElement(By.xpath(XPATH_RELATIVE_INPUT));
        fileUpload.sendKeys(fieldValue);
        unfocus();
        
        LOG.debug("FILE UPLOAD FIELD POPULATION : " + fieldValue);
        return true;
    }
    
    public static boolean waitFor(String fieldName) {
        try {
            (new WebDriverWait(driver, timeoutSeconds)).until(ExpectedConditions.presenceOfElementLocated(By.xpath(String.format(XPATH_ABSOLUTE_LABEL, fieldName))));
            WebElement fieldLayout = getFieldLayout(fieldName);
            scrollIntoView(fieldLayout);
        } catch (TimeoutException e) {
            return false;
        }
        
        return true;
    }
    
    public static boolean waitForFileName(WebElement fieldLayout, String fileName) {
        try {
            (new WebDriverWait(driver, timeoutSeconds)).until(ExpectedConditions.presenceOfElementLocated(By.xpath(String.format(XPATH_RELATIVE_FILENAME, fileName))));
        } catch (Exception e) {
            return false;
        }
        
        return true;
    }
    
    public static String getValue(WebElement fieldLayout) {
        String value = fieldLayout.findElement(By.xpath(String.format(XPATH_RELATIVE_FILE))).getText();
        
        Matcher m = FILENAME_PATTERN.matcher(value);
        
        if (m.find()) {
            value = m.group(1);
        }
        
        LOG.debug("FILE UPLOAD FIELD VALUE : " + value);
        
        return value;
    }
    
    public static boolean contains(WebElement fieldLayout, String fieldValue) {
        // For read-only
        try {
            return TempoField.contains(fieldLayout, fieldValue);
        } catch (Exception e) {}
        
        fieldValue = Paths.get(fieldValue).getFileName().toString();
        waitForFileName(fieldLayout, fieldValue);
        String compareString = getValue(fieldLayout);
        
        LOG.debug("FILE UPLOAD FIELD COMPARISON : Field value [" + fieldValue + "] compared to Entered value [" + compareString + "]");
        
        return compareString.equals(fieldValue);
    }
    
    public static boolean clear(WebElement fieldLayout) {
        WebElement removeLink = fieldLayout.findElement(By.xpath(XPATH_RELATIVE_REMOVE));
        removeLink.click();
        
        return true;
    }
    
    public static boolean isType(WebElement fieldLayout) {
        try {
            fieldLayout.findElement(By.xpath(XPATH_RELATIVE_INPUT));
        } catch (Exception e) {
            return false;
        }
        
        return true;
    }
}
