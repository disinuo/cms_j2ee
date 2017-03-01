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
<%
int counter_visitor=Integer.parseInt((String)pageContext.findAttribute("counter_visitor"));
int counter_login=Integer.parseInt((String)pageContext.findAttribute("counter_login"));
int counter_total=counter_login+counter_visitor;

%>
<p style='font-size:10px'>站点统计：	在线人数：<%= counter_total%>,  已登录：<%=counter_login %>,  游客：<%=counter_visitor %></p>
</body>
</html>