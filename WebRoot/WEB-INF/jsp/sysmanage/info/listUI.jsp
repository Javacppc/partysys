<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@include file="/common/header.jsp"%>
    <title>信息发布管理</title>
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
  		document.forms[0].action = "${basePath}sysmanage/info_addUI.action";
  		document.forms[0].submit();
  	}
  	//编辑
  	function doEdit(id){
  		document.forms[0].action = "${basePath}sysmanage/info_editUI.action?info.infoId=" + id;
  		document.forms[0].submit();
  	}
  	//删除
  	function doDelete(id){
  		var option = window.confirm("您确定删除?");
  		if (option == true) {
	  		document.forms[0].action = "${basePath}sysmanage/info_delete.action?info.infoId=" + id;
	  		document.forms[0].submit();
  		}
  	}
  	//批量删除
  	function doDeleteAll(){
  		var option = window.confirm("您确定删除?");
  		if (option == true) {
	  		document.forms[0].action = "${basePath}sysmanage/info_deleteSelected.action";
	  		document.forms[0].submit();
  		}
  	}
  	var list_url = "${basePath}sysmanage/info_listUI.action";
  	function doSearch() {
  		//每次点搜索按钮的时候都回到第一页（相当于重新查询）
  		$("#pageNo").val(1);
  		document.forms[0].action = list_url;
  		document.forms[0].submit();
  	}
  	//异步发布信息infoId：传入的信息Id值，state是想要改为的状态值
    function doPublic(infoId, state) {
    	$.ajax({
    		url: "${basePath}sysmanage/info/info_publicInfo.action",
    		data: {"info.infoId": infoId, "info.state": state},
    		type: "post",
    		success: function(msg) {
	    			if ("更新状态成功" == msg) {
	    				if (state == 1) {
	    					$("#show_"+infoId).html("发布");
	    					$("#oper_"+infoId).html('<a href="javascript:doPublic(\''+infoId+'\', 0)">停用</a>');
	    				} else {
	    					$("#show_"+infoId).html("停用");
	    					$("#oper_"+infoId).html('<a href="javascript:doPublic(\''+infoId+'\', 1)">发布</a>');
	    				}
	    			}else {
	    				alert('更新信息失败！');
	    			} 
    			},
    		error: function() {
    			alert('更新信息失败');
    			}
    	});
    }
    </script>
</head>
<body class="rightBody">
<form name="form1" action="" method="post">
    <div class="p_d_1">
        <div class="p_d_1_1">
            <div class="content_info">
                <div class="c_crumbs"><div><b></b><strong>信息发布管理</strong></div> </div>
                <div class="search_art">
                    <li>
                        信息标题：<s:textfield name="info.title" cssClass="s_text" id="infoTitle"  cssStyle="width:160px;"/>
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
                            <td align="center">信息标题</td>
                            <td width="120" align="center">信息分类</td>
                            <td width="120" align="center">创建人</td>
                            <td width="140" align="center">创建时间</td>
                            <td width="80" align="center">状态</td>
                            <td width="120" align="center">操作</td>
                        </tr>
                        <s:iterator value="pageResult.items" status="st">
                            <tr <s:if test="#st.odd"> bgcolor="f8f8f8" </s:if> >
                                <td align="center"><input type="checkbox" name="selectedRow" value="<s:property value='infoId'/>"/></td>
                                <td align="center"><s:property value="title"/></td>
                                <td align="center">
                                	<s:property value="#infoTypeMap[type]"/>	
                                </td>
                                <td align="center"><s:property value="creator"/></td>
                                <td align="center"><s:date name="createTime" format="yyyy-MM-dd HH:mm"/></td>
                                <td align="center" id="show_<s:property value='infoId' />"><s:property value="state==1?'发布':'停用'"/></td>
                                <td align="center">
                                	<span id="oper_<s:property value='infoId' />">
                                		<s:if test="state==1">
                                			<a href="javascript:doPublic('<s:property value='infoId'/>', 0)">停用</a>
                                		</s:if>
                                		<s:else>
                                			<a href="javascript:doPublic('<s:property value='infoId'/>', 1)">发布</a>
                                		</s:else>
                                	</span>
                                    <a href="javascript:doEdit('<s:property value='infoId'/>')">编辑</a>
                                    <a href="javascript:doDelete('<s:property value='infoId'/>')">删除</a>
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