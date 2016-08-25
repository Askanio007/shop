<%@ page language="java" contentType="text/html; charset=utf8"
         pageEncoding="utf8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf8">
    <title>Generate invite link</title>
</head>
<body>
<jsp:include page="menuUser.jsp" />
<h1>Generate invite link</h1>
<form action="generateInviteLink" method="post">
    <table>
        <tr>
            <td>
                Enter ancor:
            </td>
            <td>
                <input name="ancor" type="text" />
            </td>
        </tr>
        <tr>
            <td>
                Enter tracker:
            </td>
            <td>
                <input name="tracker" type="text" />
            </td>
        </tr>
        <tr><input type="submit" value="generate" /></tr>
    </table>
</form>
    <br>
<c:if test="${inviteLink != null}">${inviteLink}</c:if>
</body>
</html>
