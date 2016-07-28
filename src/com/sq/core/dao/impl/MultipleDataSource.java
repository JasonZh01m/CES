 package com.sq.core.dao.impl;
 
 import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
 
 public class MultipleDataSource extends AbstractRoutingDataSource
 {
   private static final ThreadLocal<String> dataSourceKey = new InheritableThreadLocal();
 
   public static void setDataSourceKey(String dataSource) {
     dataSourceKey.set(dataSource);
   }
 
   protected Object determineCurrentLookupKey()
   {
     return dataSourceKey.get();
   }
 }

