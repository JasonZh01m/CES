 package com.sq.core.cache;
 
 import com.alibaba.fastjson.JSON;
 import com.alibaba.fastjson.TypeReference;
 import com.alibaba.fastjson.parser.Feature;
 import com.alibaba.fastjson.serializer.SerializerFeature;
 
 public class JsonSerializable
 {
   public static byte[] serializable(Object o)
   {
     return JSON.toJSONBytes(o, new SerializerFeature[0]);
   }
 
   public static String serializableString(Object o)
   {
     return JSON.toJSONString(o, new SerializerFeature[] { SerializerFeature.DisableCircularReferenceDetect });
   }
 
   public static Object parseObject(byte[] ob, Object obj)
   {
     return JSON.parseObject(ob, obj.getClass(), new Feature[0]);
   }
 
   public static Object parseObject(String json, TypeReference<?> typeReference)
   {
     return JSON.parseObject(json, typeReference, new Feature[0]);
   }
 
   public static Object parseObject(byte[] ob, Class cls)
   {
     return JSON.parseObject(ob, cls, new Feature[0]);
   }
 
   public static Object parse(String str)
   {
     return JSON.parseObject(str);
   }
 }

