 package com.sq.sso.model;
 
 import java.io.Serializable;
 
 public class User
   implements Serializable
 {
   private static final long serialVersionUID = 1L;
   private int id;
   private String loginName;
   private String userName;
   private String passWord;
   private String saltCode;
   private String sex;
   private String phone;
   private String email;
   private String address;
   private String unit;
   private int timeLimit;
   private String ip;
   private String regDate;
   private String expireDate;
   private String userCardId;
   private String validStatus;
   private int errorNum;
   private String currentDate;
   private String remark;
   private int level;
   private String orgCode;
 
   public int getLevel()
   {
     return this.level;
   }
 
   public void setLevel(int level) {
     this.level = level;
   }
 
   public String toString() {
     return "User [id=" + this.id + ",loginName =" + this.loginName + ", userName = " + this.userName + ", passWord = " + this.passWord + ", saltCode = " + this.saltCode + 
       ", sex = " + this.sex + ", phone = " + this.phone + ", email = " + this.email + ", address = " + this.address + ", unit = " + this.unit + ",timeLimit = " + this.timeLimit + 
       ", ip = " + this.ip + ",regdate = " + this.regDate + ",expireDate = " + this.expireDate + ",userCardId=" + this.userCardId + ",validStatus=" + this.validStatus + ",errorNum=" + this.errorNum + ",currentDate=" + this.currentDate + ", remark = " + this.remark + "]";
   }
 
   public String getSex() {
     return this.sex;
   }
   public void setSex(String sex) {
     this.sex = sex;
   }
   public int getId() {
     return this.id;
   }
   public String getLoginName() {
     return this.loginName;
   }
   public String getUserName() {
     return this.userName;
   }
   public String getPassWord() {
     return this.passWord;
   }
   public String getSaltCode() {
     return this.saltCode;
   }
   public String getPhone() {
     return this.phone;
   }
   public String getEmail() {
     return this.email;
   }
   public String getAddress() {
     return this.address;
   }
   public String getUnit() {
     return this.unit;
   }
   public int getTimeLimit() {
     return this.timeLimit;
   }
   public String getIp() {
     return this.ip;
   }
   public String getRegDate() {
     return this.regDate;
   }
   public String getExpireDate() {
     return this.expireDate;
   }
   public String getUserCardId() {
     return this.userCardId;
   }
   public String getValidStatus() {
     return this.validStatus;
   }
   public String getRemark() {
     return this.remark;
   }
   public void setId(int id) {
     this.id = id;
   }
   public void setLoginName(String loginName) {
     this.loginName = loginName;
   }
   public void setUserName(String userName) {
     this.userName = userName;
   }
   public void setPassWord(String passWord) {
     this.passWord = passWord;
   }
   public void setSaltCode(String saltCode) {
     this.saltCode = saltCode;
   }
 
   public void setPhone(String phone) {
     this.phone = phone;
   }
   public void setEmail(String email) {
     this.email = email;
   }
   public void setAddress(String address) {
     this.address = address;
   }
   public String getOrgCode() {
     return this.orgCode;
   }
 
   public void setOrgCode(String orgCode) {
     this.orgCode = orgCode;
   }
 
   public void setUnit(String unit) {
     this.unit = unit;
   }
   public void setTimeLimit(int timeLimit) {
     this.timeLimit = timeLimit;
   }
   public void setIp(String ip) {
     this.ip = ip;
   }
   public void setRegDate(String regDate) {
     this.regDate = regDate;
   }
   public void setExpireDate(String expireDate) {
     this.expireDate = expireDate;
   }
   public void setUserCardId(String userCardId) {
     this.userCardId = userCardId;
   }
   public void setValidStatus(String validStatus) {
     this.validStatus = validStatus;
   }
   public void setRemark(String remark) {
     this.remark = remark;
   }
 
   public int getErrorNum() {
     return this.errorNum;
   }
 
   public void setErrorNum(int errorNum) {
     this.errorNum = errorNum;
   }
 
   public String getCurrentDate() {
     return this.currentDate;
   }
 
   public void setCurrentDate(String currentDate) {
     this.currentDate = currentDate;
   }
 }

