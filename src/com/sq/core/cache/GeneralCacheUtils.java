 package com.sq.core.cache;
 
 public class GeneralCacheUtils
   implements GeneralCache
 {
   private CacheUtils level1Cache;
   private CacheUtils level2Cache;
 
   public Object get(String key, Class<?> cls)
   {
     Object o = this.level1Cache.get(key, cls);
     return o;
   }
 
   public void put(String key, Object obj)
   {
     this.level1Cache.put(key, obj);
   }
 
   public boolean remove(String key)
   {
     boolean f = false;
     f = this.level1Cache.remove(key);
     return f;
   }
 
   public Object hget(String key, String field, Class<?> cls)
   {
     Object o = this.level1Cache.hget(key, field, cls);
     return o;
   }
 
   public void hset(String key, String field, Object obj)
   {
     this.level1Cache.hset(key, field, obj);
   }
 
   public String hgetStr(String key, String field)
   {
     String o = this.level1Cache.hgetStr(key, field);
     return o;
   }
 
   public void hsetStr(String key, String field, String obj)
   {
     this.level1Cache.hsetStr(key, field, obj);
   }
 
   public boolean hdel(String key, String field)
   {
     boolean f = this.level1Cache.hdel(key, field);
     return f;
   }
 
   public void put(String key, Object obj, int liveSeconds)
   {
     this.level1Cache.put(key, obj, liveSeconds);
   }
 
   public String getStr(String key)
   {
     String o = this.level1Cache.getStr(key);
     return o;
   }
 
   public void putStr(String key, String str)
   {
     this.level1Cache.putStr(key, str);
   }
 
   public boolean removeStr(String key)
   {
     boolean f = this.level1Cache.removeStr(key);
     return f;
   }
 
   public CacheUtils getLevel1Cache()
   {
     return this.level1Cache;
   }
 
   public CacheUtils getLevel2Cache() {
     return this.level2Cache;
   }
 
   public void setLevel1Cache(CacheUtils level1Cache) {
     this.level1Cache = level1Cache;
   }
 
   public void setLevel2Cache(CacheUtils level2Cache)
   {
     this.level2Cache = level2Cache;
   }
 }

