package com.sq.sso.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jsscom.ces.model.Organization;
import com.jsscom.ces.model.UserOrganizationRelation;
import com.jsscom.ces.service.OrganizationService;
import com.jsscom.ces.service.UserOrganizationRelationService;
import com.sq.core.vo.OptionModel;
import com.sq.core.vo.ResultModel;
import com.sq.core.web.Action;
import com.sq.sso.model.Role;
import com.sq.sso.model.User;
import com.sq.sso.model.UserRoleRelation;
import com.sq.sso.service.RoleService;
import com.sq.sso.service.SsoService;
import com.sq.sso.service.UserRoleRelationService;
import com.sq.sso.service.UserService;
import com.sq.sso.web.filter.AppFilter;

@Controller
@RequestMapping({ "/popedom" })
public class UserInfoController extends Action<User> {

	@Resource
	private UserService userService;

	@Resource
	private RoleService roleServiceImpl;

	@Resource
	private UserRoleRelationService userRoleRelationServiceImpl;

	@Resource
	private OrganizationService organizationServiceImpl;

	@Resource
	private UserOrganizationRelationService userOrganizationRelationServiceImpl;

	@Resource
	private SsoService ssoService;
	protected final Log log = LogFactory.getLog(AppFilter.class);

	@RequestMapping({ "/userInfo.do" })
	public String userInfo() {
		return "/popedom/userInfo";
	}

	@RequestMapping({ "/getSexList.do" })
	@ResponseBody
	public List<OptionModel> getSexList() {
		List list = new ArrayList(2);
		OptionModel pm = new OptionModel();
		OptionModel pm1 = new OptionModel();
		pm1.setText("请选择");
		pm1.setValue("");
		pm.setText("男");
		pm.setValue("0");
		pm1.setText("女");
		pm1.setValue("1");
		OptionModel pm2 = new OptionModel();
		pm2.setText("请选择");
		pm2.setValue("");
		list.add(pm);
		list.add(pm1);
		list.add(pm2);
		return list;
	}

	@RequestMapping({ "/validatorLoginName.do" })
	@ResponseBody
	public ResultModel validatorLoginName(String loginName) {
		ResultModel rm = new ResultModel();
		boolean bol = this.userService.queryUserByLoginName(loginName);
		if (!bol) {
			this.log.info("该登录名不存在...");
			rm.setSuccess(0);
		} else {
			this.log.info("该登录名已存在...");
			rm.setSuccess(1);
		}

		return rm;
	}

	@RequestMapping({ "/addUser.do" })
	@ResponseBody
	public ResultModel addUser(@ModelAttribute("user") User user) {
		ResultModel rm = new ResultModel();

		int result = this.userService.registerUser(user);
		if (result > 0) {
			rm.setSuccess(0);
			this.log.info("添加用户成功!");
		} else {
			rm.setSuccess(1);
			this.log.info("添加用户失败!");
		}

		return rm;
	}

	@RequestMapping({ "/deleUserById.do" })
	@ResponseBody
	public ResultModel deleUserById(String id) {
		ResultModel rm = new ResultModel();
		String[] arr = id.split(",");
		UserRoleRelation urr = new UserRoleRelation();
		User user = new User();
		for (int i = 0; i < arr.length; i++) {
			user.setId(Integer.parseInt(arr[i]));
			urr.setUserId(user.getId());
			this.userRoleRelationServiceImpl.delete(urr);
			int rs = this.userService.deleUserById(user);
			if (rs > 0) {
				rm.setSuccess(0);
				this.log.info("删除用户成功!");
			} else {
				rm.setSuccess(1);
				this.log.info("删除用户失败!");
			}
		}

		return rm;
	}

	@RequestMapping({ "/modifyUserInfo.do" })
	@ResponseBody
	public ResultModel modifyUserInfo(User user) {
		ResultModel rm = new ResultModel();
		int rs = this.userService.modifyUserInfo(user);
		if (rs > 0) {
			rm.setSuccess(0);
			this.log.info("修改用户成功!");
		} else {
			rm.setSuccess(1);
			this.log.info("修改用户失败!");
		}

		return rm;
	}

	@RequestMapping({ "/modifyValidStatusByUserId.do" })
	@ResponseBody
	public ResultModel modifyValidStatusByUserId(String ids, String validStatus) {
		ResultModel rm = new ResultModel();
		int rs = 0;

		String[] userIds = ids.split(",");
		for (int i = 0; i < userIds.length; i++) {
			User user = new User();
			user.setValidStatus(validStatus);
			user.setId(Integer.parseInt(userIds[i]));
			rs = this.userService.modifyUserInfo(user);
		}

		if (rs > 0) {
			rm.setSuccess(0);
			this.log.info("修改用户成功!");
		} else {
			rm.setSuccess(1);
			this.log.info("修改用户失败!");
		}

		return rm;
	}

	@RequestMapping({ "queryAllRole.do" })
	@ResponseBody
	public List<Role> queryAllRole(User user) {
		return this.roleServiceImpl.queryAllRole();
	}

	@RequestMapping({ "queryRoleByUserId.do" })
	@ResponseBody
	public String queryAllRoleByUser(UserRoleRelation urr) {
		List roleList = this.roleServiceImpl
				.queryAllRoleByUser(urr.getUserId());

		String roleIds = "";
		for (int i = 0; i < roleList.size(); i++) {
			roleIds = roleIds + ((Role) roleList.get(i)).getId() + ",";
		}
		if (roleList.size() > 0) {
			roleIds = roleIds.substring(0, roleIds.length() - 1);
		}

		return roleIds;
	}

	@RequestMapping({ "queryOrgByUserId.do" })
	@ResponseBody
	public String queryAllOrgByUser(UserOrganizationRelation uor) {
		String orgIds = "";
		List orgList = this.organizationServiceImpl
				.queryAllUserByOrganization(uor.getUserId());
		for (int i = 0; i < orgList.size(); i++) {
			orgIds = orgIds + ((Organization) orgList.get(i)).getId() + ",";
		}

		if (orgList.size() > 0) {
			orgIds = orgIds.substring(0, orgIds.length() - 1);
		}

		return orgIds;
	}

	@RequestMapping({ "/queryAllOrgJson.do" })
	@ResponseBody
	public List<Organization> queryAllOrganization(HttpServletRequest request) {
		return this.organizationServiceImpl.queryOrganizationInfo();
	}

	@RequestMapping({ "/addUserRoleRelation.do" })
	@ResponseBody
	public ResultModel addAllPermission(UserRoleRelation urr) {
		ResultModel rm = new ResultModel();
		String roleIds = urr.getRoleId();
		List equalList = new ArrayList();
		Map<String, String> map = new HashMap();

		List uList = this.userRoleRelationServiceImpl.query(urr);

		if (!StringUtils.isNotEmpty(roleIds)) {
			if (uList.size() > 0) {
				this.userRoleRelationServiceImpl.delete(urr);
			}
			rm.setSuccess(0);
			return rm;
		}

		String[] roleIdsLength = roleIds.split(",");
		for (int i = 0; i < roleIdsLength.length; i++) {
			map.put(roleIdsLength[i], roleIdsLength[i]);
		}

		for (int i = 0; i < uList.size(); i++)
			for (String key : map.keySet())
				if (((UserRoleRelation) uList.get(i)).getRoleId().equals(key))
					equalList.add(key);
		int j;
		for (int i = 0; i < equalList.size(); i++) {
			map.remove(equalList.get(i));

			for (j = 0; j < uList.size(); j++) {
				if (((String) equalList.get(i))
						.equals(((UserRoleRelation) uList.get(j)).getRoleId())) {
					uList.remove(j);
				}
			}
		}

		this.log.info("equalList:" + equalList);
		this.log.info("addMap:" + map);
		this.log.info("delUlist:" + uList);

		for (int i = 0; i < uList.size(); i++) {
			urr.setRoleId(((UserRoleRelation) uList.get(i)).getRoleId());
			this.userRoleRelationServiceImpl.delete(urr);
		}

		for (String key : map.keySet()) {
			urr.setRoleId(key);
			this.userRoleRelationServiceImpl.insert(urr);
		}
		rm.setSuccess(0);
		return rm;
	}

	@RequestMapping({ "/addUserOrgRelation" })
	@ResponseBody
	public ResultModel addAllUserOrganization(UserOrganizationRelation uor) {
		ResultModel rm = new ResultModel();
		String orgIds = uor.getOrgId();
		List equalList = new ArrayList();
		Map<String, String> map = new HashMap();

		this.log.debug("orgId----> " + uor.getOrgId());

		List uorList = this.userOrganizationRelationServiceImpl.query(uor);

		if (!StringUtils.isNotEmpty(orgIds)) {
			if (uorList.size() > 0) {
				this.userOrganizationRelationServiceImpl
						.deleteByUserOrganization(uor);
			}
			rm.setSuccess(0);
			User user = new User();
			user.setOrgCode("");
			user.setId(uor.getUserId());
			this.userService.update(user);
			return rm;
		}

		String[] orgIdsLength = orgIds.split(",");

		Organization organ = this.organizationServiceImpl.findById(Integer
				.parseInt(orgIdsLength[(orgIdsLength.length - 1)]));
		this.log.debug("orgCode: " + organ.getCode());
		User user = new User();
		user.setOrgCode(organ.getCode());
		user.setId(uor.getUserId());

		this.userService.update(user);

		for (int i = 0; i < orgIdsLength.length; i++) {
			map.put(orgIdsLength[i], orgIdsLength[i]);
		}

		for (int i = 0; i < uorList.size(); i++)
			for (String key : map.keySet())
				if (((UserOrganizationRelation) uorList.get(i)).getOrgId()
						.equals(key))
					equalList.add(key);
		int j;
		for (int i = 0; i < equalList.size(); i++) {
			map.remove(equalList.get(i));

			for (j = 0; j < uorList.size(); j++) {
				if (((String) equalList.get(i))
						.equals(((UserOrganizationRelation) uorList.get(j))
								.getOrgId())) {
					uorList.remove(j);
				}
			}
		}

		this.log.info("equalList:" + equalList);
		this.log.info("addMap:" + map);
		this.log.info("delUlist:" + uorList);

		for (int i = 0; i < uorList.size(); i++) {
			uor.setOrgId(((UserOrganizationRelation) uorList.get(i)).getOrgId());
			this.userOrganizationRelationServiceImpl
					.deleteByUserOrganization(uor);
		}

		for (String key : map.keySet()) {
			uor.setOrgId(key);
			this.userOrganizationRelationServiceImpl.create(uor);
		}
		rm.setSuccess(0);
		return rm;
	}
}
