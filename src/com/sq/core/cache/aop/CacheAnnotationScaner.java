 package com.sq.core.cache.aop;
 
 import com.sq.core.annotation.ForeignKey;
 import com.sq.core.cache.classInfo.ModelCacheInfo;
 import com.sq.core.cache.classInfo.ReferenceClassInfo;
 import com.sq.core.cache.classInfo.ReferenceInfo;
 import com.sq.core.cache.sql.RelationCacheForClass;
 import com.sq.core.utils.AnnotationInfoUtil;
 import com.sq.core.utils.ClassUtil;
 import java.io.File;
 import java.io.PrintStream;
 import java.lang.reflect.Field;
 import java.net.URL;
 import java.util.ArrayList;
 import java.util.HashMap;
 import java.util.List;
 import java.util.Map;
 
 public class CacheAnnotationScaner
 {
   public Map<String, ReferenceClassInfo> initRelation(Map<String, ModelCacheInfo> map)
   {
     Map rfMap = null;
     if (map != null) {
       List list = getAllModel("model");
       if ((list != null) && (list.size() > 0)) {
         rfMap = new HashMap();
         int size = list.size();
         for (int i = 0; i < size; i++) {
           Object obj = list.get(i);
           List clist = AnnotationInfoUtil.getRelationAnnotationField(obj);
           if ((clist != null) && (clist.size() > 0)) {
             String sn = obj.getClass().getSimpleName();
             for (int j = 0; j < clist.size(); j++) {
               Class refCls = (Class)clist.get(j);
               String className = refCls.getSimpleName();
               ModelCacheInfo mci = (ModelCacheInfo)map.get(className);
               if (mci != null) {
                 List clsNames = mci.getRelationClass();
                 if (!clsNames.contains(sn)) {
                   clsNames.add(sn);
                 }
               }
             }
             ReferenceClassInfo rci = new ReferenceClassInfo();
             rci.setClaName(sn);
             rci.setCls(obj.getClass());
             Field[] fields = obj.getClass().getDeclaredFields();
             int len = fields.length;
             List riList = new ArrayList(2);
 
             for (int k = 0; k < len; k++) {
               Field f = fields[k];
               if (f.isAnnotationPresent(ForeignKey.class)) {
                 Class c = ((ForeignKey)f.getAnnotation(ForeignKey.class)).reference();
                 if (c != null) {
                   f.setAccessible(true);
                   String reFerenceName = c.getSimpleName();
                   ReferenceInfo rfi = new ReferenceInfo();
                   rfi.setField(f);
                   rfi.setFkClassName(reFerenceName);
                   riList.add(rfi);
                 }
               }
             }
             rci.setRefSet(riList);
             RelationCacheForClass rc = new RelationCacheForClass();
             rci.setRelationCache(rc);
             rfMap.put(sn, rci);
           }
         }
       }
     } else {
       System.out.println("!!!!!!!!!!!!!!map is null");
     }
     return rfMap;
   }
 
   public Map<String, String> initDaoMap() {
     Map map = null;
     List list = getAllModel("data.dao.impl");
 
     if ((list != null) && (list.size() > 0)) {
       int size = list.size();
       map = new HashMap(size, 1.0F);
       for (int i = 0; i < list.size(); i++) {
         Object obj = list.get(i);
         String cn = null;
         try {
           cn = (String)ClassUtil.getFieldValue(obj, "clsName");
         }
         catch (Exception e) {
           e.printStackTrace();
         }
         if (cn != null)
           map.put(obj.getClass().getSimpleName(), cn);
       }
     }
     else
     {
       map = new HashMap(8, 1.0F);
     }
     return map;
   }
 
   public Map<String, ModelCacheInfo> initModelAnnotationMap() {
     Map map = null;
     List models = getAllModel("model");
     System.out.println("***************models:" + models);
     if ((models != null) && (models.size() > 0)) {
       List list = getAllModelCacheInfo(models);
 
       if ((list != null) && (list.size() > 0)) {
         int size = list.size();
         map = new HashMap(size, 1.0F);
 
         for (int i = 0; i < size; i++) {
           ModelCacheInfo mci = (ModelCacheInfo)list.get(i);
           if (map.get(mci.getModelCls()) == null)
             map.put(mci.getModelCls(), mci);
         }
       }
       else {
         map = new HashMap(8, 1.0F);
       }
     } else {
       map = new HashMap(8, 1.0F);
     }
     return map;
   }
 
   private List<ModelCacheInfo> getAllModelCacheInfo(List<Object> models)
   {
     List list = new ArrayList();
 
     int size = models.size();
     for (int i = 0; i < size; i++) {
       Object o = models.get(i);
       String keyField = AnnotationInfoUtil.getKeyAnnotationField(o);
       String codeField = AnnotationInfoUtil.getKeyAnnotationCodeField(o);
       if ((keyField != null) || (codeField != null)) {
         ModelCacheInfo mci = new ModelCacheInfo();
         mci.setModelCls(o.getClass().getSimpleName());
         mci.setCls(o.getClass());
         mci.setKeyField(keyField);
         mci.setCodeField(codeField);
         list.add(mci);
       }
     }
     return list;
   }
 
   private List<Object> getAllModel(String pachageName)
   {
     String path = null;
     ClassLoader classLoager = Thread.currentThread()
       .getContextClassLoader();
     if (classLoager == null) {
       classLoager = CacheAnnotationScaner.class.getClassLoader();
     }
     path = classLoager.getResource("").getPath();
     File root = new File(path);
     List list = new ArrayList();
     try {
       list = ClassUtil.loop(list, root, "", pachageName);
     }
     catch (Exception e) {
       e.printStackTrace();
     }
     return list;
   }
 }

