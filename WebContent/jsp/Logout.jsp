<%@ page language="java" contentType="text/html; charset=UTF-8"
	session= "false" 
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
</head>
<body>
	<form method='POST' action='<%=request.getContextPath() +"/Login"%>'>
		<input type='submit' name='logout' value='logout'>
	</form>
</body>
</html>