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
		
		function doDelete(id) {
			document.forms[0].action = "${basePath}sysmanage/partymember_delete.action?partymember.id="+id;
			document.forms[0].submit();
		}
		
		function doDeleteAll() {
			document.forms[0].action = "${basePath}sysmanage/partymember_deleteSelected.action";
			document.forms[0].submit();
		}
		function doExportExcel() {
			window.open("${basePath}sysmanage/partymember_exportExcel.action");
		}
		function doImportExcel() {
			document.forms[0].action = "${basePath}sysmanage/partymember_importExcel.action";
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
                         年级：<s:select name="partymember.grade" cssClass="s_text" cssStyle="width:80px;" list="{'大一','大二','大三','大四'}" headerKey="" headerValue="请选择"/>
                    </li>
        
                    <li><input type="button" class="s_button" value="搜 索" onclick="doSearch()"/></li>
                    <li style="float:right;">
                        <input type="button" value="新增" class="s_button" onclick="doAdd()"/>&nbsp;
                        <input type="button" value="删除" class="s_button" onclick="doDeleteAll()"/>&nbsp;
                        <input type="button" value="导出" class="s_button" onclick="doExportExcel()"/>&nbsp;
                    	<!-- 由于接受用户上传的文件的步骤都是一样的，所以我们在这里借用了上传图片headImg的方法-->
                    	<input name="headImg" type="file"/>
                        <input type="button" value="导入" class="s_button" onclick="doImportExcel()"/>&nbsp;
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
</body>
</html>