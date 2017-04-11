<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@include file="/common/header.jsp"%>
    <title>党员管理</title>
    <script type="text/javascript" src="${basePath}js/datepicker/WdatePicker.js"></script>
    <script type="text/javascript">
    var vResult = false;
    	//校验账号唯一性
    	function onVerifyAccount() {
    		//获取账号的值
    		var number = $("#number").val();
    		if (number != "") {
    			//校验 get post getJson ajax
    			$.ajax({
    				url: "${basePath}sysmanage/partymember_verifyAccount.action",
    				data: {"partymember.number" : number,"partymember.id":"${partymember.id}"},
    				type: "post",
    				async: false,//强制Ajax同步
    				success: function(msg) {
    					if ("true" != msg) {
    						alert("账号已经存在，请使用其他账号！");
    						//提示完成后定焦，这样对用户友好
    						$("#number").focus();
    						vResult = false;
    					} else {
    						vResult = true;
    					}
    				}
    			});
    		}
    	}
    	function onSubmit() {
    		
    		var name = $("#username");
    		if (name.val() == "") {
				alert("用户名不可以为空！");
				name.focus();
				return false;//不准提交表单
    		}
    		var pass = $("#password");
    		if (pass.val() == "") {
				alert("密码不可以为空！");
				pass.focus();
				return false;//不准提交表单
    		}
    		//账号校验
    		onVerifyAccount();
    		if (vResult) {
    			//提交表单
    			document.forms[0].submit();
    		}
    	}
    </script>
</head>
<body class="rightBody">
<form id="form" name="form" action="${basePath}sysmanage/partymember_edit.action" method="post" enctype="multipart/form-data">
    <div class="p_d_1">
        <div class="p_d_1_1">
            <div class="content_info">
    <div class="c_crumbs"><div><b></b><strong>党员管理</strong>&nbsp;-&nbsp;编辑党员</div></div>
    <div class="tableH2">编辑党员</div>
    <table id="baseInfo" width="100%" align="center" class="list" border="0" cellpadding="0" cellspacing="0"  >
        <tr>
            <td class="tdBg" width="200px">所属部门：</td>
            <td><s:select name="branchId" list="#mapDept" listKey="branchId" listValue="branchName"/></td>
        </tr>
        <tr>
            <td class="tdBg" width="200px">头像：</td>
            <td>
                	<s:if test="%{partymember.headImg != null && partymember.headImg != ''}">
                		<!-- 这里只是显示了图片而已，并未设置字段去保存图片 -->
                    	<img src="${basePath}upload/<s:property value='partymember.headImg' />" width="100" height="100"/>
                    	<s:hidden name="partymember.headImg" />
                	</s:if>
                <input type="file" name="headImg" />
            </td>
        </tr>
        <tr>
            <td class="tdBg" width="200px">姓名：</td>
            <td><s:textfield id="username" name="partymember.name"/> </td>
        </tr>
        <tr>
        	<!-- 填入的学工号不能重复，所以需要校验 -->
            <td class="tdBg" width="200px">学工号：</td>
            <td><s:textfield id="number" name="partymember.number"
            onchange="onVerifyAccount()"/></td>
        </tr>
        <tr>
        	<td class="tdBg" width="200px">密码：</td>
            <!-- 密码默认为身份证后六位 -->
            <s:if test="%{partymember.identity != null && partymember.identity != ''}">
            	<td><s:password id="password" name="partymember.password" value="%{partymember.identity.substring(partymember.identity.length()-6,partymember.identity.length())}"/></td>
        	</s:if>
        	<s:else>
        		<td><s:password id="password" name="partymember.password" /></td>
        	</s:else>
        </tr>
        <tr>
            <td class="tdBg" width="200px">性别：</td>
            <td><s:radio list="#{'true':'男','false':'女'}" name="partymember.gender" /></td>
        </tr>
        <tr>
            <td class="tdBg" width="200px">角色：</td>
            <td>
            	<!-- roleName将会显示在页面上供用户选择 lisyKey是作为真正的Key值 -->
            	<s:checkboxlist list="#roleList" name="roleIds" listKey="roleId" listValue="roleName" />
            </td>
        </tr>
       
        <tr>
            <td class="tdBg" width="200px">身份证号：</td>
            <td><s:textfield name="partymember.identity"/></td>
        </tr>
        <tr>
            <td class="tdBg" width="200px">联系方式：</td>
            <td><s:textfield name="partymember.telenumber"/></td>
        </tr>    
        <tr>
            <td class="tdBg" width="200px">生日：</td>
            <td>
	            <s:textfield id="birthday" name="partymember.birthday" readonly="true" 
	            onfocus="WdatePicker({'skin':'whyGreen','dateFmt':'yyyy-MM-dd'})">
	            	<s:param name="value"><s:date name="partymember.birthday" format="yyyy-MM-dd"/></s:param>	
	            </s:textfield>
            </td>
        </tr>
        <tr>
            <td class="tdBg" width="200px">入党时间：</td>
            <!-- 日期字段是不可以改动的！ 所以设置了一个readonly字段 再使用WdatePicker组件 -->
            <td>
            	<s:textfield id="attendTime" name="partymember.joinTime" readonly="true" onfocus="WdatePicker({'skin':'whyGreen', 'dateFmt':'yyyy-MM-dd'})">
            		<s:param name="value"><s:date name="partymember.joinTime" format="yyyy-MM-dd"/></s:param>	
            	</s:textfield>
            </td>
        </tr>
        <tr>
            <td class="tdBg" width="200px">递交入党申请书时间：</td>
            <!-- 日期字段是不可以改动的！ 所以设置了一个readonly字段 再使用WdatePicker组件 -->
            <td>
            <s:textfield id="appTime" name="partymember.appTime" readonly="true" onfocus="WdatePicker({'skin':'whyGreen', 'dateFmt':'yyyy-MM-dd'})">
            	<s:param name="value"><s:date name="partymember.appTime" format="yyyy-MM-dd"/></s:param>	
            </s:textfield>
            </td>
        </tr>
         <tr>
            <td class="tdBg" width="200px">党校结业时间：</td>
            <!-- 日期字段是不可以改动的！ 所以设置了一个readonly字段 再使用WdatePicker组件 -->
            <td>
	            <s:textfield id="graTime" name="partymember.graTime" readonly="true" onfocus="WdatePicker({'skin':'whyGreen', 'dateFmt':'yyyy-MM-dd'})">
	            	<s:param name="value"><s:date name="partymember.graTime" format="yyyy-MM-dd"/></s:param>
	            </s:textfield>
            </td>
        </tr>
		<tr>
            <td class="tdBg" width="200px">状态：</td>
            <!-- 默认状态是有效 -->
            <td><s:radio list="#{'1':'有效','0':'无效'}" name="partymember.state"/></td>
        </tr>
        <tr>
            <td class="tdBg" width="200px">籍贯：</td>
            <td><s:textfield name="partymember.province"/></td>
        </tr>
        <tr>
            <td class="tdBg" width="200px">年级：</td>
            <td><s:select name="partymember.grade" list="{'大一','大二','大三','大四'}"/></td>
        </tr>
        <tr>
            <td class="tdBg" width="200px">班级：</td>
            <td><s:textfield name="partymember.pclass"/></td>
        </tr>
        <tr>	
        	<td class="tgBg" width="200px">称呼：</td>
        	<td><s:select name="partymember.classification" list="#{'TEACHER':'老师','STUDENT':'学生'}"/></td>
        </tr>
        <tr>
            <td class="tdBg" width="200px">志愿书编码：</td>
            <td><s:textfield name="partymember.textnumber"/></td>
        </tr>
         <tr>
            <td class="tdBg" width="200px">宿舍：</td>
            <td><s:textfield name="partymember.dorm"/></td>
        </tr>
        <tr>
            <td class="tdBg" width="200px">备注：</td>
            <td><s:textarea name="partymember.remake" cols="75" rows="3"/></td>
        </tr>
    </table>
    <!-- 暂存查询条件的值 -->
    <s:hidden name="strName"/>
    <s:hidden name="strNumber"/>
    <s:hidden name="strGrade"/>
    <!-- 如果在翻页时点击编辑仍然回到原来的那一页（及从哪里来就到哪里去）  -->
    <s:hidden name="pageNo"/>
    <s:hidden name="partymember.id"/>
    <div class="tc mt20">
        <input type="button" class="btnB2" value="保存" onclick="onSubmit()" />
        &nbsp;&nbsp;&nbsp;&nbsp;
        <input type="button"  onclick="javascript:history.go(-1)" class="btnB2" value="返回" />
    </div>
    </div></div></div>
</form>
</body>
</html>