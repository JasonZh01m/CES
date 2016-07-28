 package com.sq.core.cache.classInfo;
 
 import java.util.ArrayList;
 import java.util.List;
 
 public class ModelCacheInfo
 {
   private String modelCls;
   private String keyField;
   private String codeField;
   private Class<?> cls;
   private List<String> relationClass = new ArrayList(1);
 
   public List<String> getRelationClass()
   {
     return this.relationClass;
   }
 
   public void setRelationClass(List<String> relationClass)
   {
     this.relationClass = relationClass;
   }
 
   public Class<?> getCls()
   {
     return this.cls;
   }
 
   public void setCls(Class<?> cls)
   {
     this.cls = cls;
   }
 
   public String getModelCls() {
     return this.modelCls;
   }
 
   public void setModelCls(String modelCls) {
     this.modelCls = modelCls;
   }
 
   public String getKeyField() {
     return this.keyField;
   }
 
   public void setKeyField(String keyField) {
     this.keyField = keyField;
   }
 
   public String getCodeField()
   {
     return this.codeField;
   }
 
   public void setCodeField(String codeField)
   {
     this.codeField = codeField;
   }
 }

