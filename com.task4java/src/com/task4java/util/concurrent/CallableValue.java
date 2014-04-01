/*
 *  Copyright (c) 2014 Andree Hagelstein, Maik Schulze, Deutsche Telekom AG. All Rights Reserved.
 *  
 *  Filename: CallableValue.java
 */
package com.task4java.util.concurrent;

public interface CallableValue<VNew, V> {

    VNew call(V value);
}
