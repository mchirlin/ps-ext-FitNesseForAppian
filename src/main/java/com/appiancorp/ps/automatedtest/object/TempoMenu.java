package com.appiancorp.ps.automatedtest.object;

import org.apache.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.appiancorp.ps.automatedtest.common.Settings;

public class TempoMenu extends TempoObject {
    
    private static final Logger LOG = Logger.getLogger(TempoMenu.class);
    private static final String XPATH_ABSOLUTE_MENU_LINK = Settings.getByConstant("xpathAbsoluteMenuLink");
    
    public static boolean click(String tempoMenu, Settings s) {
        WebElement element = s.getDriver().findElement(By.xpath(String.format(XPATH_ABSOLUTE_MENU_LINK, tempoMenu)));
        element.click();

        try {
            WebDriverWait wait = new WebDriverWait(s.getDriver(), 1);
            Alert alert = wait.until(ExpectedConditions.alertIsPresent());

            //Accepting alert.
            alert.accept();
            LOG.warn("ALERT");
        } catch (Exception e) {}
        
        return true;
    }
    
    public static boolean waitFor(String tempoMenu, Settings s) {
        try {
            (new WebDriverWait(s.getDriver(), s.getTimeoutSeconds())).until(ExpectedConditions.presenceOfElementLocated(By.xpath(String.format(XPATH_ABSOLUTE_MENU_LINK, tempoMenu))));
            WebElement element = s.getDriver().findElement(By.xpath(String.format(XPATH_ABSOLUTE_MENU_LINK, tempoMenu)));
            scrollIntoView(element, true, s);
        } catch (TimeoutException e) {
            return false;
        }

        return true;
    }
}
