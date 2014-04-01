/*
 *  Copyright (c) 2014 Andree Hagelstein, Maik Schulze, Deutsche Telekom AG. All Rights Reserved.
 *  
 *  Filename: TaskResultException.java
 */
package com.task4java.util.concurrent;

public class TaskResultException extends TaskException
{
    /*
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private ITask _task;
    
    public TaskResultException() {
        super();
    }

    public TaskResultException(String message) {
        super(message);
    }
    
    public TaskResultException(String message, ITask task) {
        super(message);
        
        _task = task;
    }
    
    public ITask getTask()
    {
        return _task;
    }
    
    @Override
    public String toString() {
    	
    	if (_task == null)
    	{
    		return String.format("TaskResultException -- Message: %1$s", getMessage());
    	}
    	else
    	{
    		return String.format("TaskResultException -- Message: %1$s; Task: %2$s", getMessage(), _task.toString());
    	}
    }
}
