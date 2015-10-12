package com.appiancorp.ps.automatedtest.fields;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TempoButton extends TempoField {
    
    private static final Logger LOG = Logger.getLogger(TempoButton.class);
    
    public static boolean click(String buttonName) {
        waitForWorking();
        
        WebElement element = driver.findElement(By.xpath("//button[contains(text(), '"+buttonName+"')]"));
        element.click();
        
        LOG.debug("BUTTON CLICK : " + buttonName);
        
        return true;
    }
    
    public static boolean waitFor(String buttonName) {
        try {
            (new WebDriverWait(driver, timeOutSeconds)).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[contains(text(), '"+buttonName+"')]")));
        } catch (Exception e) {
            return false;
        }
        
        return true;
    }
}
