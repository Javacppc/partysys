package com.partysys.partymanage.period.action;

import java.net.URLDecoder;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.partysys.core.action.BaseAction;
import com.partysys.core.exception.ActionException;
import com.partysys.core.util.QueryHelper;
import com.partysys.partymanage.period.entity.Period;
import com.partysys.partymanage.period.service.PeriodService;
import com.partysys.sysmanage.party.entity.Partymember;
/**
 * 汇总党费Action
 * @author 朱可凡
 *
 */
public class PeriodAction extends BaseAction{
	@Autowired
	private PeriodService periodService;
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
	public String listUI() throws Exception{
		QueryHelper helper = new QueryHelper(Period.class, "p");
		if (period != null) {
			if (StringUtils.isNotBlank(period.getDate())) {
				period.setDate(URLDecoder.decode(period.getDate(), "utf-8"));
				helper.addWhereClause("p.date like ?", "%" + period.getDate() + "%");
			}
		}
		Partymember pm = (Partymember) ServletActionContext.getContext().getSession().get("SYS_USER");
		//若用户是学生汇总支部管理员，那么只显示学生创建的
		if (pm.getClassification() != null) {
			if (pm.getClassification().equals(Partymember.USER_STUDENT)) {
				helper.addWhereClause("p.creator=?", Period.CREATOR_STUDENT);
			}
			//只显示教师创建的
			else if (pm.getClassification().equals(Partymember.USER_TEACHER)) {
				helper.addWhereClause("p.creator=?", Period.CREATOR_TEACHER);
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
				if (pm.getClassification() != null) {
					if (pm.getClassification().equals(Partymember.USER_STUDENT)) {
						period.setCreator(Period.CREATOR_STUDENT);
					} else if (pm.getClassification().equals(Partymember.USER_TEACHER)) {
						period.setCreator(Period.CREATOR_TEACHER);
					}
				}
				periodService.save(period);
			}
			period = null;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ActionException("Action层出现异常,异常信息："+e.getMessage());
		}
		return "list";
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
				System.out.println(period.getDate());
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
		
		return "list";
	}
	/**
	 * 批量删除期数
	 * @return
	 * @throws Exception
	 */
	public String deleteSelected() throws Exception{
		
		return "list";
	}
	/**
	 * 导出党费缴纳清单汇总表
	 */
	public void exportExcel() {
		
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
}
