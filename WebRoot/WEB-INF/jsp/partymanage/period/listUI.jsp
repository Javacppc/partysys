<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@include file="/common/header.jsp"%>
    <title>党费汇总</title>
    <script type="text/javascript" src="${basePath}js/datepicker/WdatePicker.js"></script>
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
		#alert1 p input.sub1 {width:60px;margin-left:30px;} 
		
		#alert2 {border:1px solid #369;width:340px;height:120px;background:#e2ecf5;z-index:1000;position:absolute;display:none;} 
		#alert2 h4 {height:20px;background:#369;color:#fff;padding:5px 0 0 5px;} 
		#alert2 h4 span {float:left;} 
		#alert2 h4 span#close2 {margin-left:250px;font-weight:500;cursor:pointer;} 
		#alert2 p {padding:12px 0 0 30px;} 
		#alert2 p input {width:120px;margin-left:20px;} 
		#alert1 p input.myinp {border:1px solid #ccc;height:25px;}
		#alert2 p input.sub2 {width:60px;margin-left:60px;}  
    </style>
    <script type="text/javascript">
    $(function() {
    	$("#periodEdit").focus(function() {
    		WdatePicker({'skin':'whyGreen', 'dateFmt':'yyyy-MM'});
    	});
    	$("#periodEdit1").focus(function() {
    		WdatePicker({'skin':'whyGreen', 'dateFmt':'yyyy-MM'});
    	});
    })
  	//全选、全反选
	function doSelectAll(){
		// jquery 1.6 前
		//$("input[name=selectedRow]").attr("checked", $("#selAll").is(":checked"));
		//prop jquery 1.6+建议使用
		$("input[name=selectedRow]").prop("checked", $("#selAll").is(":checked"));		
	}
  	//批量删除
  	function doDeleteAll(){
  		document.forms[0].action = "${basePath}partymanage/period_deleteSelected.action";
  		document.forms[0].submit();
  	}
  	function doEnsureDelete(id) {
			document.forms[0].action = "${basePath}partymanage/period_delete.action?period.periodId="+id;
			document.forms[0].submit();
	}
	var list_url = "${basePath}partymanage/period_listUI.action";
  	function doSearch() {
  		//每次点搜索按钮的时候都回到第一页（相当于重新查询）
  		$("#pageNo").val(1);
  		document.forms[0].action = list_url;
  		document.forms[0].submit();
  	}
  	//导出一个月所有支部的党费信息（包括汇总信息）
  	function doExport() {
  		window.open("${basePath}partymanage/period_exportExcel.action");
  	}
  	//到后台验证是否有重名的期数(期数格式为yyyy-MM)
  	function onVerify() {
  		var period = $("#periodEdit").val();
  		//支部字段填写了值
  		if (period != "") {
   			$.ajax({
   				url: "${basePath}partymanage/period_verify.action",
   				data: {"period.date" : period},
   				type: "post",
   				success: function(msg) {
   					if ("true" != msg) {
   						alert("相同的期数已经存在，请指定其他时间！");
   						$("#periodEdit").focus();
   						$("#alert p input.sub").attr("disabled","true");
   					}
   				}
   			});
   		}
  	}
  	
  	function onVerify2(id){
  	var period = $("#periodEdit1").val();
  		//支部字段填写了值
  		if (period != "") {
   			$.ajax({
   				url: "${basePath}partymanage/period_verify.action",
   				data: {"period.date" : period, "period.periodId" : id},
   				type: "post",
   				success: function(msg) {
   					if ("true" != msg) {
   						alert("相同的期数已经存在，请指定其他时间！");
   						$("#periodEdit1").focus();
   						$("#alert1 p input.sub1").attr("disabled","true");
   					}
   				}
   			});
   		}
  	}
    </script>
</head>
<body class="rightBody">
<form name="form1" action="" method="post">
    <div class="p_d_1">
        <div class="p_d_1_1">
            <div class="content_info">
                <div class="c_crumbs"><div><b></b><strong>党费汇总</strong></div></div>
                <div class="search_art">
                    <li>
                        党费期数：<s:textfield name="period.date" cssClass="s_text" id="roleName"  cssStyle="width:160px;"/>
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
                            <td width="300" align="center">期数</td>
                            <td width="90" align="center">操作</td>
                        </tr>
                       		<s:iterator value="pageResult.items" status="st">
                            <tr <s:if test="#st.odd">bgcolor="f8f8f8"</s:if> >
                                <td align="center"><input type="checkbox" name="selectedRow" value="<s:property value='periodId'/>"/></td>
                                <td align="center"><s:property value="date"/></td>
                                <td align="center">
                                    <a href="javascript:doEdit('<s:property value='periodId'/>', <s:property value='date'/>)">编辑</a>
                                    <a href="javascript:doExport('<s:property value='periodId'/>')">导出</a>
                                    <a href="javascript:doDelete('<s:property value='periodId'/>')">删除</a>
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
	<form name="addPeriod" method="post" action="${basePath}partymanage/period_add.action">
		<h4><span>新增</span><span id="close">关闭</span></h4>
		<p><label>新建期数:</label><input id="periodEdit" onchange="onVerify();" name="period.date" type="text" class="myinp" onmouseover="this.style.border='1px solid #f60'" onfocus="this.style.border='1px solid #f60';WdatePicker({'skin':'whyGreen', 'dateFmt':'yyyy-MM'});" readonly="readonly" onblur="this.style.border='1px solid #ccc'" /></p>  
		<p><input type="submit" value="提交" class="sub" /><input type="reset" value="重置" class="sub" /></p> 
	</form>
</div>
<div id="alert1">
	<form name="editPeriod" method="post" action="">
		<h4><span>编辑</span><span id="close1">关闭</span></h4>
		<p><label>修改期数:</label><input id="periodEdit1" readonly="readonly" name="period.date" type="text" class="myinp" onmouseover="this.style.border='1px solid #f60'" onfocus="this.style.border='1px solid #f60';WdatePicker({'skin':'whyGreen', 'dateFmt':'yyyy-MM'});" onblur="this.style.border='1px solid #ccc'" readonly="readonly" onblur="this.style.border='1px solid #ccc'" /></p>  
		<p><input type="submit" value="提交" class="sub1" /><input type="reset" value="重置" class="sub1" /></p> 
	</form>
</div>
<div id="alert2">
		<h4><span>确定删除</span><span id="close2">关闭</span></h4>
		<p>
			<label>确定删除该期数?</label>
			<input hidden="hidden" class="myinp"/>
		</p>
		<p><input id="ensureDelete" type="button" value="确定" class="sub2" /><input hidden="hidden" class="sub2"/></p> 
</div>
<script type="text/javascript">
	var myAlert = document.getElementById("alert"); 
    var myAlert1 = document.getElementById("alert1");
    var myAlert2 = document.getElementById("alert2");
   	var mClose = document.getElementById("close"); 
   	var mClose1 = document.getElementById("close1");
   	var mClose2 = document.getElementById("close2");
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
  	function doEdit(id,periodDate){
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
		//编辑页面判断
		$(function() {//页面加载成功后要做的事情
			$("#periodEdit1").change(function() {
				onVerifiy(id);
			});
		});
		
		document.forms[2].action="${basePath}partymanage/period_edit.action?period.periodId="+id;
  	}
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
				doEnsureDelete(id);	//确定删除
			});
		});
	}
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
	mClose2.onclick = function() 
	{ 
		myAlert2.style.display = "none"; 
		mybg.style.display = "none"; 
	} 
</script>
</body>
</html>