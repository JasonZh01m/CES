 package com.sq.sso.web.filter;
 
 import com.sq.core.utils.HttpUtil;
 import com.sq.sso.model.UriInfo;
 import com.sq.sso.service.SsoService;
 import com.sq.sso.vo.SSOSession;
 import java.io.File;
 import java.io.IOException;
 import java.io.PrintStream;
 import java.io.PrintWriter;
 import javax.annotation.Resource;
 import javax.servlet.Filter;
 import javax.servlet.FilterChain;
 import javax.servlet.FilterConfig;
 import javax.servlet.RequestDispatcher;
 import javax.servlet.ServletContext;
 import javax.servlet.ServletException;
 import javax.servlet.ServletRequest;
 import javax.servlet.ServletResponse;
 import javax.servlet.http.HttpServletRequest;
 import javax.servlet.http.HttpServletResponse;
 import org.apache.commons.logging.Log;
 import org.apache.commons.logging.LogFactory;
 
 public class AppFilter
   implements Filter
 {
 
   @Resource
   private SsoService ssoService;
   private String adminStr = "admin";
 
   private String GO_URL = "getOperate.do";
 
   private String n_p_page = "/common/nopermission.jsp";
 
   private String n_rs_page = "/common/noresource.jsp";
 
   private String noPermissionJson = "{\"success\":\"4\",\"info\":\"no permission\"}";
 
   private String noLoginJson = "{\"success\":\"3\",\"info\":\"no login\"}";
   private String contextPath;
   private String REDIRECT_LOGIN_PAGE = "/login/login.do?targetURL=";
 
   private String indexPage = "indexPage.do";
 
   protected final Log log = LogFactory.getLog(AppFilter.class);
 
   public void init(FilterConfig fc)
     throws ServletException
   {
     ServletContext servletContext = fc.getServletContext();
     this.contextPath = servletContext.getContextPath();
     this.REDIRECT_LOGIN_PAGE = (this.contextPath + this.REDIRECT_LOGIN_PAGE);
     String log4jdir = servletContext.getRealPath("/");
     if (log4jdir.indexOf("webapps") > -1) {
       int index = log4jdir.indexOf("webapps");
       log4jdir = log4jdir.substring(0, index - 1);
     }
     System.setProperty("log4jdir", log4jdir + File.separator + "AppLog");
     System.out.println(System.getProperty("log4jdir"));
 
     String exclude = fc.getInitParameter("exclude");
 
     this.ssoService.intiExclude(exclude);
   }
 
   public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain filterChain)
     throws IOException, ServletException
   {
     HttpServletRequest req = (HttpServletRequest)arg0;
     HttpServletResponse resp = (HttpServletResponse)arg1;
 
     String uri = req.getRequestURI();
 
     UriInfo uriInfo = this.ssoService.getUriInfo(uri, this.contextPath);
 
     if (uriInfo != null)
     {
       boolean verifyLogin = false;
 
       SSOSession session = null;
       String clientIp = HttpUtil.getClientIp(req);
 
       session = this.ssoService.getSsoSession(req);
 
       if (session != null) {
         verifyLogin = this.ssoService.isLogin(session, clientIp);
       }
 
       if (verifyLogin)
       {
         String rids = session.getRids();
         String uriId = uriInfo.getPid();
         boolean verifyResult = false;
         String loginName = session.getLoginName();
         if (this.adminStr.equals(loginName))
           verifyResult = true;
         else {
           verifyResult = this.ssoService.isPermit(rids, uriId);
         }
         if (verifyResult) {
           filterChain.doFilter(req, resp);
         } else {
           int type = uriInfo.getType();
           if (type == 1)
             writeString(resp, this.noPermissionJson);
           else {
             req.getRequestDispatcher(this.n_p_page).forward(req, resp);
           }
         }
 
       }
       else if (uriInfo.getType() == 0)
       {
         resp.sendRedirect(this.REDIRECT_LOGIN_PAGE + req.getRequestURL());
       }
       else
       {
         writeString(resp, this.noLoginJson);
       }
     }
     else
     {
       uri = req.getServletPath();
       if (uri.endsWith(this.GO_URL)) {
         filterChain.doFilter(req, resp);
       }
       else if (this.ssoService.isExclude(uri))
         filterChain.doFilter(req, resp);
       else
         req.getRequestDispatcher(this.n_rs_page).forward(req, resp);
     }
   }
 
   private void writeString(HttpServletResponse resp, String info)
   {
     PrintWriter out = null;
     try {
       out = resp.getWriter();
       out.print(info);
     } catch (IOException e) {
       e.printStackTrace();
     } finally {
       if (out != null) {
         out.flush();
         out.close();
       }
     }
   }
 
   public void destroy()
   {
   }
 }

