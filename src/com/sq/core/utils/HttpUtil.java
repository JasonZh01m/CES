 package com.sq.core.utils;
 
 import javax.servlet.http.HttpServletRequest;
 
 public class HttpUtil
 {
   public static String getClientIp(HttpServletRequest req)
   {
     String unknownStr = "unknown";
     String ip = null;
     if (req != null) {
       ip = req.getHeader("x-forwarded-for");
       if ((ip != null) && (ip.length() >= 0) && 
         (!unknownStr.equalsIgnoreCase(ip)))
       {
         String[] ips = ip.split(",");
         for (int i = 0; i < ips.length; i++) {
           ip = ips[i];
           if (!unknownStr.equalsIgnoreCase(ip)) {
             break;
           }
         }
       }
       if ((ip == null) || (ip.length() == 0) || 
         (unknownStr.equalsIgnoreCase(ip))) {
         ip = req.getRemoteAddr();
       }
     }
     return ip;
   }
 
   public static String getClientMac(HttpServletRequest req)
   {
     String mac = null;
     return mac;
   }
 }

