/*
 *  Copyright (c) 2014 Andree Hagelstein, Maik Schulze, Deutsche Telekom AG. All Rights Reserved.
 *  
 *  Filename: ReflectionCache.java
 */

package com.task4java.lang.reflect;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReflectionCache {

	private static Map<Class<?>, List<Annotation>> _classAnnotations;
	private static Map<Field, Annotation[]> _fieldAnnotations;
	private static Map<Class<?>, List<Field>> _classFields;

	public static List<Field> getClassFields(Class<?> clazz) {

		if (_classFields == null) {
			_classFields = new HashMap<Class<?>, List<Field>>();
		}

		if (!_classFields.containsKey(clazz)) {
			List<Field> classFields = new ArrayList<Field>();

			addClassFields(clazz, classFields);

			_classFields.put(clazz, classFields);
		}

		return _classFields.get(clazz);
	}

	public static Annotation[] getFieldAnnotations(Field field) {

		if (_fieldAnnotations == null) {
			_fieldAnnotations = new HashMap<Field, Annotation[]>();
		}

		if (!_fieldAnnotations.containsKey(field)) {
			_fieldAnnotations.put(field, field.getAnnotations());
		}

		return _fieldAnnotations.get(field);
	}

	public static List<Annotation> getClassAnnotations(Class<?> clazz) {

		if (_classAnnotations == null) {
			_classAnnotations = new HashMap<Class<?>, List<Annotation>>();
		}

		if (!_classAnnotations.containsKey(clazz)) {
			List<Annotation> classAnnotations = new ArrayList<Annotation>();

			addClassAnnotations(clazz, classAnnotations);

			_classAnnotations.put(clazz, classAnnotations);
		}

		return _classAnnotations.get(clazz);
	}

	private static void addClassFields(Class<?> clazz, List<Field> fields) {

		for (Field classField : clazz.getDeclaredFields()) {
			fields.add(classField);
		}

		if (clazz.getSuperclass() != null) {
			addClassFields(clazz.getSuperclass(), fields);
		}
	}

	private static void addClassAnnotations(Class<?> clazz, List<Annotation> annotations) {

		for (Annotation annotation : clazz.getAnnotations()) {
			annotations.add(annotation);
		}

		if (clazz.getSuperclass() != null) {
			addClassAnnotations(clazz.getSuperclass(), annotations);
		}
	}
}