<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<display:table name="complaint" id="row"
	requestURI="complaint/customer/show.do">

	<display:column>

		<b><spring:message code="complaint.ticker" />: </b>
		<jstl:out value="${complaint.ticker}" /> <br/>

		<b><spring:message code="complaint.description" />: </b>
		<jstl:out value="${complaint.description}" /> <br/>

		<b><spring:message code="complaint.attachments" />: </b>
		<jstl:out value="${complaint.attachments}" /> <br/>

        <spring:message code="complaint.moment.format" var="momentFormat"/>
		<b><spring:message code="complaint.moment" />: </b>
		<fmt:formatDate value="${complaint.moment}" pattern="${momentFormat}" /> <br/>

		<b><spring:message code="complaint.fixUpTask" />: </b>
		<jstl:out value="${complaint.fixUpTask.ticker}" />

	</display:column>

</display:table>

<h3><spring:message code="complaint.reports"/>:</h3>
		<display:table name="reports" id="row" requestURI="complaint/customer/show.do" pagesize="5">
			
			<display:column property="moment" titleKey="complaint.moment" />
						
			<display:column property="description" titleKey="complaint.description" />
			
			<display:column titleKey="complaint.customer">
				<a href="actor/profile.do?actorId=${row.customer.id}"><jstl:out value="${row.customer.userAccount.username}"/></a>
			</display:column>
		</display:table>

