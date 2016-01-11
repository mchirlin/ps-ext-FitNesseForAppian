package com.appiancorp.ps.automatedtest.object;

import org.apache.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TempoMenu extends TempoObject {
    
    private static final Logger LOG = Logger.getLogger(TempoMenu.class);
    private static final String XPATH_ELEMENT = "//a[starts-with(@class, 'appian-menu-item') and contains(text(),'%s')]";
    
    public static boolean click(String tempoMenu) {
        WebElement element = driver.findElement(By.xpath(String.format(XPATH_ELEMENT, tempoMenu)));
        element.click();

        try {
            WebDriverWait wait = new WebDriverWait(driver, 1);
            Alert alert = wait.until(ExpectedConditions.alertIsPresent());

            //Accepting alert.
            alert.accept();
            LOG.warn("ALERT");
        } catch (Exception e) {}
        
        return true;
    }
    
    public static boolean waitFor(String tempoMenu) {
        try {
            (new WebDriverWait(driver, timeoutSeconds)).until(ExpectedConditions.presenceOfElementLocated(By.xpath(String.format(XPATH_ELEMENT, tempoMenu))));
            WebElement element = driver.findElement(By.xpath(String.format(XPATH_ELEMENT, tempoMenu)));
            scrollIntoView(element, true);
        } catch (TimeoutException e) {
            return false;
        }

        return true;
    }
}
