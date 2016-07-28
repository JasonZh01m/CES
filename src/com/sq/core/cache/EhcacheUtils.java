 package com.sq.core.cache;
 
 import net.sf.ehcache.Cache;
 import net.sf.ehcache.Element;
 import org.apache.commons.logging.Log;
 import org.apache.commons.logging.LogFactory;
 
 public class EhcacheUtils
   implements CacheUtils
 {
   private Log log = LogFactory.getLog(getClass());
   private Cache cache;
 
   public Object get(String key, Class<?> cls)
   {
     Element e = this.cache.get(key);
     Object o = null;
     if (e != null) {
       o = e.getObjectValue();
       if ((this.log.isDebugEnabled()) && (o != null)) {
         this.log.debug(CacheLogInfo.LEVEL_1_CACHE + CacheLogInfo.GET_LOG + 
           key);
       }
     }
     return o;
   }
 
   public void put(String key, Object obj)
   {
     this.cache.put(new Element(key, obj));
     if (this.log.isDebugEnabled())
       this.log.debug(CacheLogInfo.LEVEL_1_CACHE + CacheLogInfo.SET_LOG + key);
   }
 
   public boolean remove(String key)
   {
     boolean f = this.cache.remove(key);
     if ((this.log.isDebugEnabled()) && (f)) {
       this.log.debug(CacheLogInfo.LEVEL_1_CACHE + CacheLogInfo.DEL_LOG + key);
     }
     return f;
   }
 
   public Object hget(String key, String field, Class<?> cls)
   {
     Object o = null;
     String hkey = key + CacheLogInfo.COLON + field;
     Element e = this.cache.get(hkey);
     if (e != null) {
       o = e.getObjectValue();
       if ((this.log.isDebugEnabled()) && (o != null)) {
         this.log.debug(CacheLogInfo.LEVEL_1_CACHE + CacheLogInfo.HGET_LOG + 
           hkey);
       }
     }
     return o;
   }
 
   public void hset(String key, String field, Object obj)
   {
     String hkey = key + CacheLogInfo.COLON + field;
     this.cache.put(new Element(hkey, obj));
     if (this.log.isDebugEnabled())
       this.log.debug(CacheLogInfo.LEVEL_1_CACHE + CacheLogInfo.HSET_LOG + hkey);
   }
 
   public String hgetStr(String key, String field)
   {
     String o = null;
     String hkey = key + CacheLogInfo.COLON + field;
     Element e = this.cache.get(hkey);
     if (e != null) {
       o = (String)e.getObjectValue();
       if ((this.log.isDebugEnabled()) && (o != null)) {
         this.log.debug(CacheLogInfo.LEVEL_1_CACHE + CacheLogInfo.HGET_LOG + 
           hkey);
       }
     }
     return o;
   }
 
   public void hsetStr(String key, String field, String obj)
   {
     String hkey = key + CacheLogInfo.COLON + field;
     this.cache.put(new Element(hkey, obj));
     if (this.log.isDebugEnabled())
       this.log.debug(CacheLogInfo.LEVEL_1_CACHE + CacheLogInfo.HSET_LOG + hkey);
   }
 
   public boolean hdel(String key, String field)
   {
     boolean f = false;
     String hkey = key + CacheLogInfo.COLON + field;
     f = this.cache.remove(hkey);
     if ((this.log.isDebugEnabled()) && (f)) {
       this.log.debug(CacheLogInfo.LEVEL_1_CACHE + CacheLogInfo.HDEL_LOG + hkey);
     }
     return f;
   }
 
   public long getSize()
   {
     return this.cache.getSize();
   }
 
   public void clear()
   {
     this.cache.flush();
   }
 
   public long ttl(String key)
   {
     return this.cache.get(key).getExpirationTime();
   }
 
   public void put(String key, Object obj, int liveSeconds)
   {
     Element e = new Element(key, obj);
     e.setTimeToLive(liveSeconds);
     this.cache.put(e);
     if (this.log.isDebugEnabled())
       this.log.debug(CacheLogInfo.LEVEL_1_CACHE + CacheLogInfo.SET_LOG + key);
   }
 
   public Cache getCache()
   {
     return this.cache;
   }
 
   public void setCache(Cache cache)
   {
     this.cache = cache;
   }
 
   public String getStr(String key)
   {
     Element e = this.cache.get(key);
     String o = null;
     if (e != null) {
       o = (String)e.getObjectValue();
       if ((this.log.isDebugEnabled()) && (o != null)) {
         this.log.debug(CacheLogInfo.LEVEL_1_CACHE + CacheLogInfo.GET_LOG + 
           key);
       }
     }
     return o;
   }
 
   public void putStr(String key, String str)
   {
     this.cache.put(new Element(key, str));
     if (this.log.isDebugEnabled())
       this.log.debug(CacheLogInfo.LEVEL_1_CACHE + CacheLogInfo.SET_LOG + key);
   }
 
   public boolean removeStr(String key)
   {
     boolean f = this.cache.remove(key);
     if ((this.log.isDebugEnabled()) && (f)) {
       this.log.debug(CacheLogInfo.LEVEL_1_CACHE + CacheLogInfo.DEL_LOG + key);
     }
     return f;
   }
 }

