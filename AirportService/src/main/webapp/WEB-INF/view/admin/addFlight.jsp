<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
<c:set var="title" value="AddFlight" scope="page"/>
<%@ include file="/WEB-INF/jspf/head.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/jquery.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/datetimepicker.jspf" %>
</head>
<body>
<script type="text/javascript">
$(document).ready(function(){
	$('#datetimepicker').datetimepicker({
			dateFormat:'yy-mm-dd',
		});
});
</script>
<form:form method="POST" action="flights/add" modelAttribute="flight">
	<table>
	<tr>
		<td><form:label path="origin"><spring:message code="flight.origin"/></form:label></td>
		<td><form:input path="origin"/></td>
		<td><form:errors path="origin" cssClass="error" /></td>
	</tr>
	<tr>
		<td><form:label path="destination"><spring:message code="flight.destination"/></form:label></td>
		<td><form:input path="destination"/></td>
		<td><form:errors path="destination" cssClass="error" /></td>
	</tr>
	<tr>
		<td><form:label path="departureDate"><spring:message code="flight.departureDate"/></form:label></td>
		<td><form:input path="departureDate" id="datetimepicker"/></td>
		<td><form:errors path="departureDate" cssClass="error" /></td>
	</tr>
	</table>
	<input type="submit" value="<spring:message code="command.add"/>"/>
	</form:form>
</body>
</html>