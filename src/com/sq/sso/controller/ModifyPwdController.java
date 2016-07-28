 package com.sq.sso.controller;
 
 import com.sq.core.utils.PasswordUtils;
 import com.sq.core.vo.ResultModel;
 import com.sq.sso.data.dao.UserDao;
 import com.sq.sso.model.User;
 import com.sq.sso.service.UserService;
 import com.sq.sso.service.impl.UserServiceImpl;
 import com.sq.sso.web.filter.AppFilter;
 import javax.annotation.Resource;
 import javax.servlet.http.Cookie;
 import javax.servlet.http.HttpServletRequest;
 import javax.servlet.http.HttpServletResponse;
 import org.apache.commons.logging.Log;
 import org.apache.commons.logging.LogFactory;
 import org.springframework.stereotype.Controller;
 import org.springframework.web.bind.annotation.RequestMapping;
 import org.springframework.web.bind.annotation.ResponseBody;
 
 @Controller
 @RequestMapping({"/modifyPwd"})
 public class ModifyPwdController
 {
 
   @Resource
   private UserDao userDaoImpl;
 
   @Resource
   private UserService userServiceImpl;
   protected final Log log = LogFactory.getLog(AppFilter.class);
 
   @RequestMapping({"/modifyPwd.do"})
   public String modifyPwd(HttpServletRequest request, HttpServletResponse response)
   {
     String tokenKey = null;
 
     String userId = null;
 
     Cookie[] cookies = request.getCookies();
     if ((cookies != null) && (cookies.length > 0)) {
       for (int i = 0; i < cookies.length; i++) {
         Cookie cookie = cookies[i];
         if ("tokenKey".equals(cookie.getName())) {
           tokenKey = cookie.getValue();
         }
         if ("userId".equals(cookie.getName())) {
           userId = cookie.getValue();
         }
       }
     }
 
     request.setAttribute("userId", userId);
 
     return "/modifyPwd/modifyPwd";
   }
   @RequestMapping({"updatePwd.do"})
   @ResponseBody
   public ResultModel updatePwd(User user) { ResultModel rm = new ResultModel();
 
     String[] str = user.getPassWord().split(",");
     String oldPwd = str[0];
     String newPwd = str[1];
 
     User u = this.userServiceImpl.queryById(user.getId());
     if (u != null)
     {
       String saltCode = u.getSaltCode();
       String securePassword = PasswordUtils.getShaSecurePassword(oldPwd, saltCode);
 
       if (u.getPassWord().equals(securePassword)) {
         user.setPassWord(newPwd);
         UserServiceImpl usi = new UserServiceImpl();
         usi.setShaSecurePassword(user);
         int result = this.userDaoImpl.modifyUserInfo(user);
         if (result > 0)
           rm.setSuccess(0);
         else
           rm.setSuccess(1);
       }
       else {
         rm.setMsg("当前密码错误!");
       }
     }
 
     return rm;
   }
 }

