 package com.sq.sso.model;
 
 import java.io.Serializable;
 
 public class RedisNode
   implements Serializable
 {
   private static final long serialVersionUID = 1L;
   private String nodeIp;
   private String nodeCacheNum;
   private String portNumber;
 
   public String toString()
   {
     return "RedisNode [nodeIp=" + this.nodeIp + ",nodeCacheNum=" + this.nodeCacheNum + ",portNumber=" + this.portNumber + "]";
   }
 
   public String getNodeIp() {
     return this.nodeIp;
   }
   public String getNodeCacheNum() {
     return this.nodeCacheNum;
   }
   public void setNodeIp(String nodeIp) {
     this.nodeIp = nodeIp;
   }
   public void setNodeCacheNum(String nodeCacheNum) {
     this.nodeCacheNum = nodeCacheNum;
   }
   public String getPortNumber() {
     return this.portNumber;
   }
   public void setPortNumber(String portNumber) {
     this.portNumber = portNumber;
   }
 }

