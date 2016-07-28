package com.sq.core.cache.sql;

import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;

import com.googlecode.concurrentlinkedhashmap.ConcurrentLinkedHashMap;
import com.sq.core.cache.JsonSerializable;
import com.sq.core.utils.HashUtils;

public class SqlCacheForClass {
	private Map<String, List<String>> map = null;
	private final KeyLock keyLock;
	private final String nullStr = "@NULL";

	public SqlCacheForClass() {
		this.map = new ConcurrentLinkedHashMap.Builder()
				.maximumWeightedCapacity(64L).build();
		this.keyLock = new KeyLock();
	}

	public void putSql(String key, List<String> ids) {
		if ((ids != null) && (ids.size() > 0))
			this.map.put(key, ids);
	}

	public String getKeyForParam(Object param) {
		String sqlHash = null;
		if (param == null) {
			sqlHash = this.nullStr;
		} else {
			String json = JsonSerializable.serializableString(param);
			sqlHash = HashUtils.hash(json) + "";
		}
		return sqlHash;
	}

	public List<String> getIdList(String key) {
		return this.map.get(key);
	}

	public Lock getlock(String key) {
		Lock lock = this.keyLock.getLock(key);
		return lock;
	}

	public void clear() {
		this.map.clear();
	}
}
