<%@ page language="java" contentType="text/html; charset=utf8"
    pageEncoding="utf8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<style type="text/css">

table {
    border-collapse: collapse;
}

table, th, td {
    border: 1px solid black;
    text-align: center;
}
tr:nth-child(even) {background-color: #f2f2f2}
</style>

<h3><a href="../sail/all">Sail list</a>    <a href="../buyer/all">Buyer list</a>    <a href="../product/all">Product list</a>    <a href="<c:url value="/logout"/>">Logout</a></h3>
