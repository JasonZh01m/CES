package com.jsscom.ces.service;

import com.jsscom.ces.model.OrganChat;
import com.jsscom.ces.vo.ChatParam;
import com.jsscom.ces.vo.MonthLineChartBean;
import com.jsscom.ces.vo.ReportSearchModel;
import com.sq.core.service.Service;
import java.util.List;

public abstract interface OrganChatService extends Service<OrganChat>
{
  public abstract List<OrganChat> queryPage(ReportSearchModel paramReportSearchModel);

  public abstract MonthLineChartBean queryForOrgChat(ChatParam paramChatParam);
}

