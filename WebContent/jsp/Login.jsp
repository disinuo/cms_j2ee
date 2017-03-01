<%@ page language="java" contentType="text/html; charset=UTF-8"
	session= "false" 
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>CMS|Login</title>
</head>
<body>
<%String id=(String)request.getAttribute("cookie_id"); %>
	<form  method='post' action='/j2ee8/ShowScore'>		
		<label for='id'>name</label>		
		<input id='d' type='text' name='id' value=<%=id %>>
		<label for='password'>password</label>
		<input id='password' type='password' name='password' value=''>
		<input type='submit' name='btn' value='Login'>
	</form>
	<form method='post' action='/j2ee8/ShowScore'>
		<input type='submit' name='btn' value='Login_as_a_visitor'>
	</form>
<%@include file="Counter.jsp" %> 
</body>
</html>