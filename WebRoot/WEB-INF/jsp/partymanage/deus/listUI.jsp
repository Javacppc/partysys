<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@include file="/common/header.jsp"%>
    <title>党费管理</title>
    <script type="text/javascript">
	var list_url = "${basePath}partymanage/deus_listUI.action";
  	function doSearch() {
  		//每次点搜索按钮的时候都回到第一页（相当于重新查询）
  		$("#pageNo").val(1);
  		document.forms[0].action = list_url;
  		document.forms[0].submit();
  	}
    </script>
</head>
<body class="rightBody">
<form name="form1" action="" method="post">
    <div class="p_d_1">
        <div class="p_d_1_1">
            <div class="content_info">
                <div class="c_crumbs"><div><b></b><strong>党费管理</strong></div></div>
                <div class="search_art">
                    <li>
                        党费期数：<s:textfield name="period.date" cssClass="s_text" id="roleName"  cssStyle="width:160px;" readonly="true" onfocus="WdatePicker({'skin':'whyGreen','dateFmt':'yyyy-MM'})"/>
                    </li>
                    <li><input type="button" class="s_button" value="搜 索" onclick="doSearch()"/></li>
                </div>
                <div class="t_list" style="margin:0px; border:0px none;">
                    <table width="100%" border="0">
                        <tr class="t_tit">
                            <td width="300" align="center">期数</td>
                            <td width="90" align="center">操作</td>
                        </tr>
                       		<s:iterator value="pageResult.items" status="st">
                            <tr <s:if test="#st.odd">bgcolor="f8f8f8"</s:if> >
                                <td align="center"><input type="hidden" name="selectedRow" value="<s:property value='periodId'/>"/></td>
                                <td align="center"><s:property value="date"/></td>
                                <td align="center">
                                    <a href="${basePath}partymanage/deus_entryUI.action?periodId=<s:property value='periodId'/>">录入</a>
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