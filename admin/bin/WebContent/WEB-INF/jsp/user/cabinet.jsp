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
   .block { 
    width: 280px; 
    padding: 5px;
    float: left;
   }
  </style>
<title>Profile</title>
</head>
<body>
<jsp:include page="menuUser.jsp" />
	<div class="block">
		<h3>Personal information ${user.name}</h3>
		<img src = "${user.info.ava}" width="180" height="150" /><br>
		<!-- тут ${user.info.ava} всегда что-то типа D:\folderPath\fileName.ext -->
		<!-- всегда Карл!!! -->
		<!-- все прекрасно пока ты будешь всех твоих пользователей пускать за свой комп, на котором крутится твой магазин -->
		<!-- я же как твой пользователь из Мухосранска зайду со своего компа сюда и увижу что моя ава ссылается на мой локалный диск-->
		<!-- так же и с картинками на продукты -->
		<!-- необходимо ссылаться на файл в интернете -->>
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
				<td>${user.info.secondName}</td>
			</tr>
			<tr>
				<td>Age</td>
				<td>${user.info.age}</td>
			</tr>
			<tr>
				<td>Phone</td>
				<td>${user.info.phone}</td>
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
			</tr>
			<c:forEach items="${sails}" var="sails">
				<tr>
					<td width="100"><c:forEach items="${sails.products}"
							var="productList">
							<c:out value="${productList.name}" />
							<br>
						</c:forEach></td>
					<td>${sails.date}</td>
					<td>${sails.amount}</td>
					<td>${sails.totalsum}</td>
				</tr>
			</c:forEach>
		</table>
	<jsp:include page="../pagin.jsp">
	<jsp:param value="profile" name="page"/>
</jsp:include>
	</div>
	
</body>
</html>