package com.appiancorp.ps.automatedtest.object;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TempoRecordList extends TempoObject{

    @SuppressWarnings("unused")
    private static final Logger LOG = Logger.getLogger(TempoRecordList.class);
    private static final String XPATH_RECORD_LIST = "//a[starts-with(@href, '/suite/tempo/records/type') and contains(@href, '/view/all') and contains(text(), '%s')]";
    private static final String XPATH_RECORD_LIST_FACET = "//ol[@class='facetgroup']/descendant::a[contains(text(),'%s')]";

    public static boolean click(String listName) {
        WebElement element = driver.findElement(By.xpath(String.format(XPATH_RECORD_LIST, listName)));
        element.click();

        return true;
    }
    
    public static boolean waitFor(String listName) {
        try {
            (new WebDriverWait(driver, timeoutSeconds)).until(ExpectedConditions.visibilityOfElementLocated(By.xpath(String.format(XPATH_RECORD_LIST, listName))));
            WebElement element = driver.findElement(By.xpath(String.format(XPATH_RECORD_LIST, listName)));
            scrollIntoView(element, false);
        } catch (TimeoutException e) {
            return false;
        }
        
        return true;
    }
    
    public static boolean clickOnFacetOption(String facetName) {
        WebElement element = driver.findElement(By.xpath(String.format(XPATH_RECORD_LIST_FACET, facetName)));
        element.click();

        return true;
    }
    
    public static boolean waitForFacetOption(String facetName) {
        try {
            (new WebDriverWait(driver, timeoutSeconds)).until(ExpectedConditions.visibilityOfElementLocated(By.xpath(String.format(XPATH_RECORD_LIST_FACET, facetName))));
        } catch (TimeoutException e) {
            return false;
        }
        
        return true;
    }
}
