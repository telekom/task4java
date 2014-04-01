/*
 *  Copyright (c) 2014 Andree Hagelstein, Maik Schulze, Deutsche Telekom AG. All Rights Reserved.
 *  
 *  Filename: Logger.java
 */
package com.task4java.util.log;

public class Logger implements ILog {

	public static ILog instance = null;

	public static final int VERBOSE = 2;
	public static final int DEBUG = 3;
	public static final int INFO = 4;
	public static final int WARN = 5;
	public static final int ERROR = 6;
	public static final int ASSERT = 7;

	private ILog innerLogger;

	public Logger(ILog innerLogger) {
		this.innerLogger = innerLogger;
	}

	@Override
	public void d(String tag, String msg) {
		if (isLoggable(tag, DEBUG)) {
			innerLogger.d(tag, msg);
		}
	}

	@Override
	public void d(String tag, String msg, Throwable tr) {
		if (isLoggable(tag, DEBUG)) {
			innerLogger.d(tag, msg, tr);
		}
	}

	@Override
	public void e(String tag, String msg) {
		if (isLoggable(tag, ERROR)) {
			innerLogger.e(tag, msg);
		}
	}

	@Override
	public void e(String tag, String msg, Throwable tr) {
		if (isLoggable(tag, ERROR)) {
			innerLogger.e(tag, msg, tr);
		}
	}

	@Override
	public void i(String tag, String msg) {
		if (isLoggable(tag, INFO)) {
			innerLogger.i(tag, msg);
		}
	}

	@Override
	public void i(String tag, String msg, Throwable tr) {
		if (isLoggable(tag, INFO)) {
			innerLogger.i(tag, msg, tr);
		}
	}

	@Override
	public void v(String tag, String msg) {
		if (isLoggable(tag, VERBOSE)) {
			innerLogger.v(tag, msg);
		}
	}

	@Override
	public void v(String tag, String msg, Throwable tr) {
		if (isLoggable(tag, VERBOSE)) {
			innerLogger.v(tag, msg, tr);
		}
	}

	@Override
	public void w(String tag, String msg) {
		if (isLoggable(tag, WARN)) {
			innerLogger.w(tag, msg);
		}
	}

	@Override
	public void w(String tag, Throwable tr) {
		if (isLoggable(tag, WARN)) {
			innerLogger.w(tag, tr);
		}
	}

	@Override
	public void w(String tag, String msg, Throwable tr) {
		if (isLoggable(tag, WARN)) {
			innerLogger.w(tag, msg, tr);
		}
	}

	@Override
	public void wtf(String tag, String msg) {
		if (isLoggable(tag, ASSERT)) {
			innerLogger.wtf(tag, msg);
		}
	}

	@Override
	public void wtf(String tag, Throwable tr) {
		if (isLoggable(tag, ASSERT)) {
			innerLogger.wtf(tag, tr);
		}
	}

	@Override
	public void wtf(String tag, String msg, Throwable tr) {
		if (isLoggable(tag, ASSERT)) {
			innerLogger.wtf(tag, msg, tr);
		}
	}

	@Override
	public boolean isLoggable(String tag, int level) {
		return (innerLogger != null) && innerLogger.isLoggable(tag, level);
	}

}
