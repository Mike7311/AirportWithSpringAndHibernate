<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<c:set var="title" value="Requests" scope="page"/>
<%@ include file="/WEB-INF/jspf/head.jspf" %>
<%@ include file="/WEB-INF/jspf/header.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/jquery.jspf" %>
<script src="<c:url value="/static/js/rowSelect.js"/>"></script>
</head>
<body>
<div id="requestsForm">
<form action="request/open">
	<table id="contentTable">
	<tr>
		<th>Id</th>
		<th><spring:message code="request.header"/></th>
		<th><spring:message code="username"/></th>
		<th><spring:message code="selection"/></th>
	</tr>
	<c:forEach items="${requests}" var="request">
	<tr>
		<td>${request.id}</td>
		<td>${request.header}</td>
		<td>${request.username}</td>
		<td><input type="radio" name="requestId" value="${request.id}">
	</tr>
	</c:forEach>
	</table>
	<button type="submit"><spring:message code="open"/></button>
	</form>
	</div>
</body>
</html>