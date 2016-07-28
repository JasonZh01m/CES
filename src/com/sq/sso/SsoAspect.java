 package com.sq.sso;
 
 import com.sq.core.dao.impl.CustomerContextHolder;
 import org.aspectj.lang.ProceedingJoinPoint;
 
 public class SsoAspect
 {
   public Object changeSsoDatasource(ProceedingJoinPoint pjp)
   {
     CustomerContextHolder.setCustomerType("rm_dataSource");
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

