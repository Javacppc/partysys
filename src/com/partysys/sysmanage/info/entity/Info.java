package com.partysys.sysmanage.info.entity;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 信息（新闻，通知公告，党建工作动态）实体类
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "info")

public class Info implements java.io.Serializable {

	
	private String infoId;
	
	/**
	 * 信息类型
	 */
	private String type;
	
	/**
	 * 信息来源
	 */
	private String source;
	/**
	 * 信息内容
	 */
	private String content;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 信息创建人
	 */
	private String creator;
	/**
	 * 信息创建时间
	 */
	private Timestamp createTime;
	/**
	 * 信息标题
	 */
	private String title;
	/**
	 * 信息状态
	 */
	private String state;
	//信息状态
	/**
	 * 信息为发布状态
	 */
	private static String INFO_STATE_PUBLIC = "1";
	/**
	 * 信息为停用状态
	 */
	private static String INFO_STATE_STOP = "0";
	
	//信息分类
	/**
	 * 信息分类：通知公告
	 */
	private static final String INFO_TYPE_REPORT = "REPORT";
	/**
	 * 信息分类：党建工作动态
	 */
	private static final String INFO_TYPE_DYNAMIC = "DYNAMIC";
	/**
	 * 信息分类
	 */
	private static final String INFO_TYPE_NEWS = "NEWS";
	public static Map<String, String> INFO_TYPE_MAP = null;
	static {
		INFO_TYPE_MAP = new HashMap<>();
		INFO_TYPE_MAP.put(INFO_TYPE_REPORT, "通知公告");
		INFO_TYPE_MAP.put(INFO_TYPE_DYNAMIC, "党建工作动态");
		INFO_TYPE_MAP.put(INFO_TYPE_NEWS, "新闻");
	}
	public Info() {
	}

	public Info(Timestamp createTime, String title) {
		this.createTime = createTime;
		this.title = title;
	}

	public Info(String source, String content, String remark, String creator, Timestamp createTime, String title) {
		this.source = source;
		this.content = content;
		this.remark = remark;
		this.creator = creator;
		this.createTime = createTime;
		this.title = title;
	}

	@GenericGenerator(name = "generator", strategy = "uuid.hex")
	@Id
	@GeneratedValue(generator = "generator")

	@Column(name = "info_id", unique = true, nullable = false, length = 32)

	public String getInfoId() {
		return this.infoId;
	}

	public void setInfoId(String infoId) {
		this.infoId = infoId;
	}

	@Column(name = "source", length = 1024)

	public String getSource() {
		return this.source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	@Column(columnDefinition="text")

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Column(name = "remark", length = 1024)

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "creator", length = 1024)

	public String getCreator() {
		return this.creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	@Column(name = "createTime", nullable = false, length = 19)

	public Timestamp getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	@Column(name = "title", nullable = false, length = 1024)

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	@Column(name="state", length=10)
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	@Column(name="type", length=15)
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	
}