package com.appiancorp.ps.automatedtest.servlet.steps;

import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import com.appiancorp.process.expression.ExpressionEvaluationException;
import com.appiancorp.ps.automatedtest.servlet.ExpressionHelper;
import com.appiancorp.services.ServiceContext;
import com.appiancorp.suiteapi.common.Constants;
import com.appiancorp.suiteapi.common.ResultPage;
import com.appiancorp.suiteapi.process.ProcessDesignService;
import com.appiancorp.suiteapi.process.ProcessExecutionService;
import com.appiancorp.suiteapi.process.ProcessModel;
import com.appiancorp.suiteapi.process.ProcessStartConfig;
import com.appiancorp.suiteapi.process.ProcessSummary;
import com.appiancorp.suiteapi.process.ProcessVariable;
import com.appiancorp.suiteapi.process.TaskSummary;
import com.appiancorp.suiteapi.process.analytics2.ProcessAnalyticsService;

public class AppianAutomatedTestSteps {
    
    private static final Logger LOG = Logger.getLogger(AppianAutomatedTestSteps.class);
	
	public static String startProcessWithPMUuId(ProcessDesignService pds, String processModelUuId) {
		Long processId = null;
		try {
			ProcessVariable[] paramProcessVariables = {};
			ProcessStartConfig startConfig = new ProcessStartConfig(paramProcessVariables);
			
			ProcessModel processModel = pds.getProcessModelByUuid(processModelUuId);
			
			long processModelId = 0;
			
			if (processModel == null) {
				return "Process is not found";
			} else {
				processModelId = processModel.getId();
			}
			
			processId = pds.initiateProcess(new Long(processModelId), startConfig);
		} catch (Exception e) {
			e.printStackTrace();
			return "Exceptions occur";
		}
		return processId.toString();
	}
	
	public static String queryIsTaskCompletedWithinSeconds(ProcessDesignService pds, ProcessExecutionService pes, ProcessAnalyticsService pas, String processModelUuId, String taskName, int seconds) {
		try {
			Timestamp now = new Timestamp(new Date().getTime());
			
			ProcessModel processModel = pds.getProcessModelByUuid(processModelUuId);
			
			long processModelId = 0;
			
			if (processModel == null) {
				return "Process is not found";
			} else {
				processModelId = processModel.getId();
			}
			
			ResultPage result = pas.getProcessesForProcessModel(processModelId, 0, 10, ProcessSummary.SORT_BY_PROCESS_START_TIME, Constants.SORT_ORDER_DESCENDING);
			ProcessSummary[] processSummaries = (ProcessSummary[]) result.getResults();
			
			long processId = 0;
			
			if (processSummaries != null && processSummaries.length > 0) {
				Timestamp startTime = processSummaries[0].getStartTime();
				
				long diffInSeconds = TimeUnit.MILLISECONDS.toSeconds(now.getTime() - startTime.getTime());
				
				if (diffInSeconds <= seconds) {
					processId = processSummaries[0].getId();
				} else {
					return "Process is not found";
				}
			} else {
				return "Process is not found";
			}
			
			result = pes.getCompletedTasksForProcess(processId, ProcessExecutionService.UNATTENDED_AND_ATTENDED_TASKS, 0, 10, TaskSummary.SORT_BY_COMPLETED_TIME, Constants.SORT_ORDER_DESCENDING);
			TaskSummary[] taskSummaries = (TaskSummary[]) result.getResults();
			
			for (TaskSummary taskSummary:taskSummaries) {
				if (taskSummary.getName().startsWith(taskName)) {
					return "Task is completed";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "Exceptions occur";
		} 
		
		return "Task is not completed";
	}
	
	public static String verifyConstantHasValueOf(ProcessDesignService pds, String constantName, String expectedConstantValue) {
		try {
			String actualConstantValue = String.valueOf(pds.evaluateExpression("cons!" + constantName).getValue());
			if (expectedConstantValue.equals(actualConstantValue)) {
				return "Constant value is verified";
			} else {
				return "Constant value is not verified";
			}
		} catch (ExpressionEvaluationException e) {
			e.printStackTrace();
			return "Exceptions occur";
		}
	}
	
	public static String verifyDataInDatabase(Connection conn, String sqlQuery, String fieldsParam) {
		String returnValue = "";
	
		String fields[] = fieldsParam.split(":");
	
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			
			ps = conn.prepareStatement(sqlQuery);
			rs = ps.executeQuery();
			
			JSONArray recordJson = new JSONArray();
			
			while(rs.next()) {
				JSONObject jsonResult = new JSONObject();
				for (int j=0;j<fields.length;j++) {
					String fieldValue = rs.getString(fields[j]);
					jsonResult.put(fields[j], fieldValue);
				}
				recordJson.put(jsonResult);
			}
			
			returnValue = returnValue + recordJson.toString();
		} catch (Exception e) {
			e.printStackTrace();
			returnValue = "Exception Occurs";
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
			} catch (Exception e) {
			}
			try {
				if (ps != null) {
					ps.close();
				}
			} catch (Exception e) {
			}
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
			}
		}
		
		return returnValue;
	}
	
	public static String runExpression(ServiceContext sc, String expression) {
	    String returnValue = "";
	    
	    try {
	        expression = URLDecoder.decode(expression, "UTF-8");
	        LOG.debug(expression);
	        returnValue = (String)ExpressionHelper.evaluateExpression(sc, expression);
        } catch (Exception e) {
            e.printStackTrace();
            returnValue = "Exception Occurs";
        }
	    
	    return returnValue;
	}

}
