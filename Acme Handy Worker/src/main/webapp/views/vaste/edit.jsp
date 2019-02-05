<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<jstl:out value="${vaste.ticker}"/>
</br>
<jstl:if test="${vaste.publicationMoment == null}">
	<spring:message code="vaste.draftMessage" />
</jstl:if>

<form:form action="vaste/customer/edit.do"
		   modelAttribute="vaste">

	<form:hidden path="id" />
	<form:hidden path="version" />

	<form:hidden path="ticker" />
	<form:hidden path="publicationMoment" />
	<form:hidden path="customer" />

	<form:label path="body">
		<spring:message code="vaste.body" />:
	</form:label>
	<form:textarea path="body"/>
	<br />

	<form:label path="picture">
		<spring:message code="vaste.picture" />:
	</form:label>
	<form:textarea path="picture"/>
	<br />
	<form:select path="fixUpTask"/>

	<input type="submit" name="save"
		   value="<spring:message code="vaste.save" />"/>

	<input type="button" name="cancel"
		   value="<spring:message code="complaint.cancel" />"
		   onclick="javascript: window.location.replace('vesta/customer/list.do')" />
	<br />
	<spring:message code="vaste.finalWarning" />
	<input type="submit" name="publish"
		   value="<spring:message code="vaste.publish" />"/>

</form:form>