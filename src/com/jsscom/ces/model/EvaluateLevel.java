 package com.jsscom.ces.model;
 
 import com.alibaba.fastjson.JSON;
 import com.sq.core.annotation.Cache;
 import com.sq.core.annotation.CacheKey;
 import java.io.Serializable;
 
 @Cache(usage="r")
 public class EvaluateLevel
   implements Serializable
 {
   private static final long serialVersionUID = 1L;
 
   @CacheKey
   private int id;
   private int level;
   private String name;
   private int value;
   private String remark;
 
   public int getId()
   {
     return this.id;
   }
   public void setId(int id) {
     this.id = id;
   }
   public int getLevel() {
     return this.level;
   }
   public void setLevel(int level) {
     this.level = level;
   }
   public String getName() {
     return this.name;
   }
   public void setName(String name) {
     this.name = name;
   }
   public int getValue() {
     return this.value;
   }
   public void setValue(int value) {
     this.value = value;
   }
   public String getRemark() {
     return this.remark;
   }
   public void setRemark(String remark) {
     this.remark = remark;
   }
 
   public String toString() {
     return JSON.toJSONString(this);
   }
 }

