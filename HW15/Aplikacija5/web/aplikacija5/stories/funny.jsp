<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	session="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%
	String hex = (String) session.getAttribute("pickedBgCol");
	if (hex == null) {
		hex = "#FFFFFF";
	}
%>

<%!private String getNumber() {
		return String.valueOf(Math.round(Math.random() * 255));
	}%>

<%!private String getRGB() {
		return "rgb(" + getNumber() + "," + getNumber() + "," + getNumber()
				+ ")";
	}%>
	
<body bgcolor=<%=hex%>>
<div style="color:<%=getRGB()%>;">After his return from Rome,
	Will couldn’t find his luggage in the airport baggage area. He went to<br>
	the lost luggage office and told the woman there that his bags hadn’t<br>
	shown up on the carousel. She smiled and told him not to worry because<br>
	they were trained professionals and he was in good hands. Then she<br>
	asked Will,<br> “Has your plane arrived yet?”</div>