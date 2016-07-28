 package com.jsscom.ces.data.dao.impl;
 
 import com.jsscom.ces.data.dao.EvaluateDao;
 import com.jsscom.ces.model.Evaluate;
 import com.sq.core.dao.impl.DaoImpl;
 import java.util.List;
 import java.util.Map;
 import org.mybatis.spring.SqlSessionTemplate;
 import org.springframework.stereotype.Repository;
 
 @Repository
 public class EvaluateDaoImpl extends DaoImpl<Evaluate>
   implements EvaluateDao
 {
   public int count(Map<String, Object> map)
   {
     return ((Integer)getSqlSession().selectOne("Evaluate.count", map)).intValue();
   }
 
   public List<Evaluate> queryPage(Map<String, Object> map, int start, int limit)
   {
     map.put("start", Integer.valueOf(start));
     map.put("limit", Integer.valueOf(limit));
     return getSqlSession().selectList("Evaluate.queryForPage", map);
   }
 }

