 package com.sq.core.utils;
 
 import com.sq.core.annotation.Cache;
 import com.sq.core.annotation.CacheKey;
 import com.sq.core.annotation.CodeField;
 import com.sq.core.annotation.ForeignKey;
 import com.sq.core.annotation.Relation;
 import com.sq.core.cache.classInfo.ModelCacheInfo;
 import java.lang.reflect.Field;
 import java.util.ArrayList;
 import java.util.List;
 
 public class AnnotationInfoUtil
 {
   private static String UNDER_LINE = "_";
 
   public static final String getCacheKeyForModel(ModelCacheInfo dci, Object id)
     throws Exception
   {
     String key = dci.getModelCls() + UNDER_LINE + id;
     return key;
   }
 
   public static final String getCacheKeyInModel(ModelCacheInfo dci, Object obj) throws Exception
   {
     String key = dci.getModelCls() + UNDER_LINE + ClassUtil.getFieldValue(obj, dci.getKeyField());
     return key;
   }
 
   public static final String getCacheCodeInModel(ModelCacheInfo dci, Object obj)
     throws Exception
   {
     String key = dci.getModelCls() + UNDER_LINE + ClassUtil.getFieldValue(obj, dci.getCodeField());
     return key;
   }
 
   public static final String getCacheKeyForModel(String clsName, Object id)
     throws Exception
   {
     String key = clsName + UNDER_LINE + id;
     return key;
   }
 
   public static final String getKeyAnnotationField(Object obj)
   {
     String fieldName = null;
     Class clz = obj.getClass();
     if (clz.isAnnotationPresent(Cache.class)) {
       Field[] fields = clz.getDeclaredFields();
 
       int len = fields.length;
       for (int i = 0; i < len; i++) {
         Field f = fields[i];
         if (f.isAnnotationPresent(CacheKey.class)) {
           fieldName = f.getName();
           break;
         }
       }
     }
     return fieldName;
   }
 
   public static final List<Class> getRelationAnnotationField(Object obj)
   {
     List list = new ArrayList(4);
     Class clz = obj.getClass();
     if (clz.isAnnotationPresent(Relation.class)) {
       Field[] fields = clz.getDeclaredFields();
       int len = fields.length;
       for (int i = 0; i < len; i++) {
         Field f = fields[i];
         if (f.isAnnotationPresent(ForeignKey.class)) {
           Class c = ((ForeignKey)f.getAnnotation(ForeignKey.class)).reference();
           list.add(c);
         }
       }
     }
     return list;
   }
 
   public static final String getKeyAnnotationCodeField(Object obj)
   {
     String fieldName = null;
     Class clz = obj.getClass();
     if (clz.isAnnotationPresent(Cache.class)) {
       Field[] fields = clz.getDeclaredFields();
 
       int len = fields.length;
       for (int i = 0; i < len; i++) {
         Field f = fields[i];
         if (f.isAnnotationPresent(CodeField.class)) {
           fieldName = f.getName();
           break;
         }
       }
     }
     return fieldName;
   }
 }

