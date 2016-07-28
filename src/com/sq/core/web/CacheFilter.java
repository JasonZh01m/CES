 package com.sq.core.web;
 
 import java.io.ByteArrayOutputStream;
 import java.io.IOException;
 import java.io.InputStream;
 import javax.servlet.Filter;
 import javax.servlet.FilterChain;
 import javax.servlet.FilterConfig;
 import javax.servlet.ServletContext;
 import javax.servlet.ServletException;
 import javax.servlet.ServletOutputStream;
 import javax.servlet.ServletRequest;
 import javax.servlet.ServletResponse;
 import javax.servlet.http.HttpServletRequest;
 import javax.servlet.http.HttpServletResponse;
 
 public class CacheFilter
   implements Filter
 {
   private FilterConfig filterconfig;
   private String contextPath;
   private ServletContext ctx;
 
   public void destroy()
   {
   }
 
   public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
     throws IOException, ServletException
   {
     HttpServletRequest req = (HttpServletRequest)request;
     HttpServletResponse res = (HttpServletResponse)response;
     String uri = req.getRequestURI();
     String accept = req.getHeader("Accept-Encoding");
 
     InputStream in = null;
     this.contextPath = this.ctx.getContextPath();
     uri = uri.substring(this.contextPath.length());
 
     if ((accept != null) && (accept.contains("gzip")) && ((in = this.ctx.getResourceAsStream(uri + ".gz".intern())) != null)) {
       ByteArrayOutputStream bout = new ByteArrayOutputStream();
       byte[] b = new byte[8192];
       int read = 0;
       while ((read = in.read(b)) >= 0) {
         bout.write(b, 0, read);
       }
       in.close();
       res.setHeader("Content-Encoding", "gzip");
       res.setContentType("application/javascript;charset=UTF-8");
       res.setContentLength(bout.size());
       ServletOutputStream out = res.getOutputStream();
       out.write(bout.toByteArray());
       out.flush();
       return;
     }
 
     if ((this.contextPath == null) || ("".equals(this.contextPath))) {
       this.contextPath = req.getContextPath();
     }
     uri = uri.substring(uri.lastIndexOf(".") + 1);
     long date = 0L;
 
     if (uri.equalsIgnoreCase("jpg"))
     {
       String value = this.filterconfig.getInitParameter("jpg");
 
       date = System.currentTimeMillis() + Long.parseLong(value) * 60L * 60L * 1000L;
     }
 
     if (uri.equalsIgnoreCase("gif")) {
       String value = this.filterconfig.getInitParameter("gif");
       date = System.currentTimeMillis() + Long.parseLong(value) * 60L * 60L * 1000L;
     }
 
     if (uri.equalsIgnoreCase("png")) {
       String value = this.filterconfig.getInitParameter("png");
       date = System.currentTimeMillis() + Long.parseLong(value) * 60L * 60L * 1000L;
     }
 
     if (uri.equalsIgnoreCase("css")) {
       String value = this.filterconfig.getInitParameter("css");
       date = System.currentTimeMillis() + Long.parseLong(value) * 60L * 60L * 1000L;
     }
 
     if (uri.equalsIgnoreCase("js")) {
       String value = this.filterconfig.getInitParameter("js");
       date = System.currentTimeMillis() + Long.parseLong(value) * 60L * 60L * 1000L;
     }
 
     res.setDateHeader("Expires", date);
     chain.doFilter(req, res);
   }
 
   public void init(FilterConfig filterConfig)
     throws ServletException
   {
     this.ctx = filterConfig.getServletContext();
     this.filterconfig = filterConfig;
   }
 }

