<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%
	String hex = (String) session.getAttribute("pickedBgCol");
	if (hex == null) {
		hex =(String) getServletContext().getAttribute("defcolor");
	}
%>
	<body bgcolor=<%=hex%>>
	
<body>Invalid parameters <br>
			<c:forEach var = "item" items="${error }" >
				${item} <br>
			</c:forEach>
			
</body>