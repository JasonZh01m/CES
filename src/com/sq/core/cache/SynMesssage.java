 package com.sq.core.cache;
 
 public class SynMesssage
 {
   private int type = 1;
   private String key;
   private Object obj;
 
   public int getType()
   {
     return this.type;
   }
 
   public void setType(int type)
   {
     this.type = type;
   }
 
   public String getKey() {
     return this.key;
   }
 
   public void setKey(String key) {
     this.key = key;
   }
 
   public Object getObj() {
     return this.obj;
   }
 
   public void setObj(Object obj) {
     this.obj = obj;
   }
 }

