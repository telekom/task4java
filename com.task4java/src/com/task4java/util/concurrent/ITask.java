/*
 *  Copyright (c) 2014 Andree Hagelstein, Maik Schulze, Deutsche Telekom AG. All Rights Reserved.
 *  
 *  Filename: ITask.java
 */
package com.task4java.util.concurrent;

import java.util.concurrent.ExecutionException;

/**
 * The {@code ITask} defines the interface to retrieve information from a {@link Task}.
 */
public interface ITask
{
    
    /**
     * To string.
     *
     * @return the string
     */
    String toString();
    
    /**
     * Gets the.
     *
     * @return the object
     * @throws InterruptedException the interrupted exception
     * @throws ExecutionException the execution exception
     */
    Object get() throws InterruptedException, ExecutionException;
}
