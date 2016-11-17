 <%@ page language="java" contentType="text/html; charset=utf8"
	pageEncoding="utf8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf8">
<title>Login</title>
</head>
<body>
<c:if test="${not empty param.error}">
	<font color="red">Bad credits
	: ${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message} </font>
</c:if>
<h2>Authorization in Admin Shop</h2>
<form method="post" action="<c:url value="/login" />">
<table>
	<tr>
		<td align="right">Login</td>
		<td><input type="text" name="username" /></td>
	</tr>
	<tr>
		<td align="right">Password:</td>
		<td><input type="password" name="password" /></td>
	</tr>
	<tr>
		<td><input type="submit"  value="login" /></td>
	</tr>
</table>
</form>
<h1><a href="<%=request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()%>/site/">Go to shop</a></h1>

</body>


</html>