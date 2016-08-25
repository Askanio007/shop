<%@ page language="java" contentType="text/html; charset=utf8"
    pageEncoding="utf8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf8"> 
<title>Add Product</title>
</head>
<body>
<h1>Add Product</h1>

	<jsp:include page="uploadZip.jsp">
		<jsp:param name="met" value="uploadAva" />
	</jsp:include>
		<c:if test="${not empty infoMessage}">
		<h5>${infoMessage}</h5>
	</c:if>

	<c:if test="${not empty pics}">
		<table>
			<tr>
				<c:forEach items="${pics}" var="pic">
					<th><img src="${pic.path}" width="120" height="120"></th>
				</c:forEach>
			</tr>
		</table>
	</c:if>

	<form:form method="post" commandName="product" action="add">

	<table>
		<tr>
			<td>Name</td>
			<td><form:input path="name" /></td>
			<td><form:errors path="name" /></td>
		</tr>
		<tr>
			<td>Cost</td>
			<td><form:input path="cost" /></td>
			<td><form:errors path="cost" /></td>
		</tr>
		<tr>
			<td colspan="2"><input type="submit"
				value="Add product" /></td>
		</tr>
		
	</table>
</form:form>
<a href="all">Back</a>
</body>
</html>