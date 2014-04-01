/*
 *  Copyright (c) 2014 Andree Hagelstein, Maik Schulze, Deutsche Telekom AG. All Rights Reserved.
 *  
 *  Filename: CallableTask.java
 */
package com.task4java.util.concurrent;

/**
 * The {@code CallableTask} defines the interface for implementing callback functions
 * for the {@link Task}. Implementors define a single method {@code call}.
 *
 * @param <VNew> the result type of this callback function
 * @param <V> the value type
 */
public interface CallableTask<VNew, V> {

    /**
     * Call.
     *
     * @param task the task
     * @return the result of this callback function
     * @throws Exception the exception
     */
    VNew call(Task<V> task) throws Exception;
}
