 package com.jsscom.ces.data.dao.impl;
 
 import com.jsscom.ces.data.dao.BusinessDao;
 import com.jsscom.ces.model.Business;
 import com.sq.core.dao.impl.DaoImpl;
 import java.util.List;
 import org.mybatis.spring.SqlSessionTemplate;
 import org.springframework.stereotype.Repository;
 
 @Repository
 public class BusinessDaoImpl extends DaoImpl<Business>
   implements BusinessDao
 {
   public List<Business> queryBusinessInfo()
   {
     return getSqlSession().selectList(super.getClsName() + ".queryBusiness");
   }
 
   public List<Business> queryBusiness(String name)
   {
     return getSqlSession().selectList(super.getClsName() + ".query", name);
   }
 
   public int findBusinessByCode(String code)
   {
     return ((Integer)getSqlSession().selectOne(getClsName() + ".queryBusinessByCode", code)).intValue();
   }
 }

