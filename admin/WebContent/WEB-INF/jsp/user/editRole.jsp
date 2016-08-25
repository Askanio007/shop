<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Role ${role.role}</title>
</head>
<body>
<form:form method="post" commandName="role" action="editRole">
		<table>
			<tr>
				<td>Id</td>
				<td><input type="text" value="${role.id}" name="id" readonly></td>
			</tr>
			<tr>
				<td>Name</td>
				<td><input type="text" value="${role.role}" name="role" readonly></td>
			</tr>
			<tr>
				<td>Info.jsp</td>
				<jsp:include page="roleCheckboxEnable.jsp" >
					<jsp:param value="${role.info}" name="role"/>
					<jsp:param value="info" name="action"/>
				</jsp:include>
			</tr>
			<tr>
				<td>Add sail</td>
				<jsp:include page="roleCheckboxEnable.jsp" >
					<jsp:param value="${role.addSail}" name="role"/>
					<jsp:param value="addSail" name="action"/>
				</jsp:include>
			</tr>
			<tr>
				<td colspan="2"><input type="submit" value="Save" /> <a href="listRole">Back</a></td>
			</tr>
		</table>
	</form:form>

</body>
</html>