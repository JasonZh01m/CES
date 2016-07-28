 package com.jsscom.ces.model;
 
 import com.alibaba.fastjson.JSON;
 
 public class Satisfaction
 {
   private String orgName;
   private String empName;
   private int sumCount;
   private Double agvSatis;
   private int point1;
   private int point3;
   private int point5;
   private String agvsSatis;
   private String agvPoint1;
   private String agvPoint3;
   private String agvPoint5;
   private String serviceName;
   private String serviceCode;
   private String orgCode;
 
   public String getOrgCode()
   {
     return this.orgCode;
   }
   public void setOrgCode(String orgCode) {
     this.orgCode = orgCode;
   }
   public String getOrgName() {
     return this.orgName;
   }
   public void setOrgName(String orgName) {
     this.orgName = orgName;
   }
   public String getEmpName() {
     return this.empName;
   }
   public void setEmpName(String empName) {
     this.empName = empName;
   }
   public int getSumCount() {
     return this.sumCount;
   }
   public void setSumCount(int sumCount) {
     this.sumCount = sumCount;
   }
   public Double getAgvSatis() {
     return this.agvSatis;
   }
   public void setAgvSatis(Double agvSatis) {
     this.agvSatis = agvSatis;
   }
   public int getPoint1() {
     return this.point1;
   }
   public void setPoint1(int point1) {
     this.point1 = point1;
   }
   public int getPoint3() {
     return this.point3;
   }
   public void setPoint3(int point3) {
     this.point3 = point3;
   }
   public int getPoint5() {
     return this.point5;
   }
   public void setPoint5(int point5) {
     this.point5 = point5;
   }
   public String getAgvPoint1() {
     return this.agvPoint1;
   }
   public void setAgvPoint1(String agvPoint1) {
     this.agvPoint1 = agvPoint1;
   }
   public String getAgvsSatis() {
     return this.agvsSatis;
   }
   public void setAgvsSatis(String agvsSatis) {
     this.agvsSatis = agvsSatis;
   }
   public String getAgvPoint3() {
     return this.agvPoint3;
   }
   public void setAgvPoint3(String agvPoint3) {
     this.agvPoint3 = agvPoint3;
   }
   public String getAgvPoint5() {
     return this.agvPoint5;
   }
   public void setAgvPoint5(String agvPoint5) {
     this.agvPoint5 = agvPoint5;
   }
   public String getServiceName() {
     return this.serviceName;
   }
   public void setServiceName(String serviceName) {
     this.serviceName = serviceName;
   }
   public String getServiceCode() {
     return this.serviceCode;
   }
   public void setServiceCode(String serviceCode) {
     this.serviceCode = serviceCode;
   }
 
   public String toString() {
     return JSON.toJSONString(this);
   }
 }

