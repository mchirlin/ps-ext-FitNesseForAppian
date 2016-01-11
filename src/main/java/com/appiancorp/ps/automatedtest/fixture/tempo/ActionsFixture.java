package com.appiancorp.ps.automatedtest.fixture.tempo;

import com.appiancorp.ps.automatedtest.exception.MissingObjectException;
import com.appiancorp.ps.automatedtest.fixture.tempo.TempoFixture;
import com.appiancorp.ps.automatedtest.object.TempoAction;

public class ActionsFixture extends TempoFixture {
    
    /**
     * Clicks on the associated action.<br>
     * <br>
     * FitNesse Example: <code>| click on action | ACTION_NAME |</code>
     * 
     * @param actionName Name of action to click (partial names are acceptable)
     * If multiple actions contain the same name the first will be selected
     * @return True if action completed
     */
    public boolean clickOnAction(String actionName) {
        if(!TempoAction.waitFor(actionName)) {
            throw new MissingObjectException("Action", actionName);
        }
        
        return returnHandler(TempoAction.click(actionName));
    }
    
    /**
     * Returns true if the 'Action Completed successfully' is currently being displayed in the interface.<br>
     * <br>
     * FitNesse Example: <code>| verify action completed |</code>
     * 
     * @return True if the 'Action Completed successfully' is currently being displayed in the interface.
     */
    public boolean verifyActionCompleted() {
        return returnHandler(TempoAction.isCompleted());
    }
    
}
