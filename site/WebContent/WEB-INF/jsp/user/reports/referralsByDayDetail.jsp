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
            excel.tracker.value = form.tracker.value;
            excel.sort.value = form.sort.value;
            excel.date.value = form.date.value;
            excel.page.value = form.page.value;
            excel.submit();
        }
    </script>
    <title>Referals by day</title>
</head>
<body>
<jsp:include page="../menuUser.jsp" />
<h1>Referals by day</h1>


<form name = "filters" action="referralsByDayDetail" method="post">
    <table>
        <jsp:include page="filters/byTracker.jsp" />
        <jsp:include page="filters/submit.jsp" />
    </table>
    <input type="hidden" name="sort" />
    <input type="hidden" name="date" value="${day}" />
    <input type='hidden' name='page'>

</form>

<table>
    <tr>
        <th>
            ID
            <a href="#" onclick="Go('id_up')">↑</a>
            <a href="#" onclick="Go('id_down')">↓</a>
        </th>
        <th>Sails</th>
        <th>
            Profit
            <a href="#" onclick="Go('profit_up')">↑</a>
            <a href="#" onclick="Go('profit_down')">↓</a>
        </th>
    </tr>
    <c:forEach items="${referrals}" var="refer">
        <tr>
            <td>${refer.id}</td>
        <td><c:forEach items="${refer.sails}" var="sail">
            ${sail.date} <br>
            <c:forEach items="${sail.products}" var="product">
                ${product.name} <br>
                ${product.amount} <br>
                ${product.cost} <br>
                ${product.discount} <br>
            </c:forEach>
            <br>
            </c:forEach></td>
            <td>${refer.viewProfit}</td>
        </tr>
    </c:forEach>
</table>

<jsp:include page="paginationPost.jsp" />

<form name = "excel" action="byDayDetailExcel" method="post">
    <input type='hidden' name='tracker'>
    <input type='hidden' name='sort'>
    <input type='hidden' name='date'>
    <input type='hidden' name='page'>

</form>
<a href="#" onclick="GoExcel()">Export to excel</a>

</body>
</html>
