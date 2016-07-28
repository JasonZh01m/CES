 package com.jsscom.ces.model;
 
 import com.alibaba.fastjson.JSON;
 import java.io.Serializable;
 
 public class CarInfoTemp
   implements Serializable
 {
   private static final long serialVersionUID = 1L;
   private int id;
   private String emunumber;
   private int carindex = -1;
   private int cartype = -1;
   private int num1 = -1;
   private int num2 = -1;
   private int num3 = -1;
   private int num4 = -1;
   private int num5 = -1;
   private int num6 = -1;
   private int num7 = -1;
   private int num8 = -1;
   private int allnum = -1;
   private String ichnography;
 
   public int getId()
   {
     return this.id;
   }
   public void setId(int id) {
     this.id = id;
   }
   public String getEmunumber() {
     return this.emunumber;
   }
   public void setEmunumber(String emunumber) {
     this.emunumber = emunumber;
   }
   public int getCarindex() {
     return this.carindex;
   }
   public void setCarindex(int carindex) {
     this.carindex = carindex;
   }
   public int getCartype() {
     return this.cartype;
   }
   public void setCartype(int cartype) {
     this.cartype = cartype;
   }
   public int getNum1() {
     return this.num1;
   }
   public void setNum1(int num1) {
     this.num1 = num1;
   }
   public int getNum2() {
     return this.num2;
   }
   public void setNum2(int num2) {
     this.num2 = num2;
   }
   public int getNum3() {
     return this.num3;
   }
   public void setNum3(int num3) {
     this.num3 = num3;
   }
   public int getNum4() {
     return this.num4;
   }
   public void setNum4(int num4) {
     this.num4 = num4;
   }
   public int getNum5() {
     return this.num5;
   }
   public void setNum5(int num5) {
     this.num5 = num5;
   }
   public int getNum6() {
     return this.num6;
   }
   public void setNum6(int num6) {
     this.num6 = num6;
   }
   public int getNum7() {
     return this.num7;
   }
   public void setNum7(int num7) {
     this.num7 = num7;
   }
   public int getNum8() {
     return this.num8;
   }
   public void setNum8(int num8) {
     this.num8 = num8;
   }
   public int getAllnum() {
     return this.allnum;
   }
   public void setAllnum(int allnum) {
     this.allnum = allnum;
   }
   public String getIchnography() {
     return this.ichnography;
   }
   public void setIchnography(String ichnography) {
     this.ichnography = ichnography;
   }
 
   public String toString()
   {
     return JSON.toJSONString(this);
   }
 }

