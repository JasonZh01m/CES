package com.jsscom.ces.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.jsscom.ces.data.dao.OrganizationDao;
import com.jsscom.ces.data.dao.UserOrganizationRelationDao;
import com.jsscom.ces.model.Organization;
import com.jsscom.ces.model.UserOrganizationRelation;
import com.jsscom.ces.service.OrganizationService;
import com.sq.core.service.impl.ServiceImpl;
import com.sq.core.vo.ZtreeBean;

@Service
public class OrganizationServiceImpl extends ServiceImpl<Organization>
		implements OrganizationService {

	@Resource
	private OrganizationDao organizationDao;

	@Resource
	private UserOrganizationRelationDao userOrganizationRelationDao;
	private String delIds = "";

	@Override
	public boolean queryOrgByInfo(Organization org) {
		Organization o = this.organizationDao.findOrgByInfo(org);

		return o != null;
	}

	@Override
	public List<Organization> queryByClient() {
		return this.organizationDao.query("queryByClient", null);
	}

	@Override
	public List<Organization> queryAllOrganizationByPid(String pId) {
		return this.organizationDao.findAllOrganizationByPid(pId);
	}

	@Override
	public List<Organization> queryAllOrganizationByMap(Organization organ) {
		return this.organizationDao.findAllOrganizationByMap(organ);
	}

	@Override
	public boolean hasChilders(Organization org) {
		List organizations = this.organizationDao.findAllOrganizationByMap(org);
		return organizations.size() > 0;
	}

	@Override
	public void deleteOrganizationByPid(int organizationId) {
		this.delIds = "";
		String delIds = getDelIds(organizationId);
		delete(delIds);
	}

	private String getDelIds(int organizationId) {
		List<Organization> childorganization = this.organizationDao
				.findAllOrganizationByPid(organizationId);
		for (Organization organization : childorganization) {
			deleteOrganizationByPid(organization.getId());
		}
		this.delIds = (this.delIds + organizationId + ",");
		return this.delIds.substring(0, this.delIds.length() - 1);
	}

	@Override
	public Organization queryCheckedOrganizationByPid(String pId) {
		return this.organizationDao.queryCheckedOrganizationByPid(pId);
	}

	@Override
	public List<Organization> queryOrganizationInfo(Organization organ) {
		List list = this.organizationDao.queryOrganizationInfo(organ);

		return list;
	}

	@Override
	public List<Organization> queryAllUserByOrganization(int userId) {
		return this.organizationDao.queryAllUserByOrganization(userId);
	}

	@Override
	public List<Organization> queryOrganizationInfo() {
		return this.organizationDao.queryOrganizationInfo();
	}

	@Override
	public List<ZtreeBean> queryAllOrganiztionByUser(Organization organ) {
		List results = null;
		UserOrganizationRelation uor = new UserOrganizationRelation();
		uor.setUserId(organ.getUserId());
		List listUor = this.userOrganizationRelationDao.query(uor);
		HashMap map = null;
		UserOrganizationRelation uorm;
		if ((listUor != null) && (listUor.size() > 0)) {
			Object o = new Object();
			map = new HashMap();
			for (int i = 0; i < listUor.size(); i++) {
				uorm = (UserOrganizationRelation) listUor.get(i);
				map.put(uorm.getOrgId(), o);
			}
		} else {
			map = new HashMap();
		}

		List<Organization> listOrganizations = this.organizationDao
				.findAllOrganByMap(organ);
		results = new ArrayList(listOrganizations.size());

		for (Organization org : listOrganizations) {
			ZtreeBean zb = new ZtreeBean();
			if ((org != null) && (map.get(org.getId()) != null))
				zb.setChecked("true");
			else {
				zb.setChecked("false");
			}
			zb.setId(org.getId());
			zb.setPId(org.getParentId());
			zb.setName(org.getName());
			zb.setCode(org.getCode());
			if (hasChilders(org))
				zb.setIsParent("true");
			else {
				zb.setIcon("../UI/css/zTree/img/diy/leaf.gif");
			}
			results.add(zb);
		}
		return results;
	}

	@Override
	public List<Organization> queryLikeByCode(String code) {
		Organization organ = new Organization();
		organ.setCode(code);
		return this.organizationDao.queryList("queryLikeByCode", organ);
	}

	@Override
	public List<UserOrganizationRelation> queryAllUserByOrg(int orgId) {
		return this.userOrganizationRelationDao.queryByOrg(orgId);
	}
}
