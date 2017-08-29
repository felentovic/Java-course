<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	session="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<body>
	<h1>Ankete</h1>
	<p>Odaberite anketu</p>
	<ol>
		<c:forEach var="poll" items="${pollovi }">
			<li> 
			<a href="./glasanje?pollID=<c:out value="${poll.id}"/>"><c:out value="${poll.title}"/> </a></li>			
		</c:forEach>
	</ol>
</body>
</html>