package com.appiancorp.ps.automatedtest.object;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.appiancorp.ps.automatedtest.common.Settings;

public class TempoGrid extends TempoField {
    
    private static final Logger LOG = Logger.getLogger(TempoGrid.class);
    private static final String XPATH_ABSOLUTE_GRID_BY_INDEX = Settings.getByConstant("xpathAbsoluteGridByIndex");
    private static final String XPATH_ABSOLUTE_GRID_BY_LABEL = Settings.getByConstant("xpathAbsoluteGridByLabel");
    private static final String XPATH_ABSOLUTE_GRID_BY_LABEL_INDEX = "(" + XPATH_ABSOLUTE_GRID_BY_LABEL + ")[%d]";
    
    private static final String XPATH_ABSOLUTE_GRID_BY_LABEL_CELL_BY_COLUMN_LABEL = XPATH_ABSOLUTE_GRID_BY_LABEL + Settings.getByConstant("xpathConcatCellByColumnLabel");
    private static final String XPATH_ABSOLUTE_GRID_BY_LABEL_CELL_BY_COLUMN_INDEX = XPATH_ABSOLUTE_GRID_BY_LABEL + Settings.getByConstant("xpathConcatCellByColumnIndex");
    private static final String XPATH_ABSOLUTE_GRID_BY_INDEX_CELL_BY_COLUMN_LABEL = XPATH_ABSOLUTE_GRID_BY_INDEX + Settings.getByConstant("xpathConcatCellByColumnLabel");
    private static final String XPATH_ABSOLUTE_GRID_BY_INDEX_CELL_BY_COLUMN_INDEX = XPATH_ABSOLUTE_GRID_BY_INDEX + Settings.getByConstant("xpathConcatCellByColumnIndex");
    private static final String XPATH_ABSOLUTE_GRID_BY_LABEL_INDEX_CELL_BY_COLUMN_LABEL = XPATH_ABSOLUTE_GRID_BY_LABEL_INDEX + Settings.getByConstant("xpathConcatCellByColumnLabel");
    private static final String XPATH_ABSOLUTE_GRID_BY_LABEL_INDEX_CELL_BY_COLUMN_INDEX = XPATH_ABSOLUTE_GRID_BY_LABEL_INDEX + Settings.getByConstant("xpathConcatCellByColumnIndex");
    
    private static final String XPATH_RELATIVE_GRID_CELL_BY_COLUMN_LABEL = Settings.getByConstant("xpathRelativeGridCellByColumnLabel");
    private static final String XPATH_RELATIVE_GRID_CELL_BY_COLUMN_INDEX = Settings.getByConstant("xpathRelativeGridCellByColumnIndex");
    
    private static final String XPATH_RELATIVE_GRID_CHECKBOX = Settings.getByConstant("xpathRelativeGridCheckbox");
    private static final String XPATH_RELATIVE_GRID_ADD_ROW_LINK = Settings.getByConstant("xpathRelativeGridAddRowLink") ;

    public static WebElement getGrid(String gridName, Settings s) {
        if (isFieldIndex(gridName)) {
            int gNum = getIndexFromFieldIndex(gridName);
            String gName = getFieldFromFieldIndex(gridName);
            if(StringUtils.isBlank(gName)) {
                return s.getDriver().findElement(By.xpath(String.format(XPATH_ABSOLUTE_GRID_BY_INDEX, gNum)));
            } else {
                return s.getDriver().findElement(By.xpath(String.format(XPATH_ABSOLUTE_GRID_BY_LABEL_INDEX, gName, gNum)));
            }
        } else {
            return s.getDriver().findElement(By.xpath(String.format(XPATH_ABSOLUTE_GRID_BY_LABEL, gridName)));
        }
    }
    
    public static WebElement getCell(String gridName, String columnName, String rowNum, Settings s) {
        WebElement grid = getGrid(gridName, s);
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
    
    public static boolean populate(String gridName, String columnName, String rowNum, String[] fieldValues, Settings s) {
        for (String fieldValue : fieldValues) {
            try {
                WebElement cell = getCell(gridName, columnName, rowNum, s);
                if (!populate(cell, null, fieldValue, s)) return false;
            } catch (Exception e) {
                LOG.warn("GRID POPULATION for " + gridName + "|" + columnName + "|" + rowNum +": " + e.getClass());
                return false;
            }
        }
        
        return true;
    }
    
    public static boolean selectRow(String gridName, String rowNum, Settings s) {
        try {
            WebElement cell = getCell(gridName, "[1]", rowNum, s);
            WebElement checkbox = cell.findElement(By.xpath(XPATH_RELATIVE_GRID_CHECKBOX));
            checkbox.click();
        } catch (Exception e) {
            LOG.warn("GRID ROW SELECTION for " + gridName + "|" + rowNum +": " + e.getClass());
            return false;
        }
        
        return true;
    }
    
    public static String getValue(String gridName, String columnName, String rowNum, Settings s) {
        WebElement cell = getCell(gridName, columnName, rowNum, s);
        
        return getValue(cell, null);
    }
    
    public static boolean contains(String gridName, String columnName, String rowNum, String[] fieldValues, Settings s) {
        for (String fieldValue : fieldValues) {
            try {
                WebElement cell = getCell(gridName, columnName, rowNum, s);
                if (!contains(cell, null, fieldValue, s)) return false;
            } catch (Exception e) {
                LOG.warn("GRID CONTAINS for " + gridName + "|" + columnName + "|" + rowNum +": " + e.getClass());
                return false;
            }
        }
        
        return true;
    }
    
    public static boolean verifyGridRowIsSelected(String gridName, String rowNum, Settings s) {
        WebElement cell = getCell(gridName, "[1]", rowNum, s);
        WebElement checkbox = cell.findElement(By.xpath(XPATH_RELATIVE_GRID_CHECKBOX));
        
        return checkbox.isSelected();
    }
    
    public static boolean clear(String gridName, String columnName, String rowNum, Settings s) {
        WebElement cell = getCell(gridName, columnName, rowNum, s);
        
        return clear(cell, null, s);
    }

    public static boolean waitFor(String gridName, Settings s) {
       try { 
           if (isFieldIndex(gridName)) {
               int gNum = getIndexFromFieldIndex(gridName);
               String gName = getFieldFromFieldIndex(gridName);

               if(StringUtils.isBlank(gName)) {
                   (new WebDriverWait(s.getDriver(), s.getTimeoutSeconds())).until(ExpectedConditions.presenceOfElementLocated(By.xpath(String.format(XPATH_ABSOLUTE_GRID_BY_INDEX, gNum))));
               } else {
                   (new WebDriverWait(s.getDriver(), s.getTimeoutSeconds())).until(ExpectedConditions.presenceOfElementLocated(By.xpath(String.format(XPATH_ABSOLUTE_GRID_BY_LABEL_INDEX, gName, gNum))));
               }
           } else {
               (new WebDriverWait(s.getDriver(), s.getTimeoutSeconds())).until(ExpectedConditions.presenceOfElementLocated(By.xpath(String.format(XPATH_ABSOLUTE_GRID_BY_LABEL, gridName))));
           }
           // Attempt to scroll into view
           int attempt = 0;
           while (attempt < s.getAttemptTimes()) {
               try {
                   WebElement grid = getGrid(gridName, s);
                   scrollIntoView(grid, s);
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
    
    public static boolean waitFor(String gridName, String rowNum, Settings s) {
        return waitFor(gridName, "[1]", rowNum, s);
    }
    
    public static boolean waitFor(String gridName, String columnName, String rowNum, Settings s) {
        try {
            int rNum = getIndexFromFieldIndex(rowNum);
            
            if (isFieldIndex(gridName)) {
                int gNum = getIndexFromFieldIndex(gridName);
                String gName = getFieldFromFieldIndex(gridName);
                
                if(StringUtils.isBlank(gName)) {
                    if (isFieldIndex(columnName)) {
                        // Using a columnNum
                        int cNum = getIndexFromFieldIndex(columnName);
                        (new WebDriverWait(s.getDriver(), s.getTimeoutSeconds())).until(ExpectedConditions.presenceOfElementLocated(By.xpath(String.format(XPATH_ABSOLUTE_GRID_BY_INDEX_CELL_BY_COLUMN_INDEX, gNum, rNum, cNum))));   
                    } else {
                        // Using a columnName
                        (new WebDriverWait(s.getDriver(), s.getTimeoutSeconds())).until(ExpectedConditions.presenceOfElementLocated(By.xpath(String.format(XPATH_ABSOLUTE_GRID_BY_INDEX_CELL_BY_COLUMN_LABEL, gNum, rNum, gridName, columnName))));
                    }
                } else {
                    if (isFieldIndex(columnName)) {
                        // Using a columnNum
                        int cNum = getIndexFromFieldIndex(columnName);
                        (new WebDriverWait(s.getDriver(), s.getTimeoutSeconds())).until(ExpectedConditions.presenceOfElementLocated(By.xpath(String.format(XPATH_ABSOLUTE_GRID_BY_LABEL_INDEX_CELL_BY_COLUMN_INDEX, gName, gNum, rNum, cNum))));
                        
                    } else {
                        // Using a columnName
                        (new WebDriverWait(s.getDriver(), s.getTimeoutSeconds())).until(ExpectedConditions.presenceOfElementLocated(By.xpath(String.format(XPATH_ABSOLUTE_GRID_BY_LABEL_INDEX_CELL_BY_COLUMN_LABEL, gName, gNum, rNum, gridName, columnName))));
                    }
                }
            } else {
                if (isFieldIndex(columnName)) {
                    // Using a columnNum
                    int cNum = getIndexFromFieldIndex(columnName);
                    (new WebDriverWait(s.getDriver(), s.getTimeoutSeconds())).until(ExpectedConditions.presenceOfElementLocated(By.xpath(String.format(XPATH_ABSOLUTE_GRID_BY_LABEL_CELL_BY_COLUMN_INDEX, gridName, rNum, cNum))));
                    
                } else {
                    // Using a columnName
                    (new WebDriverWait(s.getDriver(), s.getTimeoutSeconds())).until(ExpectedConditions.presenceOfElementLocated(By.xpath(String.format(XPATH_ABSOLUTE_GRID_BY_LABEL_CELL_BY_COLUMN_LABEL, gridName, rNum, gridName, columnName))));
                }
            }
            
            // Attempt to scroll into view
            int attempt = 0;
            while (attempt < s.getAttemptTimes()) {
                try {
                    WebElement grid = getGrid(gridName, s);
                    scrollIntoView(grid, s);
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
    
    public static boolean clickOnAddRowLink(String gridName, Settings s){
        
        try {
            WebElement grid = getGrid(gridName, s);
            WebElement link = grid.findElement(By.xpath(XPATH_RELATIVE_GRID_ADD_ROW_LINK));
            link.click();
            waitForWorking(s);
        } catch (Exception e) {
            LOG.warn("GRID ADD LINK for " + gridName + e.getClass());
            return false;
        }
        
        return true;
    }
}