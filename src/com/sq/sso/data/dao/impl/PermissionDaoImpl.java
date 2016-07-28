 package com.sq.sso.data.dao.impl;
 
 import com.sq.core.dao.impl.DaoImpl;
 import com.sq.sso.data.dao.PermissionDao;
 import com.sq.sso.model.Permission;
 import com.sq.sso.model.Role;
 import java.util.HashMap;
 import java.util.List;
 import java.util.Map;
 import org.mybatis.spring.SqlSessionTemplate;
 import org.springframework.stereotype.Repository;
 
 @Repository
 public class PermissionDaoImpl extends DaoImpl<Permission>
   implements PermissionDao
 {
   public List<Integer> queryForSysId(String url)
   {
     return getSqlSession().selectList(getClsName() + ".querySysId", url);
   }
 
   public List<Permission> queryAllPermissionByRole(Role role)
   {
     return getSqlSession().selectList(getClsName() + ".queryPermissionByRole", role);
   }
 
   public List<Permission> queryAllPermissionByRoleIdAndType(int roleId)
   {
     return getSqlSession().selectList(getClsName() + ".queryPermissionByRoleIdAndType", Integer.valueOf(roleId));
   }
 
   public List<String> queryMainPagesByRole(Role role)
   {
     return getSqlSession().selectList(getClsName() + ".queryMainPagesByRole", role);
   }
 
   public List<String> queryMainPages()
   {
     return getSqlSession().selectList(getClsName() + ".queryMainPages");
   }
 
   public List<Permission> queryAllPermissionByPid(String pId)
   {
     return getSqlSession().selectList(getClsName() + ".queryAllPermissionByPid", pId);
   }
 
   public Integer validateResourceString(String resourceString)
   {
     return (Integer)getSqlSession().selectOne(getClsName() + ".queryResourceString", resourceString);
   }
 
   public void deleteByResourceString(String resourceString)
   {
     getSqlSession().delete(getClsName() + ".deleteRolePermissionByResourceString", resourceString);
     getSqlSession().delete(getClsName() + ".deleteByResourceString", resourceString);
   }
 
   public List<Permission> queryChildPermission(String resourceString, int roleId, boolean isLink)
   {
     Map map = new HashMap();
     map.put("resourceString", resourceString);
     map.put("roleId", roleId);
     if (isLink)
       map.put("type", "0");
     else {
       map.put("type", "1");
     }
     return getSqlSession().selectList(getClsName() + ".queryChildPermission", map);
   }
 
   public List<Permission> queryAllLinkPermissionByPid(String pId)
   {
     return getSqlSession().selectList(getClsName() + ".queryAllLinkPermissionByPid", pId);
   }
 
   public List<Permission> queryAllPermissionByUserId(String userId)
   {
     return getSqlSession().selectList(getClsName() + ".queryAllPermissionByUserId", userId);
   }
 }

