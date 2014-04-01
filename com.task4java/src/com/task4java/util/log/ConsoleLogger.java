/*
 *  Copyright (c) 2014 Andree Hagelstein, Maik Schulze, Deutsche Telekom AG. All Rights Reserved.
 *  
 *  Filename: ConsoleLogger.java
 */
package com.task4java.util.log;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Calendar;

public class ConsoleLogger implements ILog {

    private boolean showTime;
    
    public ConsoleLogger()
    {    
    }
    
    public ConsoleLogger(boolean showTime)
    {    
        this.showTime = showTime;
    }
    
	@Override
	public synchronized void d(String tag, String msg) {
		
	    if (showTime)
		{
		    System.out.format("D:%d %s: %s", Calendar.getInstance().getTimeInMillis(), tag, msg);
		}
		else
		{
		    System.out.format("D:%s: %s", tag, msg);
		}
		
		System.out.println();
	}

	@Override
	public synchronized void d(String tag, String msg, Throwable tr) {
	    
	    if (showTime)
        {
            System.out.format("D:%d %s: %s %s", Calendar.getInstance().getTimeInMillis(), tag, msg, getStackTraceString(tr));    
        }
        else
        {
            System.out.format("D:%s: %s %s", tag, msg, getStackTraceString(tr));    
        }
	    	    
		System.out.println();
	}

	@Override
	public synchronized void e(String tag, String msg) {
		System.out.format("E:%s: %s", tag, msg);
		System.out.println();
	}

	@Override
	public synchronized void e(String tag, String msg, Throwable tr) {
		System.out.format("E:%s: %s %s", tag, msg, getStackTraceString(tr));
		System.out.println();
	}

	@Override
	public synchronized void i(String tag, String msg) {
		System.out.format("I:%s: %s", tag, msg);
		System.out.println();
	}

	@Override
	public synchronized void i(String tag, String msg, Throwable tr) {
		System.out.format("I:%s: %s %s", tag, msg, getStackTraceString(tr));
		System.out.println();
	}

	@Override
	public synchronized void v(String tag, String msg) {
		System.out.format("V:%s: %s", tag, msg);	
		System.out.println();
	}

	@Override
	public synchronized void v(String tag, String msg, Throwable tr) {
		System.out.format("V:%s: %s %s", tag, msg, getStackTraceString(tr));
		System.out.println();
	}

	@Override
	public synchronized void w(String tag, String msg) {
		System.out.format("W:%s: %s", tag, msg);
		System.out.println();
	}

	@Override
	public synchronized void w(String tag, Throwable tr) {
		System.out.format("W:%s: %s", tag, getStackTraceString(tr));	
		System.out.println();
	}

	@Override
	public synchronized void w(String tag, String msg, Throwable tr) {
		System.out.format("W:%s: %s %s", tag, msg, getStackTraceString(tr));	
		System.out.println();
		
	}

	@Override
	public synchronized void wtf(String tag, String msg) {
		System.out.format("A:%s: %s", tag, msg);	
		System.out.println();
	}

	@Override
	public synchronized void wtf(String tag, Throwable tr) {
		System.out.format("A:%s: %s", tag, getStackTraceString(tr));
		System.out.println();
	}

	@Override
	public synchronized void wtf(String tag, String msg, Throwable tr) {
		System.out.format("A:%s: %s %s", tag, msg, getStackTraceString(tr));
		System.out.println();
	}

	@Override
	public boolean isLoggable(String tag, int level) {
		return true;
	}

	private static String getStackTraceString(Throwable tr) {
        if (tr == null) {
            return "";
        }

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        
        tr.printStackTrace(pw);
        
        return sw.toString();
    }

}
