<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link href="style.css" rel="style">
<c:set var="title" value="ConfigureEmployee" scope="page"/>
<%@ include file="/WEB-INF/jspf/head.jspf" %>
<%@ include file="/WEB-INF/jspf/header.jspf" %>
</head>
<body>
<form:form method="POST" action="employees/change" modelAttribute="employee">
	<form:hidden path="id" />
	<form:hidden path="free" />
	<table>
	<tr>
		<td><spring:message code="employee.firstName"/></td>
		<td><form:input path="firstName"/></td>
	</tr>
	<tr>
	<tr>
		<td><spring:message code="employee.lastName"/></td>
		<td><form:input path="lastName"/></td>
	</tr>
	<tr>
		<td>Email</td>
		<td><form:input path="email"/></td>
	</tr>
	<tr>
		<td><spring:message code="employee.job"/></td>
		<td>
			<form:select path="job" items="${jobs}"/>
		</td>
	</tr>
	</table>
	<button type="submit"><fmt:message key="command.update"/></button>
	</form:form>
</body>
</html>