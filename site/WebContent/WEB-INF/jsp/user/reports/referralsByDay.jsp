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
            excel.regFrom.value = form.regFrom.value;
            excel.regTo.value = form.regTo.value;
            excel.tracker.value = form.tracker.value;
            excel.sort.value = form.sort.value;
            excel.page.value = form.page.value;
            excel.submit();
        }
    </script>
    <title>Referals by day</title>
</head>
<body>
<jsp:include page="../menuUser.jsp" />
<h1>Referals by day</h1>

<form name = "filters" action="referralsByDay" method="post">
    <table>
        <jsp:include page="filters/byRegistrationDate.jsp" />
        <jsp:include page="filters/byTracker.jsp" />
        <jsp:include page="filters/submit.jsp" />
    </table>
    <input type='hidden' name='sort' value="${sort}">
    <input type='hidden' name='page'>
</form>

<table>
    <tr>
        <th>
            Date
            <a href="#" onclick="Go('date_up')">↑</a>
            <a href="#" onclick="Go('date_down')">↓</a>
        </th>
        <th>
            Click
            <a href="#" onclick="Go('clickLinkAmount_up')">↑</a>
            <a href="#" onclick="Go('clickLinkAmount_down')">↓</a>
        </th>
        <th>
            Enter code
            <a href="#" onclick="Go('enterCodeAmount_up')">↑</a>
            <a href="#" onclick="Go('enterCodeAmount_down')">↓</a>
        </th>
        <th>
            Amount sail
            <a href="#" onclick="Go('sailAmount_up')">↑</a>
            <a href="#" onclick="Go('sailAmount_down')">↓</a>
        </th>
        <th>
            Amount registration
            <a href="#" onclick="Go('registrationAmount_up')">↑</a>
            <a href="#" onclick="Go('registrationAmount_down')">↓</a>
        </th>
        <th>
            Profit
            <a href="#" onclick="Go('profit_up')">↑</a>
            <a href="#" onclick="Go('profit_down')">↓</a>
        </th>
    </tr>
    <c:forEach items="${days}" var="day">
        <tr>
            <td><a href="referralsByDayDetail?date=${day.dateRequest}&tracker=${tracker}">${day.dateView}</a></td>
            <td>${day.clickLinkAmount}</td>
            <td>${day.enterCodeAmount}</td>
            <td>${day.sailAmount}</td>
            <td>${day.registrationAmount}</td>
            <td>${day.viewProfit}</td>
        </tr>
    </c:forEach>
</table>

<jsp:include page="paginationPost.jsp" />

<form name = "excel" action="byDayExcel" method="post">
    <input type='hidden' name='regTo'>
    <input type='hidden' name='regFrom'>
    <input type='hidden' name='tracker'>
    <input type='hidden' name='sort'>
    <input type='hidden' name='page'>

</form>
<a href="#" onclick="GoExcel()">Export to excel</a>

</body>
</html>
