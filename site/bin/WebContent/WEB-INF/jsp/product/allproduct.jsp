<%@ page language="java" contentType="text/html; charset=utf8"
	pageEncoding="utf8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf8">
	<title>Products</title> 
</head>
<body>
<jsp:include page="../menu.jsp" />
  
<h2>Products</h2>

	<c:if test="${not empty infoMessage}">
		<h3>${infoMessage}</h3>
	</c:if>
	<h3>
		<a href="add">add new product</a>
	</h3>
	<c:if test="${!empty productList}">
		<table>
			<tr>
				<th>Id</th>
				<th>Name</th>
				<th>Cost</th>
				<th colspan="3">Actions</th>
			</tr>
			<c:forEach items="${productList}" var="product">
				<tr>
					<td>${product.id}</td>
					<td><a href="../sail/product/${product.id}">${product.name}</a></td>
					<td>${product.cost}</td>
					<td><a href="edit/${product.id}">Edit</a></td>
					<td><a href="delete?id=${product.id}">Delete</a></td>
					<td><a href="addDiscount?id=${product.id}">Add discount</a></td>
				</tr>
			</c:forEach>
		</table>
	</c:if>


	<jsp:include page="../pagin.jsp">
	<jsp:param value="all" name="page"/>
</jsp:include>



</body>
</html>