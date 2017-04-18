package com.partysys.tmp;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.GenericGenerator;

/**
 * Partymember entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "partymember", catalog = "partysystem")

public class Partymember implements java.io.Serializable {

	// Fields

	private String id;
	private String name;
	private Boolean gender;
	private String number;
	private String nation;
	private String identity;
	private String province;
	private String telenumber;
	private String grade;
	private String class_;
	private String cultivate;
	private String classification;
	private String branchId;
	private String textnumber;
	private Date jionTime;
	private String roleId;
	private String remake;
	private Set<Period> periods = new HashSet<Period>(0);
	private Set<Deus> deuses = new HashSet<Deus>(0);

	// Constructors

	/** default constructor */
	public Partymember() {
	}

	/** full constructor */
	public Partymember(String name, Boolean gender, String number, String nation, String identity, String province,
			String telenumber, String grade, String class_, String cultivate, String classification, String branchId,
			String textnumber, Date jionTime, String roleId, String remake, Set<Period> periods, Set<Deus> deuses) {
		this.name = name;
		this.gender = gender;
		this.number = number;
		this.nation = nation;
		this.identity = identity;
		this.province = province;
		this.telenumber = telenumber;
		this.grade = grade;
		this.class_ = class_;
		this.cultivate = cultivate;
		this.classification = classification;
		this.branchId = branchId;
		this.textnumber = textnumber;
		this.jionTime = jionTime;
		this.roleId = roleId;
		this.remake = remake;
		this.periods = periods;
		this.deuses = deuses;
	}

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "uuid.hex")
	@Id
	@GeneratedValue(generator = "generator")

	@Column(name = "id", unique = true, nullable = false, length = 20)

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "name", length = 30)

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "gender")

	public Boolean getGender() {
		return this.gender;
	}

	public void setGender(Boolean gender) {
		this.gender = gender;
	}

	@Column(name = "number", length = 30)

	public String getNumber() {
		return this.number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	@Column(name = "nation", length = 30)

	public String getNation() {
		return this.nation;
	}

	public void setNation(String nation) {
		this.nation = nation;
	}

	@Column(name = "identity", length = 40)

	public String getIdentity() {
		return this.identity;
	}

	public void setIdentity(String identity) {
		this.identity = identity;
	}

	@Column(name = "province", length = 30)

	public String getProvince() {
		return this.province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	@Column(name = "telenumber", length = 20)

	public String getTelenumber() {
		return this.telenumber;
	}

	public void setTelenumber(String telenumber) {
		this.telenumber = telenumber;
	}

	@Column(name = "grade", length = 20)

	public String getGrade() {
		return this.grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	@Column(name = "class", length = 20)

	public String getClass_() {
		return this.class_;
	}

	public void setClass_(String class_) {
		this.class_ = class_;
	}

	@Column(name = "cultivate", length = 20)

	public String getCultivate() {
		return this.cultivate;
	}

	public void setCultivate(String cultivate) {
		this.cultivate = cultivate;
	}

	@Column(name = "classification", length = 30)

	public String getClassification() {
		return this.classification;
	}

	public void setClassification(String classification) {
		this.classification = classification;
	}

	@Column(name = "branchId", length = 20)

	public String getBranchId() {
		return this.branchId;
	}

	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}

	@Column(name = "textnumber", length = 30)

	public String getTextnumber() {
		return this.textnumber;
	}

	public void setTextnumber(String textnumber) {
		this.textnumber = textnumber;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "jionTime", length = 10)

	public Date getJionTime() {
		return this.jionTime;
	}

	public void setJionTime(Date jionTime) {
		this.jionTime = jionTime;
	}

	@Column(name = "roleId", length = 20)

	public String getRoleId() {
		return this.roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	@Column(name = "remake", length = 50)

	public String getRemake() {
		return this.remake;
	}

	public void setRemake(String remake) {
		this.remake = remake;
	}

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "partymembers")

	public Set<Period> getPeriods() {
		return this.periods;
	}

	public void setPeriods(Set<Period> periods) {
		this.periods = periods;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "partymember")

	public Set<Deus> getDeuses() {
		return this.deuses;
	}

	public void setDeuses(Set<Deus> deuses) {
		this.deuses = deuses;
	}

}