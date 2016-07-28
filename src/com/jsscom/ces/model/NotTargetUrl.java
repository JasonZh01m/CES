 package com.jsscom.ces.model;
 
 import java.io.Serializable;
 
 public class NotTargetUrl
   implements Serializable
 {
   private static final long serialVersionUID = 1L;
   private String name;
   private String url;
 
   public String getName()
   {
     return this.name;
   }
   public String getUrl() {
     return this.url;
   }
   public void setName(String name) {
     this.name = name;
   }
   public void setUrl(String url) {
     this.url = url;
   }
 }

