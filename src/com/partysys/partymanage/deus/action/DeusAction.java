package com.partysys.partymanage.deus.action;

import java.net.URLDecoder;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.partysys.core.action.BaseAction;
import com.partysys.core.exception.ActionException;
import com.partysys.core.permission.impl.PermissionCheckImpl;
import com.partysys.core.util.QueryHelper;
import com.partysys.partymanage.deus.entity.Deus;
import com.partysys.partymanage.deus.service.DeusService;
import com.partysys.partymanage.period.entity.Period;
import com.partysys.partymanage.period.service.PeriodService;
import com.partysys.sysmanage.party.entity.Partymember;
import com.partysys.sysmanage.party.service.PartymemberService;
/**
 * 党费管理Action(针对普通管理员)
 * @author 朱可凡
 *
 */
public class DeusAction extends BaseAction{
	@Autowired
	private DeusService deusService;
	@Autowired
	private PeriodService periodService;
	@Autowired
	private PartymemberService partymemberService;
	
	private Period period;
	private String strTitle;
	private Deus deus;
	private Partymember partymember;
	
	
	/**
	 * 显示所有期数列表,管理员可以在相应的期数上进行党费的录入
	 * @return
	 */
	public String listUI() throws Exception{
		try {
			QueryHelper helper = new QueryHelper(Period.class, "p");
			
			if (period != null) {
				if (StringUtils.isNotBlank(period.getDate())) {
					period.setDate(URLDecoder.decode(period.getDate(), "utf-8"));
					helper.addWhereClause("p.date like ?", period.getDate());
				}
			}
			Partymember pm = (Partymember) ServletActionContext.getRequest().getSession().getAttribute("SYS_USER");
			//如果管理员是学生支部书记那么就只能显示学生汇总支部书记创建的期数
			//教师的亦是如此
			//如果学生和教师都有，那么就全部显示
			PermissionCheckImpl pc = new PermissionCheckImpl();
			if (pc.isAccess(pm, "studentmanage") || pc.isAccess(pm, "studentsumcash")) {
				helper.addInClause("p.creator IN (", Period.CREATOR_STUDENT, false);
			}
			if (pc.isAccess(pm, "teachersumcash") || pc.isAccess(pm, "Tmanage")){
				helper.addInClause("p.creator IN (", Period.CREATOR_TEACHER, true);
			}
			if (!pc.isAccess(pm, "studentmanage") 
					|| !pc.isAccess(pm, "studentsumcash")
					|| !pc.isAccess(pm, "teachersumcash")
					|| !pc.isAccess(pm, "Tmanage")) {
				//若不具有支部书记的权限，直接返回空页面
				return "listUI";
			}
			pageResult = periodService.findByPage(helper, getPageNo(), getPageSize());
		} catch (Exception e) {
			e.printStackTrace();
			throw new ActionException("Action层出现异常, 异常信息: " + e.getMessage());
		}
		return "listUI";
	}
	/**
	 * 录入党员交党费
	 * @return
	 */
	//@SuppressWarnings("deprecation")
	public String entryUI() throws Exception{
		try {
			//跳转到录入党费页面
			Partymember pm = (Partymember) ServletActionContext.getRequest().getSession().getAttribute("SYS_USER");
			if (period != null && period.getPeriodId() != null) {
				//如果该角色没有汇总权限，那么只能显示其管理部分和称呼的列表
				//例如普通数学支部书记只能录入数学支部和学生党员的信息
				Period pd = periodService.findById(period.getPeriodId());
				String type = pd.getCreator();
				//只能录入该部门的党员
				String branchName = null;
				if (pm.getBranch() != null)
					branchName = pm.getBranch().getBranchName();
				QueryHelper helper = new QueryHelper(Deus.class, "d");
				if (partymember != null && partymember.getName() != null) {
					helper.addWhereClause("d.partymember.name=?", partymember.getName());
				}
				helper.addWhereClause("d.period.periodId=?", period.getPeriodId());
				//String hql = "FROM (Deus d LEFT OUTER JOIN d.partymember pm WHERE pm.classification=? AND pm.branch.branchName=?) LEFT OUTER JOIN Period p WHERE p.periodId=?";
				//String hql = "FROM (Deus d inner join d.partymember pm where pm.classification=?1 AND pm.branch.branchName=?2) inner join d.period pr where pr.periodId=?3";
				if (new PermissionCheckImpl().isAccess(pm, "teachersumcash") 
						&& new PermissionCheckImpl().isAccess(pm, "studentsumcash")) {
					//-----------下面5句仅为测试-----------
					/*if (Period.CREATOR_STUDENT.equals(type))
						helper.addWhereClause("d.partymember.classification=?", Partymember.USER_STUDENT);
					else 
						helper.addWhereClause("d.partymember.classification=?", Partymember.USER_TEACHER);
					helper.addWhereClause("d.partymember.branch.branchName=?", branchName);*/
				} else {
					if (type != null && branchName != null){
						if (Period.CREATOR_STUDENT.equals(type))
							helper.addWhereClause("d.partymember.classification=?", Partymember.USER_STUDENT);
						else 
							helper.addWhereClause("d.partymember.classification=?", Partymember.USER_TEACHER);
						helper.addWhereClause("d.partymember.branch.branchName=?", branchName);
						//pageResult = deusService.findByPage(hql, getPageNo(), getPageSize(), new Object[]{type, branchName, period.getPeriodId()});
					} else {
						//若管理员信息不完整则不能显示数据
						return "entryUI";
					}
				}
				pageResult = deusService.findByPage(helper, getPageNo(), getPageSize());
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new ActionException("Action层出现异常,异常信息是: " + e.getMessage());
		}
		return "entry";
	}
	/**
	 * 录入相应党员的党费
	 * @return
	 */
	public String entry() {
		//录入完成后跳转到录入党员页面
		if (deus != null && deus.getDeusId() != null) {
			Deus newdeus = deusService.findById(deus.getDeusId());
			newdeus.setCost(deus.getCost());
			if (deus.getManager() != null) {
				newdeus.setManager(deus.getManager());
			}
			deusService.update(newdeus);
		}
		return "entryUI";
	}
	
	
	public DeusService getDeusService() {
		return deusService;
	}

	public void setDeusService(DeusService deusService) {
		this.deusService = deusService;
	}

	public PeriodService getPeriodService() {
		return periodService;
	}

	public void setPeriodService(PeriodService periodService) {
		this.periodService = periodService;
	}
	public PartymemberService getPartymemberService() {
		return partymemberService;
	}
	public void setPartymemberService(PartymemberService partymemberService) {
		this.partymemberService = partymemberService;
	}
	public Period getPeriod() {
		return period;
	}
	public void setPeriod(Period period) {
		this.period = period;
	}
	public String getStrTitle() {
		return strTitle;
	}
	public void setStrTitle(String strTitle) {
		this.strTitle = strTitle;
	}
	public Deus getDeus() {
		return deus;
	}
	public void setDeus(Deus deus) {
		this.deus = deus;
	}
	public Partymember getPartymember() {
		return partymember;
	}
	public void setPartymember(Partymember partymember) {
		this.partymember = partymember;
	}
}
