package com.partysys.partymanage.period.action;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.partysys.core.action.BaseAction;
import com.partysys.core.exception.ActionException;
import com.partysys.core.permission.impl.PermissionCheckImpl;
import com.partysys.core.util.ExcelUtil;
import com.partysys.core.util.QueryHelper;
import com.partysys.partymanage.deus.entity.Deus;
import com.partysys.partymanage.deus.service.DeusService;
import com.partysys.partymanage.period.entity.Period;
import com.partysys.partymanage.period.service.PeriodService;
import com.partysys.sysmanage.branch.service.BranchService;
import com.partysys.sysmanage.party.entity.Partymember;
import com.partysys.sysmanage.party.service.PartymemberService;
/**
 * 汇总党费Action
 * @author 朱可凡
 *
 */
public class PeriodAction extends BaseAction{
	@Autowired
	private PeriodService periodService;
	@Autowired
	private PartymemberService partymemberService;
	@Autowired
	private DeusService deusService;
	@Autowired
	private BranchService branchService;
	
	
	/**
	 * 接受从前台传递的period
	 */
	private Period period;
	private String strTitle;
	
	/**
	 * 显示列表页面
	 * @return
	 * @throws Exception
	 */
	public String listUI() throws Exception {
		QueryHelper helper = new QueryHelper(Period.class, "p");
		if (period != null) {
			if (StringUtils.isNotBlank(period.getDate())) {
				period.setDate(URLDecoder.decode(period.getDate(), "utf-8"));
				helper.addWhereClause("p.date like ?", "%" + period.getDate() + "%");
			}
		}
		Partymember pm = (Partymember) ServletActionContext.getContext().getSession().get("SYS_USER");
		if (pm.getRolepartymembers() != null) {
			//若用户是学生汇总支部管理员，那么只显示学生创建的
			if (new PermissionCheckImpl().isAccess(pm, "studentsumcash")) {
				helper.addInClause("p.creator IN (", Period.CREATOR_STUDENT, false);
			}
			//只显示教师创建的
			if (new PermissionCheckImpl().isAccess(pm, "teachersumcash")) {
				helper.addInClause("p.creator IN (", Period.CREATOR_TEACHER, true);
			}
		}
		pageResult = periodService.findByPage(helper, getPageNo(), getPageSize());
		return "listUI";
	}
	/**
	 * 增加页面
	 * @return
	 * @throws ActionException
	 */
	public String add() throws ActionException {
		try {
			if (period != null) {
				Partymember pm = (Partymember) ServletActionContext.getContext().getSession().get("SYS_USER");
				//设置该期数是谁创建的（是老师还是学生?）
				if (pm.getRolepartymembers() != null) {
					if (new PermissionCheckImpl().isAccess(pm, "studentsumcash")) {
						//学生汇总书记在创建期数的时候就向党费表中插入数据{
						period.setCreator(Period.CREATOR_STUDENT);
						saveDeusAndPartymember(period.getCreator());
					}
					if (new PermissionCheckImpl().isAccess(pm, "teachersumcash")) {
						period.setCreator(Period.CREATOR_TEACHER);
						//教师汇总书记在创建期数的时候就向党费表中插入数据
						saveDeusAndPartymember(period.getCreator());
					}
					if (!new PermissionCheckImpl().isAccess(pm, "studentsumcash") 
							|| !new PermissionCheckImpl().isAccess(pm, "teachersumcash")) {
						return "list";
					}
				}
				periodService.save(period);
				period = null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ActionException("Action层出现异常,异常信息："+e.getMessage());
		}
		return "list";
	}
	/**
	 * 保存党费和党员
	 * @param classification: 创建期数的人是学生汇总书记还是教师汇总书记
	 */
	private void saveDeusAndPartymember(String classification) {
		QueryHelper helper = new QueryHelper(Partymember.class, "p");
		//若创建人是学生汇总党员，那么其针对的仅仅只是学生
		if (Period.CREATOR_STUDENT.equals(classification))
			helper.addWhereClause("p.classification=?", Partymember.USER_STUDENT);
		else
			//若创建人是教师汇总党员，那么其针对的仅仅只是教师
			helper.addWhereClause("p.classification=?", Partymember.USER_TEACHER);
		List<Partymember> list = partymemberService.find(helper);
		Set<Deus> deuses = new HashSet<>();
		for (Partymember p : list) {
			Deus deus = new Deus();
			//设置党费和党员及期数的关联关系
			deus.setPartymember(p);
			deus.setPeriod(period);
			deuses.add(deus);
		}
		//设置期数中的党费(即期数中有多少党费)
		period.setDeus(deuses);
		//设置期数中的党员(即期数中有多少党员)
		period.setPartymembers(new HashSet<>(list));	
	}
	
	/**
	 * 编辑页面
	 * @return
	 * @throws Exception
	 */
	public String edit() throws Exception{
		try {
			if (period != null) {
				Period myperiod = periodService.findById(period.getPeriodId());
				myperiod.setDate(period.getDate());
				periodService.update(myperiod);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ActionException("Action层出现异常，异常信息："+e.getMessage());
		}
		return "list";
	}
	/**
	 * 验证数据库中是否有这一期
	 */
	public void verify() throws Exception{
		try {
			if (period != null && period.getDate() != null) {
				Period experiod = periodService.findPeriodByIdAndDate(period.getPeriodId(), period.getDate());
				String strResult = "true";
				if (experiod != null) {
					strResult = "false";
				}
				HttpServletResponse response = ServletActionContext.getResponse();
				response.setContentType("text/html");
				ServletOutputStream output = response.getOutputStream();
				output.write(strResult.getBytes());
				output.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ActionException("Action层异常发生,异常信息: " + e.getMessage());
		}
	}
	/**
	 * 删除期数
	 * @return
	 * @throws Exception
	 */
	public String delete() throws Exception{
		try {
			if (period != null && period.getPeriodId() != null) {
				periodService.delete(period.getPeriodId());
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ActionException("Action层出现问题，原因: " + e.getMessage());
		}
		return "list";
	}
	/**
	 * 批量删除期数
	 * @return
	 * @throws Exception
	 */
	public String deleteSelected() throws Exception{
		try {
			if (selectedRow != null) {
				for (String id : selectedRow) {
					periodService.delete(id);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ActionException("Action层出现异常,异常信息是: " + e.getMessage());
		}
		return "list";
	}
	/**
	 * 导出党费缴纳清单汇总表
	 */
	public void exportExcel() throws Exception{
		try {
			Partymember pm = (Partymember) ServletActionContext.getRequest().getSession().getAttribute("SYS_USER");
			List<Deus> list = new ArrayList<>();
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setContentType("application/x-excel");
			response.setHeader("Content-Disposition", "attachment;filename=" + new String("数计学院学生党支部党费收缴单".getBytes(), "ISO-8859-1"));
			ServletOutputStream out = response.getOutputStream();
			if (new PermissionCheckImpl().isAccess(pm, "studentsumcash")) {
				//导出学生党费
				QueryHelper helper = new QueryHelper(Deus.class, "d");
				helper.addWhereClause("d.partymember.classification=?", Partymember.USER_STUDENT);
				list = deusService.find(helper);
			}
			if (new PermissionCheckImpl().isAccess(pm, "teachersumcash")) {
				//导出教师党费
				QueryHelper helper = new QueryHelper(Deus.class, "d");
				helper.addWhereClause("d.partymember.classification=?", Partymember.USER_TEACHER);
				list = deusService.find(helper);
			}
			ExcelUtil.exportDeusExcelFor(list, out, branchService);
			if (out != null) {
				out.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ActionException("Action层出现异常,异常信息是: " + e.getMessage());
		}
	}
	public Period getPeriod() {
		return period;
	}

	public void setPeriod(Period period) {
		this.period = period;
	}

	public PeriodService getPeriodService() {
		return periodService;
	}

	public void setPeriodService(PeriodService periodService) {
		this.periodService = periodService;
	}
	public String getStrTitle() {
		return strTitle;
	}
	public void setStrTitle(String strTitle) {
		this.strTitle = strTitle;
	}
	public PartymemberService getPartymemberService() {
		return partymemberService;
	}
	public void setPartymemberService(PartymemberService partymemberService) {
		this.partymemberService = partymemberService;
	}
	public DeusService getDeusService() {
		return deusService;
	}
	public void setDeusService(DeusService deusService) {
		this.deusService = deusService;
	}
	public BranchService getBranchService() {
		return branchService;
	}
	public void setBranchService(BranchService branchService) {
		this.branchService = branchService;
	}
}
