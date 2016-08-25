<%@ page language="java" contentType="text/html; charset=utf8"
         pageEncoding="utf8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf8">
    <title>Deposit</title>
</head>
<body>
<jsp:include page="menuUser.jsp" />
<h1>Deposit</h1>
<form action="deposit" method="post">
    <table>
        <tr>
            <td>Введите сумму:</td>
            <td><input name="sum" type="text" /></td>
        </tr>
    </table>
    <input type="submit" value="Create pay">
</form>

</body>
</html>
