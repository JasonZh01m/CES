 package com.jsscom.ces.data.dao.impl;
 
 import com.jsscom.ces.data.dao.UserOrganizationRelationDao;
 import com.jsscom.ces.model.UserOrganizationRelation;
 import com.sq.core.dao.impl.DaoImpl;
 import java.util.List;
 import org.mybatis.spring.SqlSessionTemplate;
 import org.springframework.stereotype.Repository;
 
 @Repository
 public class UserOrganizationRelationDaoImpl extends DaoImpl<UserOrganizationRelation>
   implements UserOrganizationRelationDao
 {
   public List<UserOrganizationRelation> query(UserOrganizationRelation uor)
   {
     return getSqlSession().selectList(super.getClsName() + ".queryByUserId", uor);
   }
 
   public List<UserOrganizationRelation> queryOrgan(UserOrganizationRelation uor)
   {
     return getSqlSession().selectList(super.getClsName() + ".queryByOrgan", Integer.valueOf(Integer.parseInt(uor.getOrgId())));
   }
 
   public List<UserOrganizationRelation> queryByOrg(int orgId)
   {
     List list = getSqlSession().selectList(super.getClsName() + ".queryByOrgan", Integer.valueOf(orgId));
 
     return list;
   }
 }

