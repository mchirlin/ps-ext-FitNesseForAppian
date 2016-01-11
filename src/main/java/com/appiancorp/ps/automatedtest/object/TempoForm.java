package com.appiancorp.ps.automatedtest.object;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TempoForm extends TempoObject {
    
    @SuppressWarnings("unused")
    private static final Logger LOG = Logger.getLogger(TempoForm.class);
    private static final String XPATH_TITLE = "//h2";
    private static final String XPATH_INSTRUCTIONS = "//h2/following-sibling::p";
    
    public static String getTitle() {
        WebElement element = driver.findElement(By.xpath(String.format(XPATH_TITLE)));
        
        return element.getText();
    }
    
    public static boolean waitForTitle() {
        try {
            (new WebDriverWait(driver, timeoutSeconds)).until(ExpectedConditions.presenceOfElementLocated(By.xpath(String.format(XPATH_TITLE))));
            WebElement element = driver.findElement(By.xpath(String.format(XPATH_TITLE)));
            scrollIntoView(element, true);
        } catch (TimeoutException e) {
            return false;
        }

        return true;
    }
    
    public static String getInstructions() {
        WebElement element = driver.findElement(By.xpath(String.format(XPATH_INSTRUCTIONS)));
        
        return element.getText();
    }
    
    public static boolean waitForInstructions() {
        try {
            (new WebDriverWait(driver, timeoutSeconds)).until(ExpectedConditions.presenceOfElementLocated(By.xpath(String.format(XPATH_INSTRUCTIONS))));
            WebElement element = driver.findElement(By.xpath(String.format(XPATH_INSTRUCTIONS)));
            scrollIntoView(element, true);
        } catch (TimeoutException e) {
            return false;
        }

        return true;
    }
}
