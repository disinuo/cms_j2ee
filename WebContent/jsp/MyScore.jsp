<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>CMS|showScore</title>
</head>
<body>
<jsp:useBean id="scores" type="javabean.ScoreListBean" scope="request"></jsp:useBean>
<jsp:useBean id="score" class="model.Score" scope="page"></jsp:useBean>
<jsp:useBean id="exams_not_taken" class="javabean.ExamListBean" scope="request"></jsp:useBean>
<jsp:useBean id="exam" class="model.Exam" scope="page"></jsp:useBean>
	
	<p>Welcome!  <%=request.getAttribute("chineseName")%></p>
	<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
			<TBODY>
				<TR>
					<TH width="25%">课程</TH>
					<TH width="25%">考试</TH>
					<TH width="25%">分数</TH>
					<TH width="25%">日期</TH>
				</TR>
				<%for (int i = 0; i < scores.getScoreList().size(); i++) {
						pageContext.setAttribute("score", scores.getScore(i));
				%>
				<TR>
					<TD align="center"><jsp:getProperty name="score" property="courseName" /></TD>
					<TD align="center"><jsp:getProperty name="score" property="examName"/></TD>
					<TD align="center"><jsp:getProperty name="score" property="score" /></TD>
					<TD align="center"><jsp:getProperty name="score" property="examDate" /></TD>
				</TR>
				<%}%>
			</TBODY>
	</TABLE>
	<%if(exams_not_taken!=null){%>
		<p style='color:red'>您有没参加的考试!</p>
		<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
			<thead>
				<TR>
					<TH width="25%">课程</TH>
					<TH width="25%">考试</TH>
					<TH width="25%">日期</TH>
				</TR>
			</thead>

			<TBODY style='color:red'>
				<%for (int i = 0; i < exams_not_taken.getExamList().size(); i++) {
						pageContext.setAttribute("exam", exams_not_taken.getExam(i));
				%>
				<TR>
					<TD align="center"><jsp:getProperty name="exam" property="courseName" /></TD>
					<TD align="center"><jsp:getProperty name="exam" property="name"/></TD>
					<TD align="center"><jsp:getProperty name="exam" property="date" /></TD>
				</TR>
				<%}%>
			</TBODY>
	</TABLE>
	<%} %>
		
</body>
		<%@include file="Logout.jsp" %> 
		<%@include file="Counter.jsp" %> 

</html>