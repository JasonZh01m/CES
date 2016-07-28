package com.jsscom.ces.data.dao;

import com.jsscom.ces.model.OrganChat;
import com.jsscom.ces.vo.MonthLineChartBean;
import com.sq.core.dao.Dao;
import java.util.List;
import java.util.Map;

public abstract interface OrganChatDao extends Dao<OrganChat>
{
  public abstract List<OrganChat> queryPage(String paramString1, String paramString2);

  public abstract MonthLineChartBean queryForOrgChat(Map<String, Object> paramMap);
}

