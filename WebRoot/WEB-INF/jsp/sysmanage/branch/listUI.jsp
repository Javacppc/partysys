<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@include file="/common/header.jsp"%>
    <title>支部管理</title>
    <style type="text/css">
    	#alert {border:1px solid #369;width:300px;height:100px;background:#e2ecf5;z-index:1000;position:absolute;display:none;} 
		#alert h4 {height:20px;background:#369;color:#fff;padding:5px 0 0 5px;} 
		#alert h4 span {float:left;} 
		#alert h4 span#close {margin-left:220px;font-weight:500;cursor:pointer;} 
		#alert p {padding:12px 0 0 30px;} 
		#alert p input {width:120px;margin-left:20px;} 
		#alert p input.myinp {border:1px solid #ccc;height:16px;} 
		#alert p input.sub {width:60px;margin-left:33px;} 
		
		#alert1 {border:1px solid #369;width:300px;height:100px;background:#e2ecf5;z-index:1000;position:absolute;display:none;} 
		#alert1 h4 {height:20px;background:#369;color:#fff;padding:5px 0 0 5px;} 
		#alert1 h4 span {float:left;} 
		#alert1 h4 span#close1 {margin-left:210px;font-weight:500;cursor:pointer;} 
		#alert1 p {padding:12px 0 0 30px;} 
		#alert1 p input {width:120px;margin-left:20px;} 
		#alert1 p input.myinp {border:1px solid #ccc;height:16px;} 
		#alert1 p input.sub {width:60px;margin-left:30px;} 
    </style>
    <script type="text/javascript">
  
  	//全选、全反选
	function doSelectAll(){
		// jquery 1.6 前
		//$("input[name=selectedRow]").attr("checked", $("#selAll").is(":checked"));
		//prop jquery 1.6+建议使用
		$("input[name=selectedRow]").prop("checked", $("#selAll").is(":checked"));		
	}
	
  	//删除
  	function doDelete(id){
  		document.forms[0].action = "${basePath}sysmanage/branch_delete.action?branch.branchId=" + id;
  		document.forms[0].submit();
  	}
  	//批量删除
  	function doDeleteAll(){
  		document.forms[0].action = "${basePath}sysmanage/branch_deleteSelected.action";
  		document.forms[0].submit();
  	}
	var list_url = "${basePath}sysmanage/branch_listUI.action";
  	function doSearch() {
  		//每次点搜索按钮的时候都回到第一页（相当于重新查询）
  		$("#pageNo").val(1);
  		document.forms[0].action = list_url;
  		document.forms[0].submit();
  	}
  	//根据支部名称在党员表里查询出属于该支部的所有成员
  	function doMemberManage(branchId) {
  		
  	}
    </script>
   
</head>
<body class="rightBody">

<form name="form1" action="" method="post">
    <div class="p_d_1">
        <div class="p_d_1_1">
            <div class="content_info">
                <div class="c_crumbs"><div><b></b><strong>支部管理 </strong></div> </div>
                <div class="search_art">
                    <li>
                        支部名称：<s:textfield name="branch.branchName" cssClass="s_text" id="branchName"  cssStyle="width:160px;"/>
                    </li>
                    <li><input type="button" class="s_button" value="搜 索" onclick="doSearch()"/></li>
                    <li style="float:right;">
                        <input type="button" value="新增" class="s_button" onclick="doAdd()"/>&nbsp;
                        <input type="button" value="删除" class="s_button" onclick="doDeleteAll()"/>&nbsp;
                    </li>
                </div>

                <div class="t_list" style="margin:0px; border:0px none;">
                    <table width="100%" border="0">
                        <tr class="t_tit">
                            <td width="30" align="center"><input type="checkbox" id="selAll" onclick="doSelectAll()" /></td>
                            <td width="120" align="center">支部名称</td>
                            <td align="center">支部管理员</td>
                            <td width="80" align="center">支部人数</td>
                            <td width="120" align="center">操作</td>
                        </tr>
                       		<s:iterator value="pageResult.items" status="st">
                            <tr <s:if test="#st.odd">bgcolor="f8f8f8"</s:if> >
                                <td align="center"><input type="checkbox" name="selectedRow" value="<s:property value='branchId'/>"/></td>
                                <td align="center"><s:property value="branchName"/></td>
                                <td align="center">
                                	<!-- 列出该部门下的所有管理员 -->
                                	<!-- id属性指定了集合里元素的ID -->
                                	<s:iterator value="branchAdmin" id="name">
                                		<s:property value="name"/>
                                	</s:iterator>
                                </td>
                                <td align="center"><s:property value="%{partymembers.size()}" /></td>
           
                                <td align="center">
                                    <a href="javascript:doEdit('<s:property value='branchId'/>','<s:property value='branchName'/>')">编辑</a>
                                    <a href="javascript:doDelete('<s:property value='branchId'/>')">删除</a>
                                    <!-- 每一个支部下都有很多成员，点击这里显示该支部下所拥有的所有成员 ，传入支部名称在党员信息表里查询以便显示该支部所有的成员-->
                                    <a href="javascript:doMemberManage('<s:property value='branchId'/>')">成员管理</a>
                                </td>
                            </tr>
                           </s:iterator>
                    </table>
                </div>
            </div>
			<jsp:include page="/common/pageNavigator.jsp" />
        </div>
    </div>
</form>
<div id="alert">
	<form name="addBranch" method="post" action="${basePath}sysmanage/branch_add.action">
		<h4><span>新增</span><span id="close">关闭</span></h4>
		<p><label>支部名称:</label><input name="branch.branchName" type="text" class="myinp" onmouseover="this.style.border='1px solid #f60'" onfocus="this.style.border='1px solid #f60'" onblur="this.style.border='1px solid #ccc'" /></p>  
		<p><input type="submit" value="提交" class="sub" /><input type="reset" value="重置" class="sub" /></p> 
	</form>
</div>

<div id="alert1">
	<form name="editBranch" method="post" action="">
		<h4><span>编辑</span><span id="close1">关闭</span></h4>
		<p><label>支部名称:</label><input id="branchEdit" name="branch.branchName" type="text" class="myinp" onmouseover="this.style.border='1px solid #f60'" onfocus="this.style.border='1px solid #f60'" onblur="this.style.border='1px solid #ccc'" /></p>  
		<p><input type="submit" value="提交" class="sub" /><input type="reset" value="重置" class="sub" /></p> 
	</form>
</div>
<script type="text/javascript">
	var myAlert = document.getElementById("alert"); 
    var myAlert1 = document.getElementById("alert1");
   	var mClose = document.getElementById("close"); 
   	var mClose1 = document.getElementById("close1");

   
	
  	//新增支部名称
  	function doAdd(){
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
  	//编辑
  	function doEdit(id,branchName){
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
		$("#branchEdit").val(branchName);
		document.forms[2].action="${basePath}sysmanage/branch_edit.action?branch.branchId="+id;
  	}
  	
  	 //关闭弹出的窗口
	mClose.onclick = function() 
	{ 
		myAlert.style.display = "none"; 
		mybg.style.display = "none"; 
	} 
	mClose1.onclick = function() 
	{ 
		myAlert1.style.display = "none"; 
		mybg.style.display = "none"; 
	} 
</script>
</body>
</html>
