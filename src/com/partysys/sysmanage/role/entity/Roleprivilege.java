package com.partysys.sysmanage.role.entity;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.partysys.sysmanage.privilege.entity.Privilege;

/**
 * 角色权限实体
 * @author 朱可凡
 */
@Entity
@Table(name = "roleprivilege", catalog = "partysys")

public class Roleprivilege implements java.io.Serializable {


	private RoleprivilegeId id;
	private Role role;
	private Privilege privilege;


	public Roleprivilege() {
	}

	public Roleprivilege(RoleprivilegeId id, Role role, Privilege privilege) {
		this.id = id;
		this.role = role;
		this.privilege = privilege;
	}

	@EmbeddedId

	@AttributeOverrides({
			@AttributeOverride(name = "roleId", column = @Column(name = "role_id", nullable = false, length = 32) ),
			@AttributeOverride(name = "privilegeId", column = @Column(name = "privilege_id", nullable = false, length = 32) ) })

	public RoleprivilegeId getId() {
		return this.id;
	}

	public void setId(RoleprivilegeId id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "role_id", nullable = false, insertable = false, updatable = false)

	public Role getRole() {
		return this.role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "privilege_id", nullable = false, insertable = false, updatable = false)

	public Privilege getPrivilege() {
		return this.privilege;
	}

	public void setPrivilege(Privilege privilege) {
		this.privilege = privilege;
	}

}