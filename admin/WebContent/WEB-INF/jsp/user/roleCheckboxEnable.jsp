<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:choose>
<c:when test="${param.role eq true}"><td><input type="checkbox" name="${param.action}" checked="checked"> </td></c:when>
<c:otherwise><td><input type="checkbox" name="${param.action}" > </td></c:otherwise>
</c:choose>