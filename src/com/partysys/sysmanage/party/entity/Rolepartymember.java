package com.partysys.sysmanage.party.entity;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
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
/**
 * 党员角色表
 * @author 朱可凡
 *
 */
public class Rolepartymember implements java.io.Serializable {


	private RolepartymemberId id;
	private Partymember partymember;
	private Role role;


	public Rolepartymember() {
	}

	public Rolepartymember(/*RolepartymemberId id, */Partymember partymember, Role role) {
		/*this.id = id;*/
		this.partymember = partymember;
		this.role = role;
	}
	public Rolepartymember(RolepartymemberId id, Partymember partymember, Role role) {
		this.id = id;
		this.partymember = partymember;
		this.role = role;
	}
	@EmbeddedId

	@AttributeOverrides({
			@AttributeOverride(name = "userId", column = @Column(name = "id", nullable = false, length = 32) ),
			@AttributeOverride(name = "roleId", column = @Column(name = "role_id", nullable = false, length = 32) ) })

	public RolepartymemberId getId() {
		return this.id;
	}

	public void setId(RolepartymemberId id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id", nullable = false, insertable = false, updatable = false)
	public Partymember getPartymember() {
		return this.partymember;
	}

	public void setPartymember(Partymember partymember) {
		this.partymember = partymember;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "role_id", nullable = false, insertable = false, updatable = false)
	public Role getRole() {
		return this.role;
	}

	public void setRole(Role role) {
		this.role = role;
	}	
	
}