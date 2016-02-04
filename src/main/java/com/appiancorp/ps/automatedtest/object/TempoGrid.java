package com.appiancorp.ps.automatedtest.object;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TempoGrid extends TempoField {
    
    private static final Logger LOG = Logger.getLogger(TempoGrid.class);
    private static final String XPATH_ABSOLUTE_GRID_INDEX = "(//div[contains(@class, 'DataGrid-Table')]/descendant::table)[%d]";
    private static final String XPATH_ABSOLUTE_GRID_LABEL = "//span[contains(text(), '%s')]/parent::div/following-sibling::div/descendant::table";
    private static final String XPATH_ABSOLUTE_GRID_LABEL_INDEX = "(" + XPATH_ABSOLUTE_GRID_LABEL + ")[%d]";
    
    private static final String XPATH_ABSOLUTE_GRID_CELL = XPATH_ABSOLUTE_GRID_LABEL + "/tbody/tr[%d]/td[count(//span[contains(text(), '%s')]/parent::div/following-sibling::div/descendant::table/thead/tr/th[.='%s']/preceding-sibling::th)+1]";
    private static final String XPATH_ABSOLUTE_GRID_CELL_INDEX = XPATH_ABSOLUTE_GRID_LABEL + "/tbody/tr[%d]/td[%d]";
    private static final String XPATH_ABSOLUTE_GRID_INDEX_CELL = XPATH_ABSOLUTE_GRID_INDEX + "/tbody/tr[%d]/td[count(//span[contains(text(), '%s')]/parent::div/following-sibling::div/descendant::table/thead/tr/th[.='%s']/preceding-sibling::th)+1]";
    private static final String XPATH_ABSOLUTE_GRID_INDEX_CELL_INDEX = XPATH_ABSOLUTE_GRID_INDEX + "/tbody/tr[%d]/td[%d]";
    private static final String XPATH_ABSOLUTE_GRID_LABEL_INDEX_CELL = XPATH_ABSOLUTE_GRID_LABEL_INDEX + "/tbody/tr[%d]/td[count(//span[contains(text(), '%s')]/parent::div/following-sibling::div/descendant::table/thead/tr/th[.='%s']/preceding-sibling::th)+1]";
    private static final String XPATH_ABSOLUTE_GRID_LABEL_INDEX_CELL_INDEX = XPATH_ABSOLUTE_GRID_LABEL_INDEX + "/tbody/tr[%d]/td[%d]";
    
    private static final String XPATH_RELATIVE_GRID_CELL = ".//tbody/tr[%d]/td[count(//span[contains(text(), '%s')]/parent::div/following-sibling::div/descendant::table/thead/tr/th[.='%s']/preceding-sibling::th)+1]";
    private static final String XPATH_RELATIVE_GRID_CELL_INDEX = ".//tbody/tr[%d]/td[%d]";
    
    private static final String XPATH_RELATIVE_CHECKBOX = ".//input[@type = 'checkbox']";
    
    private static final String XPATH_RELATIVE_LINK = "./tfoot//a" ;

    public static WebElement getGrid(String gridName) {
        if (isFieldIndex(gridName)) {
            int gNum = getIndexFromFieldIndex(gridName);
            String gName = getFieldFromFieldIndex(gridName);
            if(StringUtils.isBlank(gName)) {
                return driver.findElement(By.xpath(String.format(XPATH_ABSOLUTE_GRID_INDEX, gNum)));
            } else {
                return driver.findElement(By.xpath(String.format(XPATH_ABSOLUTE_GRID_LABEL_INDEX, gName, gNum)));
            }
        } else {
            return driver.findElement(By.xpath(String.format(XPATH_ABSOLUTE_GRID_LABEL, gridName)));
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
    
    public static boolean selectRow(String gridName, String rowNum) {
        try {
            WebElement cell = getCell(gridName, "[1]", rowNum);
            WebElement checkbox = cell.findElement(By.xpath(XPATH_RELATIVE_CHECKBOX));
            checkbox.click();
        } catch (Exception e) {
            LOG.warn("GRID ROW SELECTION for " + gridName + "|" + rowNum +": " + e.getClass());
            return false;
        }
        
        return true;
    }
    
    public static String getValue(String gridName, String columnName, String rowNum) {
        WebElement cell = getCell(gridName, columnName, rowNum);
        
        return getValue(cell, null);
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
    
    public static boolean verifyGridRowIsSelected(String gridName, String rowNum) {
        WebElement cell = getCell(gridName, "[1]", rowNum);
        
        WebElement checkbox = cell.findElement(By.xpath(XPATH_RELATIVE_CHECKBOX));
        
        return checkbox.isSelected();
    }
    
    public static boolean clear(String gridName, String columnName, String rowNum) {
        WebElement cell = getCell(gridName, columnName, rowNum);
        
        return clear(cell, null);
    }

    public static boolean waitFor(String gridName) {
       try { 
           if (isFieldIndex(gridName)) {
               int gNum = getIndexFromFieldIndex(gridName);
               String gName = getFieldFromFieldIndex(gridName);

               if(StringUtils.isBlank(gName)) {
                   (new WebDriverWait(driver, timeoutSeconds)).until(ExpectedConditions.presenceOfElementLocated(By.xpath(String.format(XPATH_ABSOLUTE_GRID_INDEX, gNum))));
               }
               
               else{
                   (new WebDriverWait(driver, timeoutSeconds)).until(ExpectedConditions.presenceOfElementLocated(By.xpath(String.format(XPATH_ABSOLUTE_GRID_LABEL_INDEX, gName, gNum))));
               }
           }
           
           else{
               (new WebDriverWait(driver, timeoutSeconds)).until(ExpectedConditions.presenceOfElementLocated(By.xpath(String.format(XPATH_ABSOLUTE_GRID_LABEL, gridName))));
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
       }
       catch (TimeoutException nse) {
           return false;
           
       }
        return true;
    }
    
    public static boolean waitFor(String gridName, String rowNum) {
        return waitFor(gridName, "[1]", rowNum);
    }
    
    public static boolean waitFor(String gridName, String columnName, String rowNum) {
        try {
            int rNum = getIndexFromFieldIndex(rowNum);
            
            if (isFieldIndex(gridName)) {
                int gNum = getIndexFromFieldIndex(gridName);
                String gName = getFieldFromFieldIndex(gridName);
                
                if(StringUtils.isBlank(gName)) {
                    if (isFieldIndex(columnName)) {
                        // Using a columnNum
                        int cNum = getIndexFromFieldIndex(columnName);
                        (new WebDriverWait(driver, timeoutSeconds)).until(ExpectedConditions.presenceOfElementLocated(By.xpath(String.format(XPATH_ABSOLUTE_GRID_INDEX_CELL_INDEX, gNum, rNum, cNum))));   
                    } else {
                        // Using a columnName
                        (new WebDriverWait(driver, timeoutSeconds)).until(ExpectedConditions.presenceOfElementLocated(By.xpath(String.format(XPATH_ABSOLUTE_GRID_INDEX_CELL, gNum, rNum, gridName, columnName))));
                    }
                } else {
                    if (isFieldIndex(columnName)) {
                        // Using a columnNum
                        int cNum = getIndexFromFieldIndex(columnName);
                        (new WebDriverWait(driver, timeoutSeconds)).until(ExpectedConditions.presenceOfElementLocated(By.xpath(String.format(XPATH_ABSOLUTE_GRID_LABEL_INDEX_CELL_INDEX, gName, gNum, rNum, cNum))));
                        
                    } else {
                        // Using a columnName
                        (new WebDriverWait(driver, timeoutSeconds)).until(ExpectedConditions.presenceOfElementLocated(By.xpath(String.format(XPATH_ABSOLUTE_GRID_LABEL_INDEX_CELL, gName, gNum, rNum, gridName, columnName))));
                    }
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
    
    public static boolean clickOnAddLink(String gridName){
        
        try {
            WebElement grid = getGrid(gridName);
            WebElement link = grid.findElement(By.xpath(XPATH_RELATIVE_LINK));
            link.click();
        } catch (Exception e) {
            LOG.warn("GRID ADD LINK for " + gridName + e.getClass());
            return false;
        }
        
        return true;
    }
}