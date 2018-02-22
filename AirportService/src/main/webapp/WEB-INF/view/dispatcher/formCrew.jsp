<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="/WEB-INF/jspf/head.jspf" %>
<%@ include file="/WEB-INF/jspf/header.jspf" %>
</head>
<body>
<form method="POST" action="crew/create">
<input type="hidden" name="flightId" value="${flightId}"/>
<table id="contentTable">
	<tr>
		<td><spring:message code="crew.pilots"/></td>
		<td><select name="pilotId">
				<c:forEach items="${pilots}" var="pilot">
					<option value="${pilot.id}">${pilot.firstName} ${pilot.lastName}</option>
				</c:forEach>
		</select></td>
	</tr>
	<tr>
		<td><spring:message code="crew.operators"/></td>
		<td><select name="operatorId">
				<c:forEach items="${operators}" var="operator">
					<option value="${operator.id}">${operator.firstName} ${operator.lastName}</option>
				</c:forEach>
		</select></td>
	</tr>
	<tr>
		<td><spring:message code="crew.navigators"/></td>
		<td><select name="navigatorId">
				<c:forEach items="${navigators}" var="navigator">
					<option value="${navigator.id}">${navigator.firstName} ${navigator.lastName}</option>
				</c:forEach>
		</select></td>
	</tr>
	<tr>
		<td><spring:message code="crew.attendants"/></td>
		<td><select multiple name="attendantIds">
				<c:forEach items="${attendants}" var="attendant">
					<option value="${attendant.id}">${attendant.firstName} ${attendant.lastName}</option>
				</c:forEach>
		</select></td>
	</tr>
</table>
<button type="submit" id="singleButton"><spring:message code="command.createCrew"/></button>
</form>
</body>
</html>