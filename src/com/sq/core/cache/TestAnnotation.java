 package com.sq.core.cache;
 
 import com.sq.core.annotation.ForeignKey;
 import com.sq.core.annotation.Relation;
 import com.sq.sso.model.PermissionRoleRelation;
 import java.io.PrintStream;
 import java.lang.reflect.Field;
 import java.util.HashMap;
 import java.util.Map;
 
 public class TestAnnotation
 {
   public static void main(String[] args)
     throws IllegalArgumentException, IllegalAccessException
   {
     PermissionRoleRelation prr = new PermissionRoleRelation();
 
     Map map = new HashMap();
 
     Class clz = prr.getClass();
     if (clz.isAnnotationPresent(Relation.class)) {
       Field[] fields = clz.getDeclaredFields();
       int len = fields.length;
       for (int i = 0; i < len; i++) {
         Field f = fields[i];
         if (!f.isAnnotationPresent(ForeignKey.class))
           continue;
         Class c = ((ForeignKey)f.getAnnotation(ForeignKey.class)).reference();
         if (c != null) {
           f.setAccessible(true);
           String reFerenceName = c.getSimpleName();
           System.out.println("annotation:" + reFerenceName);
           Object value = f.get(prr);
           System.out.println("value:" + value.getClass().getSimpleName() + "-" + value);
         }
       }
     }
   }
 }

