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
		
		<jstl:if test="${row.status == 'ACCEPTED'}">
			<display:column> <a href="tutorial/handyWorker/edit.do?appId=${row.id}"><spring:message code="tutorial.workplan"/></a> </display:column>
		</jstl:if>
			
		<spring:message code="tutorial.title" var="title"/>
		<display:column property="title" title="${title}" />	
	
		<spring:message code="tutorial.summary"  var="summary"/>
		<display:column property="summary" title="${summary}" />
		
		<spring:message code="tutorial.moment"  var="momentHeader"/>
		<spring:message code="tutorial.moment.format" var="formatMoment"/>
		<display:column property="moment" title="${momentHeader}" format="{0,date,${formatMoment} }"/>
	
	</display:table>
	
	<a href="tutorial/handyWorker/create.do?appId=${row.id}"><spring:message code="tutorial.create"/></a>