package com.appiancorp.ps.automatedtest.fixture.tempo;

import org.apache.log4j.Logger;

import com.appiancorp.ps.automatedtest.exception.MissingObjectException;
import com.appiancorp.ps.automatedtest.fixture.BaseFixture;
import com.appiancorp.ps.automatedtest.object.TempoLogin;
import com.appiancorp.ps.automatedtest.object.TempoMenu;

public class TempoFixture extends BaseFixture {
	
    @SuppressWarnings("unused")
    private static final Logger LOG = Logger.getLogger(TempoFixture.class);
    
	public TempoFixture() {
		super();
	}

	/**
     * Clicks on the associated tempo menu.<br>
     * <br>
     * Example: <code>| click on menu | MENU_NAME |</code>
     * 
     * @param tempoMenu Name of tempo menu, e.g. Records, Tasks, News
     * @return True if action completed
     */
	public boolean clickOnMenu(String tempoMenu) {
		if(!TempoMenu.waitFor(tempoMenu)) {
		    throw new MissingObjectException("Tempo Menu", tempoMenu);
		}
		
		return returnHandler(TempoMenu.click(tempoMenu));
	}
	
	/**
    * Logs out of Appian
    * <br>
    * Example: <code>| logout |</code>
    * 
    * @return True if action completed
    */
	public boolean logout() {
        if(!TempoLogin.waitForLogout()) {
            throw new MissingObjectException("Logout Menu");
        }
        
        return returnHandler(TempoLogin.logout());
    }
}