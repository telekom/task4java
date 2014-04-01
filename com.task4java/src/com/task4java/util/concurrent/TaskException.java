/*
 *  Copyright (c) 2014 Andree Hagelstein, Maik Schulze, Deutsche Telekom AG. All Rights Reserved.
 *  
 *  Filename: TaskException.java
 */
package com.task4java.util.concurrent;

public class TaskException extends Exception
{
    private static final long serialVersionUID = 1L;

    public TaskException() {
        super();
    }

    public TaskException(String message) {
        super(message);
    }
}
