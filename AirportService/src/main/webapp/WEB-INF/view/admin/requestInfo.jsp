<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<c:set var="title" value="Requests" scope="page"/>
<%@ include file="/WEB-INF/jspf/head.jspf" %>
</head>
<body>
<%@ include file="/WEB-INF/jspf/header.jspf" %>
	<form:form action="update" modelAttribute="request">
	<input type="hidden" name="requestId" value="${request.id}"/>
	<form:label path="header"><spring:message code="request.header"/></form:label>
	<form:input path="header" value="${request.header}" readonly="true"/>
	<form:label path="description"><spring:message code="request.description"/></form:label>
	<form:input rows="4" cols="40" path="description" value="${request.description}" readonly="true"/>
	<button name="status" value="APPROVED"><spring:message code="request.approve"/></button>
	<button name="status" value="DECLINED"><spring:message code="request.decline"/></button>	
	</form:form>
</body>
</html>