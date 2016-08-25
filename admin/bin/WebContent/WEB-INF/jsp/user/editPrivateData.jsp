<%@ page language="java" contentType="text/html; charset=utf8"
	pageEncoding="utf8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf8">
<title>Edit profile</title>
</head>
<body>
<h1>Edit profile</h1>
<form:form method="post" commandName="newInfo" action="edit">

	<table>
		<tr>
			<td>Second Name</td>
			<td><form:input path="secondName" value = "${info.secondName}"/></td>
			<td><form:errors path="secondName" /></td>
		</tr>
		
		<tr>
			<td>Age</td>
			<td><form:input path="age" value = "${info.age}" /></td>
			<td><form:errors path="age" /></td>
		</tr>
		
		<tr>
			<td>Phone</td>
			<td><form:input path="phone"  value = "${info.phone}" /></td>
			<td><form:errors path="phone" /></td>
		</tr>
		<tr>
			<td colspan="2"><input type="submit" value="Save" />  <h3><a href = "profile">Back</a></h3>
		</tr>
		
	</table>

</form:form>
</body>
</html>