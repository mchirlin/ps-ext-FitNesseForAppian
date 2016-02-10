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

import com.appiancorp.ps.automatedtest.common.Settings;

public class TempoFileUploadField extends TempoField {

    private static final Logger LOG = Logger.getLogger(TempoFileUploadField.class);
    private static final String XPATH_ABSOLUTE_FILE_UPLOAD_FIELD_LABEL = Settings.getByConstant("xpathAbsoluteFileUploadFieldLabel");
    private static final String XPATH_RELATIVE_FILE_UPLOAD_FIELD_INPUT = Settings.getByConstant("xpathRelativeFileUploadFieldInput");
    private static final String XPATH_RELATIVE_FILE_UPLOAD_FIELD_FILE = Settings.getByConstant("xpathRelativeFileUploadFieldFile");
    private static final String XPATH_RELATIVE_FILE_UPLOAD_FIELD_REMOVE_LINK = Settings.getByConstant("xpathRelativeFileUploadFieldRemoveLink");
    private static final Pattern FILENAME_PATTERN = Pattern.compile("(.*) \\(.*\\)");
   
    public static boolean populate(WebElement fieldLayout, String fieldValue, Settings s) {
        WebElement fileUpload = fieldLayout.findElement(By.xpath(XPATH_RELATIVE_FILE_UPLOAD_FIELD_INPUT));
        fileUpload.sendKeys(fieldValue);
        unfocus(s);
        
        LOG.debug("FILE UPLOAD FIELD POPULATION : " + fieldValue);
        return true;
    }
    
    public static boolean waitFor(String fieldName, Settings s) {
        try {
            (new WebDriverWait(s.getDriver(), s.getTimeoutSeconds())).until(ExpectedConditions.presenceOfElementLocated(By.xpath(String.format(XPATH_ABSOLUTE_FILE_UPLOAD_FIELD_LABEL, fieldName))));
            WebElement fieldLayout = getFieldLayout(fieldName, s);
            scrollIntoView(fieldLayout, s);
        } catch (TimeoutException e) {
            return false;
        }
        
        return true;
    }
    
    public static String getValue(WebElement fieldLayout) {
        String value = fieldLayout.findElement(By.xpath(String.format(XPATH_RELATIVE_FILE_UPLOAD_FIELD_FILE))).getText();
        
        Matcher m = FILENAME_PATTERN.matcher(value);
        
        if (m.find()) {
            value = m.group(1);
        }
        
        LOG.debug("FILE UPLOAD FIELD VALUE : " + value);
        
        return value;
    }
    
    public static boolean contains(WebElement fieldLayout, String fieldValue, Settings s) {
        // For read-only
        try {
            return TempoField.contains(fieldLayout, fieldValue);
        } catch (Exception e) {}
        
        fieldValue = Paths.get(fieldValue).getFileName().toString();
        String compareString = getValue(fieldLayout);
        
        LOG.debug("FILE UPLOAD FIELD COMPARISON : Field value [" + fieldValue + "] compared to Entered value [" + compareString + "]");
        
        return compareString.equals(fieldValue);
    }
    
    public static boolean clear(WebElement fieldLayout) {
        WebElement removeLink = fieldLayout.findElement(By.xpath(XPATH_RELATIVE_FILE_UPLOAD_FIELD_REMOVE_LINK));
        removeLink.click();
        
        return true;
    }
    
    public static boolean isType(WebElement fieldLayout) {
        try {
            fieldLayout.findElement(By.xpath(XPATH_RELATIVE_FILE_UPLOAD_FIELD_INPUT));
        } catch (Exception e) {
            return false;
        }
        
        return true;
    }
}
