<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<display:table name="vastes" id="row" requestURI="fixUpTask/show.do" pagesize="5">

	<display:column property="ticker" titleKey="vaste.ticker"/>
	<display:column titleKey="vaste.publicationMoment">
		<fmt:formatDate value="${fixUpTask.startMoment}" pattern="${vasteMomentFormat}" />
	</display:column>
	<display:column property="body" titleKey="vaste.body" />
	<display:column property="picture" titleKey="vaste.picture" />

	<display:column>
		<a href="vaste/customer/edit.do?vasteId=${row.id}"><spring:message code="vaste.edit"/></a>
	</display:column>


</display:table><br />

<a href="vaste/customer/delete.do?vasteId=${row.id}"><spring:message code="vaste.create"/></a>
