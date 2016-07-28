package com.sq.core.cache.sql;

import java.util.List;
import java.util.Map;

import com.googlecode.concurrentlinkedhashmap.ConcurrentLinkedHashMap;

public class RelationCacheForClass {
	private Map<String, List<String>> map = null;

	public RelationCacheForClass() {
		this.map = new ConcurrentLinkedHashMap.Builder()
				.maximumWeightedCapacity(254L).build();
	}

	public void put(String key, List<String> idList) {
		if (idList != null)
			this.map.put(key, idList);
	}

	public void putById(String key, String id) {
		List list = this.map.get(key);
		if ((list != null) && (!list.contains(id)))
			list.add(id);
	}

	public List<String> get(String key) {
		return this.map.get(key);
	}

	public void remove(String key) {
		this.map.remove(key);
	}

	public void removeById(String key, String id) {
		List list = this.map.get(key);
		if ((list != null) && (list.size() > 0))
			for (int i = list.size() - 1; i > -1; i--) {
				String idL = (String) list.get(i);
				if ((id != null) && (!"".equals(idL))) {
					list.remove(i);
					break;
				}
			}
	}

	public void clear() {
		this.map.clear();
	}
}
