 package com.sq.core.cache.classInfo;
 
 import com.sq.core.cache.sql.RelationCacheForClass;
 import java.io.PrintStream;
 import java.lang.reflect.Field;
 import java.util.List;
 
 public class ReferenceClassInfo
 {
   private String claName;
   private Class cls;
   private int valNum;
   private List<ReferenceInfo> refSet;
   private static String INTEGER_STR = "Integer";
 
   private static String STRING_STR = "String";
   private RelationCacheForClass relationCache;
 
   public ReferenceKeyModel getReferenceKey(Object o)
   {
     ReferenceKeyModel rkm = null;
     if ((this.refSet != null) && (this.refSet.size() > 0)) {
       rkm = new ReferenceKeyModel(this.refSet.size());
       rkm.setClassName(this.claName);
       int num = 0;
       for (int i = 0; i < this.refSet.size(); i++) {
         ReferenceInfo rfi = (ReferenceInfo)this.refSet.get(i);
         Object value = rfi.getValue(o);
         String redCls = rfi.getFkClassName();
         if ((INTEGER_STR.equals(value.getClass().getSimpleName())) && (((Integer)value).intValue() > -1)) {
           ReferenceValue rv = new ReferenceValue();
           rv.setFkName(redCls);
           rv.setValue(value);
           num++; rkm.setFkNum(num);
           rkm.getfKeys()[i] = rv;
         } else if ((STRING_STR.equals(value.getClass().getSimpleName())) && (!"".equals(value))) {
           ReferenceValue rv = new ReferenceValue();
           rv.setFkName(redCls);
           rv.setValue(value);
           num++; rkm.setFkNum(num);
           rkm.getfKeys()[i] = rv;
         }
       }
     }
     return rkm;
   }
 
   public int getValNum() {
     return this.valNum;
   }
 
   public void setValNum(int valNum) {
     this.valNum = valNum;
   }
 
   public List<ReferenceInfo> getRefSet() {
     return this.refSet;
   }
 
   public void setRefSet(List<ReferenceInfo> refSet)
   {
     for (int i = 0; i < refSet.size(); i++) {
       ReferenceInfo ri = (ReferenceInfo)refSet.get(i);
       System.out.println("#####" + ri.getField().getName());
     }
 
     this.refSet = refSet;
   }
 
   public String getClaName() {
     return this.claName;
   }
 
   public void setClaName(String claName) {
     this.claName = claName;
   }
 
   public RelationCacheForClass getRelationCache() {
     return this.relationCache;
   }
 
   public void setRelationCache(RelationCacheForClass relationCache) {
     this.relationCache = relationCache;
   }
 
   public Class getCls() {
     return this.cls;
   }
 
   public void setCls(Class cls) {
     this.cls = cls;
   }
 }

