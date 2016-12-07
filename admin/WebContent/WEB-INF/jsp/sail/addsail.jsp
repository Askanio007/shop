<%@ page language="java" contentType="text/html; charset=utf8"
    pageEncoding="utf8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script type="text/javascript" src="http://code.jquery.com/jquery-1.10.1.min.js"></script>
 <link rel="stylesheet" href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">
  <script src="//code.jquery.com/jquery-1.10.2.js"></script>
  <script src="//code.jquery.com/ui/1.11.4/jquery-ui.js"></script>
  <link rel="stylesheet" href="/resources/demos/style.css">
  <script>
  $(function() {
    $( "#datepicker" ).datepicker();
  });
  </script>

<script type="text/javascript">
    function buyProduct() {
        $.ajax({
            url : 'addBuy',
            type: 'GET',
            data: 
            {
            	amount: $("#input_str").val(), 
            	idprod: $("#idprod").val()
            },
            dataType: 'JSON',
            contentType: 'application/json',
            mimeType: 'application/json',
            success : function(data) 
            {
            	$("#seltag").empty();
           		$.each(data, function(i, post) { 
           			var tes = $.parseJSON(post);
            	$('#seltag').append($('<option id = "'+i+'" value = "'+tes.id+'" selected>'+tes.name+' - '+tes.amount+' шт.'+'</option>'));
           		});
            }
        });
    }
    </script>  
    <script type="text/javascript">
    function removeProduct() {
        $.ajax({
            url : 'removeBuy',
            type: 'GET',
            data: 
            {
            	idprod: $("#seltag option:selected").val()
            },
            dataType: 'JSON',
            contentType: 'application/json',
            mimeType: 'application/json',
            success : function(data) 
            {
            	$("#seltag").empty();
           		$.each(data, function(i, post) { 
           			var tes = $.parseJSON(post);
            	$('#seltag').append($('<option id = "'+i+'" value = "'+tes.id+'" selected>'+tes.name+' - '+tes.amount+' шт.'+'</option>'));
           		});
            }
        });
    }
    </script>  
      
<title>Add Sail</title>
</head>
<body> 
<h1>Add Sail</h1>
<form:form method="post" commandName="sail" action="add" >
		<table>
			<tr>
				<td>User</td>
				<td><form:select path="buyerName" items="${buyerList}"
						itemValue="id" itemLabel="name" multiple="false" /></td>
				<td><form:errors path="buyerName" /></td>
			</tr>
			<tr>
				<td>Product</td>
				<td><select id="idprod">
						<c:forEach items="${productList}" var="product">
							<option label="${product.name}" value="${product.id}">
						</c:forEach>
				</select></td>
			</tr>
			<tr>
				<td>Count</td>
				<td><input id="input_str" size="20" type="text" value="1" /></td>
				<td>
					<table>
						<tr>
							<td><Button type="button" onclick="buyProduct()">Add</Button></td>
							<td><Button type="button" onclick="removeProduct()">Remove</Button></td>
						</tr>
					</table>
				</td>
				<td><form:select id="seltag" path="products"
						disabled="disabled" /></td>
				<td><form:errors path="products" /></td>
			</tr>
			<tr>
				<td>Date</td>
				<td><input name="date" type="datetime-local"/></td>
				<td><form:errors path="date" /></td>
			</tr>
			<tr>
				<td colspan="2"><input type="submit" value="Add Sail" /></td>
			</tr>
		</table>
	</form:form>
<a href="all">Back</a>
</body>
</html>