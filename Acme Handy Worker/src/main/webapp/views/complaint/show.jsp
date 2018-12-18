<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<jstl:set var="customerName"
	value="${complaint.customer.name + complaint.customer.middleName + complaint.customer.surname }" />

<display:table name="complaint" id="row"
	requestURI="complaint/customer/show.do">

	<display:column>

		<b><spring:message code="complaint.ticker" /></b>
		<jstl:out value="${complaint.ticker}" />

		<b><spring:message code="complaint.description" /></b>
		<jstl:out value="${complaint.description}" />

		<b><spring:message code="complaint.attachments" /></b>
		<jstl:out value="${complaint.attachments}" />

		<b><spring:message code="complaint.moment" /></b>
		<jstl:out value="${complaint.moment}" />

		<b><spring:message code="complaint.fixUpTask" /></b>
		<jstl:out value="${complaint.fixUpTask}" />

	</display:column>

</display:table>

