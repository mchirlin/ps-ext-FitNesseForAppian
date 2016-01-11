package com.appiancorp.ps.automatedtest.fixture.tempo;

import com.appiancorp.ps.automatedtest.exception.MissingObjectException;
import com.appiancorp.ps.automatedtest.object.TempoTask;

public class TasksFixture extends TempoFixture {
    /**
     * Clicks on the associated task.<br>
     * <br>
     * FitNesse Example: <code>| click on task | TASK_NAME |</code>
     * 
     * @param taskName Name of task to click (partial names are acceptable)
     * If multiple task contain the same name the first will be selected
     * @return True if action completed
     */
    public boolean clickOnTask(String taskName) {
        if(!TempoTask.refreshAndWaitFor(taskName)) {
            throw new MissingObjectException("Task", taskName);
        }
        
        return returnHandler(TempoTask.click(taskName));
     }
     
    /** 
     * Verifies if task is present in the user interface. This is useful for determining if security is applied correctly.<br>
     * <br>
     * FitNesse Example: <code>| verify task | TASK_NAME | is present |</code>
     * 
     * @param taskName Name of the task
     * @return True, if task is located
     */
     public boolean verifyTaskIsPresent(String taskName) {
         return returnHandler(TempoTask.refreshAndWaitFor(taskName));
     }
     
     /** 
      * Verifies if task is not present in the user interface. This is useful for determining if security is applied correctly.<br>
      * <br>
      * FitNesse Example: <code>| verify report | TASK_NAME | is not present |</code><br>
      * <br>
      * Use this rather than <code>| reject | verify task | TASK_NAME | is present |</code> as it will not refresh and wait.
      * 
      * @param taskName Name of the task
      * @return True, if task is located
      */
     public boolean verifyTaskIsNotPresent(String taskName) {
         return returnHandler(!TempoTask.waitFor(taskName));
     }
     
     /**
      * Verify a task with a specific name has a specific deadline<br>
      * <br>
      * FitNesse Example: <code>| verify task | TASK_NAME | has deadline of | DEADLINE |</code>
      * 
      * @param taskName Name of the task
      * @param deadline Deadline matching the Appian interface, e.g. 8d
      * @return True, if task with particular deadline is located
      */
     public boolean verifyTaskHasDeadlineOf(String taskName, String deadline) {
         if(!TempoTask.waitFor(taskName)) {
             throw new MissingObjectException("Task", taskName);
         }
         
         return returnHandler(TempoTask.hasDeadlineOf(taskName, deadline));
     }
}
