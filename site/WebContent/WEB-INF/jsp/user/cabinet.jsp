<%@ page language="java" contentType="text/html; charset=utf8"
	pageEncoding="utf8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf8">

	<style type="text/css">

		table {
			border-collapse: collapse;
		}

		table, th, td {
			border: 1px solid black;
			text-align: center;
		}
		tr:nth-child(even) {background-color: #f2f2f2}
	</style>
<title>Profile</title>
</head>
<body>
<jsp:include page="menuUser.jsp" />
	<div class="block">
		<h2>Personal information ${user.name}</h2>
		<h3>Balance: ${user.balance}</h3>
		<img src = "<%=request.getContextPath()%>/img?avaPic=${user.id}" width="180" height="150" /><br>
		<a href = "uploadAva">Upload ava</a>
		<table>
			<tr>
				<td>ID</td>
				<td>${user.id}</td>
			</tr>
			<tr>
				<td>Name</td>
				<td>${user.name}</td>
			</tr>
			<tr>
				<td>Second Name</td>
				<td>${user.secondName}</td>
			</tr>
			<tr>
				<td>Age</td>
				<td>${user.age}</td>
			</tr>
			<tr>
				<td>Phone</td>
				<td>${user.phone}</td>
			</tr>
			<tr>
				<td>who invited</td>
				<td>${user.refId}</td>
			</tr>
			<tr>
				<td>Your referer code</td>
				<td>${user.refCode}</td>
			</tr>
			<tr>
				<td><a href="edit">Edit profile</a></td>
			</tr>
			<tr>
				<td><a href="changePassword">Change password</a></td>
			</tr>
		</table>
	</div>

	<div class="block">
		<h2>Sails</h2>
		<table>
			<tr>
				<th>Product</th>
				<th>Date</th>
				<th>Count</th>
				<th>Price, $</th>
				<th>State</th>
				<th colspan="2" >Action</th>
			</tr>
			<c:forEach items="${sails}" var="sails">
				<tr><a href="#">view</a> </td>
					<td>${sails.date}</td>
					<td>${sails.amount}</td>
					<td>${sails.totalsum}</td>
					<td>${sails.state}</td>
					<c:if test="${sails.state eq 'SENT'}">
					<td><a href="deliveredSail?id=${sails.id}">Delivered</a></td>
					<td><a href="conflictSail?id=${sails.id}">Conflict</a></td>
					</c:if>
					<c:if test="${sails.state eq 'CONFLICT'}">
						<td><a href="deliveredSail?id=${sails.id}">Delivered</a></td>
					</c:if>
					<c:if test="${sails.state eq 'COMPLETE'}">
						<td>Delivered</td>
					</c:if>

				</tr>
			</c:forEach>
		</table>
	<jsp:include page="../pagin.jsp">
	<jsp:param value="profile" name="page"/>
</jsp:include>
	</div>
	
</body>
</html>