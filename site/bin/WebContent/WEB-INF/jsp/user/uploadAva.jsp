<%@ page language="java" contentType="text/html; charset=utf8"
	pageEncoding="utf8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf8">
<title>Upload</title>
</head>
<body>
<c:if test="${empty ava}"><img src="${user.info.ava}" width="300" height="400"  /></c:if>
	<img src="${ava}" width="180" height="150" />
	<jsp:include page="../product/uploadZip.jsp">
		<jsp:param name="met" value="uploadAva" />
	</jsp:include>

	<form:form method="post" action="saveAva">
		<table>
			<tr>
			<!-- 	<td><input name="avat" type="text" 
					value="${ava}" /></td> -->
				<td><input type="submit" value="Save" /></td>
			</tr>
		</table>
	</form:form>
	<a href="profile">Back</a>
</body>
</html>