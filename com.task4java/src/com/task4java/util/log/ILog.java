/*
 *  Copyright (c) 2014 Andree Hagelstein, Maik Schulze, Deutsche Telekom AG. All Rights Reserved.
 *  
 *  Filename: ILog.java
 */
package com.task4java.util.log;

public interface ILog {

	void d(String tag, String msg);

	void d(String tag, String msg, Throwable tr);

	void e(String tag, String msg);

	void e(String tag, String msg, Throwable tr);

	void i(String tag, String msg);

	void i(String tag, String msg, Throwable tr);

	void v(String tag, String msg);

	void v(String tag, String msg, Throwable tr);

	void w(String tag, String msg);

	void w(String tag, Throwable tr);

	void w(String tag, String msg, Throwable tr);

	void wtf(String tag, String msg);

	void wtf(String tag, Throwable tr);

	void wtf(String tag, String msg, Throwable tr);

	boolean isLoggable(String tag, int level);
}