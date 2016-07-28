 package com.sq.sso.cache;
 
 import com.sq.sso.model.UriInfo;
 import java.util.Map;
 import java.util.concurrent.ConcurrentHashMap;
 
 public class UriCache
 {
   private static final Map<String, UriInfo> uriMap = new ConcurrentHashMap();
 
   public static UriInfo get(String uri)
   {
     return (UriInfo)uriMap.get(uri);
   }
 
   public static int size()
   {
     return uriMap.size();
   }
 
   public static void put(String uri, UriInfo uriInfo) {
     uriMap.put(uri, uriInfo);
   }
 
   public static void remove(String uri)
   {
     uriMap.remove(uri);
   }
 }

