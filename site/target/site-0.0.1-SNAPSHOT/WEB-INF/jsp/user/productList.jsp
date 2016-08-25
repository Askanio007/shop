<%@ page language="java" contentType="text/html; charset=utf8"
	pageEncoding="utf8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf8">
<title>Products list</title>
</head>
<body>
<jsp:include page="menuUser.jsp" />
	<h3>Products</h3>
	
	<table>
			<tr>
			<th>Photo</th>
			<th>Name</th>
			<th>Cost</th>
			<th>Count</th>
			
			</tr>
	
	<c:forEach items="${productList}" var="product">
	<form:form action="buy?id=${product.id}" method="post">
			<tr>
			<td><c:forEach items="${product.picList}" var="pic">
				<img src="<%=request.getContextPath()%>/img?pic=${pic.picId}" width="120" height="120" />
			</c:forEach>
			</td>
			<td>${product.name}</td>
			<td>${product.viewCost}</td>
			<td><input name="amount" size="20" type="text" value="1" /></td>
			<td><input type = "submit" value="In basket"/></td>
			<c:if test="${discount.productId eq product.id}">
			<td>Discount ${discount.discount} !</td>
			</c:if>
			
			<c:forEach items="${privateDiscount}" var="disc">
			<c:if test="${disc.productId eq product.id}">
			<td>Private discount ${disc.discount} !</td>
			</c:if>
			</c:forEach>
			
			</tr>
	</form:form>
	
	</c:forEach>
	</table>
	<jsp:include page="../pagin.jsp">
		<jsp:param value="all" name="page" />
	</jsp:include>
</body>
</html>