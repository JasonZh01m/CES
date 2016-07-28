 package com.sq.sso.model;
 
 public class UriInfo
 {
   private String pid;
   private int type;
   private int level;
 
   public int getLevel()
   {
     return this.level;
   }
 
   public void setLevel(int level) {
     this.level = level;
   }
 
   public String getPid() {
     return this.pid;
   }
 
   public void setPid(String pid) {
     this.pid = pid;
   }
 
   public int getType()
   {
     return this.type;
   }
 
   public void setType(int type)
   {
     this.type = type;
   }
 }

