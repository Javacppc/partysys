package com.partysys.sysmanage.branch.entity;

import java.util.HashSet;
import java.util.List;
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
@Table(name = "branch")

public class Branch implements java.io.Serializable {


	private String branchId;
	/**
	 * 支部名称
	 */
	private String branchName;
	/**
	 * 支部管理员
	 */
	private List<String> branchAdmin;
	/**
	 * 支部人数
	 */
	private Integer branchNumber;
	private Set<Partymember> partymembers = new HashSet<Partymember>(0);
	

	public Branch() {
	}

	public Branch(String branchId, String branchName) {
		this.branchId = branchId;
		this.branchName = branchName;
	}
	

	public Branch(String brachName, List<String> branchAdmin, Integer branchNumber, Set<Partymember> partymembers) {
		this.branchName = brachName;
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

	@Column(name = "branch_name", nullable = false, length = 40)

	public String getBranchName() {
		return this.branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	@ElementCollection(targetClass=String.class,fetch=FetchType.EAGER)
	@CollectionTable(name="br_admin",joinColumns=@JoinColumn(name="branch_id", nullable=false))
	@Column(name = "branch_admin", length = 100)
	@OrderColumn(name="t_order")
	public List<String> getBranchAdmin() {
		return this.branchAdmin;
	}

	public void setBranchAdmin(List<String> branchAdmin) {
		this.branchAdmin = branchAdmin;
	}

	@Column(name = "branch_number")

	public Integer getBranchNumber() {
		return this.branchNumber;
	}

	public void setBranchNumber(Integer branchNumber) {
		this.branchNumber = branchNumber;
	}

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "branch",cascade=CascadeType.ALL)
	public Set<Partymember> getPartymembers() {
		return this.partymembers;
	}

	public void setPartymembers(Set<Partymember> partymembers) {
		this.partymembers = partymembers;
	}
}