package com.partysys.sysmanage.party.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * 用户角色主键类  
 * @author 朱可凡
 */
@Embeddable

public class RolepartymemberId implements java.io.Serializable {


	private String id;
	private String roleId;


	public RolepartymemberId() {
	}

	public RolepartymemberId(String id, String roleId) {
		this.id = id;
		this.roleId = roleId;
	}


	@Column(name = "id", nullable = false, length = 20)

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "role_id", nullable = false, length = 32)

	public String getRoleId() {
		return this.roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof RolepartymemberId))
			return false;
		RolepartymemberId castOther = (RolepartymemberId) other;

		return ((this.getId() == castOther.getId())
				|| (this.getId() != null && castOther.getId() != null && this.getId().equals(castOther.getId())))
				&& ((this.getRoleId() == castOther.getRoleId()) || (this.getRoleId() != null
						&& castOther.getRoleId() != null && this.getRoleId().equals(castOther.getRoleId())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getId() == null ? 0 : this.getId().hashCode());
		result = 37 * result + (getRoleId() == null ? 0 : this.getRoleId().hashCode());
		return result;
	}

}