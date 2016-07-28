 package com.jsscom.ces.model;
 
 import com.google.gson.annotations.SerializedName;
 import com.sq.core.cache.JsonSerializable;
 import java.io.Serializable;
 
 public class Evaluate
   implements Serializable
 {
   private static final long serialVersionUID = 1L;
   private String reqId;
   private String devCode;
 
   @SerializedName("usercode")
   private String employeeCode;
 
   @SerializedName("username")
   private String employeeName;
 
   @SerializedName("orgcode")
   private String orgCode;
 
   @SerializedName("orgname")
   private String orgName;
 
   @SerializedName("servicecode")
   private String serviceCode;
 
   @SerializedName("servicename")
   private String serviceName;
 
   @SerializedName("duration")
   private int duration;
 
   @SerializedName("point")
   private String evaluatePoint;
 
   @SerializedName("windowname")
   private String windowName;
   private int evaluateValue;
   private String evaluateName;
   private String updateTime;
   private String rectime;
   private String empField;
 
   public String toString()
   {
     return JsonSerializable.serializableString(this);
   }
 
   public String getWindowName()
   {
     return this.windowName;
   }
 
   public void setWindowName(String windowName)
   {
     this.windowName = windowName;
   }
 
   public String getEmpField()
   {
     return this.empField;
   }
 
   public void setEmpField(String empField)
   {
     this.empField = empField;
   }
 
   public int getEvaluateValue()
   {
     return this.evaluateValue;
   }
 
   public void setEvaluateValue(int evaluateValue)
   {
     this.evaluateValue = evaluateValue;
   }
 
   public String getRectime()
   {
     return this.rectime;
   }
 
   public void setRectime(String rectime)
   {
     this.rectime = rectime;
   }
 
   public String getReqId()
   {
     return this.reqId;
   }
 
   public String getDevCode()
   {
     return this.devCode;
   }
 
   public String getEmployeeCode() {
     return this.employeeCode;
   }
 
   public String getEmployeeName() {
     return this.employeeName;
   }
 
   public String getOrgCode() {
     return this.orgCode;
   }
 
   public String getOrgName() {
     return this.orgName;
   }
 
   public String getServiceCode() {
     return this.serviceCode;
   }
 
   public String getServiceName() {
     return this.serviceName;
   }
 
   public int getDuration() {
     return this.duration;
   }
   public String getEvaluatePoint() {
     return this.evaluatePoint;
   }
 
   public String getEvaluateName() {
     return this.evaluateName;
   }
   public String getUpdateTime() {
     return this.updateTime;
   }
 
   public void setReqId(String reqId) {
     this.reqId = reqId;
   }
 
   public void setDevCode(String devCode) {
     this.devCode = devCode;
   }
 
   public void setEmployeeCode(String employeeCode) {
     this.employeeCode = employeeCode;
   }
 
   public void setEmployeeName(String employeeName) {
     this.employeeName = employeeName;
   }
 
   public void setOrgCode(String orgCode) {
     this.orgCode = orgCode;
   }
 
   public void setOrgName(String orgName) {
     this.orgName = orgName;
   }
 
   public void setServiceCode(String serviceCode) {
     this.serviceCode = serviceCode;
   }
 
   public void setServiceName(String serviceName) {
     this.serviceName = serviceName;
   }
 
   public void setDuration(int duration) {
     this.duration = duration;
   }
 
   public void setEvaluatePoint(String evaluatePoint) {
     this.evaluatePoint = evaluatePoint;
   }
 
   public void setEvaluateName(String evaluateName) {
     this.evaluateName = evaluateName;
   }
   public void setUpdateTime(String updateTime) {
     this.updateTime = updateTime;
   }
 }

