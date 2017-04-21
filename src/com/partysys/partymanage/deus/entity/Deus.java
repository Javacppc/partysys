package com.partysys.partymanage.deus.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import com.partysys.partymanage.period.entity.Period;
import com.partysys.sysmanage.party.entity.Partymember;
import com.partysys.test.entity.Person;

/**
 * 党费表（一个党员在某一期党费中交了多少钱）
 * @author 朱可凡
 */
@Entity
@Table(name="deus")
@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region="Configuration")
public class Deus {
	
	private String deusId;
	
	/**
	 * 党费
	 */
	private double cost;
	
	private Partymember partymember;
	
	private Period period;
	/**
	 * 经办人
	 */
	private String manager;
	
	@Id
	@Column(name="deus_id", length=32)
	@GenericGenerator(name="pk_uuid", strategy="uuid.hex")
	@GeneratedValue(generator="pk_uuid")
	public String getDeusId() {
		return deusId;
	}

	public void setDeusId(String deusId) {
		this.deusId = deusId;
	}
	
	@Column(name="cost")
	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "partymember_id")
	public Partymember getPartymember() {
		return partymember;
	}

	public void setPartymember(Partymember partymember) {
		this.partymember = partymember;
	}
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "period_id")
	public Period getPeriod() {
		return period;
	}

	public void setPeriod(Period period) {
		this.period = period;
	}
	@Column(name="mamager", length=10)
	public String getManager() {
		return manager;
	}

	public void setManager(String manager) {
		this.manager = manager;
	}
	
}
