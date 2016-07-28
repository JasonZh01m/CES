 package com.jsscom.ces.web.controller;
 
 import com.jsscom.ces.model.EmployeePhoto;
 import com.jsscom.ces.service.EmployeePhotoService;
 import com.jsscom.ces.vo.UploadFileModel;
 import com.sq.core.vo.ResultModel;
 import com.sq.core.web.Action;
 import java.io.File;
 import java.io.IOException;
 import java.io.PrintStream;
 import java.util.Date;
 import javax.annotation.Resource;
 import javax.servlet.ServletContext;
 import javax.servlet.http.HttpServletRequest;
 import javax.servlet.http.HttpServletResponse;
 import org.apache.commons.io.FileUtils;
 import org.apache.commons.logging.Log;
 import org.apache.commons.logging.LogFactory;
 import org.springframework.http.HttpHeaders;
 import org.springframework.http.HttpStatus;
 import org.springframework.http.MediaType;
 import org.springframework.http.ResponseEntity;
 import org.springframework.stereotype.Controller;
 import org.springframework.web.bind.annotation.RequestMapping;
 import org.springframework.web.bind.annotation.RequestParam;
 import org.springframework.web.bind.annotation.ResponseBody;
 import org.springframework.web.multipart.MultipartFile;
 
 @Controller
 @RequestMapping({"/employeePhoto"})
 public class EmployeePhotoController extends Action<EmployeePhoto>
 {
   protected final Log log = LogFactory.getLog(EmployeePhotoController.class);
 
   @Resource
   private EmployeePhotoService employeePhotoService;
 
   @RequestMapping(value={"/uploadFile.do"}, produces={"text/html;charset=UTF-8"})
   @ResponseBody
   public ResultModel uploadFile(@RequestParam(value="file", required=false) MultipartFile file, @RequestParam(value="file1", required=false) MultipartFile file1, EmployeePhoto employeePhoto, HttpServletRequest request, HttpServletResponse response) { String servletContextPath = request.getServletContext().getRealPath("/");
     EmployeePhoto ep = new EmployeePhoto();
     if (file != null) {
       System.out.println("file:" + file.getSize());
       UploadFileModel um = this.employeePhotoService.addFile(servletContextPath, file);
       ep.setHpImgMd5(um.getMd5Code());
       ep.setHpImgName(um.getName());
       ep.setHpImgPath(um.getSavePath());
       ep.setHpImgUrl(um.getDownloadPath());
     }
     if (file1 != null) {
       UploadFileModel um = this.employeePhotoService.addFile(servletContextPath, file1);
       ep.setCertificateMd5(um.getMd5Code());
       ep.setCertificateName(um.getName());
       ep.setCertificatePath(um.getSavePath());
       ep.setCertificateUrl(um.getDownloadPath());
     }
     ep.setEmployeeName(employeePhoto.getEmployeeName());
     ep.setEmployeeNumber(employeePhoto.getEmployeeNumber());
     ep.setOrganName(employeePhoto.getOrganName());
     ep.setUpdateTime(new Date());
 
     this.employeePhotoService.create(ep);
     ResultModel rm = new ResultModel();
     rm.setSuccess(0);
     return rm; } 
   @RequestMapping(value={"/editFile.do"}, produces={"text/html;charset=UTF-8"})
   @ResponseBody
   public ResultModel editFile(@RequestParam(value="file", required=false) MultipartFile file, @RequestParam(value="file1", required=false) MultipartFile file1, EmployeePhoto employeePhoto, HttpServletRequest request, HttpServletResponse response) {
     String servletContextPath = request.getServletContext().getRealPath("/");
     EmployeePhoto old = (EmployeePhoto)this.employeePhotoService.findById(employeePhoto.getId());
     EmployeePhoto ep = new EmployeePhoto();
     UploadFileModel um1 = null;
     UploadFileModel um2 = null;
     if ((file != null) && (!file.isEmpty())) {
       System.out.println("file:" + file.getSize());
       um1 = this.employeePhotoService.addFile(servletContextPath, file);
       ep.setHpImgMd5(um1.getMd5Code());
       ep.setHpImgName(um1.getName());
       ep.setHpImgPath(um1.getSavePath());
       ep.setHpImgUrl(um1.getDownloadPath());
     }
     if ((file1 != null) && (!file1.isEmpty())) {
       um2 = this.employeePhotoService.addFile(servletContextPath, file1);
       ep.setCertificateMd5(um2.getMd5Code());
       ep.setCertificateName(um2.getName());
       ep.setCertificatePath(um2.getSavePath());
       ep.setCertificateUrl(um2.getDownloadPath());
     }
     ep.setEmployeeName(employeePhoto.getEmployeeName());
     ep.setEmployeeNumber(employeePhoto.getEmployeeNumber());
     ep.setOrganName(employeePhoto.getOrganName());
     ep.setUpdateTime(new Date());
     ep.setId(employeePhoto.getId());
 
     int rs = this.employeePhotoService.update(ep);
     ResultModel rm = new ResultModel();
     if (rs > 0) {
       rm.setSuccess(0);
       if ((um1 != null) && (old != null) && (old.getHpImgPath() != null)) {
         this.employeePhotoService.deleteFile(old.getHpImgPath());
       }
       if ((um2 != null) && (old != null) && (old.getCertificatePath() != null))
         this.employeePhotoService.deleteFile(old.getCertificatePath());
     }
     else {
       rm.setSuccess(1);
     }
 
     return rm;
   }
   @RequestMapping({"deleteFile.do"})
   @ResponseBody
   public ResultModel deleteFile(HttpServletRequest request, @RequestParam(value="id", defaultValue="0") int id) {
     EmployeePhoto ep = (EmployeePhoto)this.employeePhotoService.findById(id);
     ResultModel rm = new ResultModel();
     if ((ep != null) && (this.employeePhotoService.delete(id) > 0)) {
       String savePath1 = ep.getCertificatePath();
       if (savePath1 != null) {
         this.employeePhotoService.deleteFile(savePath1);
       }
 
       String savePath2 = ep.getHpImgPath();
       if (savePath2 != null)
         this.employeePhotoService.deleteFile(savePath2);
     }
     else {
       rm.setSuccess(1);
     }
     return rm;
   }
   @RequestMapping({"getFile.do"})
   public ResponseEntity<byte[]> getFile(@RequestParam(value="code", defaultValue="0") String code) throws IOException {
     ResponseEntity respinseEntity = null;
     UploadFileModel uf = this.employeePhotoService.fileFile(code);
     if (uf != null) {
       String path = uf.getSavePath();
       File file = new File(path);
       HttpHeaders headers = new HttpHeaders();
       String fileName = uf.getName();
       headers.setContentDispositionFormData("attachment", new String(fileName.getBytes("utf-8"), "ISO8859-1"));
       headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
       respinseEntity = new ResponseEntity(FileUtils.readFileToByteArray(file), headers, HttpStatus.CREATED);
     }
     return respinseEntity;
   }
 
   public EmployeePhotoService getEmployeePhotoService()
   {
     return this.employeePhotoService;
   }
 
   public void setEmployeePhotoService(EmployeePhotoService employeePhotoService) {
     this.employeePhotoService = employeePhotoService;
   }
 }

