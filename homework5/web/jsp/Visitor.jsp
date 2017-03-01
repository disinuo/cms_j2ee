<%@ page language="java" contentType="text/html; charset=UTF-8"
	session= "false" 
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>CMS|visitor</title>
</head>
<body>
   	<p>Welcome 游客~！</p>
	<p>想看更多信息就去登录吧！</p>
	<%@include file="GoToLogin.jsp" %> 
	<%@include file="Counter.jsp" %> 
		
</body>
</html>