package com.appiancorp.ps.automatedtest.fields;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TempoLinkField extends TempoField {
    
    @SuppressWarnings("unused")
    private static final Logger LOG = Logger.getLogger(TempoLinkField.class);
    protected static final String XPATH_ELEMENT = "//a[contains(text(), '%s')]";
    
    public static boolean click(String linkName) {
        WebElement element = driver.findElement(By.xpath(String.format(XPATH_ELEMENT, linkName)));
        element.click();
        
        return true;
    }
    
    public static boolean waitFor(String linkName) {
        try {
            (new WebDriverWait(driver, timeoutSeconds)).until(ExpectedConditions.presenceOfElementLocated(By.xpath(String.format(XPATH_ELEMENT, linkName))));
            WebElement element = driver.findElement(By.xpath(String.format(XPATH_ELEMENT, linkName)));
            scrollIntoView(element);
        } catch (Exception e) {
            return false;
        }
        
        return true;
    }
}
