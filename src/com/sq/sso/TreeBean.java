 package com.sq.sso;
 
 public class TreeBean
 {
   private String id;
   private String pId;
   private String name;
   private String checked;
   private String open;
   private String isParent;
   private String icon;
   private String uri;
 
   public String getUri()
   {
     return this.uri;
   }
 
   public void setUri(String uri) {
     this.uri = uri;
   }
   public String getId() {
     return this.id;
   }
   public void setId(String id) {
     this.id = id;
   }
   public String getPId() {
     return this.pId;
   }
   public void setPId(String pId) {
     this.pId = pId;
   }
   public String getName() {
     return this.name;
   }
   public void setName(String name) {
     this.name = name;
   }
   public String getChecked() {
     return this.checked;
   }
   public void setChecked(String checked) {
     this.checked = checked;
   }
   public String getOpen() {
     return this.open;
   }
   public void setOpen(String open) {
     this.open = open;
   }
   public String getIsParent() {
     return this.isParent;
   }
   public void setIsParent(String isParent) {
     this.isParent = isParent;
   }
   public String getIcon() {
     return this.icon;
   }
   public void setIcon(String icon) {
     this.icon = icon;
   }
 }

