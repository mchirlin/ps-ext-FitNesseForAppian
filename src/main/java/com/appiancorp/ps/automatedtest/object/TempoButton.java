package com.appiancorp.ps.automatedtest.object;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.appiancorp.ps.automatedtest.common.Settings;

public class TempoButton extends TempoField {
    
    private static final Logger LOG = Logger.getLogger(TempoButton.class);
    private static final String XPATH_ABSOLUTE_BUTTON = Settings.getByConstant("xpathAbsoluteButton");
    
    public static boolean click(String buttonName, Settings s) {        
        WebElement element = s.getDriver().findElement(By.xpath(String.format(XPATH_ABSOLUTE_BUTTON, buttonName)));
        element.click();
        
        LOG.debug("BUTTON CLICK : " + buttonName);
        
        waitForWorking(s);
        return true;
    }
    
    public static boolean waitFor(String buttonName, Settings s) {
        try {
            (new WebDriverWait(s.getDriver(), s.getTimeoutSeconds())).until(ExpectedConditions.presenceOfElementLocated(By.xpath(String.format(XPATH_ABSOLUTE_BUTTON, buttonName))));
            WebElement element = s.getDriver().findElement(By.xpath(String.format(XPATH_ABSOLUTE_BUTTON, buttonName)));
            scrollIntoView(element, s);
        } catch (TimeoutException e) {
            return false;
        }
        
        return true;
    }
}
