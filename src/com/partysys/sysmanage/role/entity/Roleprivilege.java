package com.partysys.sysmanage.role.entity;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.partysys.sysmanage.privilege.entity.Privilege;

/**
 * 角色权限实体
 * @author 朱可凡
 */
@Entity
@Table(name = "roleprivilege")

public class Roleprivilege implements java.io.Serializable {
	
	private String id;
	private Role role;
	//private Privilege privilege;
	private String code;

	public Roleprivilege() {
	}
	
	/*public Roleprivilege(RoleprivilegeId id) {
		this.id = id;
	}*/

	public Roleprivilege(String id, Role role, String code) {
		this.id = id;
		this.role = role;
		this.code = code;
	}
	/**
	 * 角色和角色所对应的权限
	 * @param role 角色
	 * @param code 权限
	 */
	public Roleprivilege(Role role, String code) {
		this.role = role;
		this.code = code;
	}


	/*@EmbeddedId

	@AttributeOverrides({
			@AttributeOverride(name = "roleId", column = @Column(name = "role_id", nullable = false, length = 32) ),
			@AttributeOverride(name = "privilegeId", column = @Column(name = "privilege_id", nullable = false) ) })

	public RoleprivilegeId getId() {
		return this.id;
	}

	public void setId(RoleprivilegeId id) {
		this.id = id;
	}*/
	@Id
	@GenericGenerator(name="pk_uuid", strategy="uuid")
	@GeneratedValue(generator="pk_uuid")
	@Column(name="roleprivilege_id")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	@ManyToOne(targetEntity=Role.class, fetch = FetchType.EAGER)
	@JoinColumn(name = "role_id",referencedColumnName="role_id")

	public Role getRole() {
		return this.role;
	}

	public void setRole(Role role) {
		this.role = role;
	}
	
	@Column(name="code", length=20)
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((role == null) ? 0 : role.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Roleprivilege other = (Roleprivilege) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (role == null) {
			if (other.role != null)
				return false;
		} else if (!role.equals(other.role))
			return false;
		return true;
	}

	
	
	/*@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "privilege_id", nullable = false, insertable = false, updatable = false)

	public Privilege getPrivilege() {
		return this.privilege;
	}

	public void setPrivilege(Privilege privilege) {
		this.privilege = privilege;
	}*/
	
}