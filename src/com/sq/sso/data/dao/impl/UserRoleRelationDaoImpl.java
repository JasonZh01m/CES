 package com.sq.sso.data.dao.impl;
 
 import com.sq.core.dao.impl.DaoImpl;
 import com.sq.sso.data.dao.UserRoleRelationDao;
 import com.sq.sso.model.UserRoleRelation;
 import java.util.List;
 import org.springframework.stereotype.Repository;
 
 @Repository
 public class UserRoleRelationDaoImpl extends DaoImpl<UserRoleRelation>
   implements UserRoleRelationDao
 {
   public int insert(UserRoleRelation urr)
   {
     return insert("save", urr);
   }
 
   public int delete(UserRoleRelation urr)
   {
     return delete("deleteByUserRoleRelation", urr);
   }
 
   public List<UserRoleRelation> query(UserRoleRelation urr)
   {
     return queryList("queryByUserRoleRelation", urr);
   }
 
   public List<UserRoleRelation> queryUserIdByRoleId(UserRoleRelation urr)
   {
     return queryList("queryUserIdByRoleId", urr);
   }
 
   public List<UserRoleRelation> queryUserIdByPerimssionId(UserRoleRelation urr)
   {
     return queryList("queryUserIdByPerimssionId", urr);
   }
 }

