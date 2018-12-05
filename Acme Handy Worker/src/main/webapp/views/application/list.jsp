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

		<display:table name="applications" id="row" requestURI="application/customer/list.do" pagesize="5">
				
			<spring:message code="application.handyWorker" var="handyWorker"/>
			<display:column property="handyWorker" title="${handyWorker}" />	
		
			<spring:message code="application.customer"  var="customer"/>
			<display:column property="customer" title="${customer}" />
			
			<spring:message code="application.moment"  var="momentHeader"/>
			<spring:message code="application.moment.format" var="formatMoment"/>
			<display:column property="moment" title="${momentHeader}" format="{0,date,${formatMoment} }"/>
			
			<spring:message code="application.price"  var="price"/>
			<display:column property="price" title="${price}" />
			
			<spring:message code="application.status" var="status"/>
			<display:column property="status" title="${status}" />
			
			<jstl:if test="${row.status == 'ACCEPTED'}">
				<display:column> <a href="workplan/create.do?appId=${row.id}"></a> </display:column>
			</jstl:if>
		
		</display:table>