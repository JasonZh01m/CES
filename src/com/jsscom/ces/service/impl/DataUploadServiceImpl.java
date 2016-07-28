 package com.jsscom.ces.service.impl;
 
 import com.jsscom.ces.data.dao.DevInfoDao;
 import com.jsscom.ces.model.DevInfo;
 import com.jsscom.ces.model.Employee;
 import com.jsscom.ces.service.DataUploadService;
 import java.io.PrintStream;
 import java.util.HashMap;
 import java.util.Map;
 import javax.annotation.Resource;
 import org.joda.time.DateTime;
 import org.springframework.stereotype.Service;
 
 @Service
 public class DataUploadServiceImpl
   implements DataUploadService
 {
 
   @Resource
   private DevInfoDao devInfoDao;
 
   public int registerDevInfo(String ip, DevInfo devInfo)
   {
     int rs = 1;
     Map map = new HashMap(8);
     map.put("field", "ip");
     map.put("value", devInfo.getIp());
     if (this.devInfoDao.validateUnique(map) > 0) {
       rs = 2;
     }
     map.put("field", "dev_sn");
     map.put("value", devInfo.getSerialNo());
     if (this.devInfoDao.validateUnique(map) > 0) {
       rs = 3;
     }
     if (rs == 1) {
       DateTime dateTime = new DateTime();
       devInfo.setCreateTime(dateTime.toString("yyyy-MM-dd HH:mm:ss"));
       rs = this.devInfoDao.insert("save", devInfo);
     }
     return rs;
   }
 
   public static void main(String[] args) {
     DateTime dateTime = new DateTime();
     System.out.println(dateTime.toString("yyyy-MM-dd HH:mm:ss"));
   }
 
   public int registerEmployee(Employee employee)
   {
     return 0;
   }
 
   public int updateEvaluate(Employee employee)
   {
     return 0;
   }
 }

