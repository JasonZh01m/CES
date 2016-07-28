package com.sq.sso.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.sq.core.service.impl.ServiceImpl;
import com.sq.core.vo.ZtreeBean;
import com.sq.sso.ComparatorPermission;
import com.sq.sso.cache.UriCache;
import com.sq.sso.data.dao.PermissionDao;
import com.sq.sso.data.dao.PermissionRoleRelationDao;
import com.sq.sso.model.Permission;
import com.sq.sso.model.PermissionRoleRelation;
import com.sq.sso.model.UriInfo;
import com.sq.sso.service.PermissionService;

@Service
public class PermissionServiceImpl extends ServiceImpl<Permission> implements
		PermissionService {

	@Resource
	private PermissionDao permissionDaoImpl;

	@Resource
	private PermissionRoleRelationDao permissionRoleRelationDao;
	private static String ADMIN_ID = "1";
	private final Permission linkPermission;
	private final ComparatorPermission cp;

	public PermissionServiceImpl() {
		this.linkPermission = new Permission();
		this.linkPermission.setType(0);
		this.cp = new ComparatorPermission();
	}

	@Override
	public List<Permission> queryAllPermissionByRoleId(int roleId) {
		PermissionRoleRelation prr = new PermissionRoleRelation();
		prr.setRoleId(roleId);
		List list = null;
		List prList = this.permissionRoleRelationDao.query("query", prr);
		if ((prList != null) && (prList.size() > 0)) {
			list = new ArrayList(prList.size());
			for (int i = 0; i < prList.size(); i++) {
				PermissionRoleRelation pr = (PermissionRoleRelation) prList
						.get(i);
				int pid = pr.getPermissionId();
				Permission p = this.permissionDaoImpl.queryById(
						"queryById".intern(), pid);
				if (p != null) {
					list.add(p);
				}
			}
		}
		return list;
	}

	@Override
	public List<Permission> queryPermissionForMenu(String rids) {
		List list = null;
		if (rids != null) {
			String[] rs = StringUtils.split(rids, ",");
			for (int i = 0; i < rs.length; i++) {
				if (ADMIN_ID.equals(rs[i])) {
					list = this.permissionDaoImpl.iterator("iterator",
							this.linkPermission);
					return list;
				}
			}

			PermissionRoleRelation prr = new PermissionRoleRelation();
			List prList = null;
			prList = getPermissionRoleRelationForRoleId(rs, prr);
			if ((prList != null) && (prList.size() > 0)) {
				list = new ArrayList(prList.size());
				for (int i = 0; i < prList.size(); i++) {
					PermissionRoleRelation pr = (PermissionRoleRelation) prList
							.get(i);
					int pid = pr.getPermissionId();
					Permission p = this.permissionDaoImpl.queryById(
							"queryById".intern(), pid);
					if ((p != null) && (p.getType() == 0)) {
						list.add(p);
					}
				}
			}
		}
		return list;
	}

	private List<PermissionRoleRelation> getPermissionRoleRelationForRoleId(
			String[] rs, PermissionRoleRelation prr) {
		Set set = new HashSet();
		List list = new ArrayList();
		for (int i = 0; i < rs.length; i++) {
			int roleId = Integer.valueOf(rs[i]).intValue();
			prr.setRoleId(roleId);
			List prList = this.permissionRoleRelationDao.query("query", prr);
			if (prList != null) {
				set.addAll(prList);
			}
		}
		list.addAll(set);
		return list;
	}

	@Override
	public List<Permission> getOperateByRid(int pid, String rids) {
		List list = null;
		if ((rids != null) && (!"".equals(rids))) {
			Permission permission = new Permission();
			permission.setParentId(pid);
			List nmList = this.permissionDaoImpl.iterator("iterator",
					permission);
			String[] ridstr = rids.split(",");
			for (int i = 0; i < ridstr.length; i++) {
				if (ADMIN_ID.equals(ridstr[i])) {
					return nmList;
				}
			}
			if ((nmList != null) && (nmList.size() > 0)) {
				list = new ArrayList(nmList.size());
				for (int j = 0; j < nmList.size(); j++) {
					for (int i = 0; i < ridstr.length; i++) {
						Permission p = (Permission) nmList.get(j);
						String pId = p.getId() + "";
						if (validateResourceString(ridstr[i], pId)) {
							list.add(p);
							break;
						}
					}
				}
			}
		}
		return list;
	}

	@Override
	public List<ZtreeBean> queryAllPermissionZtreeBean() {
		List results = null;
		List allPermission = queryAllPermission();
		if ((allPermission != null) && (allPermission.size() > 0)) {
			Collections.sort(allPermission, this.cp);
			results = new ArrayList(allPermission.size());
			for (int i = 0; i < allPermission.size(); i++) {
				Permission p = (Permission) allPermission.get(i);
				ZtreeBean zb = new ZtreeBean();
				zb.setChecked("false");
				zb.setId(p.getId());
				zb.setPId(p.getParentId());
				zb.setName(p.getName());
				zb.setSid(p.getSysId());
				if (p.getParentId() == 0) {
					zb.setOpen("true");
					zb.setIsParent("true");
				}
				results.add(zb);
			}
		}
		return results;
	}

	@Override
	public List<ZtreeBean> queryAllPermissionByRole(int roleId) {
		List results = null;
		List allPermission = queryAllPermission();
		PermissionRoleRelation prr = new PermissionRoleRelation();
		prr.setRoleId(roleId);
		List list = this.permissionRoleRelationDao.query("query", prr);
		HashMap map = null;
		if ((list != null) && (list.size() > 0)) {
			Object o = new Object();
			map = new HashMap();
			for (int i = 0; i < list.size(); i++) {
				PermissionRoleRelation pr = (PermissionRoleRelation) list
						.get(i);
				map.put(pr.getPermissionId(), o);
			}
		}
		if ((allPermission != null) && (allPermission.size() > 0)) {
			results = new ArrayList(allPermission.size());
			for (int i = 0; i < allPermission.size(); i++) {
				Permission p = (Permission) allPermission.get(i);
				ZtreeBean zb = new ZtreeBean();
				if ((p != null) && (map.get(p.getId()) != null))
					zb.setChecked("true");
				else {
					zb.setChecked("false");
				}
				zb.setId(p.getId());
				zb.setPId(p.getParentId());
				zb.setName(p.getName());
				zb.setSid(p.getSysId());
				if (p.getParentId() == 0) {
					zb.setOpen("true");
					zb.setIsParent("true");
				}
				results.add(zb);
			}
		}
		return results;
	}

	@Override
	public Integer validateResourceString(String resourceString) {
		return this.permissionDaoImpl.validateResourceString(resourceString);
	}

	@Override
	public List<Permission> initUriBySysId(int sysId) {
		Permission permission = new Permission();
		permission.setSysId(sysId);
		List list = this.permissionDaoImpl.queryList("queryByResSys",
				permission);
		if ((list != null) && (list.size() > 0)) {
			for (int i = 0; i < list.size(); i++) {
				Permission p = (Permission) list.get(i);
				this.permissionDaoImpl.queryById("queryById", p.getId());
			}
		}
		return list;
	}

	@Override
	public boolean validateResourceString(String rid, String pid) {
		boolean f = false;
		if ((!StringUtils.isNotEmpty(rid)) || (!StringUtils.isNotEmpty(pid))) {
			return false;
		}
		if (ADMIN_ID.equals(rid)) {
			return true;
		}
		PermissionRoleRelation prr = new PermissionRoleRelation();
		prr.setPermissionId(Integer.valueOf(pid).intValue());
		prr.setRoleId(Integer.valueOf(rid).intValue());
		List list = this.permissionRoleRelationDao.query("query", prr);
		if (list != null) {
			f = true;
		}
		return f;
	}

	@Override
	public List<Permission> queryAllPermission() {
		return this.permissionDaoImpl.iterator("iterator", null);
	}

	@Override
	public int deletePermissionId(int permissionId) {
		Permission p = findById(permissionId);
		int rs = 0;
		if (p != null) {
			String uri = p.getResourceString();
			rs = delete(permissionId);
			if (rs > 0) {
				UriCache.remove(uri);
			}
		}
		return rs;
	}

	@Override
	public int updatePermission(Permission permission) {
		int rs = update(permission);
		if (rs > 0) {
			UriInfo uriInfo = new UriInfo();
			uriInfo.setPid(permission.getId());
			uriInfo.setType(permission.getType());
			UriCache.put(permission.getResourceString(), uriInfo);
		}
		return rs;
	}

	@Override
	public int addPermission(Permission permission) {
		int rs = create(permission);
		if (rs > 0) {
			UriInfo uriInfo = new UriInfo();
			uriInfo.setPid(permission.getId());
			uriInfo.setType(permission.getType());
			UriCache.put(permission.getResourceString(), uriInfo);
		}
		return rs;
	}

	@Override
	public List<Permission> queryAllPermissionByUserId(String userId) {
		return this.permissionDaoImpl.queryAllPermissionByUserId(userId);
	}
}
