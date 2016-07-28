 package com.sq.sso.model;
 
 import java.io.Serializable;
 
 public class System
   implements Serializable
 {
   private static final long serialVersionUID = 1L;
   private int id;
   private String name;
   private String code;
   private String remark;
   private String contextPath;
 
   public String toString()
   {
     return "System [id=" + this.id + ",name=" + this.name + ",code=" + this.code + ",remark=" + this.remark + "]";
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
 
   public String getContextPath() {
     return this.contextPath;
   }
 
   public void setContextPath(String contextPath) {
     this.contextPath = contextPath;
   }
 }

