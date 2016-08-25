<%@ page language="java" contentType="text/html; charset=utf8"
    pageEncoding="utf8"%>
    <%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf8">
<title>All sail by buyer</title>
</head>
<body>
<jsp:include page="../menu.jsp" />
<h2>sail by buyer "${buyer.name}"</h2>
<c:if test="${not empty infoMessage}"><h3>${infoMessage}</h3></c:if>
<c:choose>
<c:when test="${!empty sailview}">
	<table >
		<tr>
			<th>ID</th>
			<th>Date</th>
			<th>Count</th>
			<th>Price, $</th>
			<th>Products</th>
			<th>&nbsp;</th>
		</tr>

		<c:forEach items="${sailview}" var="allsail">
			<tr>
				<td>${allsail.id}</td>
				<td><c:out value="${allsail.dateStr}" /></td>
				<td>${allsail.amount}</td>
				<td>${allsail.totalsum}</td>
				<td><c:forEach items="${allsail.products}" var="productList">
							<c:out value="${productList.name}" /><br>
					</c:forEach></td>
				<td><a href="../delete?buy=${buyer.id}&id=${allsail.id}">Delete</a></td>
			</tr>
		</c:forEach>
	</table>
</c:when>
<c:when test="${empty allsail}">
<h3>The product don't have sails</h3>
</c:when>
</c:choose>


<jsp:include page="../pagin.jsp" >
	<jsp:param value="sailbuyer?" name="page"/>
</jsp:include>


<h4><a href="../../buyer/all">Back</a></h4>


</body>
</html>