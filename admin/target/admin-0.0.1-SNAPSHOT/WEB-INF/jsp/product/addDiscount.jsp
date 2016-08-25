<%@ page language="java" contentType="text/html; charset=utf8"
    pageEncoding="utf8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf8"> 
<title>add Discount</title>
</head>

<form:form commandName = "disc" method = "post" action = "addDiscount">
<table>

<tr>
			<td>Product id</td>
			<td><form:input path="productId" type="text" readonly="true" value="${product}" /></td>
		</tr>
		<tr>
			<td>Buyer Name</td>
			<td><select name="buyerName">
			<c:forEach items="${buyers}" var="buyer">
			<option>${buyer.name}</option>
			</c:forEach>
			</select>
			</td>
		</tr>
		<tr>
			<td>Discount, %</td>
			<td><form:input path="discount" /></td>
		</tr>
		<tr>
			<td colspan="2"><input type="submit"
				value="Add discount" /></td>
		</tr>
		
	</table>
</form:form>
<body>

</body>
</html>