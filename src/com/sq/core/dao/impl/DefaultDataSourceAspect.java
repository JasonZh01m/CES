 package com.sq.core.dao.impl;
 
 import org.aspectj.lang.ProceedingJoinPoint;
 
 public class DefaultDataSourceAspect
 {
   public Object doDatasource(ProceedingJoinPoint pjp)
   {
     CustomerContextHolder.setCustomerType("dataSource");
     Object retVal = null;
     try {
       retVal = pjp.proceed();
     }
     catch (Throwable e) {
       e.printStackTrace();
     } finally {
       CustomerContextHolder.clearCustomerType();
     }
 
     return retVal;
   }
 }

