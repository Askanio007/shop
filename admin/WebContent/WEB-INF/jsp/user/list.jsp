<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
   <%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>User list</title>
</head>
<body>
<jsp:include page="../menu.jsp" />

<h2>Users</h2>

<c:if test="${not empty infoMessage}"><h3>${infoMessage}</h3></c:if>

	<h3>
		<a href="add">add new user</a>
	</h3>

<c:if test="${!empty userList}">
	<table>
		<tr>
			<th>Id</th>
			<th>Name</th>
			<th>Active</th>			
			<th>Role</th>
			<th colspan="2">Action</th>
		</tr>
		<c:forEach items="${userList}" var="user">
			<tr>
				<td>${user.id}</td> 
				<td>${user.name}</td>
				<c:choose>
				<c:when test="${user.enable eq true }"><td>Active</td></c:when>
				<c:otherwise><td>Disable</td></c:otherwise>
				</c:choose>				
				<td>${user.role}</td>
				<td><a href="delete?id=${user.id}">Delete</a></td>
			</tr>
		</c:forEach>	
	</table>
</c:if>
<jsp:include page="../pagin.jsp" >
	<jsp:param value="all?" name="page"/>
</jsp:include>
</body>
</html>