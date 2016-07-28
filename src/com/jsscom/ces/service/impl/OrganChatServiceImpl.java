package com.jsscom.ces.service.impl;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.jsscom.ces.data.dao.OrganChatDao;
import com.jsscom.ces.model.OrganChat;
import com.jsscom.ces.service.OrganChatService;
import com.jsscom.ces.vo.ChatParam;
import com.jsscom.ces.vo.MonthLineChartBean;
import com.jsscom.ces.vo.ReportSearchModel;
import com.sq.core.service.impl.ServiceImpl;
import com.sq.core.utils.DateUtil;

@Service
public class OrganChatServiceImpl extends ServiceImpl<OrganChat> implements
		OrganChatService {
	protected final Log log = LogFactory.getLog(OrganChatServiceImpl.class);

	@Resource
	private OrganChatDao organChatDao;

	@Override
	public MonthLineChartBean queryForOrgChat(ChatParam chatParam) {
		Map map = new HashMap();
		map.put("year", chatParam.getYear());
		map.put("orgCode", chatParam.getOrgCode());
		map.put("serviceCode", chatParam.getServiceCode());
		MonthLineChartBean mcb = this.organChatDao.queryForOrgChat(map);
		if (mcb == null) {
			mcb = new MonthLineChartBean();
		}
		return mcb;
	}

	@Override
	public List<OrganChat> queryPage(ReportSearchModel rsm) {
		String chatDate = "";
		DateUtil du = new DateUtil();
		if ((rsm.getDate() == null) || (rsm.getDate() == "")) {
			du.calendarUtil("");
			chatDate = du.getYear();
		} else {
			du.calendarUtil(rsm.getDate());
			chatDate = du.getYear();
		}

		List<OrganChat> nlist = new ArrayList();
		List<OrganChat> list = this.organChatDao.queryPage(chatDate, "");
		DecimalFormat df = new DecimalFormat("0.00%");
		OrganChat organChat = null;
		for (OrganChat oc : list) {
			organChat = new OrganChat();
			organChat.setMonth1(df.format(Double.parseDouble(oc.getMonth1())));
			organChat.setMonth2(df.format(Double.parseDouble(oc.getMonth2())));
			organChat.setMonth3(df.format(Double.parseDouble(oc.getMonth3())));
			organChat.setMonth4(df.format(Double.parseDouble(oc.getMonth4())));
			organChat.setMonth5(df.format(Double.parseDouble(oc.getMonth5())));
			organChat.setMonth6(df.format(Double.parseDouble(oc.getMonth6())));
			organChat.setMonth7(df.format(Double.parseDouble(oc.getMonth7())));
			organChat.setMonth8(df.format(Double.parseDouble(oc.getMonth8())));
			organChat.setMonth9(df.format(Double.parseDouble(oc.getMonth9())));
			organChat
					.setMonth10(df.format(Double.parseDouble(oc.getMonth10())));
			organChat
					.setMonth11(df.format(Double.parseDouble(oc.getMonth11())));
			organChat
					.setMonth12(df.format(Double.parseDouble(oc.getMonth12())));
			organChat.setOrgName(oc.getOrgName());
			organChat.setOrgYear(chatDate);

			nlist.add(organChat);
		}

		return nlist;
	}

	public static void main(String[] args) {
		String source = "2013-09-30";
	}
}
