/*
 *  Copyright (c) 2014 Andree Hagelstein, Maik Schulze, Deutsche Telekom AG. All Rights Reserved.
 *  
 *  Filename: IndexedList.java
 */

package com.task4java;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class IndexedList<K, V, L> extends ArrayList<L> {

	private static final long serialVersionUID = 1L;

	private Object _oLock = new Object();
	private HashMap<K, V> _keyItem = null;

	protected abstract Tuple<K, V> extractKeyValue(L item);

	public List<K> getAllKeys() {

		synchronized (_oLock) {

			List<K> allKeys = new ArrayList<K>();

			for (L item : this) {

				if (item != null) {

					allKeys.add(extractKeyValue(item).left);
				}
			}

			return allKeys;
		}
	}

	public V getItem(K key, V defaultValue) {

		synchronized (_oLock) {

			HashMap<K, V> keyItem = _keyItem;

			if (keyItem == null) {
				keyItem = new HashMap<K, V>();

				for (L item : this) {

					if (item != null) {

						Tuple<K, V> keyValue = extractKeyValue(item);

						keyItem.put(keyValue.left, keyValue.right);
					}
				}

				_keyItem = keyItem;
			}

			if (keyItem.containsKey(key)) {

				return keyItem.get(key);
			} else {

				return defaultValue;
			}
		}
	}

	public void refresh() {

		synchronized (_oLock) {
			_keyItem = null;
		}
	}
}