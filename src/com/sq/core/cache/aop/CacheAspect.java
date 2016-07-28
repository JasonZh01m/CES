 package com.sq.core.cache.aop;
 
 import com.sq.core.cache.GeneralCache;
 import com.sq.core.cache.classInfo.ModelCacheInfo;
 import com.sq.core.cache.classInfo.ReferenceClassInfo;
 import com.sq.core.cache.classInfo.ReferenceInfo;
 import com.sq.core.cache.classInfo.ReferenceKeyModel;
 import com.sq.core.cache.classInfo.ReferenceValue;
 import com.sq.core.cache.sql.RelationCacheForClass;
 import com.sq.core.cache.sql.SqlCacheForClass;
 import com.sq.core.utils.AnnotationInfoUtil;
 import com.sq.core.utils.ClassUtil;
 import java.io.PrintStream;
 import java.lang.reflect.Field;
 import java.lang.reflect.Method;
 import java.util.ArrayList;
 import java.util.HashMap;
 import java.util.List;
 import java.util.Map;
 import java.util.concurrent.CopyOnWriteArrayList;
 import java.util.concurrent.locks.Lock;
 import java.util.concurrent.locks.ReentrantLock;
 import org.apache.commons.lang3.StringUtils;
 import org.aspectj.lang.ProceedingJoinPoint;
 
 public class CacheAspect
 {
   private GeneralCache cacheUtil;
   private String idStr = "id";
 
   private String intStr = "Integer";
   private Map<String, ModelCacheInfo> map;
   private Map<String, ReferenceClassInfo> refMap;
   private Map<String, String> daoMap = new HashMap();
 
   private final Lock lock = new ReentrantLock();
 
   private String sqlCacheStr = "sqlCache";
 
   public CacheAspect() {
     CacheAnnotationScaner cas = new CacheAnnotationScaner();
     try {
       this.map = cas.initModelAnnotationMap();
       this.daoMap = cas.initDaoMap();
       this.refMap = cas.initRelation(this.map);
       if (this.map == null) {
         this.map = new HashMap();
       }
       if (this.daoMap == null) {
         this.daoMap = new HashMap();
       }
       if (this.refMap == null)
         this.refMap = new HashMap();
     }
     catch (Exception e) {
       if (this.map == null) {
         this.map = new HashMap();
       }
       if (this.daoMap == null) {
         this.daoMap = new HashMap();
       }
       e.printStackTrace();
     }
   }
 
   private String getClsName(Object dao) {
     return (String)this.daoMap.get(dao.getClass().getSimpleName());
   }
 
   public Object getModelInCacheByCode(ProceedingJoinPoint pjp) {
     Object dao = pjp.getTarget();
     Object retVal = null;
     try {
       String clsName = getClsName(dao);
       ModelCacheInfo mci = (ModelCacheInfo)this.map.get(clsName);
       if (mci != null) {
         Object code = pjp.getArgs()[1];
         String codeKey = AnnotationInfoUtil.getCacheKeyForModel(mci, 
           code);
         Class cls = mci.getCls();
         String ObjKey = this.cacheUtil.getStr(codeKey);
         if (ObjKey != null) {
           retVal = this.cacheUtil.get(ObjKey, cls);
           if (retVal == null) {
             retVal = pjp.proceed();
             if (retVal != null) {
               ObjKey = AnnotationInfoUtil.getCacheKeyInModel(mci, 
                 retVal);
               this.cacheUtil.put(ObjKey, retVal);
               this.cacheUtil.putStr(codeKey, ObjKey);
             } else {
               this.cacheUtil.remove(codeKey);
             }
           }
         } else {
           retVal = pjp.proceed();
           if (retVal != null) {
             ObjKey = AnnotationInfoUtil.getCacheKeyInModel(mci, 
               retVal);
             this.cacheUtil.putStr(codeKey, ObjKey);
             this.cacheUtil.put(ObjKey, retVal);
           }
         }
       } else {
         retVal = pjp.proceed();
       }
     }
     catch (Throwable e) {
       e.printStackTrace();
     }
     return retVal;
   }
 
   public Object getModelInCache(ProceedingJoinPoint pjp) {
     Object dao = pjp.getTarget();
     Object retVal = null;
     try {
       String clsName = getClsName(dao);
       ModelCacheInfo mci = (ModelCacheInfo)this.map.get(clsName);
       if (mci != null) {
         Object id = pjp.getArgs()[1];
         String key = AnnotationInfoUtil.getCacheKeyForModel(mci, id);
         Class cls = mci.getCls();
         retVal = this.cacheUtil.get(key, cls);
         if (retVal == null) {
           retVal = pjp.proceed();
           this.cacheUtil.put(key, retVal);
         }
       } else {
         retVal = pjp.proceed();
       }
     }
     catch (Throwable e) {
       e.printStackTrace();
     }
     return retVal;
   }
 
   public Object updateInCache(ProceedingJoinPoint pjp) {
     Object dao = pjp.getTarget();
     Object retVal = null;
     try {
       retVal = pjp.proceed();
       String clsName = getClsName(dao);
       ModelCacheInfo mci = (ModelCacheInfo)this.map.get(clsName);
       int rs = ((Integer)retVal).intValue();
       if ((mci != null) && (rs > 0)) {
         Object id = ClassUtil.getFieldValue(pjp.getArgs()[1], 
           mci.getKeyField());
         String key = AnnotationInfoUtil.getCacheKeyForModel(mci, id);
         clearSqlCache(dao);
         this.cacheUtil.remove(key);
       }
     }
     catch (Throwable e) {
       e.printStackTrace();
     }
     return retVal;
   }
 
   public Object deleteInCache(ProceedingJoinPoint pjp) {
     Object dao = pjp.getTarget();
     Object retVal = null;
     try {
       retVal = pjp.proceed();
       String clsName = getClsName(dao);
       ModelCacheInfo mci = (ModelCacheInfo)this.map.get(clsName);
       int rs = ((Integer)retVal).intValue();
       if ((mci != null) && (rs > 0)) {
         Object id = pjp.getArgs()[1];
         String key = AnnotationInfoUtil.getCacheKeyForModel(mci, id);
         clearSqlCache(dao);
         this.cacheUtil.remove(key);
         List relationCls = mci.getRelationClass();
         if ((relationCls != null) && (relationCls.size() > 0)) {
           for (int i = 0; i < relationCls.size(); i++) {
             String relationClsName = (String)relationCls.get(i);
             ReferenceClassInfo rci = (ReferenceClassInfo)this.refMap.get(relationClsName);
             RelationCacheForClass rc = rci.getRelationCache();
             if (rc != null)
               rc.remove(key);
           }
         }
       }
     }
     catch (Throwable e)
     {
       e.printStackTrace();
     }
     return retVal;
   }
 
   public Object insertInCache(ProceedingJoinPoint pjp) {
     Object dao = pjp.getTarget();
     Object retVal = null;
     try {
       retVal = pjp.proceed();
       if (pjp.getArgs().length == 2) {
         String clsName = getClsName(dao);
         ModelCacheInfo mci = (ModelCacheInfo)this.map.get(clsName);
         Integer id = Integer.valueOf(0);
         Object o = pjp.getArgs()[1];
         if ((retVal != null) && (this.intStr.equals(retVal.getClass().getSimpleName()))) {
           if (((Integer)retVal).intValue() > 1) {
             id = (Integer)retVal;
           } else {
             Object rid = null;
             if (mci != null)
               rid = ClassUtil.getFieldValue(o, mci.getKeyField());
             else {
               rid = ClassUtil.getFieldValue(o, "id".intern());
             }
             if ((rid != null) && (((Integer)rid).intValue() > 0)) {
               id = (Integer)rid;
             }
           }
         }
         if ((mci != null) && (id.intValue() > 0)) {
           String key = AnnotationInfoUtil.getCacheKeyForModel(mci, id);
           clearSqlCache(dao);
           this.cacheUtil.put(key, o);
         }
         ReferenceClassInfo rci = (ReferenceClassInfo)this.refMap.get(clsName);
         if (rci != null) {
           Object param = pjp.getArgs()[1];
           ReferenceKeyModel rkm = rci.getReferenceKey(param);
           String key = rkm.getKey();
           if (rkm.isSingleKey()) {
             this.cacheUtil.put(key, param);
             RelationCacheForClass rc = rci.getRelationCache();
             ReferenceValue[] rvs = rkm.getfKeys();
             if (rvs.length == 2) {
               String key1 = rvs[0].getFkName() + "_" + rvs[0].getValue();
               String key2 = rvs[1].getFkName() + "_" + rvs[1].getValue();
               if (rc.get(key1) != null) {
                 rc.putById(key1, key);
               }
               if (rc.get(key2) != null)
                 rc.putById(key2, key);
             }
           }
         }
       }
     }
     catch (Throwable e)
     {
       String expName = e.getClass().getSimpleName();
       if (("DuplicateKeyException".intern().equals(expName)) || ("MySQLIntegrityConstraintViolationException".intern().equals(expName))) {
         throw new RuntimeException("DuplicateKeyException".intern());
       }
       e.printStackTrace();
     }
 
     if (retVal == null) {
       retVal = Integer.valueOf(0);
     }
     return retVal;
   }
 
   public Object removeCache(ProceedingJoinPoint pjp) {
     Object dao = pjp.getTarget();
     Object retVal = null;
     try {
       retVal = pjp.proceed();
       String clsName = getClsName(dao);
       ReferenceClassInfo rci = (ReferenceClassInfo)this.refMap.get(clsName);
       if ((rci != null) && (retVal != null)) {
         Object param = pjp.getArgs()[1];
         ReferenceKeyModel rkm = rci.getReferenceKey(param);
         String key = rkm.getKey();
         if (rkm.isSingleKey()) {
           this.cacheUtil.remove(key);
           RelationCacheForClass rc = rci.getRelationCache();
           ReferenceValue[] rvs = rkm.getfKeys();
           if (rvs.length == 2) {
             String key1 = rvs[0].getFkName() + "_" + rvs[0].getValue();
             String key2 = rvs[1].getFkName() + "_" + rvs[1].getValue();
             rc.removeById(key1, key);
             rc.removeById(key2, key);
           }
         } else {
           RelationCacheForClass rc = rci.getRelationCache();
           rc.remove(key);
         }
       }
     }
     catch (Throwable e) {
       e.printStackTrace();
     }
     return retVal;
   }
 
   public Object queryInCache(ProceedingJoinPoint pjp)
   {
     Object dao = pjp.getTarget();
     Object retVal = null;
     try {
       String clsName = getClsName(dao);
       ReferenceClassInfo rci = (ReferenceClassInfo)this.refMap.get(clsName);
       if (rci != null) {
         Object param = pjp.getArgs()[1];
         if (param == null) {
           return pjp.proceed();
         }
         ReferenceKeyModel rkm = rci.getReferenceKey(param);
         String key = rkm.getKey();
         Class cls = rci.getCls();
         List list = null;
         if (rkm.isSingleKey()) {
           Object o = this.cacheUtil.get(key, cls);
           if (o == null) {
             retVal = pjp.proceed();
             if (retVal != null) {
               list = (List)retVal;
               this.cacheUtil.put(key, list.get(0));
             }
           } else {
             list = new ArrayList(1);
             list.add(o);
           }
         } else {
           RelationCacheForClass rc = rci.getRelationCache();
           List ids = rc.get(key);
           if (ids != null) {
             list = new ArrayList(ids.size());
             for (i = 0; i < ids.size(); i++) {
               String id = (String)ids.get(i);
               Object o = this.cacheUtil.get(id, cls);
               Method m = null;
               if (o == null) {
                 if (m == null) {
                   m = dao.getClass().getSuperclass().getDeclaredMethod("query", new Class[] { String.class, Object.class });
                 }
                 o = m.invoke(dao, new Object[] { pjp.getArgs()[0], newInstanceObj(cls, id, rci.getRefSet()) });
                 if (o != null) {
                   List singleList = (List)o;
                   if ((singleList != null) && (singleList.size() > 0)) {
                     Object ro = singleList.get(0);
                     this.cacheUtil.put(key, ro);
                     list.add(ro);
                   }
                 }
               } else {
                 list.add(o);
               }
             }
           } else {
             retVal = pjp.proceed();
             list = (List)retVal;
             if ((list != null) && (list.size() > 0)) {
               this.lock.lock();
               try {
                 if (rc.get(key) == null)
                   rc.put(key, new CopyOnWriteArrayList());
               }
               finally {
                 this.lock.unlock();
               }
               for (int i = 0; i < list.size(); i++) {
                 Object o = list.get(i);
                 ReferenceKeyModel rkm1 = rci.getReferenceKey(o);
                 String id = rkm1.getKey();
                 this.cacheUtil.put(id, o);
                 rc.putById(key, id);
               }
             }
           }
         }
 
         retVal = list;
       } else {
         retVal = pjp.proceed();
       }
     }
     catch (Throwable e) {
       e.printStackTrace();
     }
     return retVal;
   }
 
   private Object newInstanceObj(Class cls, String id, List<ReferenceInfo> refSet)
   {
     Object o = null;
     System.out.println();
     String[] vs = StringUtils.split(id, "_");
     try {
       o = cls.newInstance();
       for (int i = 0; i < refSet.size(); i++) {
         ReferenceInfo rfi = (ReferenceInfo)refSet.get(i);
         Field f = rfi.getField();
         if ("int".equals(f.getGenericType().toString()))
           f.set(o, Integer.valueOf(Integer.parseInt(vs[(i + 1)])));
         else
           f.set(o, vs[(i + 1)]);
       }
     }
     catch (Exception e)
     {
       e.printStackTrace();
     }
     return o;
   }
 
   private void clearSqlCache(Object dao)
   {
     try {
       SqlCacheForClass sqlCache = (SqlCacheForClass)ClassUtil.getFieldValue(dao, this.sqlCacheStr);
       sqlCache.clear();
     }
     catch (Exception e) {
       e.printStackTrace();
     }
   }
 
   public GeneralCache getCacheUtil()
   {
     return this.cacheUtil;
   }
 
   public void setCacheUtil(GeneralCache cacheUtil) {
     this.cacheUtil = cacheUtil;
   }
 }

