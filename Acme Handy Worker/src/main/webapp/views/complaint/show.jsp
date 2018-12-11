<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<display:table name="${complaints}" id="row"
	requestURI="complaint/customer/show.do" pagesize="5">

	<security:authorize access="hasRole('CUSTOMER')">
		<display:column>
			<a href="complaint/customer/edit.do?complaintId=${row.id}"> <spring:message
					code="complaint.edit" />
			</a>
		</display:column>

	</security:authorize>

	<display:column titleKey="complaint.ticker" property="ticker" />

	<display:column titleKey="complaint.description" property="description" />

	<display:column titleKey="complaint.attachments" property="attachments"
		sortable="false" />

	<spring:message code="complaint.moment.format" var="formatMoment" />
	<display:column titleKey="complaint.moment" property="moment"
		sortable="true" format="{0, date, ${formatMoment}}" />

	<display:column titleKey="complaint.fixUpTask" property="fixUpTask" />

</display:table>

