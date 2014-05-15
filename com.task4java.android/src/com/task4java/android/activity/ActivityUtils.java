/*
 *  Copyright (c) 2014 Andree Hagelstein, Maik Schulze, Deutsche Telekom AG. All Rights Reserved.
 *  
 *  Filename: ActivityUtils.java
 */

package com.task4java.android.activity;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import com.task4java.android.annotation.Annotations.ViewMapping;
import com.task4java.lang.reflect.ReflectionCache;

import android.app.Activity;
import android.view.View;


public class ActivityUtils {

	protected static void InitializeView(Activity activity) {

		for (Field classField : ReflectionCache.getClassFields(activity.getClass())) {
			try {
				Annotation[] annotations = ReflectionCache.getFieldAnnotations(classField);

				for (Annotation annotation : annotations) {

					if (annotation instanceof ViewMapping) {

						ViewMapping viewMapping = (ViewMapping) annotation;

						View view = activity.findViewById(viewMapping.resourceId());

						if (view != null) {
							classField.setAccessible(true);
							classField.set(activity, view);
						}
					}
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}
	
}
