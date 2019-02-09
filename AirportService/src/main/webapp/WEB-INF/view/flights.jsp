<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/WEB-INF/jspf/head.jspf" %>
<%@ include file="/WEB-INF/jspf/header.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/jquery.jspf" %>
<%@ taglib uri="http://sargue.net/jsptags/time" prefix="javatime" %>
<c:set var="title" value="Flights" scope="page"/>
<script src="<c:url value="/static/js/rowSelect.js"/>"></script>
<script src="<c:url value="/static/js/tablesorter/jquery.tablesorter.js"/>"></script>
<script>
$(document).ready(function() {
	$('#contentTable').on('click', 'tr:not(#headers)', function(){
		var flightId = $(this).find('input:radio').val();
		$('button[name="flightId"]').val(flightId);
	});
	$('button:not(button[name="addFlightPage"])').click(function(e){
		if(!$('button[name="flightId"]').val()){
			e.preventDefault();
			alert('select flight');
			return;
		}
	});
	$("#contentTable").tablesorter();
	clickableRows();
	}
);
</script>
</head>
<body>
	<table id="mainTable">
	<tr>
		<td>
		<form:form method="POST" id="flightForm" action="flight">
			<table id="contentTable" class="tablesorter">
				<thead>
					<tr id="headers">
						<th>Id</th>
						<th><spring:message code="flight.origin"/></th>
						<th><spring:message code="flight.destination"/></th>
						<th><spring:message code="flight.date"/></th>
						<th><spring:message code="status"/></th>
					</tr>
				</thead>
				<tbody>
				<c:forEach var="flight" items="${flights}">
					<tr>
						<td>${flight.id}
						<input type="radio" id="rowRadio" name="flightId" value="${flight.id}">
						</td>
						<td>${flight.origin}</td>
						<td>${flight.destination}</td>
						<td><javatime:format value="${flight.departureDate}" pattern="yyyy-MM-dd HH:mm" /></td>
						<td>${flight.flightStatus}</td>
					</tr>
				</c:forEach>
				</tbody>
			</table>
		 	</form:form>
		 	<form:form method="POST" id="freeCrewForm" action="crew/free"/>
	 	</td>
	 	<td>
	 	<div id="control Div">
			<security:authorize access="hasRole('ROLE_ADMIN')">
				<button name="addFlightPage" form="flightForm" type="submit"><spring:message code="command.add"/></button><br/>
				<button name="remove" form="flightForm" type="submit"><spring:message code="command.remove"/></button><br/>
				<button name="changeFlightPage" form="flightForm" type="submit"><spring:message code="command.configure"/></button><br/>
				<button name="flightId" form="freeCrewForm" type="submit"><spring:message code="command.freeCrew"/></button><br/>
	 		</security:authorize>
	 		<form:form method="GET" id="crewForm" action="crew">
		 		<security:authorize access="hasRole('ROLE_DISPATCHER')">
					<button name="flightId" type="submit"><spring:message code="command.createCrew"/></button><br/>
		 		</security:authorize>
	 		</form:form>
	 	</div>
	 	</td>
	 </tr> 
 	</table>
</body>
</html>