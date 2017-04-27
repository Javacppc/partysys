<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@include file="/common/header.jsp"%>
    <title>党费管理</title>
    <style type="text/css">
    	#alert {border:1px solid #369;width:300px;height:100px;background:#e2ecf5;z-index:1000;position:absolute;display:none;} 
		#alert h4 {height:20px;background:#369;color:#fff;padding:5px 0 0 5px;} 
		#alert h4 span {float:left;} 
		#alert h4 span#close {margin-left:220px;font-weight:500;cursor:pointer;} 
		#alert p {padding:12px 0 0 30px;} 
		#alert p input {width:120px;margin-left:20px;} 
		#alert p input.myinp {border:1px solid #ccc;height:16px;} 
		#alert p input.sub {width:60px;margin-left:33px;} 
		#alert p input.sub2 {width:60px;margin-left:33px;} 
    </style>
</head>
<body class="rightBody">
<form name="form1" action="" method="post">
    <div class="p_d_1">
        <div class="p_d_1_1">
            <div class="content_info">
                <div class="c_crumbs"><div><b></b><strong>党费管理 </strong></div></div>
                <div class="t_list" style="margin:0px; border:0px none;">
                    <table width="100%" border="0">
                        <tr class="t_tit">
                            <td width="120" align="center">姓名</td>
                            <td align="center">性别</td>
                            <td width="80" align="center">民族</td>
                            <td width="50" align="center">党费</td>
                            <td width="50" align="center">经办人</td>
                            <td width="50" align="center">操作</td>
                        </tr>
                       		<s:iterator value="pageResult.items" status="st">
                            <tr <s:if test="#st.odd">bgcolor="f8f8f8"</s:if> >
                                <td align="center"><input type="hidden" name="selectedRow" value="<s:property value='deusId'/>"/></td>
                                <td align="center"><s:property value="partymember.name"/></td>
                                <td align="center"><s:property value="%{partymember.gender==true?'男':'女'}"/></td>
                                <td align="center"><s:property value="partymember.nation"/></td>
                                <!-- 党费初始值每个人均为0 -->
                                <td align="center"><s:property value="cost" value="0"/></td>
                                <td align="center"><s:property value="manager"/></td>
                                <td align="center"><a href="javascript:doEntryIn('<s:property value='deusId'/>')">录入</a></td>
                            </tr>
                           </s:iterator>
                    </table>
                </div>
                <div class="tc mt20">
       				 <input type="button"  onclick="javascript:history.go(-1)" class="btnB2" value="返回" />
    			</div>
            </div>
        </div>
    </div>
    
</form>
<div id="alert">
	<form name="entrydeus" method="post" action="${basePath}partymanage/deus_entry.action">
		<h4><span>录入党费</span><span id="close">关闭</span></h4>
		<p><label>缴纳党费:</label><input id="deusEdit" name="deus.cost" type="text" class="myinp" onmouseover="this.style.border='1px solid #f60'" onfocus="this.style.border='1px solid #f60'" onblur="this.style.border='1px solid #ccc'" /></p>
		<p><label>经办人:</label><input id="managerEdit" name="deus.manager" type="text" class="myinp" onmouseover="this.style.border='1px solid #f60'" onfocus="this.style.border='1px solid #f60'" onblur="this.style.border='1px solid #ccc'" /></p>    
		<input id="hidedeusId" type="hidden" name="deus.deusId"/>
		<p><input type="submit" value="录入" class="sub" /><input type="reset" value="重置" class="sub2" /></p> 
	</form>
</div>
<script type="text/javascript">
	var myAlert = document.getElementById("alert"); 
   	var mClose = document.getElementById("close"); 
  	//录入
  	function doEntryIn(id){
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
		$("#hidedeusId").val(id);
  	}
  	 //关闭弹出的窗口
	mClose.onclick = function() 
	{ 
		myAlert.style.display = "none"; 
		mybg.style.display = "none"; 
	} 
	
</script>
</body>
</html>