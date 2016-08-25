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


<table class="menu" cellpadding="7">
	<tr class="menu">
		<td class="menu">
			<h2>
				<a href="<%=request.getContextPath()%>/user/profile">Profile</a>

			</h2>
		</td>
		<td class="menu">
			<h2 class="menu">
				<a href="<%=request.getContextPath()%>/user/products">Products</a>

			</h2>
		</td>
		<td class="menu">
			<h2>
				<a href="<%=request.getContextPath()%>/user/basket">Basket (${countProductInBasket})</a>
			</h2>
		</td>
		<td class="menu">
			<h2>
				<a href="<%=request.getContextPath()%>/user/deposit">Deposit</a>
			</h2>
		</td>
		<td class="menu">
			<h2>
				<a href="<%=request.getContextPath()%>/user/reports/referrals">List Referal</a>
			</h2>
		</td>
		<td class="menu">
			<h2>
				<a href="<%=request.getContextPath()%>/user/reports/referralsByDay">Statistic by days</a>
			</h2>
		</td>
		<td class="menu">
			<h2>
				<a href="<%=request.getContextPath()%>/user/generateInviteLink">Invite Link</a>
			</h2>
		</td>
		<td class="menu">
			<h2>
				<a href="<%=request.getContextPath()%>/user/chat">Chat</a>
			</h2>
		</td>
		<td class="menu">
			<h2>
				<a href="<c:url value="/logout"/>">Logout</a>
			</h2>
		</td>
	</tr>
</table>