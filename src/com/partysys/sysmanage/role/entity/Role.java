package com.partysys.sysmanage.role.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.GenericGenerator;

import com.partysys.sysmanage.party.entity.Rolepartymember;

/**
 * 角色实体 
 * @author 朱可凡
 */
@Entity
@Table(name = "role", catalog = "partysys")

public class Role implements java.io.Serializable {


	private String roleId;
	private String roleName;
	private String state;
	private Set<Rolepartymember> rolepartymembers = new HashSet<Rolepartymember>(0);
	private Set<Roleprivilege> roleprivileges = new HashSet<Roleprivilege>(0);
	
	//角色状态
	/**
	 * 表示角色状态有效
	 */
	public static String ROLE_STATE_VALID = "1";
	/**
	 * 表示角色状态无效
	 */
	public static String ROLE_STATE_INVALID = "0";

	public Role() {
	}

	public Role(String roleName) {
		this.roleName = roleName;
	}

	public Role(String roleName, String state, Set<Rolepartymember> rolepartymembers,
			Set<Roleprivilege> roleprivileges) {
		this.roleName = roleName;
		this.state = state;
		this.rolepartymembers = rolepartymembers;
		this.roleprivileges = roleprivileges;
	}

	@GenericGenerator(name = "generator", strategy = "uuid.hex")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "role_id", unique = true, nullable = false, length = 32)

	public String getRoleId() {
		return this.roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	@Column(name = "role_name", nullable = false, length = 40)

	public String getRoleName() {
		return this.roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	@Column(name = "state", length = 10)

	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "role")

	public Set<Rolepartymember> getRolepartymembers() {
		return this.rolepartymembers;
	}

	public void setRolepartymembers(Set<Rolepartymember> rolepartymembers) {
		this.rolepartymembers = rolepartymembers;
	}
	//禁止懒加载，因为在显示角色的同时需要显示权限
	//@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "role")
	
	
	@OneToMany(fetch = FetchType.EAGER, orphanRemoval = true, mappedBy = "role", cascade=CascadeType.ALL)
	@Fetch(FetchMode.SELECT)
	public Set<Roleprivilege> getRoleprivileges() {
		return this.roleprivileges;
	}

	public void setRoleprivileges(Set<Roleprivilege> roleprivileges) {
		this.roleprivileges = roleprivileges;
	}
}