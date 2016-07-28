 package com.sq.core.utils;
 
 import java.util.Properties;
 
 public class TestConnectionPool extends WSBean
 {
   private static final String WSDL;
   private static final String nameSpace;
   private static final String actionName;
   public static final String XML_HEADER;
 
   static
   {
     Properties prop = ProUtils.getProperties("ws.properties");
     WSDL = prop.getProperty("WSDL");
     nameSpace = prop.getProperty("NS");
     actionName = prop.getProperty("TCP");
     XML_HEADER = "<?xml version='1.0' encoding='UTF-8'?>";
     prop = null;
   }
 
   public String parseToXML()
   {
     return "test";
   }
 
   public String getActionName()
   {
     return actionName;
   }
 
   public String getWSDL()
   {
     return WSDL;
   }
 
   public String getNameSpace()
   {
     return nameSpace;
   }
 }

