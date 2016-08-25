 <%@ page language="java" contentType="text/html; charset=utf8"
	pageEncoding="utf8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Registration</title>
</head>
<body>
<h2>Registration</h2>
<form:form method="post" action="reg" commandName="password">
		<table>
			<tr>
				<td>Enter Login</td>
				<td><input name="name" /></td>
			</tr>
			<tr>
				<td>Password</td>
				<td><form:password path="newPassword" /></td>
				<td><form:errors path="newPassword" /></td>
			</tr>
			<tr>
				<td>Conf Password</td>
				<td><form:password path="confPassword" /></td>
			</tr>
			<tr>
				<td>Enter Referal Code</td>
				<td><input name="refer" /></td>
			</tr>
			<tr>
				<td><input type="submit" value="Registration" /></td>
			</tr>
		</table>
	</form:form>
<a href = "login.jsp">I have account</a>

</body>
</html>