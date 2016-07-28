package com.sq.sso.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sq.sso.ComparatorPermission;
import com.sq.sso.model.Permission;
import com.sq.sso.service.PermissionService;
import com.sq.sso.service.SsoService;
import com.sq.sso.vo.SSOSession;

@Controller
@RequestMapping({ "/" })
public class SsoController {
	private final Log log = LogFactory.getLog(getClass());

	private final String adminStr = "admin";

	private final String ADMIN_ID = "1";
	private final List<Permission> NullPermissionList;
	private final ComparatorPermission cp;

	@Resource
	private SsoService ssoService;

	@Resource
	private PermissionService permissionService;

	public SsoController() {
		this.NullPermissionList = new ArrayList(0);
		this.cp = new ComparatorPermission();
	}

	@RequestMapping({ "/sso/editPassword.do" })
	public void editPassword(HttpServletResponse resp) {
		String url = this.ssoService.getSsoServer();
		try {
			resp.sendRedirect(url);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping({ "/sso/getAllMenuByRole.do" })
	@ResponseBody
	public List<Permission> getAllMenuByRole(HttpServletRequest req) {
		String rids = getRidsBySession(req);
		List list = this.permissionService.queryPermissionForMenu(rids);
		if (list == null)
			list = this.NullPermissionList;
		else {
			Collections.sort(list, this.cp);
		}
		return list;
	}

	@RequestMapping({ "/sso/logout.do" })
	@ResponseBody
	public String logout(HttpServletRequest request) {
		request.getSession().invalidate();
		this.log.info("剔除在线用户成功!");
		return "1";
	}

	@RequestMapping({ "/sso/getUser.do" })
	@ResponseBody
	public SSOSession getSession(HttpServletRequest request) {
		SSOSession session = this.ssoService.getSsoSession(request);
		return session;
	}

	private String getRidsBySession(HttpServletRequest req) {
		String rids = null;
		SSOSession session = this.ssoService.getSsoSession(req);
		if (session != null) {
			if (this.adminStr.equals(session.getLoginName()))
				rids = this.ADMIN_ID;
			else {
				rids = session.getRids();
			}
		}
		return rids;
	}

	/*
	 * getOperate.do"})
	 * 
	 * @ResponseBody public String getOperate1(HttpServletRequest req) { return
	 * getOperate(req); } getOperate.do"})
	 * 
	 * @ResponseBody public String getOperate2(HttpServletRequest req) { return
	 * getOperate(req); } getOperate.do"})
	 * 
	 * @ResponseBody public String getOperate3(HttpServletRequest req) { return
	 * getOperate(req); }
	 * 
	 * @RequestMapping({"/getOperate.do"})
	 * 
	 * @ResponseBody public String getOperate4(HttpServletRequest req) { return
	 * getOperate(req); }
	 */
	@RequestMapping({ "/getOperate.do" })
	@ResponseBody
	public String getOperate(HttpServletRequest req) {
		String codes = null;
		String uri = req.getParameter("uri");
		String pid = this.ssoService.getPidByUri(uri);
		String rid = getRidsBySession(req);
		List list = this.permissionService.getOperateByRid(
				Integer.parseInt(pid), rid);
		if ((list != null) && (list.size() > 0)) {
			StringBuilder sb = new StringBuilder(list.size() * 12);
			sb.append(((Permission) list.get(0)).getCode());
			for (int i = 1; i < list.size(); i++) {
				Permission p = (Permission) list.get(i);
				sb.append(",");
				sb.append(p.getCode());
			}
			codes = sb.toString();
		}
		return codes;
	}
}
