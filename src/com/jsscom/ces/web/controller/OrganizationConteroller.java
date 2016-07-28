package com.jsscom.ces.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jsscom.ces.model.Organization;
import com.jsscom.ces.model.UserOrganizationRelation;
import com.jsscom.ces.service.OrganizationService;
import com.jsscom.ces.service.UserOrganizationRelationService;
import com.sq.core.vo.OptionModel;
import com.sq.core.vo.ResultModel;
import com.sq.core.vo.ZtreeBean;
import com.sq.core.web.Action;
import com.sq.sso.model.User;
import com.sq.sso.service.SsoService;
import com.sq.sso.service.UserService;
import com.sq.sso.vo.SSOSession;

@Controller
@RequestMapping({ "/organization" })
public class OrganizationConteroller extends Action<Organization> {
	protected final Log log = LogFactory.getLog(OrganizationConteroller.class);

	@Resource
	private OrganizationService organizationService;

	@Resource
	private SsoService ssoService;

	@Resource
	private UserOrganizationRelationService userOrganizationRelationService;

	@Resource
	private UserService userService;
	private String orgId;

	@RequestMapping({ "/validatorOrgInfo.do" })
	@ResponseBody
	public ResultModel validatorOrgCode(Organization org) {
		ResultModel rm = new ResultModel();
		boolean bol = this.organizationService.queryOrgByInfo(org);
		if (!bol) {
			this.log.info("该机构不存在...");
			rm.setSuccess(0);
		} else {
			this.log.info("该机构已存在...");
			rm.setSuccess(1);
		}

		return rm;
	}

	@RequestMapping({ "/getOrganizationTreeJson.do" })
	@ResponseBody
	public List<ZtreeBean> getOrganizationTree(HttpServletRequest request,
			HttpServletResponse response, String pId) {
		if (pId == null) {
			pId = "0";
		}
		SSOSession sSOSession = this.ssoService.getSsoSession(request);
		this.orgId = "";
		Organization organ = new Organization();
		String orgId = getOrgIds(sSOSession.getUserId(), sSOSession.getRids());
		organ.setId(Integer.parseInt(pId));
		organ.setOrgId(orgId);
		List<Organization> listOrganizations = this.organizationService
				.queryAllOrganizationByMap(organ);

		List results = new ArrayList();
		for (Organization org : listOrganizations) {
			ZtreeBean zb = new ZtreeBean();
			zb.setId(org.getId());
			zb.setPId(org.getParentId());
			zb.setName(org.getName());
			zb.setCode(org.getCode());
			org.setOrgId(orgId);
			System.out.println("hasChilders:   "
					+ this.organizationService.hasChilders(org));
			if (this.organizationService.hasChilders(org))
				zb.setIsParent("true");
			else {
				zb.setIcon("../UI/css/zTree/img/diy/leaf.gif");
			}
			results.add(zb);
		}
		return results;
	}

	@RequestMapping({ "/deleteOrganizationJson.do" })
	@ResponseBody
	public Map<String, Integer> deleteOrganization(int organizationId) {
		Map result = new HashMap();
		try {
			this.organizationService.deleteOrganizationByPid(organizationId);
			result.put("success", Integer.valueOf(0));
		} catch (Exception e) {
			e.printStackTrace();
			result.put("success", Integer.valueOf(1));
		}
		return result;
	}

	@RequestMapping({ "/optionOrgListJson.do" })
	@ResponseBody
	public List<OptionModel> optionOrgList(HttpServletRequest request) {
		List optionModels = new ArrayList();
		SSOSession sSOSession = this.ssoService.getSsoSession(request);
		List<Organization> organizations = new ArrayList();
		Organization organ = new Organization();
		this.orgId = "";

		String orgId = getOrgIds(sSOSession.getUserId(), sSOSession.getRids());

		this.log.debug("登陆用户被分配的机构有: " + orgId);
		organ.setOrgId(orgId);
		try {
			organizations = this.organizationService
					.queryOrganizationInfo(organ);
			for (Organization organization : organizations) {
				OptionModel optionModel = new OptionModel();
				optionModel.setText(organization.getName());
				optionModel.setValue(organization.getCode());
				optionModels.add(optionModel);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return optionModels;
	}

	@RequestMapping({ "/getAllOrganiztionTree.do" })
	@ResponseBody
	public List<ZtreeBean> getAllOrganiztionTree(HttpServletRequest request,
			HttpServletResponse response, Organization organ, String pId) {
		List<ZtreeBean> results = new ArrayList();
		if (pId == null) {
			pId = "0";
		}
		SSOSession sSOSession = this.ssoService.getSsoSession(request);
		if ((sSOSession != null)
				&& ((sSOSession.getOrgCode() == null) || (sSOSession
						.getOrgCode().equals("")))) {
			this.log.debug("没有分配机构！");
			return results;
		}
		this.orgId = "";
		String orgId = getOrgIds(sSOSession.getUserId(), sSOSession.getRids());

		this.log.debug("登陆用户被分配的机构有: " + orgId);
		organ.setOrgId(orgId);
		organ.setId(Integer.parseInt(pId));

		results = this.organizationService.queryAllOrganiztionByUser(organ);

		return results;
	}

	@RequestMapping({ "/queryAllUserByOrgJson.do" })
	@ResponseBody
	public String queryAllUserByOrg(int orgId) {
		List list = this.organizationService.queryAllUserByOrg(orgId);
		String userIds = "";
		this.log.debug(Integer.valueOf(list.size()));
		for (int i = 0; i < list.size(); i++) {
			userIds = userIds
					+ ((UserOrganizationRelation) list.get(i)).getUserId()
					+ ",";
		}
		if (list.size() > 0) {
			userIds = userIds.substring(0, userIds.length() - 1);
		}
		return userIds;
	}

	@RequestMapping({ "/addOrgRelationUser" })
	@ResponseBody
	public ResultModel addAllOrganizationUser(UserOrganizationRelation uor) {
		ResultModel rm = new ResultModel();
		String userIds = uor.getUserIds();
		List equalList = new ArrayList();
		Map map = new HashMap();

		this.log.debug("userIds----> " + uor.getUserIds());

		List uorList = this.userOrganizationRelationService.queryByOrgan(uor);
		String[] userIdsLength = userIds.split(",");

		if (!StringUtils.isNotEmpty(userIds)) {
			if (uorList.size() > 0) {
				this.userOrganizationRelationService
						.deleteByUserOrganization(uor);
			}
			rm.setSuccess(0);
			for (int i = 0; i < userIdsLength.length; i++) {
				User user = new User();
				user.setOrgCode("");
				user.setId(Integer.parseInt(userIdsLength[i]));
				this.userService.update(user);
			}

			return rm;
		}

		for (int i = 0; i < userIdsLength.length; i++) {
			User user = new User();
			user.setOrgCode(uor.getCode());
			System.out.println("--->" + userIdsLength[i]);
			user.setId(Integer.parseInt(userIdsLength[i]));
			this.userService.update(user);
		}

		for (int i = 0; i < userIdsLength.length; i++) {
			map.put(Integer.valueOf(Integer.parseInt(userIdsLength[i])),
					Integer.valueOf(Integer.parseInt(userIdsLength[i])));
		}

		for (int i = 0; i < uorList.size(); i++) {
			for (Iterator localIterator = map.keySet().iterator(); localIterator
					.hasNext();) {
				int key = ((Integer) localIterator.next()).intValue();
				if (((UserOrganizationRelation) uorList.get(i)).getUserId() == key) {
					equalList.add(Integer.valueOf(key));
				}
			}

		}

		for (int i = 0; i < equalList.size(); i++) {
			map.remove(equalList.get(i));

			for (int j = 0; j < uorList.size(); j++) {
				if (((Integer) equalList.get(i)).equals(Integer
						.valueOf(((UserOrganizationRelation) uorList.get(j))
								.getUserId()))) {
					uorList.remove(j);
				}
			}
		}

		this.log.info("equalList:" + equalList);
		this.log.info("addMap:" + map);
		this.log.info("delUlist:" + uorList);

		for (int i = 0; i < uorList.size(); i++) {
			uor.setUserId(((UserOrganizationRelation) uorList.get(i))
					.getUserId());
			this.userOrganizationRelationService.deleteByUserOrganization(uor);
		}

		for (int j = map.keySet().iterator(); j.hasNext();) {
			int key = ((Integer) j.next()).intValue();
			uor.setUserId(key);
			this.userOrganizationRelationService.create(uor);
		}
		rm.setSuccess(0);
		return rm;
	}

	private String getOrgIds(String userId, String rids) {
		String orgId = "";
		UserOrganizationRelation uor = new UserOrganizationRelation();
		if (!rids.equals("")) {
			uor.setUserId(Integer.parseInt(userId));
			List<UserOrganizationRelation> list = this.userOrganizationRelationService
					.query(uor);

			if (list.size() > 0) {
				for (UserOrganizationRelation userOrganizationRelation : list) {
					orgId = orgId + userOrganizationRelation.getOrgId() + ",";
				}
				orgId = orgId.substring(0, orgId.length() - 1);
			}
		} else {
			return orgId;
		}
		return orgId;
	}

	public static void main(String[] args) {
		System.out.println(Integer.parseInt("0"));
	}
}
