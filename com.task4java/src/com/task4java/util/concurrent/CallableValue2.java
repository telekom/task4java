/*
 *  Copyright (c) 2014 Andree Hagelstein, Maik Schulze, Deutsche Telekom AG. All Rights Reserved.
 *  
 *  Filename: CallableValue2.java
 */
package com.task4java.util.concurrent;

public interface CallableValue2<VNew, V1, V2> {

    VNew call(V1 value1, V2 value2);
}
