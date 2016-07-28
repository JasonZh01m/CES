 package com.sq.core.cache.classInfo;
 
 import com.sq.core.cache.JsonSerializable;
 
 public class ReferenceValue
 {
   private String fkName;
   private Object value;
 
   public String toString()
   {
     return JsonSerializable.serializableString(this);
   }
 
   public String getFkName()
   {
     return this.fkName;
   }
 
   public void setFkName(String fkName) {
     this.fkName = fkName;
   }
 
   public Object getValue() {
     return this.value;
   }
 
   public void setValue(Object value) {
     this.value = value;
   }
 }

