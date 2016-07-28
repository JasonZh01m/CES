 package com.jsscom.DataUpload.web.interceptor;
 
 import com.jsscom.ces.vo.ResponseModel;
 import com.jsscom.ces.web.controller.DataUploadController;
 import com.sq.core.cache.JsonSerializable;
 import com.sq.core.utils.MD5Utils;
 import java.io.IOException;
 import java.io.PrintWriter;
 import javax.servlet.http.HttpServletRequest;
 import javax.servlet.http.HttpServletResponse;
 import org.apache.commons.lang3.StringUtils;
 import org.apache.commons.logging.Log;
 import org.apache.commons.logging.LogFactory;
 import org.springframework.beans.factory.annotation.Value;
 import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
 
 public class VerifyInterceptor extends HandlerInterceptorAdapter
 {
   private final String CMD = "cmd";
 
   private final String ARGS = "args";
 
   private final String VERIFYCODE = "verifycode";
   private String key;
   protected final Log log = LogFactory.getLog(DataUploadController.class);
 
   public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception
   {
     boolean f = false;
     String veriftCode = request.getParameter("verifycode");
     String args = request.getParameter("args");
     String cmd = request.getParameter("cmd");
     ResponseModel rm = null;
     if ((StringUtils.isEmpty(veriftCode)) || (StringUtils.isEmpty(args)) || (StringUtils.isEmpty(cmd))) {
       this.log.debug("非法参数");
       rm = new ResponseModel();
       rm.setReturncode("1001".intern());
     } else {
       StringBuilder sb = new StringBuilder();
       sb.append(cmd).append(args);
       String verifyString = sb.toString();
       String argsMD5Code = MD5Utils.getMD5Code(verifyString, this.key);
       this.log.debug("verifyString:" + verifyString);
       this.log.debug("veriftCode:" + veriftCode);
       this.log.debug("argsMD5Code:" + argsMD5Code);
       if (veriftCode.equalsIgnoreCase(argsMD5Code))
       {
         this.log.debug("验证通过");
         f = true;
       } else {
         this.log.debug("验证失败");
         rm = new ResponseModel();
         rm.setReturncode("1000".intern());
         f = false;
       }
     }
     if (rm != null) {
       writeString(response, JsonSerializable.serializableString(rm));
     }
     return f;
   }
 
   private void writeString(HttpServletResponse resp, String info)
   {
     PrintWriter out = null;
     try {
       resp.setHeader("Content-Length".intern(), info.length());
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
 
   public String getKey()
   {
     return this.key;
   }
   @Value("#{propertiesReader['verifyKey']}")
   public void setKey(String key) {
     this.key = key;
   }
 }

