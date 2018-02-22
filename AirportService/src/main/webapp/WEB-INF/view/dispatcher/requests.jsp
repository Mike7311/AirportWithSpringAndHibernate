<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<c:set var="title" value="RequestForm" scope="page"/>
<%@ include file="/WEB-INF/jspf/head.jspf" %>
<%@ include file="/WEB-INF/jspf/header.jspf" %>
</head>
<body>
	<table>
		<tr>
			<th>Id</th>
			<th><spring:message code="request.header"/></th>
			<th><spring:message code="status"/></th>
		</tr>
		<c:forEach items="${requests}" var="request">
		<tr>
			<td>${request.id}</td>
			<td>${request.header}</td>
			<td>${request.status}</td>
		</tr>
		</c:forEach>
	</table>
	<form action="request/write">
		<button type="submit"><spring:message code="write"/></button>
	</form>
</body>
</html>