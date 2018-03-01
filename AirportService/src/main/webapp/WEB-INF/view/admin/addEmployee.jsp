<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<c:set var="title" value="AddEmployee" scope="page"/>
<%@ include file="/WEB-INF/jspf/head.jspf" %>
<%@ include file="/WEB-INF/jspf/header.jspf" %>
</head>
<body>
<form:form method="POST" action="employees/add" modelAttribute="employee">
	<table>
	<tr>
		<td><form:label path="firstName"><spring:message code="employee.firstName"/></form:label></td>
		<td><form:input path="firstName"/>
		<form:errors path="firstName" cssClass="error" /></td>
	</tr>
	<tr>
		<td><form:label path="lastName"><spring:message code="employee.lastName"/></form:label></td>
		<td><form:input path="lastName"/>
		<form:errors path="lastName" cssClass="error" /></td>
	</tr>
	<tr>
		<td><form:label path="email">email</form:label></td>
		<td><form:input path="email" type="email"/>
		<form:errors path="email" cssClass="error" /></td>
	</tr>
	<tr>
		<td><form:select path="job">
		<spring:message code="chooseJob" var="chooseJob"/>
    		<form:option value="${chooseJob}" selected="true" disabled="true"/>
    		<form:options items="${jobs}"/>
		</form:select></td>
	</tr>
	</table>
	<input type="submit" value="<spring:message code="command.add"/>"/>
	</form:form>
</body>
</html>