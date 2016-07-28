 package com.jsscom.ces.data.dao.impl;
 
 import com.jsscom.ces.data.dao.SatisfactionDao;
 import com.jsscom.ces.model.Satisfaction;
 import com.sq.core.dao.impl.DaoImpl;
 import java.util.List;
 import java.util.Map;
 import org.mybatis.spring.SqlSessionTemplate;
 import org.springframework.stereotype.Repository;
 
 @Repository
 public class SatisfactionDaoImpl extends DaoImpl<Satisfaction>
   implements SatisfactionDao
 {
   public int count(String sqlId, Map<String, Object> map)
   {
     return ((Integer)getSqlSession().selectOne("Satisfaction." + sqlId, map)).intValue();
   }
 
   public List<Satisfaction> queryPage(String sqlId, Map<String, Object> map, int start, int limit)
   {
     map.put("start", Integer.valueOf(start));
     map.put("limit", Integer.valueOf(limit));
     return getSqlSession().selectList("Satisfaction." + sqlId, map);
   }
 }

