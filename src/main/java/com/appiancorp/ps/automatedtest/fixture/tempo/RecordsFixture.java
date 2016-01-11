package com.appiancorp.ps.automatedtest.fixture.tempo;

import com.appiancorp.ps.automatedtest.exception.MissingObjectException;
import com.appiancorp.ps.automatedtest.object.TempoRecordItem;
import com.appiancorp.ps.automatedtest.object.TempoRecordList;

public class RecordsFixture extends TempoFixture {
    
    /**
     * Clicks on the record item list.<br>
     * <br>
     * FitNesse Example: <code>| click on record list | RECORD_LIST |</code>
     * 
     * @param listName Name of record list to click (partial names are acceptable)
     * If multiple record lists contain the same name, then the first will be selected
     * @return True, if completed successfully
     */
    public boolean clickOnRecordList(String listName) {
        if(!TempoRecordList.waitFor(listName)) {
            throw new MissingObjectException("Record List", listName);
        }
        
        return returnHandler(TempoRecordList.click(listName));
    }
    
    /**
     * Clicks on the record list facet option.<br>
     * <br>
     * FitNesse Example: <code>| click on record list facet option | FACET_OPTION |</code>
     * 
     * @param facetOption Facet option to click (partial names are acceptable)
     * If multiple facet options contain the same name, then the first will be selected
     * @return True, if completed successfully
     */
    public boolean clickOnRecordListFacetOption(String facetOption) {
        if(!TempoRecordList.waitForFacetOption(facetOption)) {
            throw new MissingObjectException("Record List Facet", facetOption);
        }
        
        return returnHandler(TempoRecordList.clickOnFacetOption(facetOption));       
    }
    
    /** 
     * Verifies if facet option is present in the user interface. This is useful for determining if security is applied correctly.<br>
     * <br>
     * FitNesse Example: <code>| verify record list facet option | FACET_OPTION | is present |</code>
     * 
     * @param facetOption Name of facet option
     * @return True, if facet option is located
     */
    public boolean verifyRecordListFacetOptionIsPresent(String facetOption) {
        return returnHandler(TempoRecordList.waitForFacetOption(facetOption));
    }
    
    /**
     * Clicks on the associated record item.<br>
     * <br>
     * FitNesse Example: <code>| click on record item | REPORT_ITEM_NAME |</code>
     * 
     * @param itemName Name of record item to click (partial names are acceptable)
     * If multiple record items contain the same name, then the first will be selected
     * @return True, if completed successfully
     */
    public boolean clickOnRecordItem(String itemName) {
        if(!TempoRecordItem.refreshAndWaitFor(itemName)) {
            throw new MissingObjectException("Tempo Record Item", itemName);
        }
        
        return returnHandler(TempoRecordItem.click(itemName));
    }
    
    /** 
     * Verifies if record item is present in the user interface. This is useful for determining if security is applied correctly.<br>
     * <br>
     * FitNesse Example: <code>| verify record item | RECORD_ITEM_NAME | is present |</code>
     * 
     * @param itemName Name of record item
     * @return True, if record item is located
     */
    public boolean verifyRecordItemIsPresent(String itemName) {
        return returnHandler(TempoRecordItem.waitFor(itemName));
    }
    
    /** 
     * Verifies if record item is not present in the user interface. This is useful for determining if security is applied correctly.<br>
     * <br>
     * FitNesse Example: <code>| verify record item | RECORD_ITEM_NAME | is not present |</code><br>
     * <br>
     * Use this rather than <code>| reject | verify record item | RECORD_ITEM_NAME | is present |</code> as it will not refresh and wait.
     * 
     * @param itemName Name of record item
     * @return True, if record item is not located
     */
    public boolean verifyRecordItemIsNotPresent(String itemName) {
        return returnHandler(!TempoRecordItem.waitFor(itemName));
    }
    
    /**
     * Clicks on the associated record item facet.<br>
     * <br>
     * FitNesse Example: <code>| click on record item facet | FACET_NAME |</code>
     * 
     * @param facetName Name of facet to click (partial names are acceptable)
     * If multiple facet items contain the same name, then the first will be selected
     * @return True, if completed successfully
     */
    public boolean clickOnRecordItemFacet(String facetName) {
        if(!TempoRecordItem.waitForFacet(facetName)) {
            throw new MissingObjectException("Record Item Facet", facetName);
        }
        
        return returnHandler(TempoRecordItem.clickOnFacet(facetName));
    }
    
    /**
     * Clicks on the associated related action.<br>
     * <br>
     * FitNesse Example: <code>| click on record item related action | RELATED_ACTION_NAME |</code>
     * 
     * @param relatedActionName Name of related action to click (partial names are acceptable)
     * If multiple related actions contain the same name, then the first will be selected
     * @return True, if completed successfully
     */
    public boolean clickOnRecordItemRelatedAction(String relatedActionName) {
        if(!TempoRecordItem.refreshAndWaitForRelatedAction(relatedActionName)) {
            throw new MissingObjectException("Related Action", relatedActionName);
        }
        
        return returnHandler(TempoRecordItem.clickOnRelatedAction(relatedActionName));
    }
    
    /** 
     * Verifies if record item related action is present in the user interface. This is useful for determining if security is applied correctly.<br>
     * <br>
     * FitNesse Example: <code>| verify record item related action | RELATED_ACTION_NAME | is present |</code>
     * 
     * @param relatedActionName Name of the related action
     * @return True, if related action is located
     */
    public boolean verifyRecordItemRelatedActionIsPresent(String relatedActionName) {
        return returnHandler(TempoRecordItem.refreshAndWaitForRelatedAction(relatedActionName));
    }
    
    /** 
     * Verifies if record item related action is not present in the user interface. This is useful for determining if security is applied correctly.<br>
     * <br>
     * FitNesse Example: <code>| verify record item related action | RELATED_ACTION_NAME | is not present |</code><br>
     * <br>
     * Use this rather than <code>| reject | verify record item related action | RELATED_ACTION_NAME | is present |</code> as it will not refresh and wait.
     * 
     * @param relatedActionName Name of related action
     * @return True, if related action is not located
     */
    public boolean verifyRecordItemRelatedActionIsNotPresent(String relatedActionName) {
        return returnHandler(!TempoRecordItem.waitForRelatedAction(relatedActionName));
    }
}
