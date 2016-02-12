package com.appiancorp.ps.automatedtest.object;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.appiancorp.ps.automatedtest.common.Settings;

public class TempoAction extends TempoObject {
    
    @SuppressWarnings("unused")
    private static final Logger LOG = Logger.getLogger(TempoAction.class);
    private static final String XPATH_ABSOLUTE_ACTION_LINK = Settings.getByConstant("xpathAbsoluteActionLink");
    
    public static boolean click(String actionName, Settings s) {
        WebElement element = s.getDriver().findElement(By.xpath(String.format(XPATH_ABSOLUTE_ACTION_LINK, actionName)));
        element.click();
    
        return true;
    }
    
    public static boolean waitFor(String actionName, Integer timeout, Settings s) {
        try {
            (new WebDriverWait(s.getDriver(), timeout)).until(ExpectedConditions.presenceOfElementLocated(By.xpath(String.format(XPATH_ABSOLUTE_ACTION_LINK, actionName))));
            WebElement element = s.getDriver().findElement(By.xpath(String.format(XPATH_ABSOLUTE_ACTION_LINK, actionName)));
            scrollIntoView(element, s);
        } catch (TimeoutException e) {
            return false;
        }
        
        return true;
    }
    
    public static boolean waitFor(String actionName, Settings s) {
        return waitFor(actionName, s.getTimeoutSeconds(), s);
    }
    
    public static boolean isCompleted(Settings s) {
        try {
            (new WebDriverWait(s.getDriver(), s.getTimeoutSeconds())).until(ExpectedConditions.textToBePresentInElementLocated(By.id("inlineConfirmMessage"), "Action completed successfully"));
        } catch (Exception e) {
            return false;
        }
        
        return true;
    }
}
