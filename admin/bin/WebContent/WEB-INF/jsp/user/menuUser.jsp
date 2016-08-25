<%@ page language="java" contentType="text/html; charset=utf8"
    pageEncoding="utf8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<table cellpadding="7">
	<tr>
		<td>
			<h2>
				<a href="products">Products</a>
			</h2>
		</td>
		<td>
			<h2>
				<a href="chat">Chat</a>
			</h2>
		</td>
		<td>
			<h2>
				<a href="profile">Profile</a>
			</h2>
		</td>
		<td>
			<h2>
				<a href="basket">Basket (${countProductInBasket})</a>
			</h2>
		</td>
		<td>
			<h2>
				<a href="<c:url value="/logout"/>">Logout</a>
			</h2>
		</td>
	</tr>
</table>