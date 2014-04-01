package com.task4java.test.common;

import com.task4java.util.log.ConsoleLogger;
import com.task4java.util.log.Logger;

public class InitSetup {

	public static void initLogger() {
        
        Logger.instance = new ConsoleLogger(true);
    }
}
