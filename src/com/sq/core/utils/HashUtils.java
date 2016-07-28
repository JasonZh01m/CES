 package com.sq.core.utils;
 
 public class HashUtils
 {
   public static int hash(String data)
   {
     int p = 16777619;
     int hash = -2128831035;
     for (int i = 0; i < data.length(); i++)
       hash = (hash ^ data.charAt(i)) * 16777619;
     hash += (hash << 13);
     hash ^= hash >> 7;
     hash += (hash << 3);
     hash ^= hash >> 17;
     hash += (hash << 5);
     return hash;
   }
 }

