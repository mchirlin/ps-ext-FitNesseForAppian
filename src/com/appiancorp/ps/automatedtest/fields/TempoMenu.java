package com.appiancorp.ps.automatedtest.fields;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TempoMenu extends TempoField {
    
    public static boolean click(String tempoMenu) {
        WebElement element = driver.findElement(By.xpath("//a[starts-with(@class, 'appian-menu-item') and contains(text(),'" + tempoMenu +"')]"));
        element.click();

        return true;
    }
    
    public static boolean waitFor(String tempoMenu) {
        try {
            (new WebDriverWait(driver, timeOutSeconds)).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[starts-with(@class, 'appian-menu-item') and contains(text(),'" + tempoMenu +"')]")));
        } catch (Exception e) {
            return false;
        }

        return true;
    }
}
