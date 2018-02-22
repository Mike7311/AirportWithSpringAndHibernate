<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<c:set var="title" value="RequestForm" scope="page"/>
<%@ include file="/WEB-INF/jspf/head.jspf" %>
</head>
<body>
<%@ include file="/WEB-INF/jspf/header.jspf" %>
	<form:form method="POST" action="write" class="mTop" modelAttribute="request">
		<c:set var="username"><security:authentication property="principal.username"/></c:set>
		<form:hidden path="username" value="${username}"/>
		<form:label path="header"><spring:message code="request.header"/></form:label>
		<form:input path="header"/>
		<form:label path="description"><spring:message code="request.description"/></form:label>
		<form:input path="description" cols="40" rows="5"/>
		<button type = "submit"><spring:message code="write"/></button>
	</form:form>
</body>
</html>