 package com.jsscom.ces.web.controller;
 
 import com.jsscom.ces.model.Media;
 import com.jsscom.ces.service.MediaService;
 import com.jsscom.ces.vo.UploadResultModel;
 import com.sq.core.vo.ResultModel;
 import com.sq.core.web.Action;
 import com.sq.sso.vo.SSOSession;
 import java.io.File;
 import java.io.IOException;
 import java.io.PrintStream;
 import javax.annotation.Resource;
 import javax.servlet.ServletContext;
 import javax.servlet.http.HttpServletRequest;
 import javax.servlet.http.HttpServletResponse;
 import javax.servlet.http.HttpSession;
 import org.apache.commons.io.FileUtils;
 import org.apache.commons.logging.Log;
 import org.apache.commons.logging.LogFactory;
 import org.springframework.stereotype.Controller;
 import org.springframework.web.bind.annotation.RequestMapping;
 import org.springframework.web.bind.annotation.RequestParam;
 import org.springframework.web.bind.annotation.ResponseBody;
 import org.springframework.web.multipart.MultipartFile;
 
 @Controller
 @RequestMapping({"/media"})
 public class MediaController extends Action<Media>
 {
   protected final Log log = LogFactory.getLog(MediaController.class);
 
   @Resource
   private MediaService mediaService;
 
   @RequestMapping(value={"/uploadFile.do"}, produces={"text/html;charset=UTF-8"})
   @ResponseBody
   public UploadResultModel uploadFile(@RequestParam(value="file", required=false) MultipartFile file, HttpServletRequest request, HttpServletResponse response) { String servletContextPath = request.getServletContext().getRealPath("/");
     Media m = new Media();
     SSOSession session = (SSOSession)request.getSession().getAttribute("$session".intern());
     if (session != null) {
       m.setCreateUserId(Integer.valueOf(session.getUserId()).intValue());
       m.setCreateUserName(session.getUserName());
     }
     return this.mediaService.addFile(servletContextPath, file, m); } 
   @RequestMapping({"deleteFile.do"})
   @ResponseBody
   public ResultModel deleteFile(HttpServletRequest request, @RequestParam(value="id", defaultValue="0") String id) {
     return this.mediaService.deleteFile(id);
   }
 
   @RequestMapping({"/add"})
   public UploadResultModel addUser(@RequestParam MultipartFile[] myfiles, HttpServletRequest request)
     throws IOException
   {
     System.out.println("文件未上传数:" + myfiles.length);
     for (MultipartFile myfile : myfiles) {
       if (myfile.isEmpty()) {
         System.out.println("文件未上传");
       } else {
         System.out.println("文件长度: " + myfile.getSize());
         System.out.println("文件类型: " + myfile.getContentType());
         System.out.println("文件名称: " + myfile.getName());
         System.out.println("文件原名: " + myfile.getOriginalFilename());
         System.out.println("========================================");
 
         String realPath = request.getSession().getServletContext().getRealPath("/WEB-INF/upload");
 
         FileUtils.copyInputStreamToFile(myfile.getInputStream(), new File(realPath, myfile.getOriginalFilename()));
       }
     }
     UploadResultModel u = new UploadResultModel();
     return u;
   }
 }

