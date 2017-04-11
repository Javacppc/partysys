package com.partysys.sysmanage.party.entity;

import java.util.Date;
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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import com.partysys.sysmanage.branch.entity.Branch;

/**
 * 党员实体类
 * @author 朱可凡
 */
@Entity
@Table(name = "partymember", catalog = "partysys")

public class Partymember implements java.io.Serializable {

	/**
	 * 党员实体Id
	 */
	private String id;
	/**
	 * 密码
	 */
	private String password;
	/**
	 * 党员所属支部，外键字段
	 */
	private Branch branch;
	/**
	 * 党员姓名
	 */
	private String name;
	/**
	 * 党员性别
	 */
	private boolean gender;
	/**
	 * 学工号，登陆时用的就是这个字段的值,该字段非空
	 */
	private String number;
	/**
	 * 民族
	 */
	private String nation;
	/**
	 * 身份证号
	 */
	private String identity;
	/**
	 * 籍贯
	 */
	private String province;
	/**
	 * 联系方式
	 */
	private String telenumber;
	/**
	 * 年级
	 */
	private String grade;
	/**
	 * 所属专业班级
	 */
	private String pclass;
	/**
	 * 培养联系人
	 */
	private String[] cultivate;
	/**
	 * 类别（是学生？老师？）
	 */
	private String classification;
	/**
	 * 支部名称
	 */
	private String branchName;
	/**
	 * 志愿书编码
	 */
	private String textnumber;
	/**
	 * 入党日期(年-月-日)
	 */
	private Date joinTime;
	/**
	 * 备注
	 */
	private String remake;
	/**
	 * 出生年月
	 */
	private Date birthday;
	/**
	 * 宿舍
	 */
	private String dorm;
	/**
	 * 递交入党申请书事件(年-月)
	 */
	private Date appTime;
	/**
	 * 党校结业事件（年-月）
	 */
	private Date graTime;
	/**
	 * 用户头像地址（在服务器中的位置）
	 */
	private String headImg;
	/**
	 * 党员状态（有效？无效？）---无效用户无法登陆
	 */
	private String state;
	/**
	 * 党员所具有的角色
	 */
	private Set<Rolepartymember> rolepartymembers = new HashSet<Rolepartymember>(0);
	//用户状态
	/**
	 * 用户状态有效
	 */
	public static String USER_STATE_VALID = "1";
	/**
	 * 用户状态无效
	 */
	public static String USER_STATE_INVALID = "0";
	
	//用户是老师还是学生
	public static final String USER_TEACHER = "TEACHER";
	public static final String USER_STUDENT = "STUDENT";

	
	public Partymember() {
	}
	
	public Partymember(String id) {
		super();
		this.id = id;
	}

	public Partymember(Branch branch, String name, boolean gender, String number, String nation, String identity,
			String province, String telenumber, String grade, String class_, String[] cultivate, String classification,
			String branchId, String textnumber, Date joinTime, String roleId, String remake,
			Set<Rolepartymember> rolepartymembers) {
		this.branch = branch;
		this.name = name;
		this.gender = gender;
		this.number = number;
		this.nation = nation;
		this.identity = identity;
		this.province = province;
		this.telenumber = telenumber;
		this.grade = grade;
		this.pclass = class_;
		this.cultivate = cultivate;
		this.classification = classification;
		this.branchName = branchId;
		this.textnumber = textnumber;
		this.joinTime = joinTime;
		this.remake = remake;
		this.rolepartymembers = rolepartymembers;
	}

	@GenericGenerator(name = "generator", strategy = "uuid.hex")
	@Id
	@GeneratedValue(generator = "generator")

	@Column(name = "id", unique = true, nullable = false, length = 32)

	public String getId() {
		return this.id;
	}
	
	
	public void setId(String id) {
		this.id = id;
	}
	@Column(name="password", length=25)
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name="user_state", length=1)
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@Column(name="head_img", length=100)
	public String getHeadImg() {
		return headImg;
	}

	public void setHeadImg(String headImg) {
		this.headImg = headImg;
	}
	@Column(name="appTime", length=15)
	public Date getAppTime() {
		return appTime;
	}

	public void setAppTime(Date appTime) {
		this.appTime = appTime;
	}
	@Column(name="graTime", length=15)
	public Date getGraTime() {
		return graTime;
	}

	public void setGraTime(Date graTime) {
		this.graTime = graTime;
	}

	@Column(name="birthday",length=20)
	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	
	@Column(name="dorm",length=10)
	
	public String getDorm() {
		return dorm;
	}

	public void setDorm(String dorm) {
		this.dorm = dorm;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "branch_id")

	public Branch getBranch() {
		return this.branch;
	}

	public void setBranch(Branch branch) {
		this.branch = branch;
	}

	@Column(name = "name", length = 30, nullable=false)

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "gender")

	public boolean getGender() {
		return this.gender;
	}

	public void setGender(boolean gender) {
		this.gender = gender;
	}

	@Column(name = "number", length = 30, nullable=false)

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

	public String getPclass() {
		return this.pclass;
	}

	public void setPclass(String class_) {
		this.pclass = class_;
	}
	//集合属性，保存党员对应的培养人
	@ElementCollection(targetClass=String.class)
	//映射保存集合元素的表
	@CollectionTable(name="cultivate_person",joinColumns=@JoinColumn(name="person_id",nullable=false))
	//指定保存集合元素的列
	@Column(name = "cultivate", length = 20)
	//映射集合元素的索引列
	@OrderColumn(name="t_order")
	public String[] getCultivate() {
		return this.cultivate;
	}

	public void setCultivate(String[] cultivate) {
		this.cultivate = cultivate;
	}

	@Column(name = "classification", length = 10)

	public String getClassification() {
		return this.classification;
	}

	public void setClassification(String classification) {
		this.classification = classification;
	}

	@Column(name = "branchname", length = 20)

	public String getBranchName() {
		return this.branchName;
	}

	public void setBranchName(String branchId) {
		this.branchName = branchId;
	}

	@Column(name = "textnumber", length = 30)

	public String getTextnumber() {
		return this.textnumber;
	}

	public void setTextnumber(String textnumber) {
		this.textnumber = textnumber;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "joinTime", length = 10)

	public Date getJoinTime() {
		return this.joinTime;
	}

	public void setJoinTime(Date joinTime) {
		this.joinTime = joinTime;
	}

	

	@Column(name = "remake", length = 50)

	public String getRemake() {
		return this.remake;
	}

	public void setRemake(String remake) {
		this.remake = remake;
	}

	@OneToMany(cascade=CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "partymember")
	public Set<Rolepartymember> getRolepartymembers() {
		return this.rolepartymembers;
	}

	public void setRolepartymembers(Set<Rolepartymember> rolepartymembers) {
		this.rolepartymembers = rolepartymembers;
	}
}