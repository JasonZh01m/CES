 package com.sq.sso.data.dao.impl;
 
 import com.sq.core.dao.impl.DaoImpl;
 import com.sq.sso.data.dao.RoleDao;
 import com.sq.sso.model.Role;
 import java.util.List;
 import java.util.Map;
 import org.mybatis.spring.SqlSessionTemplate;
 import org.springframework.stereotype.Repository;
 
 @Repository
 public class RoleDaoImpl extends DaoImpl<Role>
   implements RoleDao
 {
   public List<Role> queryAllRoleByUser(int userId)
   {
     return getSqlSession().selectList(getClsName() + ".queryRoleByUser", Integer.valueOf(userId));
   }
 
   public int update(Role role)
   {
     return update("updateByRoldId", role);
   }
 
   public void deleteRolePermission(Role role)
   {
     deleteById("deleteRolePermission", role.getId());
   }
 
   public void insertRolePermission(Map entityMap)
   {
     getSqlSession().insert(getClsName() + ".insertRolePermission", entityMap);
   }
 
   public List<Role> queryAllRole()
   {
     return queryList("queryAllRole", null);
   }
 }

