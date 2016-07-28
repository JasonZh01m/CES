 package com.jsscom.ces.data.dao.impl;
 
 import com.jsscom.ces.data.dao.OrganizationDao;
 import com.jsscom.ces.model.Organization;
 import com.sq.core.dao.impl.DaoImpl;
 import java.util.HashMap;
 import java.util.List;
 import java.util.Map;
 import org.mybatis.spring.SqlSessionTemplate;
 import org.springframework.stereotype.Repository;
 
 @Repository
 public class OrganizationDaoImpl extends DaoImpl<Organization>
   implements OrganizationDao
 {
   public Organization findOrgByInfo(Organization org)
   {
     return (Organization)getSqlSession().selectOne(super.getClsName() + ".queryByInfo", org);
   }
 
   public List<Organization> findAllOrganizationByPid(String pId)
   {
     return getSqlSession().selectList(super.getClsName() + ".queryAllOrganizationByPid", pId);
   }
 
   public Organization queryCheckedOrganizationByPid(String pId)
   {
     return (Organization)getSqlSession().selectOne(super.getClsName() + ".queryCheckedOrganizationByPid", pId);
   }
 
   public List<Organization> queryOrganizationInfo(Organization organ)
   {
     Map map = new HashMap();
     map.put("orgId", organ.getOrgId());
     return getSqlSession().selectList(super.getClsName() + ".queryOrganizationByUserId", map);
   }
 
   public List<Organization> queryAllUserByOrganization(int userId)
   {
     return getSqlSession().selectList(super.getClsName() + ".queryOrgByUser", Integer.valueOf(userId));
   }
 
   public List<Organization> queryOrganizationInfo()
   {
     return getSqlSession().selectList(super.getClsName() + ".queryOrganization", null);
   }
 
   public List<Organization> findAllOrganizationByMap(Organization organ)
   {
     Map map = new HashMap();
     String code = "";
     if ((organ.getCode() == null) || (organ.getCode().equals(""))) {
       code = "0";
     }
     map.put("code", code);
     map.put("parentId", Integer.valueOf(organ.getId()));
     return getSqlSession().selectList(super.getClsName() + ".queryAllOrganizationByMap", map);
   }
 
   public List<Organization> findAllOrganByMap(Organization organ)
   {
     Map map = new HashMap();
     map.put("orgId", organ.getOrgId());
     map.put("parentId", Integer.valueOf(organ.getId()));
     return getSqlSession().selectList(super.getClsName() + ".queryAllOrganByMap", map);
   }
 }

