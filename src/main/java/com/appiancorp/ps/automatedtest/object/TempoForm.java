package com.appiancorp.ps.automatedtest.object;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.appiancorp.ps.automatedtest.common.AppianVersions;

public class TempoForm extends TempoObject {
    
    @SuppressWarnings("unused")
    private static final Logger LOG = Logger.getLogger(TempoForm.class);
    private static final String XPATH_ABSOLUTE_FORM_TITLE = AppianVersions.getByConstant("xpathAbsoluteFormTitle");
    private static final String XPATH_ABSOLUTE_FORM_INSTRUCTIONS = AppianVersions.getByConstant("xpathAbsoluteFormInstructions");
    
    public static String getFormTitle() {
        WebElement element = driver.findElement(By.xpath(String.format(XPATH_ABSOLUTE_FORM_TITLE)));
        
        return element.getText();
    }
    
    public static boolean waitForTitle() {
        try {
            (new WebDriverWait(driver, timeoutSeconds)).until(ExpectedConditions.presenceOfElementLocated(By.xpath(String.format(XPATH_ABSOLUTE_FORM_TITLE))));
            WebElement element = driver.findElement(By.xpath(String.format(XPATH_ABSOLUTE_FORM_TITLE)));
            scrollIntoView(element, true);
        } catch (TimeoutException e) {
            return false;
        }

        return true;
    }
    
    public static String getFormInstructions() {
        WebElement element = driver.findElement(By.xpath(String.format(XPATH_ABSOLUTE_FORM_INSTRUCTIONS)));
        
        return element.getText();
    }
    
    public static boolean waitForInstructions() {
        try {
            (new WebDriverWait(driver, timeoutSeconds)).until(ExpectedConditions.presenceOfElementLocated(By.xpath(String.format(XPATH_ABSOLUTE_FORM_INSTRUCTIONS))));
            WebElement element = driver.findElement(By.xpath(String.format(XPATH_ABSOLUTE_FORM_INSTRUCTIONS)));
            scrollIntoView(element, true);
        } catch (TimeoutException e) {
            return false;
        }

        return true;
    }
}
