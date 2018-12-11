<%--
 * action-2.jsp
 *
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<display:table name="${complaints}" id="row"
	requestURI="complaint/customer/list.do" pagesize="5">

	<display:column titleKey="complaint.ticker" property="ticker" />

	<display:column titleKey="complaint.description" property="description" />

	<spring:message code="complaint.moment.format" var="formatMoment" />
	<display:column titleKey="complaint.moment" property="moment"
		sortable="true" format="{0, date, ${formatMoment}}" />

	<display:column titleKey="complaint.customer" property="customer" />

	<display:column titleKey="complaint.fixUpTask" property="fixUpTask" />

</display:table>

<security:authorize access="hasRole('CUSTOMER')">
	<form action="complaint/edit.do">
		<input type="submit" value="<spring:message code="complaint.create"/>" />
	</form>
</security:authorize>
