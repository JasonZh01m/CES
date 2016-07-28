 package com.jsscom.ces.data.dao.impl;
 
 import com.jsscom.ces.data.dao.OrganChatDao;
 import com.jsscom.ces.model.OrganChat;
 import com.jsscom.ces.vo.MonthLineChartBean;
 import com.sq.core.dao.impl.DaoImpl;
 import java.util.HashMap;
 import java.util.List;
 import java.util.Map;
 import org.mybatis.spring.SqlSessionTemplate;
 import org.springframework.stereotype.Repository;
 
 @Repository
 public class OrganChatDaoImpl extends DaoImpl<OrganChat>
   implements OrganChatDao
 {
   public List<OrganChat> queryPage(String year, String month)
   {
     Map map = new HashMap();
     map.put("year", year);
     map.put("moth", month);
     return getSqlSession().selectList("OrganChat.queryForPage", map);
   }
 
   public MonthLineChartBean queryForOrgChat(Map<String, Object> map) {
     return (MonthLineChartBean)getSqlSession().selectOne("OrganChat.queryForOrgChat".intern(), map);
   }
 }

