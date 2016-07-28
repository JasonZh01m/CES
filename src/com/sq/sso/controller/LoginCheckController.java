 package com.sq.sso.controller;
 
 import com.sq.core.cache.GeneralCache;
 import com.sq.core.utils.HttpUtil;
 import com.sq.core.vo.ResultModel;
 import com.sq.sso.model.LoginInfo;
 import com.sq.sso.model.LoginResult;
 import com.sq.sso.service.LoginInfoService;
 import com.sq.sso.service.SsoService;
 import com.sq.sso.vo.SSOSession;
 import java.awt.Color;
 import java.awt.Font;
 import java.awt.Graphics2D;
 import java.awt.image.BufferedImage;
 import java.io.IOException;
 import java.util.Random;
 import javax.annotation.Resource;
 import javax.imageio.ImageIO;
 import javax.servlet.ServletOutputStream;
 import javax.servlet.http.HttpServletRequest;
 import javax.servlet.http.HttpServletResponse;
 import javax.servlet.http.HttpSession;
 import org.apache.commons.lang3.StringUtils;
 import org.apache.commons.logging.Log;
 import org.apache.commons.logging.LogFactory;
 import org.springframework.stereotype.Controller;
 import org.springframework.web.bind.annotation.RequestMapping;
 import org.springframework.web.bind.annotation.ResponseBody;
 
 @Controller
 @RequestMapping({"/login"})
 public class LoginCheckController
 {
   private String session_flag = "$session";
 
   @Resource
   private GeneralCache gc;
   private static final Log log = LogFactory.getLog(LoginCheckController.class);
 
   @Resource
   private SsoService ssoService;
 
   @Resource
   private LoginInfoService loginInfoService;
   private String CAPTCHAKEY_FLAG = "@R_";
 
   private String base = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
 
   @RequestMapping({"/login.do"})
   public String login(HttpServletRequest request, HttpServletResponse response)
   {
     String targetUrl = request.getParameter("targetURL");
     request.setAttribute("targetUrl", targetUrl);
     request.setAttribute("CAPTCHAKEY", this.CAPTCHAKEY_FLAG + System.currentTimeMillis());
     return "/login/login";
   }
   @RequestMapping({"/changePwd.do"})
   @ResponseBody
   public ResultModel changePwd(HttpServletRequest request) { ResultModel rs = new ResultModel();
     SSOSession session = getSession(request);
     if (session != null) {
       String oldPwd = request.getParameter("oldPwd");
       String newPwd = request.getParameter("passWord");
       String newPwd1 = request.getParameter("passWord1");
 
       if ((newPwd != null) && (newPwd1 != null) && (newPwd.equals(newPwd1))) {
         int result = this.loginInfoService.updateUserPwd(Integer.parseInt(session.getUserId()), oldPwd, newPwd);
         if (result == 0) {
           rs.setSuccess(0);
           rs.setMsg("密码修改成功!");
         } else {
           rs.setSuccess(1);
           rs.setMsg("原密码错误,请重新输入!");
         }
       } else {
         rs.setSuccess(1);
         rs.setMsg("确认密码与输入密码不同,请确认两次输入密码必须相同");
       }
     } else {
       rs.setSuccess(3);
     }
     return rs;
   }
 
   private void putCode(String key, String value)
   {
     this.gc.putStr(key, value);
   }
 
   private String getCode(String key) {
     return this.gc.getStr(key);
   }
 
   private void removeCode(String key) {
     this.gc.removeStr(key);
   }
   @RequestMapping({"/getUser.do"})
   @ResponseBody
   public SSOSession getSession(HttpServletRequest request) { SSOSession session = null;
     session = (SSOSession)request.getSession().getAttribute(this.session_flag);
     return session; }
 
   @RequestMapping({"/logout.do"})
   @ResponseBody
   public String logout(HttpServletRequest request, HttpServletResponse response)
   {
     request.getSession().invalidate();
     return "1";
   }
   @RequestMapping({"/loginCheck.do"})
   @ResponseBody
   public LoginResult login(HttpServletRequest request, HttpServletResponse response, LoginInfo loginInfo) {
     LoginResult lr = new LoginResult();
     try {
       String clientIp = HttpUtil.getClientIp(request);
       loginInfo.setClientIp(clientIp);
 
       boolean verifyLogin = false;
       String tokenKey = null;
 
       String rand = getCode(loginInfo.getCaptchaKey());
       if ((rand == null) || (
         (StringUtils.isNotEmpty(rand)) && 
         (!loginInfo.getCaptcha()
         .equalsIgnoreCase(rand)))) {
         lr.setMessage("验证码不正确！");
         log.info("验证码不正确!");
         lr.setResult(false);
         return lr;
       }
       SSOSession session = this.ssoService.getSsoSession(request);
       if (session != null) {
         verifyLogin = this.ssoService.isLogin(session, 
           loginInfo.getClientIp());
         if (verifyLogin)
         {
           if (!session.getLoginName()
             .equals(loginInfo.getLoginName())) {
             verifyLogin = false;
             log.info("用户未登录!");
           }
         }
       }
       this.loginInfoService.setSesionTimeout(this.ssoService.getOvertime());
       lr = this.loginInfoService.login(loginInfo, verifyLogin, tokenKey);
       if (lr.isResult()) {
         request.getSession().setAttribute(this.session_flag, lr.getSession());
         if (StringUtils.isNotEmpty(rand)) {
           removeCode(loginInfo.getCaptchaKey());
         }
         log.info(loginInfo.getLoginName() + "  success login！！");
       }
     } catch (Exception e) {
       e.printStackTrace();
       log.error(e.getMessage());
 
       lr.setResult(false);
     }
     return lr;
   }
 
   @RequestMapping({"/checkCode.do"})
   @ResponseBody
   public LoginResult checkCode(LoginInfo loginInfo)
   {
     LoginResult lr = new LoginResult();
     String rand = getCode(loginInfo.getCaptchaKey());
     if ((StringUtils.isNotEmpty(rand)) && 
       (loginInfo.getCaptcha().equalsIgnoreCase(rand)))
       lr.setResult(true);
     else {
       lr.setResult(false);
     }
     return lr;
   }
 
   @RequestMapping({"/code.do"})
   public void code(HttpServletRequest request, HttpServletResponse response)
     throws IOException
   {
     int width = 64;
 
     int height = 23;
     BufferedImage buffImg = new BufferedImage(width, height, 
       1);
     Graphics2D g = buffImg.createGraphics();
 
     Random random = new Random();
 
     g.setColor(getRandColor(200, 250));
     g.fillRect(0, 0, width, height);
 
     Font font = new Font("Times New Roman", 2, 18);
 
     g.setFont(font);
 
     g.setColor(Color.BLACK);
     g.drawRect(0, 0, width - 1, height - 1);
 
     g.setColor(Color.GRAY);
     g.setColor(getRandColor(160, 200));
     for (int i = 0; i < 100; i++) {
       int x = random.nextInt(width);
       int y = random.nextInt(height);
       int xl = random.nextInt(12);
       int yl = random.nextInt(12);
       g.drawLine(x, y, x + xl, y + yl);
     }
 
     StringBuffer randomCode = new StringBuffer();
 
     int length = 4;
 
     int size = this.base.length();
 
     for (int i = 0; i < length; i++)
     {
       int start = random.nextInt(size);
       String strRand = this.base.substring(start, start + 1);
 
       g.setColor(
         new Color(20 + random.nextInt(110), 20 + random
         .nextInt(110), 20 + random.nextInt(110)));
       g.drawString(strRand, 14 * i + 7, 15);
 
       randomCode.append(strRand);
     }
 
     String captchaKey = request.getParameter("captchaKey");
 
     putCode(captchaKey, randomCode.toString());
 
     g.dispose();
 
     response.setHeader("Cache-Control", "no-cache");
     response.setHeader("Cache-Control", "no-store");
     response.setHeader("Pragma", "no-cache");
     response.setDateHeader("Expires", 0L);
 
     response.setContentType("image/jpeg");
 
     ServletOutputStream sos = response.getOutputStream();
     ImageIO.write(buffImg, "jpeg", sos);
     sos.flush();
     sos.close();
   }
 
   private Color getRandColor(int fc, int bc)
   {
     Random random = new Random();
     if (fc > 255)
       fc = 255;
     if (bc > 255)
       bc = 255;
     int r = fc + random.nextInt(bc - fc);
     int g = fc + random.nextInt(bc - fc);
     int b = fc + random.nextInt(bc - fc);
     return new Color(r, g, b);
   }
 }

