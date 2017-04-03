package com.partysys.sysmanage.party.entity;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.partysys.sysmanage.role.entity.Role;

/**
 * 用户角色表 
 * @author 朱可凡
 */
@Entity
@Table(name = "rolepartymember", catalog = "partysys")

public class Rolepartymember implements java.io.Serializable {

	// Fields

	private RolepartymemberId id;
	private Partymember partymember;
	private Role role;

	// Constructors

	/** default constructor */
	public Rolepartymember() {
	}

	/** full constructor */
	public Rolepartymember(RolepartymemberId id, Partymember partymember, Role role) {
		this.id = id;
		this.partymember = partymember;
		this.role = role;
	}

	// Property accessors
	@EmbeddedId

	@AttributeOverrides({
			@AttributeOverride(name = "id", column = @Column(name = "id", nullable = false, length = 20) ),
			@AttributeOverride(name = "roleId", column = @Column(name = "role_id", nullable = false, length = 32) ) })

	public RolepartymemberId getId() {
		return this.id;
	}

	public void setId(RolepartymemberId id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id", nullable = false, insertable = false, updatable = false)

	public Partymember getPartymember() {
		return this.partymember;
	}

	public void setPartymember(Partymember partymember) {
		this.partymember = partymember;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "role_id", nullable = false, insertable = false, updatable = false)

	public Role getRole() {
		return this.role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

}