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
	<a href="./setcolor?color=white">WHITE</a>
	<a href="./setcolor?color=red">RED</a>
	<a href="./setcolor?color=green">GREEN</a>
	<a href="./setcolor?color=cyan">CYAN</a>