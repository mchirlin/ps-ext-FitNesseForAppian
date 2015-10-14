package com.appiancorp.ps.automatedtest.fields;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TempoGrid extends TempoField {
    
    @SuppressWarnings("unused")
    private static final Logger LOG = Logger.getLogger(TempoGrid.class);
    protected static final String XPATH_NUM_GRID_CELL = "//span[contains(text(), '%s')]/parent::div/following-sibling::div/descendant::table/tbody/tr[%s]/td[%s]";
    protected static final String XPATH_NAME_GRID_CELL = "//span[contains(text(), '%s')]/parent::div/following-sibling::div/descendant::table/tbody/tr[%s]/td[count(//span[contains(text(), '%s')]/parent::div/following-sibling::div/descendant::table/thead/tr/th[.='%s']/preceding-sibling::th)+1]";

    public static WebElement getCell(String gridName, String columnName, String rowNum) {
        // Using a columnNum
        try {
            int columnNum = Integer.parseInt(columnName);
            return driver.findElement(By.xpath(String.format(XPATH_NUM_GRID_CELL, gridName, rowNum, columnNum)));
        } catch (Exception e) {}
        
        // Using columnName
        return driver.findElement(By.xpath(String.format(XPATH_NAME_GRID_CELL, gridName, rowNum, gridName, columnName)));
    }
    
    public static boolean populate(String gridName, String columnName, String rowNum, String[] fieldValues) {
        WebElement cell = getCell(gridName, columnName, rowNum);
        
        // TODO Handle group picker in a grid
        return populate(cell, null, fieldValues);
    }
    
    public static boolean contains(String gridName, String columnName, String rowNum, String[] fieldValues) {
        WebElement cell = getCell(gridName, columnName, rowNum);
        
        // TODO Handle group picker in a grid
        return contains(cell, null, fieldValues);
    }
    
    public static boolean clear(String gridName, String columnName, String rowNum) {
        WebElement cell = getCell(gridName, columnName, rowNum);
        
        // TODO Handle group picker in a grid
        return clear(cell, null);
    }
    
    public static boolean waitFor(String gridName, String columnName, String rowNum) {
        // Using a columnNum
        try {
            int columnNum = Integer.parseInt(columnName);
            (new WebDriverWait(driver, timeoutSeconds)).until(ExpectedConditions.presenceOfElementLocated(By.xpath(String.format(XPATH_NUM_GRID_CELL, gridName, rowNum, columnNum))));
        } catch (Exception e) {}
        
        // Using a columnName
        try {
            (new WebDriverWait(driver, timeoutSeconds)).until(ExpectedConditions.presenceOfElementLocated(By.xpath(String.format(XPATH_NAME_GRID_CELL, gridName, rowNum, gridName, columnName))));
        } catch (Exception e) {
            return false;
        }
        
        return true;
    }
    
}