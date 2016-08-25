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
			<th colspan="2">Action</th>
		</tr>
		<c:forEach items="${buyerList}" var="buyer">
		 <c:set value="0" var="i" />
			<tr>
				<td><a href="../sail/buyer/${buyer.id}">${buyer.id}</a></td> 
				<td>${buyer.name}</td>				
				<td>${buyer.info.secondName}</td>
				<td>${buyer.info.age}</td>
			  	<td>${buyer.info.phone}</td>
				<td><c:forEach items="${buyer.sails}" var="sails" varStatus="sailCount">
				<c:set value="${sailCount.count}" var="i" />
				</c:forEach>
				<c:choose>
				   <c:when test="${!empty i}">${i}</c:when>
   				   <c:otherwise>0</c:otherwise>
   				 </c:choose>
   				
				</td>
				<td><a href="edit/${buyer.id}">Edit</a></td>
				<td><a href="delete?id=${buyer.id}">Delete</a></td>
				<td><a href="chat?name=${buyer.name}">Chat</a></td>
			</tr>
		</c:forEach>	
	</table>
</c:if>
<jsp:include page="../pagin.jsp" >
	<jsp:param value="all" name="page"/>
</jsp:include>

</body>
</html>