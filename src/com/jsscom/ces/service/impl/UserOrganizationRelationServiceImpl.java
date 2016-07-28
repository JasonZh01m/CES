 package com.jsscom.ces.service.impl;
 
 import com.jsscom.ces.data.dao.UserOrganizationRelationDao;
 import com.jsscom.ces.model.UserOrganizationRelation;
 import com.jsscom.ces.service.UserOrganizationRelationService;
 import com.sq.core.service.impl.ServiceImpl;
 import java.util.List;
 import javax.annotation.Resource;
 import org.springframework.stereotype.Service;
 
 @Service
 public class UserOrganizationRelationServiceImpl extends ServiceImpl<UserOrganizationRelation>
   implements UserOrganizationRelationService
 {
 
   @Resource
   private UserOrganizationRelationDao userOrganizationRelationDaoImpl;
 
   public List<UserOrganizationRelation> query(UserOrganizationRelation uor)
   {
     return this.userOrganizationRelationDaoImpl.query(uor);
   }
 
   public List<UserOrganizationRelation> queryByOrgan(UserOrganizationRelation uor)
   {
     return this.userOrganizationRelationDaoImpl.queryOrgan(uor);
   }
 
   public int deleteByUserOrganization(UserOrganizationRelation uor)
   {
     return this.userOrganizationRelationDaoImpl.delete("deleteByUserOrganizationRelation", uor);
   }
 }

