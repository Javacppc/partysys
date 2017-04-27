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
			PermissionCheckImpl pc = new PermissionCheckImpl();
			if (pc.isAccess(pm, "studentmanage") || pc.isAccess(pm, "studentsumcash")) {
				helper.addWhereClause("p.creator", Period.CREATOR_STUDENT);
			} else if (pc.isAccess(pm, "teachersumcash") || pc.isAccess(pm, "Tmanage")){
				helper.addWhereClause("p.creator", Period.CREATOR_TEACHER);
			}
			pageResult = periodService.findByPage(helper, getPageNo(), getPageSize());
		} catch (Exception e) {
			e.printStackTrace();
			throw new ActionException("Action层出现异常");
		}
		return "listUI";
	}
	/**
	 * 录入党员交党费
	 * @return
	 */
	public String entryUI() throws Exception{
		try {
			//跳转到录入党费页面
			Partymember pm = (Partymember) ServletActionContext.getRequest().getSession().getAttribute("SYS_USER");
			QueryHelper helper = null;
			if (period != null && period.getPeriodId() != null) {
				//如果该角色没有汇总权限，那么只能显示其管理部分和称呼的列表
				//例如普通数学支部书记只能录入数学支部和学生党员的信息
				String type = pm.getClassification();
				//只能录入该部门的党员
				String branchName = pm.getBranch().getBranchName();
				helper = new QueryHelper(Deus.class, "d");
				helper.addWhereClause("d.period.periodId=?", period.getPeriodId());
				if (type != null && branchName != null){
					helper.addWhereClause("d.partymember.classification=?", type);
					helper.addWhereClause("d.partymember.branch.branchName=?", branchName);
				} else {
					//若管理员信息不完整则不能显示数据
					return "entryUI";
				}
			}
			pageResult = deusService.findByPage(helper, getPageNo(), getPageSize());
		} catch (Exception e) {
			e.printStackTrace();
			throw new ActionException("Action层出现异常,异常信息是: " + e.getMessage());
		}
		return "entryUI";
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
	
}
