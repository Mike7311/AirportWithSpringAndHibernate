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
		<td><form:input path="firstName"/></td>
	</tr>
	<tr>
		<td><form:label path="lastName"><spring:message code="employee.lastName"/></form:label></td>
		<td><form:input path="lastName"/></td>
	</tr>
	<tr>
		<td><form:label path="email">email</form:label></td>
		<td><form:input path="email"/></td>
	</tr>
	<tr>
		<td><form:select path="job">
    		<form:options items="${jobs}" />
		</form:select></td>
	</tr>
	</table>
	<input type="submit" value="<spring:message code="command.add"/>"/>
	</form:form>
</body>
</html>