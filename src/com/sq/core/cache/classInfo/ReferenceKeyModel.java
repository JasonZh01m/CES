 package com.sq.core.cache.classInfo;
 
 public class ReferenceKeyModel
 {
   private int fkNum = 0;
   private String className;
   private static String underLine = "_";
   private ReferenceValue[] fKeys;
 
   public ReferenceKeyModel(int num)
   {
     this.fKeys = new ReferenceValue[num];
   }
 
   public boolean isSingleKey() {
     return this.fKeys.length == this.fkNum;
   }
 
   public String getKey() {
     StringBuilder sb = new StringBuilder();
     if (this.fKeys.length == 2) {
       if (this.fKeys.length == this.fkNum) {
         sb.append(this.className);
         sb.append(underLine);
         sb.append(this.fKeys[0].getValue());
         sb.append(underLine);
         sb.append(this.fKeys[1].getValue());
       } else {
         for (int i = 0; i < this.fKeys.length; i++) {
           ReferenceValue rv = this.fKeys[i];
           if (rv != null) {
             sb.append(rv.getFkName());
             sb.append(underLine);
             sb.append(rv.getValue());
           }
         }
       }
     }
 
     return sb.toString();
   }
 
   public ReferenceValue[] getfKeys()
   {
     return this.fKeys;
   }
 
   public void setfKeys(ReferenceValue[] fKeys) {
     this.fKeys = fKeys;
   }
 
   public int getFkNum() {
     return this.fkNum;
   }
 
   public void setFkNum(int fkNum) {
     this.fkNum = fkNum;
   }
 
   public String getClassName() {
     return this.className;
   }
 
   public void setClassName(String className) {
     this.className = className;
   }
 
   public static String getUnderLine() {
     return underLine;
   }
 
   public static void setUnderLine(String underLine) {
     underLine = underLine;
   }
 }

