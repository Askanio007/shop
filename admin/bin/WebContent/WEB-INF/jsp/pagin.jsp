<%@ page language="java" contentType="text/html; charset=utf8"
    pageEncoding="utf8"%>
  <%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<style type="text/css">

table.pagin {
    border-collapse: collapse;
}
table.pagin, th.pagin, td.pagin {
    text-align: center;
    border: 1px solid black;
}
td.pagin {
	width: 20px;
	height: 20px;
	background-color: #F5F5F5;
}
</style>

</head>
<body>
<br>
<table class = "pagin">
	<c:choose>
		<c:when test="${countPage < 10}">
		<tr class = "pagin">
			<c:forEach begin="1" end="${countPage}" var="i">
   				<c:choose>
                	<c:when test="${currentPage eq i}">
                    	<td class = "pagin">${i}</td>
                	</c:when>
            		<c:otherwise>
   						<td class = "pagin"><a href="${page}?page=${i}">${i}</a></td>
   					</c:otherwise>
   				</c:choose>
   			</c:forEach>
   		</tr>
	</c:when>
		
		
		
		<c:otherwise>
			<c:choose>
     			<c:when test="${currentPage eq 1}">
 					<tr class = "pagin">
        				<c:forEach begin="${currentPage}" end="${currentPage+2}" var="i">
   							<c:choose>
                				<c:when test="${currentPage eq i}">
                    				<td class = "pagin">${i}</td>
                				</c:when>
            					<c:otherwise>
   									<td class = "pagin"><a href="${page}?page=${i}">${i}</a></td>
   								</c:otherwise>
   							</c:choose>
   						</c:forEach>
   						<td class = "pagin">...</td>
   						<td class = "pagin"><a href="${page}?page=${countPage}">${countPage}</a></td>
   					</tr>
   				</c:when>
   				
   				
   				
   				<c:when test="${currentPage eq countPage}">
   					<tr class = "pagin">
   					<td class = "pagin"><a href="${page}?page=1">1</a></td>
   					<td class = "pagin">...</td>
   		 				<c:forEach begin="${currentPage-2}" end="${currentPage}" var="i">
   							<c:choose>
                				<c:when test="${currentPage eq i}">
                    				<td class = "pagin">${i}</td>
                				</c:when>
            					<c:otherwise>
   									<td class = "pagin"><a href="${page}?page=${i}">${i}</a></td>
   								</c:otherwise>
   							</c:choose>
   						</c:forEach>
   					</tr>
   				</c:when>
   				
   				
   				
				<c:otherwise>
   					<tr class = "pagin">
   						<c:if test="${currentPage ge 3}">
   							<td class = "pagin"><a href="${page}?page=1">1</a></td>
   								<c:if test="${currentPage gt 3}">
   									<td class = "pagin">...</td>
   								</c:if>
   						</c:if>
   						<c:forEach begin="${currentPage-1}" end="${currentPage+1}" var="i">
   							<c:choose>
                				<c:when test="${currentPage eq i}">
                    				<td class = "pagin">${i}</td>
                				</c:when>
            					<c:otherwise>
   									<td class = "pagin"><a href="${page}?page=${i}">${i}</a></td>
   								</c:otherwise>
   							</c:choose>
   						</c:forEach>
   						<c:if test="${currentPage le countPage-3}">
   							<td class = "pagin">...</td>
   						</c:if>
   						<c:if test="${currentPage le countPage-2}">
   							<td class = "pagin"><a href="${page}?page=${countPage}">${countPage}</a></td>
   						</c:if>
					</tr>
				</c:otherwise>
			</c:choose>
		</c:otherwise>
	</c:choose>
</table>

</body>
</html>