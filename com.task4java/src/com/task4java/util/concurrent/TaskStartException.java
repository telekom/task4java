/*
 *  Copyright (c) 2014 Andree Hagelstein, Maik Schulze, Deutsche Telekom AG. All Rights Reserved.
 *  
 *  Filename: TaskStartException.java
 */
package com.task4java.util.concurrent;

public class TaskStartException extends TaskException
{
    /*
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private ITask _task;
    
    public TaskStartException() {
        super();
    }

    public TaskStartException(String message) {
        super(message);
    }
    
    public TaskStartException(String message, ITask task) {
        super(message);
        
        _task = task;
    }
    
    public ITask getTask()
    {
        return _task;
    }
}
