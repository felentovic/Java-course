<%@ page import="java.time.LocalDateTime"
		import ="java.time.Duration"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	session="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%
	String hex = (String) session.getAttribute("pickedBgCol");
	if (hex == null) {
		hex =(String) getServletContext().getAttribute("defcolor");
	}
%>
	<body bgcolor=<%=hex%>>
<html>
<body>
	<h1>Server is running for:</h1>
	<p><%=getElapsedTime()%></p>
</body>
</html>

<%!private String getElapsedTime() {
	  LocalDateTime now = LocalDateTime.now();
      LocalDateTime init = (LocalDateTime) getServletContext().getAttribute("initTime");
     
      Duration diff = Duration.between(init, now);
      long days = diff.toDays();
      long hours = diff.minusDays(days).toHours();
      long minutes = diff.minusHours(diff.toHours()).toMinutes();
      long seconds = diff.minusMinutes(diff.toMinutes()).getSeconds();
      long milis = diff.minusSeconds(diff.getSeconds()).toMillis();
		String dateFormatted =days+ " days "
							+ hours+ " hours " 
							+ minutes+ " minutes "
							+ seconds+" seconds "
							+ milis+" miliseconds ";
		return dateFormatted;
	}%>

