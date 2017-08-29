<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	session="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%
	String hex = (String) session.getAttribute("pickedBgCol");
	if (hex == null) {
		hex =(String) getServletContext().getAttribute("defcolor");
	}
%>
	<body bgcolor=<%=hex%>>
	<a href="colors.jsp">Background colors</a><br>	
	<a href="./trigonometric?a=0&b=90">Trigonometrics a = 0 and b = 90</a><br>
	<a href="stories/funny.jsp"> Funny story</a><br>
	<a href="./powers?a=1&b=100&n=3">Excel file parameters a=1, b=100, n=3</a><br>	
	<a href="appinfo.jsp">Time elapsed from server startup</a><br>	
	
	