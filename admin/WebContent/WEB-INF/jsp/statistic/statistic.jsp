 <%@ page language="java" contentType="text/html; charset=utf8"
	pageEncoding="utf8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf8">
	<script type="text/javascript" src="http://code.jquery.com/jquery-1.10.1.min.js"></script>
 <link rel="stylesheet" href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">
  <script src="//code.jquery.com/jquery-1.10.2.js"></script>
  <script src="//code.jquery.com/ui/1.11.4/jquery-ui.js"></script>
  <link rel="stylesheet" href="/resources/demos/style.css">
<script type="text/javascript">
    function viewBuyer(a) {
    		 $('#results').load('buyersByProduct/'+arguments[0]); 
    		 PopUpShow();
    }
    
    function viewBuyerPagin(a,b) {
		 $('#results').load('buyersByProduct/'+arguments[0]+'?page='+arguments[1]); 
		 PopUpShow();
}
    
    $(document).ready(function(){
        PopUpHide();
    });
    
    function PopUpShow(){
        $("#popup").show();
    }
    function PopUpHide(){
        $("#popup").hide();
    }
    </script>  
    
    <style type="text/css">

.popup{
    width:100%;
    min-height:100%;
    background-color: rgba(0,0,0,0.5);
    overflow:hidden;
    position:fixed;
    top:0px;
}
.popup .popup-content{
    margin:40px auto 0px auto;
    width:450px;
    height: 500px;
    padding:10px;
    background-color:#FFFFFF;
    border-radius:5px;
    box-shadow: 0px 0px 10px #000;
}
    
    </style>
    
<title>Statistics</title>
</head>
<body>
<div class = base>
<jsp:include page="../menu.jsp" />

<form:form action="products?sort=${sort}" commandName="filterTotalSoldProduct" method="get">
			<table>
				<tr>
					<td>ID</td>
					<td><form:input path="id" /></td>
				</tr>
				<tr>
					<td>Name Product</td>
					<td><form:input path="name" /></td>
				</tr>
				<tr>
					<td><input type="submit" value="Search"/></td>
				</tr>
			</table>
		</form:form>
<c:choose>
<c:when test="${!empty soldProducts}">
	<table >
					<tr>
						<th>ID</th>
						<th>Name</th>
						<c:choose>
						<c:when test="${sort eq 'totalCost_up'}"><th><a href="products?sort=totalCost_down">Price, $</a></th></c:when>
						<c:when test="${sort eq 'totalCost_down'}"><th><a href="products?sort=totalCost_up">Price, $</a></th></c:when>
						<c:otherwise><th><a href="products?sort=totalCost_up">Price, $</a></c:otherwise>
						</c:choose>
						
						<c:choose>
						<c:when test="${sort eq 'totalAmount_up'}"><th><a href="products?sort=totalAmount_down">Amount</a></th></c:when>
						<c:when test="${sort eq 'totalAmount_down'}"><th><a href="products?sort=totalAmount_up">Amount</a></th></c:when>
						<c:otherwise><th><a href="products?sort=totalAmount_up">Amount</a></th></c:otherwise>
						</c:choose>
						<th>Buyers</th>
					</tr>

					<c:forEach items="${soldProducts}" var="soldProduct">
			<tr>
				<td>${soldProduct.id}</td>
				<td>${soldProduct.productName}</td>
				<td>${soldProduct.totalCost}</td>
				<td>${soldProduct.totalAmount}</td>
				<td><a href="#" onclick="viewBuyer(${soldProduct.productId})">buyer list</a>
				</td>
					</tr>
		</c:forEach>
	</table>
</c:when>
<c:when test="${empty soldProducts}">
<h3>The products don't have sails</h3>
</c:when>
</c:choose>

<c:choose>
			<c:when test="${sort eq null}">
				<jsp:include page="../pagin.jsp">
					<jsp:param value="products?" name="page" />
				</jsp:include>
			</c:when>


			<c:otherwise>
				<jsp:include page="../pagin.jsp">
					<jsp:param value="products?sort=${sort}" name="page" />
				</jsp:include>
			</c:otherwise>
		</c:choose>
	

</div>
	<div class="popup" id="popup">
		<div class="popup-content">
		<div class="result" id="results"></div>
			<div class="popup-content-footer" id="popupfooter">
				<br><a href="#" onclick="PopUpHide()">Close</a>
			</div>
		</div>
	</div>
</body>
</html>