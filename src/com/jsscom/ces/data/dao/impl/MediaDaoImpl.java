 package com.jsscom.ces.data.dao.impl;
 
 import com.jsscom.ces.data.dao.MediaDao;
 import com.jsscom.ces.model.Media;
 import com.sq.core.dao.impl.DaoImpl;
 import org.mybatis.spring.SqlSessionTemplate;
 import org.springframework.stereotype.Repository;
 
 @Repository
 public class MediaDaoImpl extends DaoImpl<Media>
   implements MediaDao
 {
   public Media queryByMD5(String md5Code)
   {
     return (Media)getSqlSession().selectOne("Media.queryByMD5".intern(), md5Code);
   }
 }

