package com.sq.sso.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.sq.sso.cache.UriCache;
import com.sq.sso.model.Permission;
import com.sq.sso.model.UriInfo;
import com.sq.sso.service.PermissionService;
import com.sq.sso.service.SsoService;
import com.sq.sso.vo.SSOSession;

@Service
public class SsoServiceImpl implements SsoService {
	private String ssoServer;
	private int overtime;
	private int rootPermissionId;

	@Resource
	private PermissionService permissionService;
	private static String COMMA = ",";

	private final String session_flag = "$session";

	private int excludeSize = 0;
	private List<String> excludeList;
	private List<Permission> allPermissionList;

	@Override
	public int getRootPermissionId() {
		return this.rootPermissionId;
	}

	public void setRootPermissionId(int rootPermissionId) {
		this.rootPermissionId = rootPermissionId;
	}

	@Override
	public boolean isPermit(String rids, String pid) {
		boolean isPermit = false;
		if ((rids != null) && (pid != null)) {
			String[] ids = rids.split(COMMA);
			for (int i = 0; i < ids.length; i++) {
				String rid = ids[i];
				if (this.permissionService.validateResourceString(rid, pid)) {
					isPermit = true;
					break;
				}
			}
		}
		return isPermit;
	}

	@Override
	public boolean isExclude(String path) {
		boolean f = false;
		for (int i = 0; i < this.excludeSize; i++) {
			if (path.startsWith(this.excludeList.get(i))) {
				return true;
			}
		}
		return f;
	}

	@Override
	public void intiExclude(String exclude) {
		if (StringUtils.isNotEmpty(exclude)) {
			String[] strArray = exclude.split(",");
			this.excludeSize = (strArray.length + 3);
			this.excludeList = new ArrayList(strArray.length + 3);
			for (int i = 0; i < strArray.length; i++) {
				String s = strArray[i];
				this.excludeList.add(s);
			}
			this.excludeList.add("/sso");
			this.excludeList.add("/common");
			this.excludeList.add("/login");
		} else {
			this.excludeSize = 3;
			this.excludeList = new ArrayList(3);
			this.excludeList.add("/sso");
			this.excludeList.add("/common");
			this.excludeList.add("/login");
		}
	}

	@Override
	public boolean isEmpty(String value) {
		return (value == null) || (!value.isEmpty());
	}

	@Override
	public SSOSession getSsoSession(HttpServletRequest req) {
		SSOSession session = (SSOSession) req.getSession().getAttribute(
				this.session_flag);
		return session;
	}

	@Override
	public boolean isLogin(SSOSession session, String ip) {
		boolean isLogin = false;
		if ((session != null) && (ip.equals(session.getIp()))) {
			isLogin = true;
		}

		return isLogin;
	}

	@Override
	public UriInfo getUriInfo(String uri, String contextPath) {
		if (UriCache.size() == 0) {
			initUriBySysId(contextPath);
		}
		return UriCache.get(uri);
	}

	@Override
	public String getPidByUri(String uri) {
		String pid = null;
		UriInfo info = UriCache.get(uri);
		if (info != null) {
			pid = info.getPid();
		}
		return pid;
	}

	@Override
	public void initUriBySysId(String contextPath) {
		this.allPermissionList = this.permissionService.initUriBySysId(0);
		if ((this.allPermissionList != null)
				&& (this.allPermissionList.size() > 0))
			for (int i = 0; i < this.allPermissionList.size(); i++) {
				Permission p = this.allPermissionList.get(i);
				if (p.getParentId() == 0) {
					this.rootPermissionId = p.getId();
				}
				UriInfo info = new UriInfo();
				// Modified By JasonZh 0728
				// info.setPid(p.getId());
				info.setPid(p.getId() + "");
				info.setType(p.getType());
				UriCache.put(p.getResourceString(), info);
			}
	}

	@Override
	public int getOvertime() {
		return this.overtime;
	}

	@Override
	public void setOvertime(int overtime) {
		this.overtime = overtime;
	}

	public List<Permission> getAllPermissionList() {
		return this.allPermissionList;
	}

	public void setAllPermissionList(List<Permission> allPermissionList) {
		this.allPermissionList = allPermissionList;
	}

	@Override
	public String getSsoServer() {
		return this.ssoServer;
	}

	@Override
	public void setSsoServer(String ssoServer) {
		this.ssoServer = ssoServer;
	}
}
