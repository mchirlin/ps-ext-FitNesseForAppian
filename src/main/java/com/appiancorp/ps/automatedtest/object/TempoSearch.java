package com.appiancorp.ps.automatedtest.object;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.appiancorp.ps.automatedtest.common.Metadata;

public class TempoSearch extends TempoObject{

    private static final Logger LOG = Logger.getLogger(TempoRecordType.class);
    private static final String XPATH_ABSOLUTE_SEARCH_FIELD = Metadata.getByConstant("xpathAbsoluteSearchField");
    
    public static boolean searchFor(String searchValue){
        WebElement fieldLayout = driver.findElement(By.xpath(XPATH_ABSOLUTE_SEARCH_FIELD));
        fieldLayout.clear();
        fieldLayout.sendKeys(searchValue);
        fieldLayout.sendKeys(Keys.ENTER);
        
        LOG.debug("SEARCH FIELD POPULATION: " + searchValue);

        return true;
    }
    
    public static boolean waitFor(String type) {
        try {
            (new WebDriverWait(driver, timeoutSeconds)).until(ExpectedConditions.visibilityOfElementLocated(By.xpath(String.format(XPATH_ABSOLUTE_SEARCH_FIELD))));
            WebElement element = driver.findElement(By.xpath(String.format(XPATH_ABSOLUTE_SEARCH_FIELD)));
            scrollIntoView(element, false);
        } catch (TimeoutException e) {
            return false;
        }
        
        return true;
}
}
