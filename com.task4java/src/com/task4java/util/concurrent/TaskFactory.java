/*
 *  Copyright (c) 2014 Andree Hagelstein, Maik Schulze, Deutsche Telekom AG. All Rights Reserved.
 *  
 *  Filename: TaskFactory.java
 */
package com.task4java.util.concurrent;

import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * A factory for creating {@link Task} objects.
 */
public class TaskFactory {

	/** The default executor service for the {@link Task}. */
	public static ExecutorService defaultExecutorService = Executors.newCachedThreadPool();
	
	/** The background executor service. */
	public static ExecutorService backgroundExecutorService = Executors.newFixedThreadPool(4);
	
	/** The image executor service. */
	public static ExecutorService imageExecutorService = Executors.newFixedThreadPool(2);
	
	/** The callback function to retrieve exceptions from the {@link Task}. */
	public static CallableValue<Void, TaskException> unhandledExceptions;

	/**
	 * Creates and starts a new {@code Task}.
	 *
	 * @param <V> the value type
	 * @param callable the callable
	 * @return the task
	 */
	public static <V> Task<V> startNew(Callable<V> callable) {
		
	    return startNew(callable, "");
	}

	/**
	 * Creates and starts a new {@code Task}.
	 *
	 * @param <V> the value type
	 * @param callable the callable
	 * @param id the id
	 * @return the task
	 */
	public static <V> Task<V> startNew(Callable<V> callable, String id) {
        
	    Task<V> task = new Task<V>(callable, id);
        task.start();

        return task;
    }
	
	/**
	 * Creates and starts a new {@code Task}.
	 *
	 * @param <V> the value type
	 * @param callable the callable
	 * @param executor the executor
	 * @return the task
	 */
	public static <V> Task<V> startNew(Callable<V> callable, Executor executor) {
		
		return startNew(callable, executor, "");
	}
	
	/**
	 * Creates and starts a new {@code Task}.
	 *
	 * @param <V> the value type
	 * @param callable the callable
	 * @param executor the executor
	 * @param id the id
	 * @return the task
	 */
	public static <V> Task<V> startNew(Callable<V> callable, Executor executor, String id) {
        
        Task<V> task = new Task<V>(callable, executor, id);
        task.start();

        return task;
    }

	/**
	 * Shutdown.
	 */
	public static void shutdown() {
		
		defaultExecutorService.shutdown();
		backgroundExecutorService.shutdown();
		imageExecutorService.shutdown();
	}
}
