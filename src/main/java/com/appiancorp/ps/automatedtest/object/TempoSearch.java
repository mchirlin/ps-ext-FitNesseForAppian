package com.appiancorp.ps.automatedtest.object;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TempoSearch extends TempoObject{

    private static final Logger LOG = Logger.getLogger(TempoRecordType.class);
    private static final String XPATH_TEMPO_SEARCH = "//div[contains(@class, 'appian-tempo-search')]/descendant::input"; //this works for 7.11
    
    public static boolean populateSearch(String searchValue){
        WebElement fieldLayout = driver.findElement(By.xpath(XPATH_TEMPO_SEARCH));
        fieldLayout.clear();
        fieldLayout.sendKeys(searchValue);
        fieldLayout.sendKeys(Keys.ENTER);
        
        LOG.debug("TEXT FIELD POPULATION ERROR: " + searchValue);

        return true;
    }
    
    public static boolean waitFor(String type) {
        try {
            (new WebDriverWait(driver, timeoutSeconds)).until(ExpectedConditions.visibilityOfElementLocated(By.xpath(String.format(XPATH_TEMPO_SEARCH))));
            WebElement element = driver.findElement(By.xpath(String.format(XPATH_TEMPO_SEARCH)));
            scrollIntoView(element, false);
        } catch (TimeoutException e) {
            return false;
        }
        
        return true;
}
}
