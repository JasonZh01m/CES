 package com.sq.sso;
 
 import com.sq.sso.model.Permission;
 import java.util.Comparator;
 
 public class ComparatorPermission
   implements Comparator<Permission>
 {
   public int compare(Permission p1, Permission p2)
   {
     int p = Integer.valueOf(p1.getParentId()).compareTo(Integer.valueOf(p2.getParentId()));
     if (p == 0) {
       int o = Integer.valueOf(p1.getOrderIndex()).compareTo(Integer.valueOf(p2.getOrderIndex()));
       if (o == 0) {
         int i = Integer.valueOf(p1.getId()).compareTo(Integer.valueOf(p2.getId()));
         return i;
       }
       return o;
     }
 
     return p;
   }
 }

