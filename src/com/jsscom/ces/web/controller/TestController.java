 package com.jsscom.ces.web.controller;
 
 import com.jsscom.ces.model.CarInfoTemp;
 import org.springframework.stereotype.Controller;
 import org.springframework.web.bind.annotation.RequestMapping;
 import org.springframework.web.bind.annotation.ResponseBody;
 
 @Controller
 @RequestMapping({"/Test"})
 public class TestController
 {
   @RequestMapping({"/testJson.do"})
   @ResponseBody
   public CarInfoTemp get()
   {
     CarInfoTemp c = new CarInfoTemp();
     return c;
   }
 }

