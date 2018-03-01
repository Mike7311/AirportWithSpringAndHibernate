<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<c:set var="title" value="Employees" scope="page"/>
<%@ include file="/WEB-INF/jspf/head.jspf" %>
<%@ include file="/WEB-INF/jspf/header.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/jquery.jspf" %>
<script src="<c:url value="/static/js/rowSelect.js"/>"></script>
<script src="<c:url value="/static/js/tablesorter/jquery.tablesorter.js"/>"></script>
<script>
$(document).ready(function() {
	$('#contentTable').on('click', 'tr:not(#headers)', function(){
		var employeeId = $(this).find('input:radio').val();
		$('button[name="configureEmployeeId"]').val(employeeId);
		$('button[name="removeEmployeeId"]').val(employeeId);
	});
	$('button:not(button[name="addFlightPage"])').click(function(e){
		if(!$('button[name="removeEmployeeId"]').val()){
			e.preventDefault();
			alert('select employee');
			return;
		}
	});
	$("#contentTable").tablesorter();
	clickableRows();
});
</script>
</head>
<body>
	<table id="mainTable">
	<form:form method="POST" action="employee">
		<tr>
			<td>
		 	<table id="contentTable" class="tablesorter">
		 	<thead>
			<tr id=headers>
				<th>Id</th>
				<th><spring:message code="employee.firstName"/></th>
				<th><spring:message code="employee.lastName"/></th>
				<th><spring:message code="employee.job"/></th>
				<th>Email</th>
				<th><spring:message code="employee.free"/></th>
			</tr>
			</thead>
			<tbody>
				<c:forEach var="employee" items="${employees}">
					<tr>
						<td>${employee.id}
						<input type="radio" id="rowRadio" name="employeeId" value="${employee.id}">
						</td>
						<td>${employee.firstName}</td>
						<td>${employee.lastName}</td>
						<td>${employee.job}</td>
						<td>${employee.email}</td>
						<td>${employee.free}</td>
					</tr>
				</c:forEach>
			</tbody>
			</table>
		</td>
		<td>
		<div id="controlDiv">
			<button name="removeEmployeeId" type="submit"><spring:message code="command.remove"/></button><br/>
			<button name="configureEmployeeId" type="submit"><spring:message code="command.configure"/></button><br/>
	 		<button name="addEmployeePage" type="submit"><spring:message code="command.add"/></button>
	 	</div>
		</td>
		</tr>
	</form:form>
	</table>
</body>
</html>