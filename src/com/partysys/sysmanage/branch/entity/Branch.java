package com.partysys.sysmanage.branch.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.partysys.sysmanage.party.entity.Partymember;

/**
 * 支部实体类(在支部-党员表中充当主表)
 * @author 朱可凡
 */
@Entity
@Table(name = "branch", catalog = "partysys")

public class Branch implements java.io.Serializable {


	private String branchId;
	/**
	 * 支部名称
	 */
	private String brachName;
	/**
	 * 支部管理员
	 */
	
	private String[] branchAdmin;
	/**
	 * 支部人数
	 */
	private Integer branchNumber;
	private Set<Partymember> partymembers = new HashSet<Partymember>(0);


	public Branch() {
	}

	public Branch(String brachName) {
		this.brachName = brachName;
	}

	public Branch(String brachName, String[] branchAdmin, Integer branchNumber, Set<Partymember> partymembers) {
		this.brachName = brachName;
		this.branchAdmin = branchAdmin;
		this.branchNumber = branchNumber;
		this.partymembers = partymembers;
	}

	@GenericGenerator(name = "generator", strategy = "uuid.hex")
	@Id
	@GeneratedValue(generator = "generator")

	@Column(name = "branch_id", unique = true, nullable = false, length = 32)

	public String getBranchId() {
		return this.branchId;
	}

	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}

	@Column(name = "brach_name", nullable = false, length = 40)

	public String getBrachName() {
		return this.brachName;
	}

	public void setBrachName(String brachName) {
		this.brachName = brachName;
	}
	@ElementCollection(targetClass=String.class)
	@CollectionTable(name="br_admin",joinColumns=@JoinColumn(name="admin_id", nullable=false))
	@Column(name = "branch_admin", length = 100)
	@OrderColumn(name="t_order")
	public String[] getBranchAdmin() {
		return this.branchAdmin;
	}

	public void setBranchAdmin(String[] branchAdmin) {
		this.branchAdmin = branchAdmin;
	}

	@Column(name = "branch_number")

	public Integer getBranchNumber() {
		return this.branchNumber;
	}

	public void setBranchNumber(Integer branchNumber) {
		this.branchNumber = branchNumber;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "branch")

	public Set<Partymember> getPartymembers() {
		return this.partymembers;
	}

	public void setPartymembers(Set<Partymember> partymembers) {
		this.partymembers = partymembers;
	}

}