package com.partysys.tmp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

/**
 * 党费表（一个党员在某一期党费中交了多少钱）
 * @author 朱可凡
 */
@Entity
@Table(name = "deus")

public class Deus implements java.io.Serializable {


	private String deusId;
	private Partymember partymember;
	private Period period;
	private Float cost;


	public Deus() {
	}

	public Deus(Partymember partymember, Period period, Float cost) {
		this.partymember = partymember;
		this.period = period;
		this.cost = cost;
	}

	@GenericGenerator(name = "generator", strategy = "uuid.hex")
	@Id
	@GeneratedValue(generator = "generator")

	@Column(name = "deus_id", unique = true, nullable = false, length = 32)

	public String getDeusId() {
		return this.deusId;
	}

	public void setDeusId(String deusId) {
		this.deusId = deusId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id")

	public Partymember getPartymember() {
		return this.partymember;
	}

	public void setPartymember(Partymember partymember) {
		this.partymember = partymember;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "pid")

	public Period getPeriod() {
		return this.period;
	}

	public void setPeriod(Period period) {
		this.period = period;
	}

	@Column(name = "cost", precision = 12, scale = 0)

	public Float getCost() {
		return this.cost;
	}

	public void setCost(Float cost) {
		this.cost = cost;
	}

}