package com.appiancorp.ps.automatedtest.object;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.appiancorp.ps.automatedtest.common.Metadata;

public class TempoAction extends TempoObject {
    
    @SuppressWarnings("unused")
    private static final Logger LOG = Logger.getLogger(TempoAction.class);
    private static final String XPATH_ABSOLUTE_ACTION_LINK = Metadata.getByConstant("xpathAbsoluteActionLink");
    
    public static boolean click(String actionName) {
        WebElement element = driver.findElement(By.xpath(String.format(XPATH_ABSOLUTE_ACTION_LINK, actionName)));
        element.click();
    
        return true;
    }
    
    public static boolean waitFor(String actionName) {
        try {
            (new WebDriverWait(driver, timeoutSeconds)).until(ExpectedConditions.presenceOfElementLocated(By.xpath(String.format(XPATH_ABSOLUTE_ACTION_LINK, actionName))));
            WebElement element = driver.findElement(By.xpath(String.format(XPATH_ABSOLUTE_ACTION_LINK, actionName)));
            scrollIntoView(element);
        } catch (TimeoutException e) {
            return false;
        }
        
        return true;
    }
    
    public static boolean isCompleted() {
        try {
            (new WebDriverWait(driver, timeoutSeconds)).until(ExpectedConditions.textToBePresentInElementLocated(By.id("inlineConfirmMessage"), "Action completed successfully"));
        } catch (Exception e) {
            return false;
        }
        
        return true;
    }
}
