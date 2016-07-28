package com.jsscom.ces.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jsscom.ces.model.Business;
import com.jsscom.ces.service.BusinessService;
import com.sq.core.utils.DateUtil;
import com.sq.core.vo.OptionModel;
import com.sq.core.vo.ResultModel;
import com.sq.core.web.Action;

@Controller
@RequestMapping({ "/business" })
public class BusinessController extends Action<Business> {
	protected final Log log = LogFactory.getLog(BusinessController.class);

	@Resource
	private BusinessService businessService;

	@RequestMapping({ "/optionBusinessListJson.do" })
	@ResponseBody
	public List<OptionModel> optionBusinessList() {
		List<OptionModel> optionModels = new ArrayList();
		try {
			List<Business> businesss = this.businessService.queryBusinessInfo();
			for (Business business : businesss) {
				OptionModel optionModel = new OptionModel();
				optionModel.setText(business.getName());
				optionModel.setValue(business.getCode());
				optionModels.add(optionModel);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return optionModels;
	}

	@RequestMapping({ "/queryBusiness.do" })
	@ResponseBody
	public List<Business> queryBusiness(
			@RequestParam(value = "name", defaultValue = "") String name) {
		List list = this.businessService.queryBusiness(name);
		return list;
	}

	@RequestMapping({ "/validatorBusiness.do" })
	@ResponseBody
	public ResultModel chackBusinessByCode(String code) {
		ResultModel rm = new ResultModel();
		System.out.println("code : " + code);
		int rs = this.businessService.queryDevCode(code);
		if (rs > 0) {
			rm.setSuccess(1);
			this.log.info("该业务已存在... ");
		} else {
			rm.setSuccess(0);
			this.log.info("该业务不存在... ");
		}
		return rm;
	}

	@RequestMapping({ "/addBusinessJson.do" })
	@ResponseBody
	public ResultModel addBusiness(@ModelAttribute("business") Business business) {
		ResultModel rm = new ResultModel();
		business.setCreateTime(DateUtil.getDateTime());
		int rs = this.businessService.create(business);
		if (rs > 0) {
			this.log.debug("业务添加成功！");
			rm.setSuccess(0);
		} else {
			this.log.debug("业务添加失败！");
			rm.setSuccess(1);
		}
		return rm;
	}

	@RequestMapping({ "/deleBusinessByIdJson.do" })
	@ResponseBody
	public ResultModel deleBusiness(String ids) {
		System.out.println("ids : " + ids);
		int rs = 0;
		ResultModel rm = new ResultModel();
		String[] id = ids.split(",");
		for (int i = 0; i < id.length; i++) {
			rs = this.businessService.delete(id[i]);
		}
		if (rs > 0) {
			rm.setSuccess(0);
			this.log.info("删除业务成功！");
		} else {
			rm.setSuccess(1);
			this.log.info("删除业务失败！");
		}
		return rm;
	}
}
