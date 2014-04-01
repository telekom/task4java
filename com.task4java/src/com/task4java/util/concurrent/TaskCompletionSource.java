/*
 *  Copyright (c) 2014 Andree Hagelstein, Maik Schulze, Deutsche Telekom AG. All Rights Reserved.
 *  
 *  Filename: TaskCompletionSource.java
 */
package com.task4java.util.concurrent;

/**
 * The {@code TaskCompletionSource} represents the producer side of a {@link Task}.
 *
 * @param <V> the value type
 */
public class TaskCompletionSource<V> {

    /** The _set callback. */
    private CallableValue<Void, V> _setCallback;
    
    /** The _exception callback. */
    private CallableValue<Void, Throwable> _exceptionCallback;
    
    /** The result value. */
    private V resultValue;
    
    /** The result throwable. */
    private Throwable resultThrowable;

    /**
     * Sets the result value of the attached {@link Task.}
     *
     * @param result the result
     */
    public void set(V result) {

        if (_setCallback != null)
        {
        	_setCallback.call(result);
        }
        else
        {
        	resultValue = result;
        }
    }

    /**
     * Sets the result of the attached {@link Task.}
     *
     * @param t the new exception
     */
    public void setException(Throwable t) {

        if (_exceptionCallback != null)
        {
        	_exceptionCallback.call(t);
        }
        else
        {
        	resultThrowable = t;
        }
    }

    /**
     * Sets the set callback.
     *
     * @param callback the callback
     */
    void setSetCallback(CallableValue<Void, V> callback)
    {
        _setCallback = callback;
        
        if (resultValue != null)
        {
        	_setCallback.call(resultValue);
        }
    }

    /**
     * Sets the exception callback.
     *
     * @param callback the callback
     */
    void setExceptionCallback(CallableValue<Void, Throwable> callback)
    {
        _exceptionCallback = callback;
        
        if (resultThrowable != null)
        {
        	_exceptionCallback.call(resultThrowable);
        }
    }
}
