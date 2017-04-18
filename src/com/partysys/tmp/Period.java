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
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.GenericGenerator;

/**
 * Period entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "period", catalog = "partysystem")

public class Period implements java.io.Serializable {


	private String pid;
	private Date pdata;
	private Set<Deus> deuses = new HashSet<Deus>(0);
	private Set<Partymember> partymembers = new HashSet<Partymember>(0);


	public Period() {
	}

	public Period(Date pdata) {
		this.pdata = pdata;
	}

	public Period(Date pdata, Set<Deus> deuses, Set<Partymember> partymembers) {
		this.pdata = pdata;
		this.deuses = deuses;
		this.partymembers = partymembers;
	}

	@GenericGenerator(name = "generator", strategy = "uuid.hex")
	@Id
	@GeneratedValue(generator = "generator")

	@Column(name = "pid", unique = true, nullable = false, length = 30)

	public String getPid() {
		return this.pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "pdata", nullable = false, length = 10)

	public Date getPdata() {
		return this.pdata;
	}

	public void setPdata(Date pdata) {
		this.pdata = pdata;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "period")

	public Set<Deus> getDeuses() {
		return this.deuses;
	}

	public void setDeuses(Set<Deus> deuses) {
		this.deuses = deuses;
	}

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable(name = "partymember_period", joinColumns = {
			@JoinColumn(name = "pid", nullable = false, updatable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "id", nullable = false, updatable = false) })

	public Set<Partymember> getPartymembers() {
		return this.partymembers;
	}

	public void setPartymembers(Set<Partymember> partymembers) {
		this.partymembers = partymembers;
	}

}