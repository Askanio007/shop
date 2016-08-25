<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Info</title>
</head>
<body>
<a href="generate">Create products and buyers</a><br>
<a href="generateSail">Create sails</a>
<br>
 <c:if test="${user eq true }">
 Hello admin! Text for ROLE_ADMIN
 </c:if><br>
 <c:if test="${user eq false}">
 Hello all!
 </c:if> 
</body>
</html>