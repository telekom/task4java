/*
 *  Copyright (c) 2014 Andree Hagelstein, Maik Schulze, Deutsche Telekom AG. All Rights Reserved.
 *  
 *  Filename: IServiceClient.java
 */

package com.task4java.data.backend;

import java.util.List;

import com.task4java.Tuple;
import com.task4java.data.backend.model.MainMenuAnnotation;
import com.task4java.util.concurrent.Task;

public interface IServiceClient {

	Task<List<Tuple<MainMenuAnnotation, Class<?>>>> getMainMenu();
}
