 package com.jsscom.ces.service.impl;
 
 import com.jsscom.ces.data.dao.DevInfoDao;
 import com.jsscom.ces.model.DevInfo;
 import com.jsscom.ces.service.DevInfoService;
 import com.sq.core.service.impl.ServiceImpl;
 import java.util.List;
 import javax.annotation.Resource;
 import org.apache.commons.logging.Log;
 import org.apache.commons.logging.LogFactory;
 import org.springframework.stereotype.Service;
 
 @Service
 public class DevInfoServiceImpl extends ServiceImpl<DevInfo>
   implements DevInfoService
 {
   protected final Log log = LogFactory.getLog(DevInfoServiceImpl.class);
 
   @Resource
   private DevInfoDao devInfoDao;
 
   public int queryDevCode(String code) {
     return this.devInfoDao.findDevCode(code);
   }
 
   public List<DevInfo> queryByClient(DevInfo devInfo)
   {
     return this.devInfoDao.query("queryByClient", devInfo);
   }
 
   public int create(DevInfo devInfo) {
     int rs = 0;
     try {
       this.devInfoDao.insert("save", devInfo);
       this.log.debug("id  :  " + devInfo.getId());
       String code = String.format("C%05d", new Object[] { Integer.valueOf(devInfo.getId()) });
       DevInfo d = new DevInfo();
       d.setCode(code);
       d.setId(devInfo.getId());
       rs = this.devInfoDao.update("updateBySaveId", d);
     } catch (RuntimeException e) {
       e.printStackTrace();
     }
     return rs;
   }
 }

