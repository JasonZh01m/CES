 package com.sq.sso.model;
 
 import com.sq.core.annotation.ForeignKey;
 import com.sq.core.annotation.Relation;
 import com.sq.core.cache.JsonSerializable;
 import java.io.Serializable;
 
 @Relation
 public class PermissionRoleRelation
   implements Serializable
 {
   private static final long serialVersionUID = 1L;
 
   @ForeignKey(reference=Permission.class)
   private int permissionId = -1;
 
   @ForeignKey(reference=Role.class)
   private int roleId = -1;
 
   public String toString() {
     return JsonSerializable.serializableString(this);
   }
 
   public int hashCode()
   {
     return (String.valueOf(this.permissionId) + "_" + String.valueOf(this.roleId)).hashCode();
   }
 
   public boolean equals(Object obj)
   {
     return toString().equals(obj.toString());
   }
 
   public int getPermissionId() {
     return this.permissionId;
   }
   public int getRoleId() {
     return this.roleId;
   }
   public void setPermissionId(int permissionId) {
     this.permissionId = permissionId;
   }
   public void setRoleId(int roleId) {
     this.roleId = roleId;
   }
 }

