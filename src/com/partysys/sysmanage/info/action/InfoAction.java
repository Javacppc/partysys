package com.partysys.sysmanage.info.action;

import java.net.URLDecoder;
import java.sql.Timestamp;
import java.util.Date;

import javax.servlet.ServletOutputStream;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionContext;
import com.partysys.core.action.BaseAction;
import com.partysys.core.exception.ActionException;
import com.partysys.core.util.QueryHelper;
import com.partysys.sysmanage.info.entity.Info;
import com.partysys.sysmanage.info.service.InfoService;
/**
 * 发布信息页面的业务逻辑层
 * @author 朱可凡
 *
 */
public class InfoAction extends BaseAction{
	
	@Autowired
	private InfoService infoService;
	/**
	 * 接受发布的信息
	 */
	private Info info;
	/**
	 * 接受用户在搜索框中输入的值
	 */
	private String strTitle;
	/**
	 * 显示信息页面
	 * @return
	 * @throws Exception
	 */
	public String listUI() throws Exception{
		try {
			//设置在发布信息页面的时候实现显示信息发布类型(是通知公告？新闻？还是党建工作动态)
			ActionContext.getContext().getContextMap().put("infoTypeMap", Info.INFO_TYPE_MAP);
			QueryHelper helper = new QueryHelper(Info.class, "i");//初始化查询工具类并执行类别名
			if (info != null) {
				//如果搜索框并不空的话
				if (StringUtils.isNotBlank(info.getTitle())) {
					//将地址栏中传来的查询参数解码转换为中文
					info.setTitle(URLDecoder.decode(info.getTitle(),"utf-8"));
					//增加模糊查询条件
					helper.addWhereClause("i.title like ?", "%" + info.getTitle() + "%");
				}
				//helper.addWhereClause("i.state = ?", "1");
				//按照时间降序排序
				helper.addOrderByClause("i.createTime", QueryHelper.ORDER_DESC);
			}
			//System.out.println(getPageSize());
			//分页查询查询出结果
			pageResult = infoService.findByPage(helper,getPageNo(),getPageSize());
		} catch (Exception e) {
			e.printStackTrace();
			throw new ActionException("Action层出现异常，异常信息："+e.getMessage());
		}
		return "listUI";
	}
	/**
	 * 跳转到新增页面
	 * @return
	 */
	public String addUI() throws Exception{
		try {
			ActionContext.getContext().getContextMap().put("infoTypeMap", Info.INFO_TYPE_MAP);
			if (info != null) {
				//将搜索框中的值存入strTitle中---为以后添加成功后仍然保持页面结果而用
				strTitle = info.getTitle();
			}
			//某次请求用户有可能没有在搜索框中输入结果所以要new Info()
			info = new Info();
			//设置管理员发布信息的时间（该时间是由系统自动生成的）
			info.setCreateTime(new Timestamp(new Date().getTime()));
		} catch (Exception e) {
			e.printStackTrace();
			throw new ActionException("Action层出现异常，异常信息："+e.getMessage());
		}
		return "addUI";
	}
	
	/**
	 * 保存新增
	 * @return
	 */
	public String add() throws Exception{
		try {
			if (info != null) {
				infoService.save(info);
			}
			//添加成功后需要保留用户原来输入在搜索框中的内容，所以此处需要设置为空
			info = null;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ActionException("Action层出现异常，异常信息："+e.getMessage());
		}
		//信息发布成功后跳转（其实是重定向）到显示信息页面
		return "list";
	}
	/**
	 * 跳转到编辑页面
	 * @return
	 * @throws Exception
	 */
	public String editUI() throws Exception{
		try {
			ActionContext.getContext().getContextMap().put("infoTypeMap", Info.INFO_TYPE_MAP);
			if (info != null && info.getInfoId() != null) {
				strTitle = info.getTitle();
				info = infoService.findById(info.getInfoId());
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ActionException("Action层出现异常，异常信息："+e.getMessage());
		}
		return "editUI";
	}
	/**
	 * 对信息进行编辑
	 * @return
	 * @throws Exception
	 */
	public String edit() throws Exception{
		try {
			if (info != null) {
				infoService.update(info);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ActionException("Action层出现异常，异常信息："+e.getMessage());
		}
		//信息编辑成功后重定向到显示信息页面
		return "list";
	}
	/**
	 * 删除信息
	 * @return
	 */
	public String delete() throws Exception{
		try {
			if (info != null && info.getInfoId() != null) {
				strTitle = info.getTitle();
				infoService.delete(info.getInfoId());
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ActionException("Action层出现异常，异常信息："+e.getMessage());
		}
		//删除完成后跳转到显示信息页面
		return "list";
	}
	/**
	 * 批量删除
	 * @return
	 */
	public String deleteSelected() throws Exception{
		try {
			//删除用户选中的信息记录
			if (selectedRow != null) {
				for (String id : selectedRow) {
					strTitle = info.getTitle();
					infoService.delete(id);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ActionException("Action层出现异常，异常信息："+e.getMessage());
		}
		//删除完成后重定向到显示信息页面
		return "list";
	}
	/**
	 * 实现异步信息发布
	 */
	public void publicInfo() throws Exception{
		try {
			if (info != null) {
				//根据Ajax发送过来的信息找到相应的信息数据
				Info newInfo = infoService.findById(info.getInfoId());
				//更新信息目前的状态
				newInfo.setState(info.getState());
				infoService.update(newInfo);
				
				//返回成功信息给Ajax
				ServletOutputStream out = ServletActionContext.getResponse().getOutputStream();
				out.write(new String("更新状态成功").getBytes("UTF-8"));
				out.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ActionException("Action层出现异常，异常信息是：" + e.getMessage());
		}
	}
	public InfoService getInfoService() {
		return infoService;
	}
	public void setInfoService(InfoService infoService) {
		this.infoService = infoService;
	}
	public Info getInfo() {
		return info;
	}
	public void setInfo(Info info) {
		this.info = info;
	}
	public String getStrTitle() {
		return strTitle;
	}
	public void setStrTitle(String strTitle) {
		this.strTitle = strTitle;
	}
	
}
