package com.jsscom.ces.web.controller;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jsscom.ces.model.DevInfo;
import com.jsscom.ces.model.UserOrganizationRelation;
import com.jsscom.ces.service.DevInfoService;
import com.jsscom.ces.service.UserOrganizationRelationService;
import com.sq.core.utils.DateUtil;
import com.sq.core.vo.PageParamModel;
import com.sq.core.vo.PaginQueryResult;
import com.sq.core.vo.ResultModel;
import com.sq.core.web.Action;

@Controller
@RequestMapping({ "/devInfo" })
public class DevInfoController extends Action<DevInfo> {
	protected final Log log = LogFactory.getLog(DevInfoController.class);

	@Resource
	private DevInfoService devInfoService;
	private String orgId;

	@Resource
	private UserOrganizationRelationService userOrganizationRelationService;

	@Override
	@RequestMapping({ "/PageJson.do" })
	@ResponseBody
	public PaginQueryResult<DevInfo> getPage(
			@ModelAttribute("ppm") PageParamModel<DevInfo> ppm,
			@ModelAttribute("devInfo") DevInfo devInfo) {
		PaginQueryResult pqr = null;
		System.out.println("ppm: " + ppm);
		System.out.println("T: " + devInfo.toString());
		String userId = devInfo.getUserId();
		this.orgId = "";
		String orgId = getOrgId(userId, devInfo.getRids());
		this.log.debug("登陆用户被分配的机构有: " + orgId);
		devInfo.setOrgId(orgId);
		pqr = this.devInfoService.paginQuery(devInfo, ppm.getPage(),
				ppm.getRows());
		return pqr;
	}

	private String getOrgId(String userId, String rids) {
		UserOrganizationRelation uor = new UserOrganizationRelation();
		if (!rids.equals("")) {
			uor.setUserId(Integer.parseInt(userId));
			List<UserOrganizationRelation> list = this.userOrganizationRelationService
					.query(uor);

			if (list != null)
				for (UserOrganizationRelation userOrganizationRelation : list)
					this.orgId = (this.orgId
							+ userOrganizationRelation.getOrgId() + ",");
		} else {
			this.log.debug("admin用户登录!");
			return "";
		}
		return this.orgId.substring(0, this.orgId.length() - 1);
	}

	@RequestMapping({ "/addDevInfoJson.do" })
	@ResponseBody
	public ResultModel add(@ModelAttribute("devInfo") DevInfo devInfo) {
		ResultModel rm = new ResultModel();
		devInfo.setAuditStatus(1);
		devInfo.setCreateTime(DateUtil.getDateTime());
		int rs = this.devInfoService.create(devInfo);
		if (rs > 0) {
			rm.setSuccess(0);
			this.log.info("终端添加成功！");
		} else {
			rm.setSuccess(1);
			this.log.info("终端添加失败！");
		}
		return rm;
	}

	@Override
	@RequestMapping({ "/auditDevJson.do" })
	@ResponseBody
	public ResultModel modify(@ModelAttribute("devInfo") DevInfo devInfo) {
		ResultModel rm = new ResultModel();
		System.out.println("DevInfo : " + devInfo.toString());
		DevInfo d = this.devInfoService.findById(devInfo.getId());
		if (devInfo.getAuditStatus() == 2) {
			devInfo.setAuditUserId(d.getAuditUserId());
			devInfo.setAuditUserName(d.getAuditUserName());
			devInfo.setInstallTime(d.getInstallTime());
		} else if (devInfo.getAuditStatus() == 0) {
			devInfo.setInstallTime(DateUtil.getDateTime());
		}

		int rs = this.devInfoService.update(devInfo);
		if (rs > 0) {
			rm.setSuccess(0);
			this.log.info("审核/禁用/启用成功！");
		} else {
			rm.setSuccess(1);
			this.log.info("审核/禁用/启用失败！");
		}
		return rm;
	}

	@RequestMapping({ "/validatorDevCodeJson.do" })
	@ResponseBody
	public ResultModel validatorDevCode(String code) {
		ResultModel rm = new ResultModel();
		System.out.println("code : " + code);
		int rs = this.devInfoService.queryDevCode(code);
		if (rs > 0) {
			rm.setSuccess(1);
			this.log.info("该终端已存在... ");
		} else {
			rm.setSuccess(0);
			this.log.info("该终端不存在... ");
		}
		return rm;
	}

	@RequestMapping({ "/deleDevByIdJson.do" })
	@ResponseBody
	public ResultModel deleDev(String ids) {
		System.out.println("ids : " + ids);
		int rs = 0;
		ResultModel rm = new ResultModel();
		String[] id = ids.split(",");
		for (int i = 0; i < id.length; i++) {
			rs = this.devInfoService.delete(id[i]);
		}
		if (rs > 0) {
			rm.setSuccess(0);
			this.log.info("删除终端成功！");
		} else {
			rm.setSuccess(1);
			this.log.info("删除终端失败！");
		}
		return rm;
	}

	@RequestMapping({ "/modifyJson.do" })
	@ResponseBody
	public ResultModel modifyDev(@ModelAttribute("devInfo") DevInfo devInfo) {
		ResultModel rm = new ResultModel();
		int rs = this.devInfoService.update(devInfo);
		if (rs > 0)
			rm.setSuccess(0);
		else {
			rm.setSuccess(1);
		}
		return rm;
	}

	public static void main(String[] args) {
		String str = "1";
		str.split(",");
	}
}
