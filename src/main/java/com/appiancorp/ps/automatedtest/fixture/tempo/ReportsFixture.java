package com.appiancorp.ps.automatedtest.fixture.tempo;

import com.appiancorp.ps.automatedtest.exception.MissingObjectException;
import com.appiancorp.ps.automatedtest.object.TempoReport;

public class ReportsFixture extends TempoFixture {

    /**
     * Clicks on the associated report.<br>
     * <br>
     * FitNesse Example: <code>| click on report | REPORT_NAME |</code>
     * 
     * @param reportName Name of report to click (partial names are acceptable)
     * If multiple reports contain the same name the first will be selected
     * @return True, if completed successfully
     */
    public boolean clickOnReport(String reportName) {
        if(!TempoReport.waitFor(reportName)) {
            throw new MissingObjectException("Report", reportName);
        }
        
        return returnHandler(TempoReport.click(reportName));
    }
    
    /** 
     * Verifies if report is present in the user interface. This is useful for determining if security is applied correctly.<br>
     * <br>
     * FitNesse Example: <code>| verify report | REPORT_NAME | is present |</code>
     * 
     * @param reportName Name of the report
     * @return True, if report is located
     */
    public boolean verifyReportIsPresent(String reportName) {
        return returnHandler(TempoReport.waitFor(reportName));
    }
    
    /** 
     * Verifies if report is not present in the user interface. This is useful for determining if security is applied correctly.<br>
     * <br>
     * FitNesse Example: <code>| verify report | REPORT_NAME | is not present |</code><br>
     * <br>
     * Use this rather than <code>| reject | verify report | REPORT_NAME | is present |</code> as it will not refresh and wait.
     * 
     * @param reportName Name of the report
     * @return True, if report is not located
     */
    public boolean verifyReportIsNotPresent(String reportName) {
        return returnHandler(!TempoReport.waitFor(reportName));
    }
}
