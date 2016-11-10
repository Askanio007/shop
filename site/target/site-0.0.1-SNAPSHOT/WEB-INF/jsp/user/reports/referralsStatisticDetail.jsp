<%@ page language="java" contentType="text/html; charset=utf8"
         pageEncoding="utf8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf8">
        <script type="text/javascript" src="http://code.jquery.com/jquery-1.10.1.min.js"></script>
    <link rel="stylesheet" href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">
    <script src="//code.jquery.com/jquery-1.10.2.js"></script>
    <script src="//code.jquery.com/ui/1.11.4/jquery-ui.js"></script>
    <link rel="stylesheet" href="/resources/demos/style.css">
    
        <script language='javascript'>
        function Go(val) {
            var form=document.filters;
            form.sort.value=val;
            form.submit();
        }

        function GoExcel() {
            var form = document.filters;
            var excel = document.excel;
            excel.sailFrom.value = form.sailFrom.value;
            excel.sailTo.value = form.sailTo.value;
            excel.id.value = form.id.value;
            excel.sort.value = form.sort.value;
            excel.page.value = form.page.value;
            excel.submit();
        }
    </script>
    <title>Referals Detail ${referral.name}</title>
</head>
<body>
<jsp:include page="../menuUser.jsp" />
<h1>Detail statistic by ${referral.name}</h1>
<form name = "filters" action="referralStatisticDetail" method="post">
    <table>
        <jsp:include page="filters/bySailDate.jsp" />
        <jsp:include page="filters/submit.jsp" />
        <tr>
            <td>Id buyer <input name="id" value="${referral.id}" /> </td>
        </tr>
    </table>
        <input type="hidden" name="sort"  value="${sort}">
    <input type='hidden' name='page'>
</form>

<table>
    <tr>
        <th>
        	Id
        	<a href="#" onclick="Go('id_up')">↑</a>
            <a href="#" onclick="Go('id_down')">↓</a>
        </th>
        <th>
        	Date
        	<a href="#" onclick="Go('date_up')">↑</a>
            <a href="#" onclick="Go('date_down')">↓</a>
        </th>
        <th>
        	Products profit
        </th>
        <th>
        	Total cost
        	<a href="#" onclick="Go('totalsum_up')">↑</a>
            <a href="#" onclick="Go('totalsum_down')">↓</a>
        </th>
        <th>
        	Cashback Percent
        	<a href="#" onclick="Go('cashbackPercent_up')">↑</a>
            <a href="#" onclick="Go('cashbackPercent_down')">↓</a>
        </th>
        <th>
        	Total Profit
        	<a href="#" onclick="Go('profit_up')">↑</a>
            <a href="#" onclick="Go('profit_down')">↓</a>
        </th>
    </tr>
    <c:forEach items="${sails}" var="sail">
        <tr>
            <td>${sail.sail.id}</td>
            <td>${sail.sail.date}</td>
            <td> <c:forEach items="${sail.products}" var="product">
                ${product.product.name} x${product.product.amount}    ${product.viewProfit} <br>
            </c:forEach></td>
            <td>${sail.sail.viewTotalsum}</td>
            <td>${sail.sail.cashbackPercent}</td>
            <td>${sail.viewProfit}</td>
        </tr>
    </c:forEach>
</table>

<jsp:include page="paginationPost.jsp" />

<form name = "excel" action="referralDetailExcel" method="post">
    <input type='hidden' name='sailTo'>
    <input type='hidden' name='sailFrom'>
    <input type='hidden' name='id'>
    <input type='hidden' name='sort'>
    <input type='hidden' name='page'>

</form>
<a href="#" onclick="GoExcel()">Export to excel</a>

</body>
</html>
