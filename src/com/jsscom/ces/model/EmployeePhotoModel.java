 package com.jsscom.ces.model;
 
 import com.sq.core.cache.JsonSerializable;
 import java.io.Serializable;
 
 public class EmployeePhotoModel
   implements Serializable
 {
   private static final long serialVersionUID = 1L;
   private String usercode;
   private String image1;
   private String image2;
   private String img1md5;
   private String img2md5;
 
   public String toString()
   {
     return JsonSerializable.serializableString(this);
   }
 
   public String getImage1()
   {
     return this.image1;
   }
 
   public void setImage1(String image1) {
     this.image1 = image1;
   }
 
   public String getImage2() {
     return this.image2;
   }
 
   public void setImage2(String image2) {
     this.image2 = image2;
   }
 
   public String getImg1md5() {
     return this.img1md5;
   }
 
   public void setImg1md5(String img1md5) {
     this.img1md5 = img1md5;
   }
 
   public String getImg2md5() {
     return this.img2md5;
   }
 
   public void setImg2md5(String img2md5) {
     this.img2md5 = img2md5;
   }
 
   public String getUsercode() {
     return this.usercode;
   }
 
   public void setUsercode(String usercode) {
     this.usercode = usercode;
   }
 }

