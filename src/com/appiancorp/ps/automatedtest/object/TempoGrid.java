package com.appiancorp.ps.automatedtest.object;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TempoGrid extends TempoField {
    
    private static final Logger LOG = Logger.getLogger(TempoGrid.class);
    private static final String XPATH_ABSOLUTE_GRID = "//span[contains(text(), '%s')]/parent::div/following-sibling::div/descendant::table";
    private static final String XPATH_ABSOLUTE_GRID_INDEX = "(" + XPATH_ABSOLUTE_GRID + ")[%d]";
    
    private static final String XPATH_ABSOLUTE_GRID_CELL = XPATH_ABSOLUTE_GRID + "/tbody/tr[%d]/td[count(//span[contains(text(), '%s')]/parent::div/following-sibling::div/descendant::table/thead/tr/th[.='%s']/preceding-sibling::th)+1]";
    private static final String XPATH_ABSOLUTE_GRID_CELL_INDEX = XPATH_ABSOLUTE_GRID + "/tbody/tr[%d]/td[%d]";
    private static final String XPATH_ABSOLUTE_GRID_INDEX_CELL = XPATH_ABSOLUTE_GRID_INDEX + "/tbody/tr[%d]/td[count(//span[contains(text(), '%s')]/parent::div/following-sibling::div/descendant::table/thead/tr/th[.='%s']/preceding-sibling::th)+1]";
    private static final String XPATH_ABSOLUTE_GRID_INDEX_CELL_INDEX = XPATH_ABSOLUTE_GRID_INDEX + "/tbody/tr[%d]/td[%d]";
    
    private static final String XPATH_RELATIVE_GRID_CELL = ".//tbody/tr[%d]/td[count(//span[contains(text(), '%s')]/parent::div/following-sibling::div/descendant::table/thead/tr/th[.='%s']/preceding-sibling::th)+1]";
    private static final String XPATH_RELATIVE_GRID_CELL_INDEX = ".//tbody/tr[%d]/td[%d]";

    public static WebElement getGrid(String gridName) {
        if (isFieldIndex(gridName)) {
            int gNum = getIndexFromFieldIndex(gridName);
            String gName = getFieldFromFieldIndex(gridName);
            
            return driver.findElement(By.xpath(String.format(XPATH_ABSOLUTE_GRID_INDEX, gName, gNum)));
        } else {
            return driver.findElement(By.xpath(String.format(XPATH_ABSOLUTE_GRID, gridName)));
        }
    }
    
    public static WebElement getCell(String gridName, String columnName, String rowNum) {
        WebElement grid = getGrid(gridName);
        int rNum = getIndexFromFieldIndex(rowNum);
        // Using a columnNum
        if (isFieldIndex(columnName)) {
            int cNum = getIndexFromFieldIndex(columnName);
            return grid.findElement(By.xpath(String.format(XPATH_RELATIVE_GRID_CELL_INDEX, rNum, cNum)));
        }
        
        // Using columnName
        if (isFieldIndex(gridName)) {
            gridName = getFieldFromFieldIndex(gridName);
        }
        return grid.findElement(By.xpath(String.format(XPATH_RELATIVE_GRID_CELL, rNum, gridName, columnName)));
    }
    
    public static boolean populate(String gridName, String columnName, String rowNum, String[] fieldValues) {
        // TODO Handle group picker in a grid
        for (String fieldValue : fieldValues) {
            try {
                WebElement cell = getCell(gridName, columnName, rowNum);
                if (!populate(cell, null, fieldValue)) return false;
            } catch (Exception e) {
                LOG.warn("GRID POPULATION for " + gridName + "|" + columnName + "|" + rowNum +": " + e.getClass());
                return false;
            }
        }
        
        return true;
    }
    
    public static boolean contains(String gridName, String columnName, String rowNum, String[] fieldValues) {
        for (String fieldValue : fieldValues) {
            try {
                WebElement cell = getCell(gridName, columnName, rowNum);
                if (!contains(cell, null, fieldValue)) return false;
            } catch (Exception e) {
                LOG.warn("GRID CONTAINS for " + gridName + "|" + columnName + "|" + rowNum +": " + e.getClass());
                return false;
            }
        }
        
        return true;
    }
    
    public static boolean clear(String gridName, String columnName, String rowNum) {
        WebElement cell = getCell(gridName, columnName, rowNum);
        
        return clear(cell, null);
    }
    
    public static boolean waitFor(String gridName, String columnName, String rowNum) {
        try {
            int rNum = getIndexFromFieldIndex(rowNum);
            
            if (isFieldIndex(gridName)) {
                int gNum = getIndexFromFieldIndex(gridName);
                String gName = getFieldFromFieldIndex(gridName);
                
                if (isFieldIndex(columnName)) {
                    // Using a columnNum
                    int cNum = getIndexFromFieldIndex(columnName);
                    (new WebDriverWait(driver, timeoutSeconds)).until(ExpectedConditions.presenceOfElementLocated(By.xpath(String.format(XPATH_ABSOLUTE_GRID_INDEX_CELL_INDEX, gName, gNum, rNum, cNum))));
                    
                } else {
                    // Using a columnName
                    (new WebDriverWait(driver, timeoutSeconds)).until(ExpectedConditions.presenceOfElementLocated(By.xpath(String.format(XPATH_ABSOLUTE_GRID_INDEX_CELL, gName, gNum, rNum, gridName, columnName))));
                }
            } else {
                if (isFieldIndex(columnName)) {
                    // Using a columnNum
                    int cNum = getIndexFromFieldIndex(columnName);
                    (new WebDriverWait(driver, timeoutSeconds)).until(ExpectedConditions.presenceOfElementLocated(By.xpath(String.format(XPATH_ABSOLUTE_GRID_CELL_INDEX, gridName, rNum, cNum))));
                    
                } else {
                    // Using a columnName
                    (new WebDriverWait(driver, timeoutSeconds)).until(ExpectedConditions.presenceOfElementLocated(By.xpath(String.format(XPATH_ABSOLUTE_GRID_CELL, gridName, rNum, gridName, columnName))));
                }
            }
            
            // Attempt to scroll into view
            int attempt = 0;
            while (attempt < attemptTimes) {
                try {
                    WebElement grid = getGrid(gridName);
                    scrollIntoView(grid);
                    return true;
                } catch (Exception e) {
                    attempt++;
                }
            }
        } catch (TimeoutException nse) {
            return false;
        }
        return false;
    }
    
}