 package com.jsscom.ces.web.controller;
 
 import com.jsscom.ces.model.Media;
 import com.jsscom.ces.model.UpdateFile;
 import com.jsscom.ces.service.DownLoadService;
 import com.sq.core.web.Action;
 import java.io.File;
 import java.io.IOException;
 import java.io.PrintStream;
 import javax.annotation.Resource;
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
 
 @Controller
 @RequestMapping({"/downLoad"})
 public class DownLoadController extends Action<Media>
 {
   protected final Log log = LogFactory.getLog(DownLoadController.class);
 
   @Resource
   private DownLoadService downLoadService;
 
   @RequestMapping({"getMedia.do"})
   public ResponseEntity<byte[]> getMedia(@RequestParam(value="code", defaultValue="") String code) throws IOException { ResponseEntity respinseEntity = null;
     Media media = this.downLoadService.getMedia(code);
     if (media != null) {
       String path = media.getSavePath();
       File file = new File(path);
       HttpHeaders headers = new HttpHeaders();
       String fileName = file.getName();
       headers.setContentDispositionFormData("attachment", fileName);
       headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
       respinseEntity = new ResponseEntity(FileUtils.readFileToByteArray(file), headers, HttpStatus.CREATED);
     }
     return respinseEntity; }
 
   @RequestMapping({"getFile.do"})
   public ResponseEntity<byte[]> getFile(@RequestParam(value="code", defaultValue="") String code) throws IOException
   {
     ResponseEntity respinseEntity = null;
     UpdateFile updateFile = this.downLoadService.getUpdateFile(code);
     if (updateFile != null) {
       String path = updateFile.getSavePath();
       File file = new File(path);
       HttpHeaders headers = new HttpHeaders();
       String fileName = updateFile.getFileName();
       headers.setContentDispositionFormData("attachment", new String(fileName.getBytes("utf-8"), "ISO8859-1"));
       headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
       respinseEntity = new ResponseEntity(FileUtils.readFileToByteArray(file), headers, HttpStatus.CREATED);
     }
     return respinseEntity;
   }
 
   @RequestMapping({"getFileName.do"})
   public ResponseEntity<byte[]> getFileByName(@RequestParam(value="name", defaultValue="") String name) throws IOException {
     ResponseEntity responseEntity = null;
     UpdateFile updateFile = this.downLoadService.getUpdateFileName(name);
     if (updateFile != null) {
       String path = updateFile.getSavePath();
       File file = new File(path);
       HttpHeaders headers = new HttpHeaders();
       String fileName = updateFile.getFileName();
       System.out.println("---->  " + fileName);
       headers.setContentDispositionFormData("attachment", new String(fileName.getBytes("utf-8"), "ISO8859-1"));
       headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
       responseEntity = new ResponseEntity(FileUtils.readFileToByteArray(file), headers, HttpStatus.CREATED);
     }
     return responseEntity;
   }
 }

