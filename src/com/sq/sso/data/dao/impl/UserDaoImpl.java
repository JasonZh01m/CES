 package com.sq.sso.data.dao.impl;
 
 import com.sq.core.dao.impl.DaoImpl;
 import com.sq.sso.data.dao.UserDao;
 import com.sq.sso.model.User;
 import org.mybatis.spring.SqlSessionTemplate;
 import org.springframework.stereotype.Repository;
 
 @Repository
 public class UserDaoImpl extends DaoImpl<User>
   implements UserDao
 {
   public User queryUserByLoginName(String loginName)
   {
     return (User)getSqlSession().selectOne("User.queryByLoginName", loginName);
   }
 
   public int deleUserById(User user)
   {
     return deleteById("deleteById", user.getId());
   }
 
   public int modifyUserInfo(User user)
   {
     return update("updateById", user);
   }
 
   public void destoryUserByUserId(String userId)
   {
     User user = new User();
     user.setId(Integer.parseInt(userId));
     user.setValidStatus("1");
     update("destoryUserByUserId", user);
     getSqlSession().update("User.destoryUserByUserId", user);
   }
 
   public User queryById(int id)
   {
     return (User)queryById("queryById", id);
   }
 
   public void updateErrorNumAndCurrentDate(User user)
   {
     update("updateErrorNumAndCurrentDate", user);
   }
 }

