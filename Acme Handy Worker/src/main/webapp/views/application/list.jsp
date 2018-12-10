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

	<display:table name="applications" id="row" requestURI="application/list.do" pagesize="5">
			
		<display:column titleKey="application.handyWorker" property="handyWorker" />	
	
		<display:column titleKey="application.customer" property="customer" />
		
		<spring:message code="application.moment.format" var="formatMoment"/>
		<display:column titleKey="application.moment" property="moment" format="{0,date,${formatMoment} }"/>
		
		<display:column titleKey="application.price" property="price" />
		
		<display:column titleKey="application.status" property="status" />
		
		<jstl:if test="${row.status == 'ACCEPTED'}">
			<display:column> <a href="workplan/create.do?appId=${row.id}"></a> </display:column>
		</jstl:if>
	
	</display:table>