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
            excel.sailFrom.value = form.sailFrom.value;
            excel.sailTo.value = form.sailTo.value;
            excel.tracker.value = form.tracker.value;
            excel.sort.value = form.sort.value;
            excel.page.value = form.page.value;
            excel.submit();
        }
    </script>

    <title>List referrals</title>
</head>
<body>
<jsp:include page="../menuUser.jsp" />

<h1>List referrals</h1>
<form name = "filters" action="referrals" method="post">
    <table>
        <jsp:include page="filters/bySailDate.jsp" />
        <jsp:include page="filters/byRegistrationDate.jsp" />
        <jsp:include page="filters/byTracker.jsp" />
        <jsp:include page="filters/submit.jsp" />
    </table>
    <input type="hidden" name="sort" value="${sort}">
    <input type='hidden' name='page'>
</form>



<table>
    <tr>
        <th>
            Id&nbsp;
            <a href="#" onclick="Go('buyer.id_up')">↑</a>
            <a href="#" onclick="Go('buyer.id_down')">↓</a>
        </th>
        <th>
            Name&nbsp;
            <a href="#" onclick="Go('buyer.name_up')">↑</a>
            <a href="#" onclick="Go('buyer.name_down')">↓</a>
        </th>
        <th>
            SecondName&nbsp;
            <a href="#" onclick="Go('buyer.info.secondName_up')">↑</a>
            <a href="#" onclick="Go('buyer.info.secondName_down')">↓</a>
        </th>
        <th>
            Age&nbsp;
            <a href="#" onclick="Go('buyer.info.age_up')">↑</a>
            <a href="#" onclick="Go('buyer.info.age_down')">↓</a>
        </th>
        <th>
            Count sails&nbsp;
            <a href="#" onclick="Go('countSail_up')">↑</a>
            <a href="#" onclick="Go('countSail_down')">↓</a>
        </th>
        <th>
            Tracker&nbsp;
            <a href="#" onclick="Go('buyer.tracker_up')">↑</a>
            <a href="#" onclick="Go('buyer.tracker_down')">↓</a>
        </th>
        <th>
            Profit&nbsp;
            <a href="#" onclick="Go('profit_up')">↑</a>
            <a href="#" onclick="Go('profit_down')">↓</a>
        </th>
    </tr>

    <c:forEach items="${referrals}" var="refer">
        <c:set value="0" var="i" />
        <tr id="result" >
            <td><a href="referralStatisticDetail?id=${refer.id}">${refer.id}</a></td>
            <td>${refer.name}</td>
            <td>${refer.info.secondName}</td>
            <td>${refer.info.age}</td>
            <td><c:forEach items="${refer.sails}" var="sails" varStatus="sailCount">
                <c:set value="${sailCount.count}" var="i" />
            </c:forEach>
                <c:choose>
                    <c:when test="${!empty i}">${i}</c:when>
                    <c:otherwise>0</c:otherwise>
                </c:choose>
            </td>
            <td>${refer.tracker}</td>
            <td>${refer.viewProfit}</td>
        </tr>
    </c:forEach>
</table>

<jsp:include page="paginationPost.jsp" />

<form name = "excel" action="referralsExcel" method="post">
    <input type='hidden' name='sailTo'>
    <input type='hidden' name='sailFrom'>
    <input type='hidden' name='regTo'>
    <input type='hidden' name='regFrom'>
    <input type='hidden' name='tracker'>
    <input type='hidden' name='sort'>
    <input type='hidden' name='page'>
</form>
<a href="#" onclick="GoExcel()">Export to excel</a>
</body>
</html>
