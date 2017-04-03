package com.partysys.sysmanage.role.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * 角色权限表主键
 * @author 朱可凡
 */
@Embeddable

public class RoleprivilegeId implements java.io.Serializable {


	private String roleId;
	private String privilegeId;


	public RoleprivilegeId() {
	}

	public RoleprivilegeId(String roleId, String privilegeId) {
		this.roleId = roleId;
		this.privilegeId = privilegeId;
	}


	@Column(name = "role_id", nullable = false, length = 32)

	public String getRoleId() {
		return this.roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	@Column(name = "privilege_id", nullable = false, length = 32)

	public String getPrivilegeId() {
		return this.privilegeId;
	}

	public void setPrivilegeId(String privilegeId) {
		this.privilegeId = privilegeId;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof RoleprivilegeId))
			return false;
		RoleprivilegeId castOther = (RoleprivilegeId) other;

		return ((this.getRoleId() == castOther.getRoleId()) || (this.getRoleId() != null
				&& castOther.getRoleId() != null && this.getRoleId().equals(castOther.getRoleId())))
				&& ((this.getPrivilegeId() == castOther.getPrivilegeId())
						|| (this.getPrivilegeId() != null && castOther.getPrivilegeId() != null
								&& this.getPrivilegeId().equals(castOther.getPrivilegeId())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getRoleId() == null ? 0 : this.getRoleId().hashCode());
		result = 37 * result + (getPrivilegeId() == null ? 0 : this.getPrivilegeId().hashCode());
		return result;
	}

}