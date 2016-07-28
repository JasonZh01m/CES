 package com.sq.sso.model;
 
 import java.io.Serializable;
 
 public class Role
   implements Serializable
 {
   private static final long serialVersionUID = 1L;
   private int id;
   private String name;
   private String code;
   private String remark;
 
   public String toString()
   {
     return "Role: [id = " + this.id + ",name = " + this.name + ",code = " + this.code + ",remark = " + this.remark + "]";
   }
 
   public int getId() {
     return this.id;
   }
 
   public String getName() {
     return this.name;
   }
 
   public String getCode() {
     return this.code;
   }
 
   public String getRemark() {
     return this.remark;
   }
 
   public void setId(int id) {
     this.id = id;
   }
 
   public void setName(String name) {
     this.name = name;
   }
 
   public void setCode(String code) {
     this.code = code;
   }
 
   public void setRemark(String remark) {
     this.remark = remark;
   }
 }

