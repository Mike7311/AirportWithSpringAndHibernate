<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link href="style.css" rel="style">
<c:set var="title" value="ConfigureFlight" scope="page"/>
<%@ include file="/WEB-INF/jspf/head.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/jquery.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/datetimepicker.jspf" %>
</head>
<body>
<script type="text/javascript">
$(document).ready(function(){
	$('#datetimepicker').datetimepicker({
		dateFormat: 'yy-mm-dd'
	});
});
</script>
<form:form method="POST" action="flights/change" modelAttribute="flight">
	<form:hidden path="id" />
	<fieldset>
		<form:label path="origin"><spring:message code="flight.origin"/></form:label>
		<form:input path="origin"/>
	</fieldset>
	<fieldset>
		<form:label path="destination"><spring:message code="flight.destination"/></form:label>
		<form:input path="destination"/>
	</fieldset>
	<fieldset>
		<label><spring:message code="flight.departureDate"/></label>
		<input type="text" name="dateAndTime" id="datetimepicker" value="${flight.departureDate}">
	</fieldset>
	<fieldset>
	<form:label path="flightStatus"><spring:message code="status"/></form:label>
		<form:select path="flightStatus" items="${flightStatuses}"/>
	</fieldset>
	<input type="submit" value="<spring:message code="command.update"/>"/>
	</form:form>
</body>
</html>