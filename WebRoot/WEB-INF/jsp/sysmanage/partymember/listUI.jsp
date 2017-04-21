<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
    pageContext.setAttribute("basePath", request.getContextPath()+"/") ;
%>
<html>
<head>
    <title>党员管理</title>
    <script type="text/javascript" src="${basePath}js/jquery/jquery-1.10.2.min.js"></script>
    <link href="${basePath}css/skin1.css" rel="stylesheet" type="text/css" />
    <style>
    	#alert {border:1px solid #369;width:340px;height:120px;background:#e2ecf5;z-index:1000;position:absolute;display:none;} 
		#alert h4 {height:20px;background:#369;color:#fff;padding:5px 0 0 5px;} 
		#alert h4 span {float:left;} 
		#alert h4 span#close {margin-left:250px;font-weight:500;cursor:pointer;} 
		#alert p {padding:12px 0 0 30px;} 
		#alert p input {width:120px;margin-left:20px;} 
		#alert p select.myinp {border:1px solid #ccc;height:25px;} 
		#alert p input.sub {width:60px;margin-left:60px;} 
		
		#alert1 {border:1px solid #369;width:340px;height:120px;background:#e2ecf5;z-index:1000;position:absolute;display:none;} 
		#alert1 h4 {height:20px;background:#369;color:#fff;padding:5px 0 0 5px;} 
		#alert1 h4 span {float:left;} 
		#alert1 h4 span#close1 {margin-left:250px;font-weight:500;cursor:pointer;} 
		#alert1 p {padding:12px 0 0 30px;} 
		#alert1 p input {width:120px;margin-left:20px;} 
		#alert1 p select.myinp {border:1px solid #ccc;height:25px;} 
		#alert1 p input.sub {width:60px;margin-left:100px;}
		
		#alert2 {border:1px solid #369;width:340px;height:120px;background:#e2ecf5;z-index:1000;position:absolute;display:none;} 
		#alert2 h4 {height:20px;background:#369;color:#fff;padding:5px 0 0 5px;} 
		#alert2 h4 span {float:left;} 
		#alert2 h4 span#close2 {margin-left:250px;font-weight:500;cursor:pointer;} 
		#alert2 p {padding:12px 0 0 30px;} 
		#alert2 p input {width:120px;margin-left:20px;} 
		#alert1 p input.myinp {border:1px solid #ccc;height:25px;}
		#alert2 p input.sub {width:60px;margin-left:60px;}  
    </style>
    <script>
      	//全选、全反选
		function doSelectAll(){
			// jquery 1.6 前
			//$("input[name=selectedRow]").attr("checked", $("#selAll").is(":checked"));
			//prop jquery 1.6+建议使用
			$("input[name=selectedRow]").prop("checked", $("#selAll").is(":checked"));		
		}
		//彈出新增頁面
		function doAdd() {
			document.forms[0].action = "${basePath}sysmanage/partymember_addUI.action";
			document.forms[0].submit();
		}
		
		function doEdit(id) {
			document.forms[0].action = "${basePath}sysmanage/partymember_editUI.action?partymember.id="+id;
			document.forms[0].submit();
		}
		
		
		function doEnsureDelete(id) {
			document.forms[0].action = "${basePath}sysmanage/partymember_delete.action?partymember.id="+id;
			document.forms[0].submit();
		}
		
		function doDeleteAll() {
			document.forms[0].action = "${basePath}sysmanage/partymember_deleteSelected.action";
			document.forms[0].submit();
		}
		
		function doExportExcel() {
			var option = $("#optionStuTea1 option:selected");
			if (option.val() == "学生") {
				window.open("${basePath}sysmanage/partymember_exportExcelForStudent.action");
			} else {
				window.open("${basePath}sysmanage/partymember_exportExcelForTeacher.action");
			}
		}
		
		function doImportExcel() {
			var option = $("#optionStuTea option:selected");
			if (option.val() == "学生") {
				document.forms[0].action = "${basePath}sysmanage/partymember_importExcelForStudent.action";
			} else {
				document.forms[0].action = "${basePath}sysmanage/partymember_importExcelForTeacher.action";
			}
			document.forms[0].submit();
		}
		
		var list_url = "${basePath}sysmanage/partymember_listUI.action";
	  	function doSearch() {
	  		//每次点搜索按钮的时候都回到第一页（相当于重新查询）
	  		$("#pageNo").val(1);
	  		document.forms[0].action = list_url;
	  		document.forms[0].submit();
	  	}
	</script>
</head>
<body class="rightBody">
<form name="form1" action="" method="post" enctype="multipart/form-data">
    <div class="p_d_1">
        <div class="p_d_1_1">
            <div class="content_info">
                <div class="c_crumbs"><div><b></b><strong>党员管理</strong></div> </div>
                <div class="search_art">
                    <li>
                        姓名：<s:textfield name="partymember.name" cssClass="s_text" id="userName"  cssStyle="width:80px;"/>
                    </li>
                    <li>
                        学工号：<s:textfield name="partymember.number" cssClass="s_text" cssStyle="width:80px;" />
                    </li>
                    <li>
                         年级：<%-- <s:select name="partymember.grade" cssClass="s_text" cssStyle="width:80px;" list="{'大一','大二','大三','大四'}" headerKey="" headerValue="请选择"/> --%>
            <s:textfield name="partymember.grade" cssClass="s_text" cssStyle="width:80px;" />             
                    </li>
                    <li>
                         称呼:<s:select list="#{'STUDENT':'学生','TEACHER':'老师'}" headerKey="" headerValue="请选择" cssClass="s_text" name="partymember.classification" cssStyle="width:80px;"></s:select>
                    </li>
        
                    <li><input type="button" class="s_button" value="搜 索" onclick="doSearch()"/></li>
                    <li style="float:right;">
                        <input type="button" value="新增" class="s_button" onclick="doAdd()"/>&nbsp;
                        <input type="button" value="删除" class="s_button" onclick="doDeleteAll()"/>&nbsp;
                        <input type="button" value="导出" class="s_button" onclick="doExport()"/>&nbsp;
                    	<!-- 由于接受用户上传的文件的步骤都是一样的，所以我们在这里借用了上传图片headImg的方法-->
                    	<input name="headImg" type="file"/>
                        <input type="button" value="导入" class="s_button" onclick="doImport()"/>&nbsp;
                    </li>
                </div>
                <div class="t_list" style="margin:0px; border:0px none;">
                    <table width="100%" border="0">
                        <tr class="t_tit">
                            <td width="30" align="center"><input type="checkbox" id="selAll" onclick="doSelectAll()" /></td>
                            <td width="140" align="center">姓名</td>
                            <td width="140" align="center">性别</td>
                            <td width="160" align="center">所属部门</td>
                            <td width="80" align="center">年级</td>
                            <td align="center">班级</td>
                            <td width="50" align="center">称呼</td>
                            <td width="100" align="center">操作</td>
                        </tr>
                        <!-- 奇数行加颜色 -->
                        <s:iterator value="pageResult.items" status="st">
                            <tr <s:if test="#st.odd">bgcolor="f8f8f8"</s:if>>
                                <td align="center"><input type="checkbox" name="selectedRow" value="<s:property value='id' />"/></td>
                                <td align="center"><s:property value="name"/></td>
                                <td align="center"><s:property value="gender?'男':'女'"/></td>
                                <td align="center"><s:property value="branch.branchName"/></td>
                                <td align="center"><s:property value="grade"/></td>
                                <td align="center"><s:property value="pclass"/></td>
                                <td align="center"><s:property value="%{(classification=='TEACHER')?'老师':'学生'}"/></td>
                                <td align="center">
                                    <a href="javascript:doEdit('<s:property value='id' />')">编辑</a>
                                    <a href="javascript:doDelete('<s:property value='id' />')">删除</a>
                                </td>
                            </tr>
                       </s:iterator>
                    </table>
                </div>
            </div>
        	<jsp:include page="/common/pageNavigator.jsp"/>
        </div>
    </div>
</form>
<div id="alert">
		<h4><span>请选择</span><span id="close">关闭</span></h4>
		<p>
			<label>选择导入学生还是教师信息:</label>
			<select id="optionStuTea" class="myinp" onmouseover="this.style.border='1px solid #f60'" onfocus="this.style.border='1px solid #f60'" onblur="this.style.border='1px solid #ccc'">
				<option value="学生">学生</option>
				<option value="教师">教师</option>	
			</select>
		</p>
		<p><input type="button" value="导入" class="sub" onClick="javascript:doImportExcel()" /><input type="reset" value="重置" class="sub" /></p> 
</div>
<div id="alert1">
		<h4><span>请选择</span><span id="close1">关闭</span></h4>
		<p>
			<label>选择导出学生还是教师信息:</label>
			<select id="optionStuTea1" class="myinp" onmouseover="this.style.border='1px solid #f60'" onfocus="this.style.border='1px solid #f60'" onblur="this.style.border='1px solid #ccc'">
				<option value="学生">学生</option>
				<option value="教师">教师</option>	
			</select>
		</p>
		<p><input type="button" value="导出" class="sub" onClick="javascript:doExportExcel()" /><input type="reset" value="重置" class="sub" /></p> 
</div>
<div id="alert2">
		<h4><span>确定删除</span><span id="close2">关闭</span></h4>
		<p>
			<label>确定删除该党员?</label>
			<input hidden="hidden" class="myinp"/>
		</p>
		<p><input id="ensureDelete" type="button" value="确定" class="sub" /><input hidden="hidden" class="sub"/></p> 
</div>

<script type="text/javascript">
	var myAlert = document.getElementById("alert");
	var mClose = document.getElementById("close"); 
	function doImport() {
		myAlert.style.display = "block"; 
		myAlert.style.position = "absolute"; 
		myAlert.style.top = "50%"; 
		myAlert.style.left = "50%"; 
		myAlert.style.marginTop = "-75px"; 
		myAlert.style.marginLeft = "-150px";
		mybg = document.createElement("div"); 
		mybg.setAttribute("id","mybg"); 
		mybg.style.background = "#ddd"; 
		mybg.style.width = "100%"; 
		mybg.style.height = "100%"; 
		mybg.style.position = "absolute"; 
		mybg.style.top = "0"; 
		mybg.style.left = "0"; 
		mybg.style.zIndex = "500"; 
		mybg.style.opacity = "0.3"; 
		mybg.style.filter = "Alpha(opacity=30)"; 
		document.body.appendChild(mybg);
		document.body.style.overflow = "hidden";
	}
	//关闭弹出的窗口
	mClose.onclick = function() 
	{ 
		myAlert.style.display = "none"; 
		mybg.style.display = "none"; 
	}
	var myAlert1 = document.getElementById("alert1");
	var mClose1 = document.getElementById("close1"); 
	function doExport() {
		myAlert1.style.display = "block"; 
		myAlert1.style.position = "absolute"; 
		myAlert1.style.top = "50%"; 
		myAlert1.style.left = "50%"; 
		myAlert1.style.marginTop = "-75px"; 
		myAlert1.style.marginLeft = "-150px";
		mybg = document.createElement("div"); 
		mybg.setAttribute("id","mybg"); 
		mybg.style.background = "#ddd"; 
		mybg.style.width = "100%"; 
		mybg.style.height = "100%"; 
		mybg.style.position = "absolute"; 
		mybg.style.top = "0"; 
		mybg.style.left = "0"; 
		mybg.style.zIndex = "500"; 
		mybg.style.opacity = "0.3"; 
		mybg.style.filter = "Alpha(opacity=30)"; 
		document.body.appendChild(mybg);
		document.body.style.overflow = "hidden";
	}
	mClose1.onclick = function() 
	{ 
		myAlert1.style.display = "none"; 
		mybg.style.display = "none"; 
	}
	var myAlert2 = document.getElementById("alert2");
	var mClose2 = document.getElementById("close2"); 
	function doDelete(id) {
		myAlert2.style.display = "block"; 
		myAlert2.style.position = "absolute"; 
		myAlert2.style.top = "50%"; 
		myAlert2.style.left = "50%"; 
		myAlert2.style.marginTop = "-75px"; 
		myAlert2.style.marginLeft = "-150px";
		mybg = document.createElement("div"); 
		mybg.setAttribute("id","mybg"); 
		mybg.style.background = "#ddd"; 
		mybg.style.width = "100%"; 
		mybg.style.height = "100%"; 
		mybg.style.position = "absolute"; 
		mybg.style.top = "0"; 
		mybg.style.left = "0"; 
		mybg.style.zIndex = "500"; 
		mybg.style.opacity = "0.3"; 
		mybg.style.filter = "Alpha(opacity=30)"; 
		document.body.appendChild(mybg);
		document.body.style.overflow = "hidden";
		$(function() {//页面加载成功后要做的事情
			$("#ensureDelete").click(function() {
				doEnsureDelete(id);	
			});
		});
	}
	mClose2.onclick = function() 
	{ 
		myAlert2.style.display = "none"; 
		mybg.style.display = "none"; 
	}
</script>
</body>
</html>