<%@ page language="java" contentType="text/html; charset=utf8"
    pageEncoding="utf8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf8">
<title>Add User</title>
</head>
<body>
<h1>Add User</h1>
<form:form method="post" modelAttribute="user" action="add">

	<table>
		<tr>
			<td>Name</td>
			<td><form:input path="name" value = "${user.name}" /></td>
		</tr>
		<tr>
			<td>Password</td>
			<td><form:input path="password" value = "${user.password}" /></td>
		</tr>
	  	<tr>
			<td>Role</td>
			<td><select name="role" >
			<c:forEach items="${roles}" var="role"  >
			<option value="${role.id}" label="${role.role}">
			</c:forEach>
			</select></td>
		</tr>
		<tr>
			<td colspan="2"><input type="submit" value="Add" />  <a href="../all">Back</a></td>
		</tr>
		
	</table>

</form:form>

</body>
</html>