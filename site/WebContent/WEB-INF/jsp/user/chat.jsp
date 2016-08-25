<%@ page language="java" contentType="text/html; charset=utf8"
	pageEncoding="utf8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf8">
<title>Chat with admin</title>
</head>
<body>
<jsp:include page="menuUser.jsp" />
<table>
		<c:forEach items="${chat}" var="chat">
			<tr>
				<td width="50">[${chat.date}]</td>
				<td width="30">${chat.from}:</td>
				<td width="400">${chat.text}</td>
			</tr>
		</c:forEach>
</table>
<jsp:include page="../pagin.jsp">
	<jsp:param value="chat" name="page"/>
</jsp:include>
<br>
<form action="addMessage" method="post">
<textarea rows="5" cols="50" name="message"></textarea><br>
<input type="submit" value="Send">
</form>
</body>
</html>