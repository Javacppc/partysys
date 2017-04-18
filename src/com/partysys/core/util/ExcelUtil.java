package com.partysys.core.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletOutputStream;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;

import com.partysys.sysmanage.branch.entity.Branch;
import com.partysys.sysmanage.branch.service.BranchService;
import com.partysys.sysmanage.party.entity.Partymember;
/**
 * 专门用于导出学生和教师信息的工具类
 * @author 朱可凡
 *
 */
public class ExcelUtil {
	/**
	 * 导出学生党员信息表
	 * @param pm
	 * @param out
	 * @param branchService 
	 */
	public static void exportExcelForStudent(List<Partymember> pm, ServletOutputStream out, BranchService branchService) {
		try {
			HSSFWorkbook workbook = new HSSFWorkbook();
			CellRangeAddress[] address1 = 
					new CellRangeAddress[] {
				new CellRangeAddress(0, 0, 0, 16),
				new CellRangeAddress(1, 2, 0, 0),
				new CellRangeAddress(1, 2, 1, 1),
				new CellRangeAddress(1, 2, 2, 2),
				new CellRangeAddress(1, 2, 3, 3),
				new CellRangeAddress(1, 2, 4, 4),
				new CellRangeAddress(1, 2, 5, 5),
				new CellRangeAddress(1, 2, 6, 6),
				new CellRangeAddress(1, 2, 7, 7),
				new CellRangeAddress(1, 2, 8, 8),
				new CellRangeAddress(1, 2, 9, 9),
				new CellRangeAddress(1, 2, 10, 10),
				new CellRangeAddress(1, 2, 11, 11),
				new CellRangeAddress(1, 2, 12, 12),
				new CellRangeAddress(1, 2, 13, 13),
				new CellRangeAddress(1, 2, 14, 15),
				new CellRangeAddress(1, 2, 16, 18),
				new CellRangeAddress(1, 2, 19, 19)
			};
			//为头标题行创建样式
			HSSFCellStyle style1 = createCellStyle(workbook, (short)16, true);
			//为列标题行创建样式
			HSSFCellStyle style2 = createCellStyle(workbook, (short)11, true);
			//为普通数据创建样式
			HSSFCellStyle style3 = createCellStyle(workbook, (short)10, false);
			List<Branch> branchs = branchService.findAll();
			List<Partymember> oneBranch = new ArrayList<>();
			for (int i = 0; i < branchs.size(); ++i) {
				//支部名
				String name = branchs.get(i).getBranchName();
				String branchName = name + "学生党支部";
				//每次都要重置oneBranch数组
				oneBranch = new ArrayList<>();
				for (Partymember p : pm) {
					//将该支部的所有党员都放到一个集合中
					if (p.getBranch().getBranchName().equals(name)) {
						oneBranch.add(p);
					}
				}
				//添加合并单元格样式
				HSSFSheet sheeti  = workbook.createSheet();
				sheeti.setDefaultColumnWidth(15);
				sheeti.setDefaultRowHeightInPoints(22);
				sheeti.setColumnWidth(19, 30 * 256);
				sheeti.setColumnWidth(2, 30 * 256);
				for (int f = 0; f < address1.length; ++f)
					sheeti.addMergedRegion(address1[f]);
				workbook.setSheetName(i, branchName);//设置sheet名称
				//创建头标题行
				HSSFRow row = sheeti.createRow(0);
				row.setHeight((short) (42 * 20));
				HSSFCell cell = row.createCell(0);
				cell.setCellStyle(style1);
				cell.setCellValue("数计学院各学生党支部党员信息统计表");
				//单元格加载样式
				
				String[] title = new String[]{"序号", "支部名称", "批次（入党时间）（年.月.日）", "姓名", "性别", "籍贯", "出生年月", "联系方式", "年级", "专业班级", "学号", "宿舍", "递交入党申请书时间", "党校结业时间", "培养人", "奖罚情况", "备注"};
				//创建列标题行
				HSSFRow row1 = sheeti.createRow(1);
				HSSFCell cellj;
				for (int j = 0; j < title.length; ++j) {
					if (j == 15) {
						cellj = row1.createCell(j + 1);
						cellj.setCellStyle(style2);
						cellj.setCellValue(title[j]);
						continue;
					}
					if (j == 16) {
						cellj = row1.createCell(j + 3);
						cellj.setCellStyle(style2);
						cellj.setCellValue(title[j]);
						continue;
					}
					cellj = row1.createCell(j);
					cellj.setCellStyle(style2);
					cellj.setCellValue(title[j]);
				}
				SimpleDateFormat formatJoin = new SimpleDateFormat("yyyy.MM.dd");
				SimpleDateFormat bir = new SimpleDateFormat("yyyy.MM");
				SimpleDateFormat party = new SimpleDateFormat("yyyy年dd月");
				
				//本支部有党员的话
				if (oneBranch != null) {
					for (int k = 0; k < oneBranch.size(); ++k) {
						HSSFRow rowk = sheeti.createRow(k + 3);
						HSSFCell cell1 = rowk.createCell(0);
						cell1.setCellStyle(style3);
						cell1.setCellValue(k + 1);//序号
						HSSFCell cell2 = rowk.createCell(1);
						cell2.setCellStyle(style3);
						//支部
						String oneName = oneBranch.get(k).getBranch().getBranchName();
						if (oneName != null)
							cell2.setCellValue(oneName+"学生党支部");
						HSSFCell cell3 = rowk.createCell(2);
						Date joinTime = oneBranch.get(k).getJoinTime();
						cell3.setCellStyle(style3);
						if (joinTime != null)
							cell3.setCellValue(formatJoin.format(joinTime));//入党时间
						HSSFCell cell4 = rowk.createCell(3);
						cell4.setCellStyle(style3);
						cell4.setCellValue(oneBranch.get(k).getName());//姓名
						System.out.println(oneBranch.get(k).getName());
						HSSFCell cell5 = rowk.createCell(4);
						cell5.setCellStyle(style3);
						cell5.setCellValue(oneBranch.get(k).getGender()?"男":"女");//性别
						HSSFCell cell6 = rowk.createCell(5);
						cell6.setCellStyle(style3);
						if (oneBranch.get(k).getProvince() != null)
							cell6.setCellValue(oneBranch.get(k).getProvince());//籍贯
						HSSFCell cell7 = rowk.createCell(6);
						cell7.setCellStyle(style3);
						Date birthday = oneBranch.get(k).getBirthday();
						if (birthday != null)
							cell7.setCellValue(bir.format(birthday));//出生日期
						HSSFCell cell8 = rowk.createCell(7);
						cell8.setCellStyle(style3);
						String tel = oneBranch.get(k).getTelenumber();
						if (tel != null)
						cell8.setCellValue(tel);//联系方式
						HSSFCell cell9 = rowk.createCell(8);
						cell9.setCellStyle(style3);
						String grade = oneBranch.get(k).getGrade();
						if (grade != null)
							cell9.setCellValue(grade);//年级
						HSSFCell cell10 = rowk.createCell(9);
						cell10.setCellStyle(style3);
						String pclass = oneBranch.get(k).getPclass();
						cell10.setCellValue(pclass);//专业班级
						HSSFCell cell11 = rowk.createCell(10);
						cell11.setCellStyle(style3);
						cell11.setCellValue(oneBranch.get(k).getNumber());//学号
						HSSFCell cell12 = rowk.createCell(11);
						cell12.setCellStyle(style3);
						String dorm = oneBranch.get(k).getDorm();
						if (dorm != null) 
							cell12.setCellValue(dorm);//宿舍
						HSSFCell cell13 = rowk.createCell(12);
						cell13.setCellStyle(style3);
						Date appTime = oneBranch.get(k).getAppTime();
						if (appTime != null)
						cell13.setCellValue(party.format(appTime));//递交入党申请书时间
						HSSFCell cell14 = rowk.createCell(13);
						cell14.setCellStyle(style3);
						Date graTime = oneBranch.get(k).getGraTime();
						cell14.setCellValue(party.format(graTime));//党校结业时间
						List<String> list = oneBranch.get(k).getCultivate();
						int size = list.size();
						//培养人
						if (size == 1) {
							HSSFCell cell15 = rowk.createCell(14);
							cell15.setCellStyle(style3);
							cell15.setCellValue(list.get(0));
						} else if (size == 2) {
							HSSFCell cell15 = rowk.createCell(14);
							cell15.setCellStyle(style3);
							cell15.setCellValue(list.get(0));
							HSSFCell cell16 = rowk.createCell(15);
							cell16.setCellStyle(style3);
							cell16.setCellValue(list.get(1));
						}
						HSSFCell cell17 = rowk.createCell(19);
						cell17.setCellStyle(style3);
						//备注
						String remark = oneBranch.get(k).getRemake();
						if (remark != null)
							cell17.setCellValue(remark);
					}
				}
			}
			//写入到Excel文件中
			workbook.write(out);
			workbook.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 创建样式
	 * @param workbook
	 * @param s
	 * @param isBold 是否加粗
	 * @return
	 */
	private static HSSFCellStyle createCellStyle(HSSFWorkbook workbook, short s, boolean isBold) {
		HSSFCellStyle style = workbook.createCellStyle();
		//设置水平和垂直居中样式
		style.setAlignment(HorizontalAlignment.CENTER);
		style.setVerticalAlignment(VerticalAlignment.CENTER);
		//设置字体样式（加粗，大小）
		HSSFFont font = workbook.createFont();
		font.setFontHeightInPoints(s);
		if (isBold)
			font.setBold(true);
		//样式加载字体
		style.setFont(font);
		return style;
	}
	/**
	 * 导出教师党员信息表
	 * @param pm
	 * @param out
	 */
	public static void exportExcelForTeacher(List<Partymember> pm, ServletOutputStream out) {
		try {
			HSSFWorkbook workbook = new HSSFWorkbook();
			CellRangeAddress address = new CellRangeAddress(0, 0, 0, 7);
			HSSFCellStyle style1 = createCellStyle(workbook, (short)18, true);
			HSSFCellStyle style2 = createCellStyle(workbook, (short)16, true);
			HSSFCellStyle style3 = createCellStyle(workbook, (short)16, false);
			HSSFSheet sheet = workbook.createSheet();
			//加载合并单元格对象
			sheet.addMergedRegion(address);
			//设置默认列宽
			sheet.setDefaultColumnWidth(15);
			sheet.setDefaultRowHeightInPoints(36);
			//自适应列宽度
			sheet.autoSizeColumn(4, true);
			sheet.autoSizeColumn(7, true);
			//设置第五列列宽
			sheet.setColumnWidth(4, 35 * 256);
			sheet.setColumnWidth(7, 30 * 256);
			
			//创建头标题行
			HSSFRow row = sheet.createRow(0);
			row.setHeight((short) (61 * 20));
			HSSFCell cell = row.createCell(0);
			cell.setCellValue("数计学院教师支部党员名册");
			//单元格加载样式
			cell.setCellStyle(style1);
			String[] title = new String[]{"序号", "姓名", "性别", "民族", "身份证号", "入党时间", "类别", "备注"};
			//创建列标题行
			HSSFRow row1 = sheet.createRow(1);
			for (int i = 0; i < title.length; ++i) {
				HSSFCell cell1 = row1.createCell(i);
				cell1.setCellStyle(style2);
				cell1.setCellValue(title[i]);
			}
			if (pm != null) {
				SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月");
				for (int i = 0; i < pm.size(); ++i) {
					HSSFRow rowi = sheet.createRow(i + 2);
					HSSFCell cell1 = rowi.createCell(0);
					cell1.setCellStyle(style3);
					cell1.setCellValue(i + 1);//序号
					HSSFCell cell2 = rowi.createCell(1);
					cell2.setCellStyle(style3);
					cell2.setCellValue(pm.get(i).getName());//姓名
					HSSFCell cell3 = rowi.createCell(2);
					cell3.setCellStyle(style3);
					cell3.setCellValue(pm.get(i).getGender()?"男性":"女性");//性别
					HSSFCell cell4 = rowi.createCell(3);
					cell4.setCellStyle(style3);
					String nation = pm.get(i).getNation();
					if (nation != null)
						cell4.setCellValue(nation);//民族
					HSSFCell cell5 = rowi.createCell(4);
					cell5.setCellStyle(style3);
					String identity = pm.get(i).getIdentity();
					cell5.setCellValue(identity);//身份证号
					HSSFCell cell6 = rowi.createCell(5);
					cell6.setCellStyle(style3);
					Date appTime = pm.get(i).getAppTime();
					if (appTime != null)
					cell6.setCellValue(format.format(appTime));//入党时间
					HSSFCell cell7 = rowi.createCell(6);
					cell7.setCellStyle(style3);
					String type = pm.get(i).getTeacherType();
					if (type != null) 
						cell7.setCellValue(type);//教师类别
					HSSFCell cell8 = rowi.createCell(7);
					cell8.setCellStyle(style3);
					String remark = pm.get(i).getRemake();
					cell8.setCellValue(remark);//备注
				}
			}
			workbook.write(out);
			workbook.close();
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
}
