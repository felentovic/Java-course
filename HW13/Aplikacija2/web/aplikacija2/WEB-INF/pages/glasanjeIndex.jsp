<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	session="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<body>
	<h1>Glasanje za omiljeni bend:</h1>
	<p>Od sljedećih bendova, koji Vam je bend najdraži? Kliknite na
		link kako biste glasali!</p>
	<ol>
		<c:forEach var="bend" items="${bendovi }">
			<li><a href="glasanje-glasaj?id=${bend.id}">${ bend.ime}</a></li>			
		</c:forEach>
	</ol>
</body>
</html>
