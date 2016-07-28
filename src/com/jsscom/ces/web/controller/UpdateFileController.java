 package com.jsscom.ces.web.controller;
 
 import com.jsscom.ces.model.UpdateFile;
 import com.jsscom.ces.service.UpdateFileService;
 import com.jsscom.ces.vo.UploadResultModel;
 import com.sq.core.vo.PageParamModel;
 import com.sq.core.vo.PaginQueryResult;
 import com.sq.core.vo.ResultModel;
 import com.sq.core.web.Action;
 import java.io.PrintStream;
 import javax.annotation.Resource;
 import javax.servlet.ServletContext;
 import javax.servlet.http.HttpServletRequest;
 import javax.servlet.http.HttpServletResponse;
 import org.apache.commons.logging.Log;
 import org.apache.commons.logging.LogFactory;
 import org.springframework.stereotype.Controller;
 import org.springframework.web.bind.annotation.ModelAttribute;
 import org.springframework.web.bind.annotation.RequestMapping;
 import org.springframework.web.bind.annotation.RequestParam;
 import org.springframework.web.bind.annotation.ResponseBody;
 import org.springframework.web.multipart.MultipartFile;
 
 @Controller
 @RequestMapping({"/updateFile"})
 public class UpdateFileController extends Action<UpdateFile>
 {
   protected final Log log = LogFactory.getLog(UpdateFileController.class);
 
   @Resource
   private UpdateFileService updateFileService;
 
   @RequestMapping(value={"/uploadFile.do"}, produces={"text/html;charset=UTF-8"})
   @ResponseBody
   public UploadResultModel uploadFile(@RequestParam(value="file", required=false) MultipartFile file, UpdateFile updateFiel, HttpServletRequest request, HttpServletResponse response) { String servletContextPath = request.getServletContext().getRealPath("/");
     return this.updateFileService.addFile(servletContextPath, file, updateFiel); } 
   @RequestMapping({"deleteFile.do"})
   @ResponseBody
   public ResultModel deleteFile(HttpServletRequest request, @RequestParam(value="id", defaultValue="0") int id) {
     return this.updateFileService.deleteFile(id);
   }
   @RequestMapping({"/updatePageJson.do"})
   @ResponseBody
   public PaginQueryResult<UpdateFile> getUpdatePage(@ModelAttribute("ppm") PageParamModel<UpdateFile> ppm, @ModelAttribute("t") UpdateFile t) { PaginQueryResult pqr = null;
     System.out.println("ppm: " + ppm);
     System.out.println("T: " + t);
     pqr = this.updateFileService.paginQuery(t, ppm.getPage(), ppm.getRows());
     return pqr;
   }
 }

