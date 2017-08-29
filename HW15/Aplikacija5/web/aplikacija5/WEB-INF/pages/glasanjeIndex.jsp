<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	session="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<body>
	<h1><c:out value="${poll.title}"/></h1>
	<p><c:out value="${poll.message}"/></p>
	<ol>
		<c:forEach var="stavka" items="${stavke }">
			<li><a href="glasanje-glasaj?id=<c:out value="${stavka.id}"/>"><c:out
						value="${stavka.naziv}" /></a></li>
		</c:forEach>
	</ol>
</body>
</html>
