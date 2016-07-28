 package com.jsscom.ces.data.dao.impl;
 
 import com.jsscom.ces.data.dao.UpdateFileDao;
 import com.jsscom.ces.model.UpdateFile;
 import com.sq.core.dao.impl.DaoImpl;
 import org.mybatis.spring.SqlSessionTemplate;
 import org.springframework.stereotype.Repository;
 
 @Repository
 public class UpdateFileDaoImpl extends DaoImpl<UpdateFile>
   implements UpdateFileDao
 {
   public UpdateFile queryByMD5(String md5Code)
   {
     return (UpdateFile)getSqlSession().selectOne("UpdateFile.queryByMD5".intern(), md5Code);
   }
 
   public UpdateFile queryByName(String name)
   {
     return (UpdateFile)getSqlSession().selectOne("UpdateFile.queryByName".intern(), name);
   }
 }

