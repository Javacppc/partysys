<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@include file="/common/header.jsp"%>
    <title>党费汇总</title>
    <script type="text/javascript">
  	//全选、全反选
	function doSelectAll(){
		// jquery 1.6 前
		//$("input[name=selectedRow]").attr("checked", $("#selAll").is(":checked"));
		//prop jquery 1.6+建议使用
		$("input[name=selectedRow]").prop("checked", $("#selAll").is(":checked"));		
	}
  	//新增
  	function doAdd(){
  		document.forms[0].action = "${basePath}partymanage/period_addUI.action";
  		document.forms[0].submit();
  	}
  	//编辑
  	function doEdit(id){
  		document.forms[0].action = "${basePath}partymanage/period_editUI.action?period.periodId=" + id;
  		document.forms[0].submit();
  	}
  	//删除
  	function doDelete(id){
  		document.forms[0].action = "${basePath}partymanage/period_delete.action?period.periodId=" + id;
  		document.forms[0].submit();
  	}
  	//批量删除
  	function doDeleteAll(){
  		document.forms[0].action = "${basePath}partymanage/period_deleteSelected.action";
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
  	
  	}
    </script>
</head>
<body class="rightBody">
<form name="form1" action="" method="post">
    <div class="p_d_1">
        <div class="p_d_1_1">
            <div class="content_info">
                <div class="c_crumbs"><div><b></b><strong>党费汇总 </strong></div> </div>
                <div class="search_art">
                    <li>
                        角色名称：<s:textfield name="" cssClass="s_text" id="roleName"  cssStyle="width:160px;"/>
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
                            <td width="200" align="center">期数</td>
                            <td width="120" align="center">操作</td>
                        </tr>
                       		<s:iterator value="pageResult.items" status="st">
                            <tr <s:if test="#st.odd">bgcolor="f8f8f8"</s:if> >
                                <td align="center"><input type="checkbox" name="selectedRow" value="<s:property value='periodId'/>"/></td>
                                <td align="center"><s:property value="date"/></td>
                                <td align="center">
                                    <a href="javascript:doEdit('<s:property value='periodId'/>')">编辑</a>
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

</body>
</html>