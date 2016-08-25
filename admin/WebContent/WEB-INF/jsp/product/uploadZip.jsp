<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<form:form method="post" action="${param.met}" enctype="multipart/form-data">
	<table>
		<tr>
			<td>File to upload:</td>
			<td><input name="file" type="file" /> <input type="submit"
				value="Upload" /></td>
		</tr>
	</table>
</form:form>