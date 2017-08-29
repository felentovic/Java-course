<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true" %>
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
	<h1> Results:</h1>
	<table>
		<thead>
		<tr><th> Sin </th><th> Cos </th></tr>
		</thead>
		<tbody>
			<c:forEach var="result" items="${results }">
				<tr><th> sin(${result.degree }) = ${result.sin} </th>
				<th> cos(${result.degree }) = ${result.cos} </th></tr>
			</c:forEach>
		</tbody>
	</table>
	</body>
</html>