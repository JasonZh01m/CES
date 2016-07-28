 package com.sq.core.web;
 
 import com.sq.core.utils.Constant;
 import org.springframework.stereotype.Controller;
 import org.springframework.web.bind.annotation.PathVariable;
 import org.springframework.web.bind.annotation.RequestMapping;
 
 @Controller
 @RequestMapping({"/"})
 public class CommonController
 {
   @RequestMapping({"/{dir}/{path}/{page}Page.do"})
   public String commonPage(@PathVariable("dir") String dir, @PathVariable("path") String path, @PathVariable("page") String page)
   {
     return Constant.PathFlag + dir + Constant.PathFlag + path + Constant.PathFlag + page;
   }
 
   @RequestMapping({"/{path}/{page}Page.do"})
   public String commonPage(@PathVariable("path") String path, @PathVariable("page") String page)
   {
     return Constant.PathFlag + path + Constant.PathFlag + page;
   }
 
   @RequestMapping({"/{Page}Page.do"})
   public String Page(@PathVariable("Page") String page)
   {
     return Constant.PathFlag + page;
   }
 }

