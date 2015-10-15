package com.appiancorp.ps.automatedtest.fields;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TempoMenu extends TempoObject {
    
    @SuppressWarnings("unused")
    private static final Logger LOG = Logger.getLogger(TempoMenu.class);
    protected static final String XPATH_ELEMENT = "//a[starts-with(@class, 'appian-menu-item') and contains(text(),'%s')]";
    
    public static boolean click(String tempoMenu) {
        WebElement element = driver.findElement(By.xpath(String.format(XPATH_ELEMENT, tempoMenu)));
        element.click();

        return true;
    }
    
    public static boolean waitFor(String tempoMenu) {
        try {
            (new WebDriverWait(driver, timeoutSeconds)).until(ExpectedConditions.presenceOfElementLocated(By.xpath(String.format(XPATH_ELEMENT, tempoMenu))));
            WebElement element = driver.findElement(By.xpath(String.format(XPATH_ELEMENT, tempoMenu)));
            scrollIntoView(element, true);
        } catch (Exception e) {
            return false;
        }

        return true;
    }
}
