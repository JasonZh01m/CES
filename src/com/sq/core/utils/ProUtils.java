 package com.sq.core.utils;
 
 import java.io.IOException;
 import java.io.InputStream;
 import java.util.Properties;
 
 public class ProUtils
 {
   public static Properties getProperties(String filePath)
   {
     Properties prop = new Properties();
     InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(filePath);
     try {
       prop.load(inputStream);
     }
     catch (IOException e) {
       e.printStackTrace();
     }
     return prop;
   }
 }

