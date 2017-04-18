package com.partysys.test.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
@Entity
@Table(name="party_person_test")
public class Person implements Serializable {
	
	private String id;
	
	private String name;
	public Person() {
		
	}
	public Person(String id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public Person(String name) {
		super();
		this.name = name;
	}
	@Id
	//该主键生成器的名字是fk_uuid  使用Hibernate的uuid策略
	@GenericGenerator(name="fk_uuid", strategy="uuid.hex")
	//使用fk_uuid主键生成器
	@GeneratedValue(generator="fk_uuid")
	@Column(length=32, name="person_id")
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	//该列不能为空值
	@Column(name="person_name", length=20, nullable=false)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		return "纳税人：" + name;
	}
	
}
