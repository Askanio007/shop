<%@ page language="java" contentType="text/html; charset=utf8"
	pageEncoding="utf8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf8">
<title>Change password</title>
</head>
<body>
<h1>Edit profile</h1>
<form:form commandName="password" method="post" action="changePassword">

	<table>
		<tr>
			<td>Old Password</td>
			<td><input type="password" name="oldPassword" /></td>
			<td>${validEq}</td>
		</tr>
		<tr>
			<td>New Password</td>
			<td><form:password path="newPassword" /></td>
			<td><form:errors path="newPassword" /></td>
		</tr>
		<tr>
			<td>Confirm Password</td>
			<td><form:password path="confPassword" /></td>
			<td><form:errors path="confPassword" /></td>
		</tr>

		
		<tr>
			<td colspan="2"><input type="submit" value="Save" />  <a href="profile">Back</a></td>
		</tr>
		
	</table>

</form:form>
</body>
</html>