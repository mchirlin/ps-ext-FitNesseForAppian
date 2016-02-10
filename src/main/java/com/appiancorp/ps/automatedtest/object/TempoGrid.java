package com.appiancorp.ps.automatedtest.object;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.appiancorp.ps.automatedtest.common.Metadata;

public class TempoGrid extends TempoField {
    
    private static final Logger LOG = Logger.getLogger(TempoGrid.class);
    private static final String XPATH_ABSOLUTE_GRID_BY_INDEX = Metadata.getByConstant("xpathAbsoluteGridByIndex");
    private static final String XPATH_ABSOLUTE_GRID_BY_LABEL = Metadata.getByConstant("xpathAbsoluteGridByLabel");
    private static final String XPATH_ABSOLUTE_GRID_BY_LABEL_INDEX = "(" + XPATH_ABSOLUTE_GRID_BY_LABEL + ")[%d]";
    
    private static final String XPATH_ABSOLUTE_GRID_BY_LABEL_CELL_BY_COLUMN_LABEL = XPATH_ABSOLUTE_GRID_BY_LABEL + Metadata.getByConstant("xpathConcatCellByColumnLabel");
    private static final String XPATH_ABSOLUTE_GRID_BY_LABEL_CELL_BY_COLUMN_INDEX = XPATH_ABSOLUTE_GRID_BY_LABEL + Metadata.getByConstant("xpathConcatCellByColumnIndex");
    private static final String XPATH_ABSOLUTE_GRID_BY_INDEX_CELL_BY_COLUMN_LABEL = XPATH_ABSOLUTE_GRID_BY_INDEX + Metadata.getByConstant("xpathConcatCellByColumnLabel");
    private static final String XPATH_ABSOLUTE_GRID_BY_INDEX_CELL_BY_COLUMN_INDEX = XPATH_ABSOLUTE_GRID_BY_INDEX + Metadata.getByConstant("xpathConcatCellByColumnIndex");
    private static final String XPATH_ABSOLUTE_GRID_BY_LABEL_INDEX_CELL_BY_COLUMN_LABEL = XPATH_ABSOLUTE_GRID_BY_LABEL_INDEX + Metadata.getByConstant("xpathConcatCellByColumnLabel");
    private static final String XPATH_ABSOLUTE_GRID_BY_LABEL_INDEX_CELL_BY_COLUMN_INDEX = XPATH_ABSOLUTE_GRID_BY_LABEL_INDEX + Metadata.getByConstant("xpathConcatCellByColumnIndex");
    
    private static final String XPATH_RELATIVE_GRID_CELL_BY_COLUMN_LABEL = Metadata.getByConstant("xpathRelativeGridCellByColumnLabel");
    private static final String XPATH_RELATIVE_GRID_CELL_BY_COLUMN_INDEX = Metadata.getByConstant("xpathRelativeGridCellByColumnIndex");
    
    private static final String XPATH_RELATIVE_GRID_CHECKBOX = Metadata.getByConstant("xpathRelativeGridCheckbox");
    private static final String XPATH_RELATIVE_GRID_ADD_ROW_LINK = Metadata.getByConstant("xpathRelativeGridAddRowLink");
    
    private static final String XPATH_RELATIVE_GRID_FIRST_PAGE_LINK = Metadata.getByConstant("xpathRelativeGridFirstPageLink");
    private static final String XPATH_RELATIVE_GRID_PREVIOUS_PAGE_LINK = Metadata.getByConstant("xpathRelativeGridPreviousPageLink");
    private static final String XPATH_RELATIVE_GRID_NEXT_PAGE_LINK = Metadata.getByConstant("xpathRelativeGridNextPageLink");
    private static final String XPATH_RELATIVE_GRID_LAST_PAGE_LINK = Metadata.getByConstant("xpathRelativeGridLastPageLink");
    
    private static final String XPATH_RELATIVE_GRID_COLUMN_LINK = Metadata.getByConstant("xpathRelativeGridColumnLink");
    
    public static WebElement getGrid(String gridName) {
        if (isFieldIndex(gridName)) {
            int gNum = getIndexFromFieldIndex(gridName);
            String gName = getFieldFromFieldIndex(gridName);
            if(StringUtils.isBlank(gName)) {
                return driver.findElement(By.xpath(String.format(XPATH_ABSOLUTE_GRID_BY_INDEX, gNum)));
            } else {
                return driver.findElement(By.xpath(String.format(XPATH_ABSOLUTE_GRID_BY_LABEL_INDEX, gName, gNum)));
            }
        } else {
            return driver.findElement(By.xpath(String.format(XPATH_ABSOLUTE_GRID_BY_LABEL, gridName)));
        }
    }
    
    public static WebElement getCell(String gridName, String columnName, String rowNum) {
        WebElement grid = getGrid(gridName);
        int rNum = getIndexFromFieldIndex(rowNum);
        // Using a columnNum
        if (isFieldIndex(columnName)) {
            int cNum = getIndexFromFieldIndex(columnName);
            return grid.findElement(By.xpath(String.format(XPATH_RELATIVE_GRID_CELL_BY_COLUMN_INDEX, rNum, cNum)));
        }
        
        // Using columnName
        if (isFieldIndex(gridName)) {
            gridName = getFieldFromFieldIndex(gridName);
        }
        return grid.findElement(By.xpath(String.format(XPATH_RELATIVE_GRID_CELL_BY_COLUMN_LABEL, rNum, gridName, columnName)));
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
            WebElement checkbox = cell.findElement(By.xpath(XPATH_RELATIVE_GRID_CHECKBOX));
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
        WebElement checkbox = cell.findElement(By.xpath(XPATH_RELATIVE_GRID_CHECKBOX));
        
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
                   (new WebDriverWait(driver, timeoutSeconds)).until(ExpectedConditions.presenceOfElementLocated(By.xpath(String.format(XPATH_ABSOLUTE_GRID_BY_INDEX, gNum))));
               } else {
                   (new WebDriverWait(driver, timeoutSeconds)).until(ExpectedConditions.presenceOfElementLocated(By.xpath(String.format(XPATH_ABSOLUTE_GRID_BY_LABEL_INDEX, gName, gNum))));
               }
           } else {
               (new WebDriverWait(driver, timeoutSeconds)).until(ExpectedConditions.presenceOfElementLocated(By.xpath(String.format(XPATH_ABSOLUTE_GRID_BY_LABEL, gridName))));
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
                        (new WebDriverWait(driver, timeoutSeconds)).until(ExpectedConditions.presenceOfElementLocated(By.xpath(String.format(XPATH_ABSOLUTE_GRID_BY_INDEX_CELL_BY_COLUMN_INDEX, gNum, rNum, cNum))));   
                    } else {
                        // Using a columnName
                        (new WebDriverWait(driver, timeoutSeconds)).until(ExpectedConditions.presenceOfElementLocated(By.xpath(String.format(XPATH_ABSOLUTE_GRID_BY_INDEX_CELL_BY_COLUMN_LABEL, gNum, rNum, gridName, columnName))));
                    }
                } else {
                    if (isFieldIndex(columnName)) {
                        // Using a columnNum
                        int cNum = getIndexFromFieldIndex(columnName);
                        (new WebDriverWait(driver, timeoutSeconds)).until(ExpectedConditions.presenceOfElementLocated(By.xpath(String.format(XPATH_ABSOLUTE_GRID_BY_LABEL_INDEX_CELL_BY_COLUMN_INDEX, gName, gNum, rNum, cNum))));
                        
                    } else {
                        // Using a columnName
                        (new WebDriverWait(driver, timeoutSeconds)).until(ExpectedConditions.presenceOfElementLocated(By.xpath(String.format(XPATH_ABSOLUTE_GRID_BY_LABEL_INDEX_CELL_BY_COLUMN_LABEL, gName, gNum, rNum, gridName, columnName))));
                    }
                }
            } else {
                if (isFieldIndex(columnName)) {
                    // Using a columnNum
                    int cNum = getIndexFromFieldIndex(columnName);
                    (new WebDriverWait(driver, timeoutSeconds)).until(ExpectedConditions.presenceOfElementLocated(By.xpath(String.format(XPATH_ABSOLUTE_GRID_BY_LABEL_CELL_BY_COLUMN_INDEX, gridName, rNum, cNum))));
                    
                } else {
                    // Using a columnName
                    (new WebDriverWait(driver, timeoutSeconds)).until(ExpectedConditions.presenceOfElementLocated(By.xpath(String.format(XPATH_ABSOLUTE_GRID_BY_LABEL_CELL_BY_COLUMN_LABEL, gridName, rNum, gridName, columnName))));
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
    
    public static boolean clickOnAddRowLink(String gridName){
        
        try {
            WebElement grid = getGrid(gridName);
            WebElement link = grid.findElement(By.xpath(XPATH_RELATIVE_GRID_ADD_ROW_LINK));
            link.click();
        } catch (Exception e) {
            LOG.warn("GRID ADD LINK for " + gridName + e.getClass());
            return false;
        }
        
        return true;
    }
    
    public static boolean clickOnFirstPageLink(String gridName){
        try {
            WebElement grid = getGrid(gridName);
            WebElement link = grid.findElement(By.xpath(XPATH_RELATIVE_GRID_FIRST_PAGE_LINK));
            link.click();
        } catch (Exception e) {
            LOG.warn("GRID FIRST PAGE LINK for " + gridName + e.getClass());
            return false;
        }
        return true;
    }
    public static boolean clickOnPreviousPageLink(String gridName){
        try {
            WebElement grid = getGrid(gridName);
            WebElement link = grid.findElement(By.xpath(XPATH_RELATIVE_GRID_PREVIOUS_PAGE_LINK));
            link.click();
        } catch (Exception e) {
            LOG.warn("GRID LAST PAGE LINK for " + gridName + e.getClass());
            return false;
        }
        return true;
    }
    public static boolean clickOnNextPageLink(String gridName){
        try {
            WebElement grid = getGrid(gridName);
            WebElement link = grid.findElement(By.xpath(XPATH_RELATIVE_GRID_NEXT_PAGE_LINK));
            link.click();
        } catch (Exception e) {
            LOG.warn("GRID NEXT PAGE LINK for " + gridName + e.getClass());
            return false;
        }
        return true;
    }
    public static boolean clickOnLastPageLink(String gridName){
        try {
            WebElement grid = getGrid(gridName);
            WebElement link = grid.findElement(By.xpath(XPATH_RELATIVE_GRID_LAST_PAGE_LINK));
            link.click();
        } catch (Exception e) {
            LOG.warn("GRID LAST PAGE LINK for " + gridName + e.getClass());
            return false;
        }
        return true;
    }
    
    public static boolean sortByColumn(String gridName, String columnName){
        try{
            WebElement grid = getGrid(gridName);
            WebElement column = grid.findElement(By.xpath(String.format(XPATH_RELATIVE_GRID_COLUMN_LINK, columnName)));
            column.click();
        }
        catch (Exception e){
            LOG.warn("GRID " + gridName + " and COLUMN " +columnName + e.getClass());
        }
        return true;
    }
    
}