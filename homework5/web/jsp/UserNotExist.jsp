<%@ page language="java" contentType="text/html; charset=UTF-8"
	session= "false" 
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>CMS|Wrong ID</title>
</head>
<body>
<p>对不起该账号不存在！</p>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@include file="GoToLogin.jsp" %> 
<%@include file="Counter.jsp" %> 
</body>
</html>