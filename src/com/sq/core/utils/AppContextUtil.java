 package com.sq.core.utils;
 
 import org.springframework.beans.BeansException;
 import org.springframework.context.ApplicationContext;
 import org.springframework.context.ApplicationContextAware;
 
 public class AppContextUtil
   implements ApplicationContextAware
 {
   private static ApplicationContext context;
 
   public void setApplicationContext(ApplicationContext context)
     throws BeansException
   {
     context = context;
   }
 
   public static ApplicationContext getContext() {
     return context;
   }
 
   public static Object getBean(String name) {
     return context.getBean(name);
   }
 }

