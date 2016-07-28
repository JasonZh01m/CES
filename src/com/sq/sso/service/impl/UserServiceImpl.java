 package com.sq.sso.service.impl;
 
 import com.sq.core.service.impl.ServiceImpl;
 import com.sq.core.utils.PasswordUtils;
 import com.sq.sso.data.dao.UserDao;
 import com.sq.sso.model.User;
 import com.sq.sso.service.LoginInfoService;
 import com.sq.sso.service.UserService;
 import com.sq.sso.vo.SSOSession;
 import java.io.PrintStream;
 import java.security.NoSuchAlgorithmException;
 import java.security.NoSuchProviderException;
 import java.text.SimpleDateFormat;
 import java.util.Date;
 import java.util.List;
 import javax.annotation.Resource;
 import org.apache.commons.lang3.StringUtils;
 import org.springframework.stereotype.Service;
 
 @Service
 public class UserServiceImpl extends ServiceImpl<User>
   implements UserService
 {
 
   @Resource
   private UserDao userDaoImpl;
 
   @Resource
   private LoginInfoService loginInfoService;
 
   public List<User> queryUser(User user)
   {
     return this.userDaoImpl.queryList("queryByUser", user);
   }
 
   public int registerUser(User user)
   {
     setShaSecurePassword(user);
 
     SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
     String regData = sdf.format(new Date());
     user.setRegDate(regData);
 
     System.out.println("into User:" + user.toString());
     return this.userDaoImpl.insert("save", user);
   }
 
   public boolean queryUserByLoginName(String loginName)
   {
     User user = this.userDaoImpl.queryUserByLoginName(loginName);
 
     return user != null;
   }
 
   public int deleUserById(User user)
   {
     return this.userDaoImpl.deleUserById(user);
   }
 
   public int modifyUserInfo(User user)
   {
     System.out.println(user.toString());
 
     if (StringUtils.isNotEmpty(user.getPassWord())) {
       setShaSecurePassword(user);
     }
 
     return this.userDaoImpl.modifyUserInfo(user);
   }
 
   public void setShaSecurePassword(User user)
   {
     try
     {
       String saltCode = PasswordUtils.getSalt();
       System.out.println("盐码:" + saltCode);
       String securePassword = PasswordUtils.getShaSecurePassword(user.getPassWord(), saltCode);
 
       System.out.println("加密后的密码:" + securePassword);
 
       user.setPassWord(securePassword);
       user.setSaltCode(saltCode);
     }
     catch (NoSuchAlgorithmException e) {
       e.printStackTrace();
     }
     catch (NoSuchProviderException e) {
       e.printStackTrace();
     }
   }
 
   public void destroyOnlineUser(SSOSession s)
   {
     this.userDaoImpl.destoryUserByUserId(s.getUserId());
     this.loginInfoService.logout(s.getUserId(), s.getTokenCode());
   }
 
   public User queryById(int id)
   {
     return this.userDaoImpl.queryById(id);
   }
 
   public void updateErrorNumAndCurrentDate(User user)
   {
     this.userDaoImpl.updateErrorNumAndCurrentDate(user);
   }
 }

