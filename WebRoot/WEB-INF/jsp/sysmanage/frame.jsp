<%@ page language="java" pageEncoding="UTF-8"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    application.setAttribute("basePath",basePath);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <base href="<%=basePath%>"/>
    <!-- <title>党建学习管理平台-党建管理</title> -->
    <title>系统管理</title>
    <link href="${basePath}css/sysmanage/css.css" rel="stylesheet" type="text/css" />
    <link href="${basePath}css/sysmanage/style.css" rel="stylesheet" type="text/css" />
</head>

<frameset cols="*,1222,*" class="bj" frameborder="no" border="0" framespacing="0" name="sysframe">
    <frame src="${basePath}common/bg.jsp" scrolling="No" noresize="noresize"/>
    <frameset rows="156,*" cols="*" frameborder="no" border="0" framespacing="0"> 
        <frame src="${basePath}sysmanage/home_top.action" scrolling="No" noresize="noresize" id="topFrame" />
        <frameset cols="14%,60%" frameborder="no" border="0" framespacing="0">
            <frame src="${basePath}sysmanage/home_left.action" scrolling="yes" noresize="noresize" id="leftFrame" name="leftFrame"/>
            <frame src="${basePath}common/welcome.jsp" name="mainFrame" id="mainFrame" />
        </frameset>
    </frameset>
    <frame src="${basePath}common/bg.jsp" scrolling="No" noresize="noresize"/>
</frameset>
<body>
<br>
</body>
</html>