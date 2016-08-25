<%@ page language="java" contentType="text/html; charset=utf8"
	pageEncoding="utf8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf8">
<title>Basket</title>
</head>
<body>
<jsp:include page="menuUser.jsp" />

<c:if test="${not empty basket.products}">
<c:forEach items="${basket.products}" var="product">
		<div>
			<br> product:${product.name} 
			     amount: ${product.amount}
			     cost:  ${product.cost} - ${product.discount} %   <a href="deleteFromBasket?id=${product.id}">Remove</a>
		</div>
	</c:forEach>
<h2><a href="order">Order</a></h2>
</c:if>
</body>
</html>