<%@ page language="java" contentType="text/html; charset=utf8"
    pageEncoding="utf8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf8">
<title>Edit Buyer</title>
</head>
<body>
<h1>Edit Buyer</h1>
<form:form method="post" commandName="buyerEdit" action="../edit/${buyer.id}">

	<table>
	<tr>
			<td>Active</td>
			<td><form:checkbox path="${buyer.enable}" value = "true" /></td>
		</tr>
	<tr>
			<td>Id</td>
			<td><form:input type="text" value="${buyer.id}" path="id" readonly="true" /></td>
		</tr>
		<tr>
			<td>Name</td>
			<td><form:input type="text" value="${buyer.name}" path="name" readonly="true" /></td>
		</tr>
		<tr>
			<td>Second Name</td>
			<td><form:input path="secondName" value = "${buyer.secondName}" /></td>
			<td><form:errors path="secondName" /></td>
		</tr>
		<tr>
			<td>Date registration</td>
			<td><form:input type="text" value="${buyer.dateReg}" path="dateReg" readonly="true" /></td>
		</tr>
		<tr>
			<td>Age</td>
			<td><form:input path="age" value = "${buyer.age}" /></td>
			<td><form:errors path="age" /></td>
		</tr>
		<tr>
			<td>Phone</td>
			<td><form:input path="phone" value = "${buyer.phone}"/></td>
			<td><form:errors path="phone" /></td>
		</tr>
		<tr>
			<td>Referral Id</td>
			<td><form:input type="text" value="${buyer.refId}" path="refId" readonly="true" /></td>
		</tr>
		<tr>
			<td>Tracker Id</td>
			<td><form:input type="text" value="${buyer.tracker}" path="tracker" readonly="true" /></td>
		</tr>
		<tr>
			<td>Balance</td>
			<td><form:input path="balance" value = "${buyer.balance}"/></td>
			<td><form:errors path="balance" /></td>
		</tr>
		<tr>
			<td>Percent cashback</td>
			<td><form:input path="percentCashback" value = "${buyer.percentCashback}"/></td>
			<td><form:errors path="percentCashback" /></td>
		</tr>
		<tr>
			<td>Count sails</td>
			<td><form:input path="countSails" value = "${buyer.countSails}"/></td>
			<td><form:errors path="countSails" /></td>
		</tr>
		<tr>
			<td><img src = "<%=request.getContextPath()%>/img?avaPic=${buyer.id}" width="180" height="150" /><br></td>
		</tr>

		<tr>
			<td colspan="2"><input type="submit" value="Save" />  <a href="../all">Back</a></td>
		</tr>
		
	</table>

</form:form>
</body>
</html>