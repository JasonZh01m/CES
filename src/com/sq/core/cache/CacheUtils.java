package com.sq.core.cache;

public abstract interface CacheUtils
{
  public abstract Object get(String paramString, Class<?> paramClass);

  public abstract void put(String paramString, Object paramObject);

  public abstract void put(String paramString, Object paramObject, int paramInt);

  public abstract boolean remove(String paramString);

  public abstract Object hget(String paramString1, String paramString2, Class<?> paramClass);

  public abstract void hset(String paramString1, String paramString2, Object paramObject);

  public abstract String hgetStr(String paramString1, String paramString2);

  public abstract void hsetStr(String paramString1, String paramString2, String paramString3);

  public abstract boolean hdel(String paramString1, String paramString2);

  public abstract long getSize();

  public abstract String getStr(String paramString);

  public abstract void putStr(String paramString1, String paramString2);

  public abstract boolean removeStr(String paramString);

  public abstract long ttl(String paramString);

  public abstract void clear();
}

