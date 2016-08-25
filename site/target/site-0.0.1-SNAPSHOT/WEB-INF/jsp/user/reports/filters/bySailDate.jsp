<%@ page language="java" contentType="text/html; charset=utf8"
         pageEncoding="utf8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
        <tr>
            <td>Sail by date</td>
            <td>from: <input type="date" name="sailFrom" value="${sailDate.fromWithoutTime}" /> </td>
            <td>to: <input type="date" name="sailTo" value="${sailDate.toWithoutTime}" /></td>
        </tr>

