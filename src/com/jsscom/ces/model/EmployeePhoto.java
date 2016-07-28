 package com.jsscom.ces.model;
 
 import com.alibaba.fastjson.annotation.JSONField;
 import java.util.Date;
 
 public class EmployeePhoto
 {
   private int id;
   private String employeeName;
   private String employeeNumber;
   private String organName;
   private String hpImgName;
   private String hpImgPath;
   private String hpImgUrl;
   private String hpImgMd5;
   private String certificateName;
   private String certificatePath;
   private String certificateUrl;
   private String certificateMd5;
   private Date updateTime;
 
   public int getId()
   {
     return this.id;
   }
 
   public void setId(int id) {
     this.id = id;
   }
 
   public String getEmployeeName() {
     return this.employeeName;
   }
 
   public void setEmployeeName(String employeeName) {
     this.employeeName = employeeName;
   }
 
   public String getEmployeeNumber() {
     return this.employeeNumber;
   }
 
   public void setEmployeeNumber(String employeeNumber) {
     this.employeeNumber = employeeNumber;
   }
 
   public String getOrganName()
   {
     return this.organName;
   }
 
   public void setOrganName(String organName) {
     this.organName = organName;
   }
 
   public String getHpImgName() {
     return this.hpImgName;
   }
 
   public void setHpImgName(String hpImgName) {
     this.hpImgName = hpImgName;
   }
 
   public String getHpImgPath() {
     return this.hpImgPath;
   }
 
   public void setHpImgPath(String hpImgPath) {
     this.hpImgPath = hpImgPath;
   }
 
   public String getHpImgUrl() {
     return this.hpImgUrl;
   }
 
   public void setHpImgUrl(String hpImgUrl) {
     this.hpImgUrl = hpImgUrl;
   }
 
   public String getHpImgMd5() {
     return this.hpImgMd5;
   }
 
   public void setHpImgMd5(String hpImgMd5) {
     this.hpImgMd5 = hpImgMd5;
   }
 
   public String getCertificateName() {
     return this.certificateName;
   }
 
   public void setCertificateName(String certificateName) {
     this.certificateName = certificateName;
   }
 
   public String getCertificatePath() {
     return this.certificatePath;
   }
 
   public void setCertificatePath(String certificatePath) {
     this.certificatePath = certificatePath;
   }
 
   public String getCertificateUrl() {
     return this.certificateUrl;
   }
 
   public void setCertificateUrl(String certificateUrl) {
     this.certificateUrl = certificateUrl;
   }
 
   public String getCertificateMd5() {
     return this.certificateMd5;
   }
 
   public void setCertificateMd5(String certificateMd5) {
     this.certificateMd5 = certificateMd5;
   }
   @JSONField(format="yyyy-MM-dd HH:mm:ss")
   public Date getUpdateTime() {
     return this.updateTime;
   }
 
   public void setUpdateTime(Date updateTime) {
     this.updateTime = updateTime;
   }
 }

