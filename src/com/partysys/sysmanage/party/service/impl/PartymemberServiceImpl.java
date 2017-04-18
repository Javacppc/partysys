package com.partysys.sysmanage.party.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletOutputStream;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.partysys.core.exception.ServiceException;
import com.partysys.core.service.impl.BaseServiceImpl;
import com.partysys.core.util.ExcelUtil;
import com.partysys.core.util.QueryHelper;
import com.partysys.sysmanage.branch.entity.Branch;
import com.partysys.sysmanage.branch.service.BranchService;
import com.partysys.sysmanage.party.dao.PartymemberDao;
import com.partysys.sysmanage.party.entity.Partymember;
import com.partysys.sysmanage.party.entity.Rolepartymember;
import com.partysys.sysmanage.party.entity.RolepartymemberId;
import com.partysys.sysmanage.party.service.PartymemberService;
import com.partysys.sysmanage.role.entity.Role;
@Service("partymemberService")
public class PartymemberServiceImpl extends BaseServiceImpl<Partymember> implements PartymemberService{
	private PartymemberDao partymemberDao;
	@Autowired
	public void setPartymemberDao(PartymemberDao partymemberDao) {
		super.setBaseDao(partymemberDao);
		this.partymemberDao = partymemberDao;
	}
	
	//TODO: BUG CAN'T BE FIXED 2017_4_11_16_24
	@Override
	public void saveUserAndRoles(Partymember partymember, String[] roleIds) {
		//BUG METHOD
		/*//先保存党员信息（先保存主表记录）
		save(partymember);
		//再保存用户所具有的角色(从表记录),如果没有选择角色那么就什么也不做
		if (roleIds != null) {
			for (String roleId : roleIds) {
				partymemberDao.saveUserRole(new Rolepartymember(new Partymember(partymember.getId()), new Role(roleId)));
			}
		}*/
		//如果填写了角色信息
		/*if (roleIds != null) {
			for (String roleId : roleIds) {
				Rolepartymember rp = new Rolepartymember();
				rp.setPartymember(partymember);
				rp.setRole(new Role(roleId));
				partymember.getRolepartymembers().add(rp);
				//partymemberDao.saveUserRole(rp);
			}
		}
		//保存党员信息
		save(partymember);*/
		//STILL BUG
		save(partymember);//尚未持久化的的Partymember怎么可能有主键所以要在这里先保存一次
		if (roleIds != null) {
			for (String roleId : roleIds) {
				System.out.println(roleId);
				System.out.println("partymember why is empty？？？"+partymember.getId());
				Rolepartymember rp = new Rolepartymember();
				rp.setId(new RolepartymemberId(partymember.getId(), roleId));	
				rp.setPartymember(partymember);//N的一端设置关联关系
				rp.setRole(new Role(roleId));
				partymember.getRolepartymembers().add(rp);
			}
		}
		//保存党员信息(此时会级联更新对应的角色)
		update(partymember);
	}
	
	@Override
	public List<Rolepartymember> findUserRoleByUserId(String id) {
		return partymemberDao.findUserRoleByUserId(id);
	}


	@Override
	public List<Partymember> findPartymemberByIdAndNumber(String id, String number) {
		return partymemberDao.findPartymemberByIdAndNumber(id, number);
	}


	@Override
	public void saveUserRole(Rolepartymember rp) {
		partymemberDao.saveUserRole(rp);		
	}

	@Override
	public String findBranchIdByUserId(String id) {
		return partymemberDao.findBranchIdByUserId(id);
	}

	@Override
	public void deleteUserRoleByUserId(Serializable id) {
		partymemberDao.deleteUserRoleByUserId(id);
	}
	@Override
	public void delete(Serializable id) {
		//先删除党员对应的角色信息(从表)
		partymemberDao.deleteUserRoleByUserId(id);
		//再删除党员信息（主表）
		partymemberDao.delete(id);
	}
	@Override
	public void delete(Partymember entity) {
		partymemberDao.deleteUserRoleByUserId(entity.getId());
		partymemberDao.delete(entity);
	}
	//导入的Excel文件的格式必须严格按照格式来否则就报错
	@Override
	public void importExcelForStudent(File headImg, String headImgFileName, BranchService branchService) throws Exception{
		try {
			FileInputStream input = new FileInputStream(headImg);
			//判断是否是Excel种类
			boolean is03Excel = headImgFileName.matches("^.+\\.(?i)(xls)$");
			Workbook workbook = is03Excel ? new HSSFWorkbook(input) : new XSSFWorkbook(input);
			//获取Excel的Sheet数
			int sheetNum = workbook.getNumberOfSheets();
			String sheetName;
			for (int i = 0; i < sheetNum; ++i) {
				Sheet sheeti = workbook.getSheetAt(i);
				sheetName = sheeti.getSheetName();
				//如果导入的Excel长度大于3，也就说明了有数据（根据需求来）
				if (sheeti.getPhysicalNumberOfRows() > 3) {
					Partymember pm = null;
					//对每一行数据进行录入
					for (int j = 3; j < sheeti.getPhysicalNumberOfRows(); ++j) {
						DataFormatter formatter = new DataFormatter();
						//获取第j行的数据
						Row rowj = sheeti.getRow(j);
						pm = new Partymember();
						//支部
						Cell cell1 = rowj.getCell(1);
						if (cell1 != null && !cell1.getStringCellValue().equals("")) {
							String branchName = cell1.getStringCellValue().substring(0, cell1.getStringCellValue().indexOf("学生"));
							//通过支部名找到支部
							QueryHelper helper = new QueryHelper(Branch.class, "b");
							helper.addWhereClause("b.branchName=?", branchName);
							Branch branch = branchService.find(helper).get(0);
							//设置该党员所属的支部
							pm.setBranch(branch);
							//相应的支部增加了人数
							branch.getPartymembers().add(pm);
							//将支部的变动更新到数据库中
							branchService.update(branch);
						}
						//入党时间(读入的格式：2014.02.03格式 读取到数据库的格式2014-02-03)
						Cell cell2 = rowj.getCell(2);
						SimpleDateFormat sdf = null;
						String entTime = formatter.formatCellValue(cell2);
						if (cell2 != null && !entTime.equals("")) {
							entTime = entTime.replaceAll("\\.", "-");
							sdf = new SimpleDateFormat("yyyy-MM-dd");
							Date newDate = sdf.parse(entTime);
							pm.setJoinTime(newDate);//存储入党时间
						}
						//姓名
						Cell cell3 = rowj.getCell(3);
						if (cell3 != null && !cell3.getStringCellValue().equals(""))
							pm.setName(cell3.getStringCellValue());
						//性别
						Cell cell4 = rowj.getCell(4);
						if (cell4 != null && !cell4.getStringCellValue().equals(""))
							pm.setGender(cell4.getStringCellValue().equals("男") ? true : false);
						//籍贯
						Cell cell5 = rowj.getCell(5);
						if (cell5 != null && !cell5.getStringCellValue().equals(""))	
							pm.setProvince(cell5.getStringCellValue());
						//出生年月
						Cell cell6 = rowj.getCell(6);
						String birth = formatter.formatCellValue(cell6);
						if (cell6 != null && !birth.equals("")) {
							birth = birth.replaceAll("\\.", "-");
							sdf = new SimpleDateFormat("yyyy-MM");
							pm.setBirthday(sdf.parse(birth));
						}
						//保存联系方式
						Cell cell7 = rowj.getCell(7);
						String contact = formatter.formatCellValue(cell7);
						if (contact != null)
							pm.setTelenumber(contact);
						//年级(输入格式：2013级)
						//BUG FOUND IN 2017_4_12_19_44  年级格式不是大一，大二，..而是20xx级
						Cell cell8 = rowj.getCell(8);
						if (cell8 != null && !cell8.getStringCellValue().equals(""))
							pm.setGrade(cell8.getStringCellValue());
						//专业班级
						Cell cell9 = rowj.getCell(9);
						if (cell9 != null && !cell9.getStringCellValue().equals(""))
							pm.setPclass(cell9.getStringCellValue());
						//学号
						Cell cell10 = rowj.getCell(10);
						//BUG FIXED IN 2017_4_13
						/*try {
							num = cell10.getStringCellValue();
						} catch (Exception e) {
							num = BigDecimal.valueOf(cell10.getNumericCellValue()).toString();
						}*/
						String num = formatter.formatCellValue(cell10);
						if (num != null)
							pm.setNumber(num);
						System.out.println(num);
						//宿舍
						Cell cell11 = rowj.getCell(11);
						if (cell11 != null && !cell11.getStringCellValue().equals(""))
							pm.setDorm(cell11.getStringCellValue());
						//递交入党申请书时间
						Cell cell12 = rowj.getCell(12);
						String appTime = formatter.formatCellValue(cell12);
						if (cell12 != null && !appTime.equals("")) {
							appTime = appTime.replace("年", "-");
							appTime = appTime.replace("月", "");
							sdf = new SimpleDateFormat("yyyy-MM");
							Date newAppDate = sdf.parse(appTime);
							pm.setAppTime(newAppDate);
						}
						//党校结业时间
						Cell cell13 = rowj.getCell(13);
						String endTime = formatter.formatCellValue(cell13);
						if (cell13 != null && !endTime.equals("")) {
							endTime = endTime.replace("年", "-");
							endTime = endTime.replace("月", "");
							sdf = new SimpleDateFormat("yyyy-MM");
							Date newEndDate = sdf.parse(endTime);
							pm.setGraTime(newEndDate);
						}
						List<String> cul = new ArrayList<>();
						//培养人1
						Cell cell14 = rowj.getCell(14);
						if (cell14 != null && !cell14.getStringCellValue().equals("")) {
							cul.add(cell14.getStringCellValue());
						}
						//培养人2(如果有的话)
						Cell cell15 = rowj.getCell(15);
						if (cell15 != null && !cell15.getStringCellValue().equals("")) {
							cul.add(cell15.getStringCellValue());
						}
						//保存培养人
						if (cul != null)
							pm.setCultivate(cul);
						//备注
						Cell cell16 = rowj.getCell(16);
						if (cell16 != null && !cell16.getStringCellValue().equals("")) {
							pm.setRemake(cell16.getStringCellValue());
						}
						//因为是录入学生党员信息所以类别字段就是学生
						pm.setClassification(Partymember.USER_STUDENT);
						//刚录入的党员信息默认都为有效
						pm.setState(Partymember.USER_STATE_VALID);
						//刚录入的党员都为正式党员（这是一定的）非正式党员需要以手工录入方式进行
						pm.setIsFormal(Partymember.IS_FORMAL);
						//密码默认置为学号后六位
						pm.setPassword(pm.getNumber().substring(pm.getNumber().length() - 6, pm.getNumber().length()));
					}
				}
			}
			workbook.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("Service层出现异常，异常信息是: " +  e.getMessage());
		}
	}

	@Override
	public void importExcelForTeacher(File headImg, String headImgFileName) throws Exception{
		try {
			FileInputStream input = new FileInputStream(headImg);
			//判断是否是Excel种类
			boolean is03Excel = headImgFileName.matches("^.+\\.(?i)(xls)$");
			Workbook workbook = is03Excel ? new HSSFWorkbook(input) : new XSSFWorkbook(input);
			Sheet sheet = workbook.getSheetAt(0);
			//如果Excel表格中的数据大于2行则表明有数据在系统中
			if (sheet.getPhysicalNumberOfRows() > 2) {
				Partymember pm = null;
				for (int j = 2; j < sheet.getPhysicalNumberOfRows(); ++j) {
					Row rowj = sheet.getRow(j);
					pm = new Partymember();
					//姓名
					Cell cell1 = rowj.getCell(1);
					if (cell1 != null && !cell1.getStringCellValue().equals("")) {
						pm.setName(cell1.getStringCellValue());
					}
					//性别
					Cell cell2 = rowj.getCell(2);
					String gender = cell2.getStringCellValue();
					if (cell2 != null && !gender.equals("")) {
						pm.setGender(gender.equals("男性")?true:false);
					}
					//民族
					Cell cell3 = rowj.getCell(3);
					String nation = cell3.getStringCellValue();
					if (cell3 != null && !nation.equals("")) {
						pm.setNation(nation);
					}
					DataFormatter formatter = new DataFormatter();
					//身份证号
					Cell cell4 = rowj.getCell(4);
					String identity = formatter.formatCellValue(cell4);
					if (cell4 != null && !identity.equals("")) {
						pm.setIdentity(identity);
					}
					System.out.println(identity);
					//入党时间
					Cell cell5 = rowj.getCell(5);
					String appTime = formatter.formatCellValue(cell5);
					System.out.println(appTime);
					appTime = appTime.replace("年", "-");
					appTime = appTime.replace("月", "");
					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
					pm.setAppTime(format.parse(appTime));
					//教师类型
					Cell cell6 = rowj.getCell(6);
					String type = cell6.getStringCellValue();
					if (cell6 != null && !type.equals("")) {
						pm.setTeacherType(type);
					}
					//备注
					Cell cell7 = rowj.getCell(7);
					String append = cell7.getStringCellValue();
					if (cell7 != null && !append.equals("")) {
						pm.setRemake(append);
					}
					//设置其类别为教师
					pm.setClassification(Partymember.USER_TEACHER);
					//刚录入的党员信息默认都为有效
					pm.setState(Partymember.USER_STATE_VALID);
					//刚录入的党员都为正式党员（这是一定的）非正式党员需要以手工录入方式进行
					pm.setIsFormal(Partymember.IS_FORMAL);
					//老师的默认登录密码是身份证号后六位
					pm.setPassword(pm.getIdentity().substring(pm.getIdentity().length() - 6, pm.getIdentity().length()));
					//由于在录入教师时没有指定工号，而工号又是必填字段，所以在此默认将工号设置为身份证号
					pm.setNumber(identity);
					//TODO: 教师所属支部只能手工录入，因为Excel文件中并未明确指出教师所属的确切支部
					//保存用户
					save(pm);
				}
			}
			workbook.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("Service层出现异常，异常信息是: " +  e.getMessage());
		}
	}

	@Override
	public void exportExcelForStudent(List<Partymember> pm, ServletOutputStream out, BranchService branchService) {
		ExcelUtil.exportExcelForStudent(pm, out, branchService);
	}

	@Override
	public void exportExcelForTeacher(List<Partymember> pm, ServletOutputStream out) {
		ExcelUtil.exportExcelForTeacher(pm, out);
		
	}

	@Override
	public List<Partymember> findUserByNumberAndPass(String number, String password) {
		return partymemberDao.findUserByNumberAndPass(number, password);
	}
}
