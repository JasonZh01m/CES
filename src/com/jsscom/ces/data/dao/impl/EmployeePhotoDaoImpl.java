 package com.jsscom.ces.data.dao.impl;
 
 import com.jsscom.ces.data.dao.EmployeePhotoDao;
 import com.jsscom.ces.model.EmployeePhoto;
 import com.sq.core.dao.impl.DaoImpl;
 import java.util.List;
 import org.mybatis.spring.SqlSessionTemplate;
 import org.springframework.stereotype.Repository;
 
 @Repository
 public class EmployeePhotoDaoImpl extends DaoImpl<EmployeePhoto>
   implements EmployeePhotoDao
 {
   public List<EmployeePhoto> findFile(String md5)
   {
     return getSqlSession().selectList("EmployeePhoto.queryPic", md5);
   }
 
   public List<EmployeePhoto> getEmployee(String employeeNumber) {
     return getSqlSession().selectList("EmployeePhoto.getEmployee", employeeNumber);
   }
 }

