package com.partysys.partymanage.period.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.partysys.partymanage.deus.entity.Deus;
import com.partysys.sysmanage.party.entity.Partymember;

/**
 * 党费期数类
 * @author 朱可凡
 */
@Entity
@Table(name="period")
public class Period {
	
	private String periodId;
	/**
	 * 显示这是多少期党费
	 */
	private String date;
	
	private Set<Partymember> partymembers = new HashSet<>();
	
	private Set<Deus> deus = new HashSet<>();
	/**
	 * 期数创建人类别（是教师创建的还是学生创建的）
	 */
	private String creator;
	/**
	 * 创建人是学生
	 */
	public static final String CREATOR_STUDENT = "STUDENT";
	/**
	 * 创建人是教师
	 */
	public static final String CREATOR_TEACHER = "TEACHER";
	
	@Id
	@Column(name="period_id", length=32)
	@GenericGenerator(name="pk_uuid", strategy="uuid.hex")
	@GeneratedValue(generator="pk_uuid")
	public String getPeriodId() {
		return periodId;
	}

	public void setPeriodId(String periodId) {
		this.periodId = periodId;
	}
	
	@Column(name="date", length=10)
	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable(name = "partymember_period", joinColumns = {
			@JoinColumn(name = "period_id", nullable = false, updatable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "partymember_id", nullable = false, updatable = false) })
	public Set<Partymember> getPartymembers() {
		return partymembers;
	}

	public void setPartymembers(Set<Partymember> partymembers) {
		this.partymembers = partymembers;
	}
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "period")
	public Set<Deus> getDeus() {
		return deus;
	}

	public void setDeus(Set<Deus> deus) {
		this.deus = deus;
	}
	@Column(name="creator_type", length=10)
	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}
}
