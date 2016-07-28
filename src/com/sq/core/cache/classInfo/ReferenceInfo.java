 package com.sq.core.cache.classInfo;
 
 import java.lang.reflect.Field;
 
 public class ReferenceInfo
 {
   private Field field;
   private String fkClassName;
 
   public Object getValue(Object o)
   {
     Object rs = null;
     try {
       rs = this.field.get(o);
     }
     catch (java.lang.IllegalAccessException e) {
       e.printStackTrace();
     }
     return rs;
   }
 
   public Field getField() {
     return this.field;
   }
 
   public void setField(Field field) {
     this.field = field;
   }
 
   public String getFkClassName() {
     return this.fkClassName;
   }
 
   public void setFkClassName(String fkClassName) {
     this.fkClassName = fkClassName;
   }
 }

