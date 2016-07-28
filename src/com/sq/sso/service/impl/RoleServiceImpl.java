package com.sq.sso.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.sq.core.service.impl.ServiceImpl;
import com.sq.sso.data.dao.PermissionRoleRelationDao;
import com.sq.sso.data.dao.RoleDao;
import com.sq.sso.model.PermissionRoleRelation;
import com.sq.sso.model.Role;
import com.sq.sso.service.RoleService;

@Service("roleServiceImpl")
public class RoleServiceImpl extends ServiceImpl<Role> implements RoleService {

	@Resource
	private RoleDao roleDaoImpl;

	@Resource
	private PermissionRoleRelationDao permissionRoleRelationDao;

	@Override
	public List<Role> queryAllRoleByUser(int userId) {
		return this.roleDaoImpl.queryAllRoleByUser(userId);
	}

	@Override
	public void updateRole(Role role, String permissionIds) {
		this.roleDaoImpl.update(role);
		List idList = new ArrayList(Arrays.asList(permissionIds.split(",")));
		saveAndRemoveRolePermission(role, idList);
	}

	@Override
	public void insertRole(Role role, String permissionIds) {
		this.roleDaoImpl.insert("insertRole", role);
		List idList = new ArrayList(Arrays.asList(permissionIds.split(",")));
		saveAndRemoveRolePermission(role, idList);
	}

	private void saveAndRemoveRolePermission(Role role, List<String> idList) {
		PermissionRoleRelation prr = new PermissionRoleRelation();
		prr.setRoleId(role.getId());
		List list = this.permissionRoleRelationDao.query("query", prr);
		Map m = null;
		if ((idList != null) && (idList.size() > 0)) {
			m = new HashMap();
			for (String permissionId : idList) {
				if ("-1".equals(permissionId))
					continue;
				m.put(permissionId, permissionId);
			}

		}

		if ((list != null) && (list.size() > 0)) {
			for (int i = 0; i < list.size(); i++) {
				PermissionRoleRelation p = (PermissionRoleRelation) list.get(i);
				// Modified By JasonZh 0728
				// String pid = p.getPermissionId();
				String pid = p.getPermissionId() + "";
				if (m.get(pid) != null) {
					m.remove(pid);
				} else {
					System.out.println("delete:" + p);
					this.permissionRoleRelationDao.delete("delete", p);
				}
			}
		}

		Iterator it = m.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			String pid = (String) entry.getKey();
			PermissionRoleRelation p = new PermissionRoleRelation();
			p.setRoleId(role.getId());
			p.setPermissionId(Integer.parseInt(pid));
			this.permissionRoleRelationDao.insert("save", p);
		}
	}

	@Override
	public void deleteRole(int roleId) {
		delete(roleId);
		Role role = new Role();
		role.setId(roleId);
		this.roleDaoImpl.deleteRolePermission(role);
	}

	@Override
	public List<Role> queryAllRole() {
		return this.roleDaoImpl.queryAllRole();
	}

	@Override
	public boolean checkRoleCode(Role role) {
		List roles = this.roleDaoImpl.queryList("queryByRole", role);
		return roles.size() > 0;
	}
}
