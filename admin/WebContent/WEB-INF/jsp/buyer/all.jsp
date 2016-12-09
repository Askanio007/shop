<%@ page language="java" contentType="text/html; charset=utf8"
	pageEncoding="utf8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf8">
<title>Users</title>
</head>
<body>
<jsp:include page="../menu.jsp" />

<h2>Buyers</h2>

<c:if test="${not empty infoMessage}"><h3>${infoMessage}</h3></c:if>

<c:if test="${!empty buyerList}">
	<table>
		<tr>
			<th>Id</th>
			<th>Name</th>
			<th>SecondName</th>			
			<th>Age</th>
			<th>Phone</th>
			<th>Count sails</th>
			<th>Percent cashback</th>
			<th>Balance</th>
			<th colspan="3">Action</th>
		</tr>
		<c:forEach items="${buyerList}" var="buyer">
		 <c:set value="0" var="i" />
			<tr>
				<td><a href="../sail/buyer/${buyer.id}">${buyer.id}</a></td> 
				<td>${buyer.name}</td>				
				<td>${buyer.secondName}</td>
				<td>${buyer.age}</td>
			  	<td>${buyer.phone}</td>
				<td>${buyer.countSails}</td>
				<td>${buyer.percentCashback}</td>
				<td>${buyer.balance}</td>
				<td><a href="edit/${buyer.id}">Edit</a></td>
				<td><a href="delete?id=${buyer.id}">Delete</a></td>
				<td><a href="chat/${buyer.name}">Chat</a></td>
			</tr>
		</c:forEach>	
	</table>
</c:if>
<jsp:include page="../pagin.jsp" >
	<jsp:param value="all?" name="page"/>
</jsp:include>

</body>
</html>