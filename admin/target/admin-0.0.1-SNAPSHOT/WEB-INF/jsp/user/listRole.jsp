<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>List role</title>
</head>
<body>
<jsp:include page="../menu.jsp" />

<h2>Roles</h2>

<c:if test="${!empty roles}">
	<table>
		<tr>
			<th>ID</th>
			<th>Name</th>
			<th>info.jsp</th>
			<th>add sail</th>
			<th>action</th>
		</tr>
		<c:forEach items="${roles}" var="role">
				<tr>
					<td>${role.id}</td>
					<td>${role.role}</td>
					<jsp:include page="roleCheckbox.jsp" ><jsp:param value="${role.info}" name="role"/></jsp:include>
					<jsp:include page="roleCheckbox.jsp" ><jsp:param value="${role.addSail}" name="role"/></jsp:include>
					<td><a href="editRole?id=${role.id}">Edit</a></td>
				</tr>
			</c:forEach>
	</table>
</c:if>
</body>
</html>