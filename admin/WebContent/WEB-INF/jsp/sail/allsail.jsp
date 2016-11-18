 <%@ page language="java" contentType="text/html; charset=utf8"
	pageEncoding="utf8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf8">
	<title>Sail</title> 
</head>
<body>

<jsp:include page="../menu.jsp" />

<h2>Sail</h2>

<c:if test="${not empty infoMessage}"><h3>${infoMessage}</h3></c:if>
<c:if test="${role.addSail eq true }">
<h3><a href = "add">add new sail</a></h3></c:if>

<c:if test="${!empty sailView}">
	<table>
		<tr>
			<th>ID</th>
			<th>Date</th>
			<th>Product</th>
			<th>Count</th>
			<th>Price, $</th>
			<th>Buyers</th>
			<th>Action</th>
		</tr>
		<c:forEach items="${sailView}" var="sail">
				<tr>
					<td>${sail.id}</td>
					<td><c:out value="${sail.dateStr}" /></td>
					<td>
						<c:forEach items="${sail.products}" var="productList">
							<c:out value="${productList.name}" /><br>
						</c:forEach>
					</td>
					<td>${sail.amount}</td>
					<td>${sail.totalSum}</td>
					<td><c:forEach items="${sail.buyers}" var="buyer">
				${buyer.name}
				</c:forEach>
					</td>
					<td><a href="delete?id=${sail.id}">Delete</a></td>
				</tr>
			</c:forEach>
	</table>
</c:if>

<jsp:include page="../pagin.jsp" >
	<jsp:param value="all?" name="page"/>
</jsp:include>

</body>
</html>