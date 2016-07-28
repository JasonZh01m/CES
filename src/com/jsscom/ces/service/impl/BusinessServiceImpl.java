 package com.jsscom.ces.service.impl;
 
 import com.jsscom.ces.data.dao.BusinessDao;
 import com.jsscom.ces.model.Business;
 import com.jsscom.ces.service.BusinessService;
 import com.sq.core.service.impl.ServiceImpl;
 import java.util.List;
 import javax.annotation.Resource;
 import org.springframework.stereotype.Service;
 
 @Service
 public class BusinessServiceImpl extends ServiceImpl<Business>
   implements BusinessService
 {
 
   @Resource
   private BusinessDao businessDao;
 
   public List<Business> queryBusinessInfo()
   {
     return this.businessDao.queryBusinessInfo();
   }
 
   public List<Business> queryBusiness(String name)
   {
     return this.businessDao.queryBusiness(name);
   }
 
   public int queryDevCode(String code)
   {
     return this.businessDao.findBusinessByCode(code);
   }
 }

