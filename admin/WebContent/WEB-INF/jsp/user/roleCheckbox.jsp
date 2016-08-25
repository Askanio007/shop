<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:choose>
<c:when test="${param.role eq true}"><td><input type="checkbox" checked="checked" disabled="disabled"> </td></c:when>
<c:otherwise><td><input type="checkbox"  disabled="disabled"> </td></c:otherwise>
</c:choose>
