 package com.sq.core.dao.impl;
 
 public class CustomerContextHolder
 {
   public static final String DATA_SOURCE = "dataSource";
   public static final String DATA_SOURCE_RM = "rm_dataSource";
   private static final ThreadLocal<String> contextHolder = new ThreadLocal();
 
   public static void setCustomerType(String customerType) {
     contextHolder.set(customerType);
   }
 
   public static String getCustomerType() {
     return (String)contextHolder.get();
   }
 
   public static void clearCustomerType() {
     contextHolder.remove();
   }
 }

