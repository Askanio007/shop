<%@ page language="java" contentType="text/html; charset=utf8"
    pageEncoding="utf8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf8">
<title>Edit product</title>
</head>
<body>
<h1>Edit Product ${product.name}</h1>

	<jsp:include page="uploadZip.jsp">
		<jsp:param name="met" value="../upload?id=${product.id}" />
	</jsp:include>

	<form:form method="post" commandName="product"
		action="../edit/${product.id}">
		<table>
			<tr>
				<td>Id</td>
				<td><form:input path="id"  readonly="readonly" /></td>
			</tr>
			<tr>
				<td>Name</td>
				<td><form:input path="name" /></td>
				<td><form:errors path="name" /></td>
			</tr>
			<tr>
				<td>Cost</td>
				<td><form:input path="cost" /></td>
				<td><form:errors path="cost" /></td>
			</tr>
			<tr>
				<c:if test="${empty pic}">
					<c:forEach items="${product.picPath}" var="pic">
						<th><img
							src="<%=request.getContextPath()%>/img?pic=${pic}"
							width="120" height="120" /></th>
					</c:forEach>
				</c:if>
				<c:if test="${not empty pic}">
				<c:set var="count" value="0" scope="page" />
					<c:forEach items="${pic}" var="pic">
						<th><img
							src="<%=request.getContextPath()%>/img?tempPic=${count}"
							width="120" height="120" /></th>
					<c:set var="count" value="${count+1}" scope="page" />
					</c:forEach>
				</c:if>
			</tr>
			<tr>
				<td colspan="2"><input type="submit" value="Save" /> <a
					href="../all">Back</a></td>
			</tr>
		</table>
	</form:form>
</body>
</html>