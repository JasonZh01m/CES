 package com.jsscom.ces.service.impl;
 
 import com.sq.core.service.impl.ServiceImpl;
 import com.sq.sso.data.dao.UserRoleRelationDao;
 import com.sq.sso.model.UserRoleRelation;
 import com.sq.sso.service.UserRoleRelationService;
 import java.util.List;
 import javax.annotation.Resource;
 import org.springframework.stereotype.Service;
 
 @Service("userRoleRelationServiceImpl")
 public class UserRoleRelationServiceImpl extends ServiceImpl<UserRoleRelation>
   implements UserRoleRelationService
 {
 
   @Resource
   private UserRoleRelationDao userRoleRelationDaoImpl;
 
   public int insert(UserRoleRelation urr)
   {
     return this.userRoleRelationDaoImpl.insert("save", urr);
   }
 
   public int delete(UserRoleRelation urr)
   {
     return this.userRoleRelationDaoImpl.delete(urr);
   }
 
   public List<UserRoleRelation> query(UserRoleRelation urr)
   {
     return this.userRoleRelationDaoImpl.query(urr);
   }
 }

