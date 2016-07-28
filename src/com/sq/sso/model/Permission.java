package com.sq.sso.model;

import java.io.Serializable;

import com.sq.core.annotation.Cache;
import com.sq.core.annotation.CacheKey;
import com.sq.core.vo.TreeNode;

@Cache(usage = "r")
public class Permission implements Serializable, TreeNode {
	private static final long serialVersionUID = 1L;

	@CacheKey
	private int id;
	private String name;
	private String code;
	private String resourceString;
	private int type = -1;
	private int sysId = -1;
	private int orderIndex;
	private String iconUri;
	private int parentId = -1;
	private boolean isNode;
	private String checked;
	private String open;
	private String isParent;

	public void setChecked(String checked) {
		this.checked = checked;
	}

	public void setOpen(String open) {
		this.open = open;
	}

	public void setIsParent(String isParent) {
		this.isParent = isParent;
	}

	public boolean isNode() {
		return this.isNode;
	}

	public void setNode(boolean isNode) {
		this.isNode = isNode;
	}

	@Override
	public String toString() {
		return "Permission: [id = " + this.id + ",name = " + this.name
				+ ",code = " + this.code + ",resourceString = "
				+ this.resourceString + "," + "type = " + this.type
				+ ",sysId = " + this.sysId + ",orderIndex = " + this.orderIndex
				+ ",iconUri = " + this.iconUri + ",parentId=" + this.parentId
				+ "]";
	}

	public int getId() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}

	public String getCode() {
		return this.code;
	}

	public String getResourceString() {
		return this.resourceString;
	}

	public int getType() {
		return this.type;
	}

	public int getSysId() {
		return this.sysId;
	}

	public int getOrderIndex() {
		return this.orderIndex;
	}

	public String getIconUri() {
		return this.iconUri;
	}

	public int getParentId() {
		return this.parentId;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setResourceString(String resourceString) {
		this.resourceString = resourceString;
	}

	public void setType(int type) {
		this.type = type;
	}

	public void setSysId(int sysId) {
		this.sysId = sysId;
	}

	public void setOrderIndex(int orderIndex) {
		this.orderIndex = orderIndex;
	}

	public void setIconUri(String iconUri) {
		this.iconUri = iconUri;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	@Override
	public String getPId() {
		return this.parentId + "";
	}

	@Override
	public String getChecked() {
		return "false";
	}

	@Override
	public String getOpen() {
		return null;
	}

	@Override
	public String getIsParent() {
		return null;
	}

	@Override
	public String getIcon() {
		return this.iconUri;
	}
}
