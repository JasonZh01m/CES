 package com.sq.core.cache;
 
 public class JedisNode
 {
   private String ip;
   private int port;
   private int weight = 1;
 
   private int timeout = 100000;
 
   public String getIp() {
     return this.ip;
   }
 
   public void setIp(String ip)
   {
     this.ip = ip;
   }
 
   public int getPort()
   {
     return this.port;
   }
 
   public void setPort(int port)
   {
     this.port = port;
   }
 
   public int getWeight()
   {
     return this.weight;
   }
 
   public void setWeight(int weight) {
     this.weight = weight;
   }
 
   public int getTimeout() {
     return this.timeout;
   }
 
   public void setTimeout(int timeout)
   {
     this.timeout = timeout;
   }
 }

