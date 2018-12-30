<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<display:table name="tutorial" id="row"
	requestURI="tutorial/handyWorker/show.do">

	<display:column>

		<b><spring:message code="tutorial.title" />: </b>
		<jstl:out value="${tutorial.title}" /> <br/>

		<b><spring:message code="tutorial.summary" />: </b>
		<jstl:out value="${tutorial.summary}" /> <br/>

		<b><spring:message code="tutorial.moment" />: </b>
		<jstl:out value="${tutorial.moment}" /> <br/>

		<b><spring:message code="tutorial.pictures" />: </b>
		<jstl:out value="${tutorial.pictures}" /> <br/>

		<b><spring:message code="tutorial.handyWorker" />: </b>
		<a href="actor/show.do?actorId=${row.handyWorker.id}">
			${row.handyWorker.userAccount.username } </a>
		<!-- <jstl:out value="${tutorial.handyWorker}" /> -->

	</display:column>

</display:table>

