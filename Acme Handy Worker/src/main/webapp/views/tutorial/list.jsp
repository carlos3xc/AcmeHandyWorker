<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

	<display:table name="tutorials" id="row" requestURI="tutorial/handyWorker/list.do" pagesize="5">
		
		<display:column><a href="tutorial/handyWorker/edit.do?tutorialId=${row.id}">
			<spring:message code="tutorial.edit"/></a></display:column>

		<display:column titleKey="tutorial.title" property="title" />	
	
		<display:column titleKey="tutorial.summary" property="summary" />
		
		<spring:message code="tutorial.moment.format" var="formatMoment"/>
		<display:column titleKey="tutorial.moment" property="moment" format="{0,date,${formatMoment} }"/>
	
	</display:table>
	
	<a href="tutorial/handyWorker/create.do"><spring:message code="tutorial.create"/></a>