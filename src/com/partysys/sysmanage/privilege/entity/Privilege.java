package com.partysys.sysmanage.privilege.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.partysys.sysmanage.role.entity.Roleprivilege;

/**
 * 权限实体类 
 * @author 朱可凡
 */
@Entity
@Table(name = "privilege")


public class Privilege implements java.io.Serializable {

	/**
	 * 权限Id(主键是自增长策略)
	 */
	private Integer privilegeId;
	/**
	 * 权限名称
	 */
	private String name;
	
	/**
	 * 权限描述
	 */
	private String privilegeName;
	/**
	 * 权限状态
	 */
	private String state;
	//private Set<Roleprivilege> roleprivileges = new HashSet<Roleprivilege>(0);
	//权限状态
	/**
	 * 权限状态有效
	 */
	public static String PRIVILEGE_STATE_VALID = "1";
	/**
	 * 权限状态无效
	 */
	public static String PRIVILEGE_STATE_INVALID = "0";


	public Privilege() {
	}

	public Privilege(String privilegeName) {
		this.privilegeName = privilegeName;
	}
	
	public Privilege(Integer id, String privilegeName) {
		this.privilegeId = id;
		this.privilegeName = privilegeName;
	}
	public Privilege(String privilegeName, String state/*, Set<Roleprivilege> roleprivileges*/) {
		this.privilegeName = privilegeName;
		this.state = state;
		//this.roleprivileges = roleprivileges;
	}
	
	
	

	@Id   
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	@Column(name = "privilege_id", unique = true, nullable = false)

	public Integer getPrivilegeId() {
		return this.privilegeId;
	}

	public void setPrivilegeId(Integer privilegeId) {
		this.privilegeId = privilegeId;
	}
	
	
	@Column(name="name", length=20,nullable=false)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "privilege_name", nullable = false, length = 40)

	public String getPrivilegeName() {
		return this.privilegeName;
	}

	public void setPrivilegeName(String privilegeName) {
		this.privilegeName = privilegeName;
	}

	@Column(name = "state", length = 10)

	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	//@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "privilege")

	/*public Set<Roleprivilege> getRoleprivileges() {
		return this.roleprivileges;
	}

	public void setRoleprivileges(Set<Roleprivilege> roleprivileges) {
		this.roleprivileges = roleprivileges;
	}*/

}