 package com.jsscom.ces.data.dao.impl;
 
 import com.jsscom.ces.data.dao.DevInfoDao;
 import com.jsscom.ces.model.DevInfo;
 import com.sq.core.dao.impl.DaoImpl;
 import java.util.Map;
 import org.mybatis.spring.SqlSessionTemplate;
 import org.springframework.stereotype.Repository;
 
 @Repository
 public class DevInfoDaoImpl extends DaoImpl<DevInfo>
   implements DevInfoDao
 {
   public int findDevCode(String code)
   {
     return ((Integer)getSqlSession().selectOne(getClsName() + ".queryDevByCode", code)).intValue();
   }
 
   public int validateUnique(Map<String, Object> map) {
     int num = 0;
     Integer rs = (Integer)getSqlSession().selectOne("validateUnique", map);
     if ((rs != null) && (rs.intValue() > 0)) {
       num = 1;
     }
     return num;
   }
 }

