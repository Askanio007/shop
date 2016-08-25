<%@ page language="java" contentType="text/html; charset=utf8"
    pageEncoding="utf8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<style type="text/css">

table {
    border-collapse: collapse;
}

table, th, td {
    border: 1px solid black;
    text-align: center;
}
tr:nth-child(even) {background-color: #f2f2f2}

.block {
    width: 280px;
    padding: 5px;
    float: left;
}
</style>

<h3><a href="<%=request.getContextPath()%>/statistic/products">Statistic by product</a>    <a href="<%=request.getContextPath()%>/sail/all">Sail list</a>    <a href="<%=request.getContextPath()%>/buyer/all">Buyer list</a>    <a href="<%=request.getContextPath()%>/product/all">Product list</a>  <sec:authorize access="hasRole('ROLE_ADMIN')"><a href="<%=request.getContextPath()%>/user/list">User list</a></sec:authorize> <sec:authorize access="hasRole('ROLE_ADMIN')"><a href="<%=request.getContextPath()%>/user/listRole">Role list</a></sec:authorize>   <a href="<c:url value="/logout"/>">Logout</a></h3>
