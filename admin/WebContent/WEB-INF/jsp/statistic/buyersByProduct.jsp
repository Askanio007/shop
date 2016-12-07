 <%@ page language="java" contentType="text/html; charset=utf8"
	pageEncoding="utf8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf8">

<title>Statistics by product</title>
</head>
<body>

<h2>Statistics by product - ${product.name} </h2>

<c:choose>
<c:when test="${!empty soldProducts}">
	<table >
		<tr>
			<th>ID</th>
			<th>Name</th>
			<th>Price, $</th>
			<th>Amount</th>
			<th>Discount, %</th>
			<th>Buyer</th>
		</tr>

		<c:forEach items="${soldProducts}" var="soldProduct">
			<tr>
				<td>${soldProduct.id}</td>
				<td>${soldProduct.name}</td>
				<td>${soldProduct.cost}</td>
				<td>${soldProduct.amount}</td>
				<td>${soldProduct.discount}</td>
				<td>${soldProduct.buyerName}</td>
					</tr>
		</c:forEach>
	</table>
</c:when>
<c:when test="${empty soldProducts}">
<h3>The products don't have sails</h3>
</c:when>
</c:choose>
<jsp:include page="../popupPagin.jsp" >
	<jsp:param value="${product.id}" name="page"/>
</jsp:include>
</body>
</html>