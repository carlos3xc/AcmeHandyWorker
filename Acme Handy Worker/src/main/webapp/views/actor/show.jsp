<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
								
	<div>					
		<b><spring:message code="actor.name"/></b>: <jstl:out value="${actor.name}"/> <br/>
		<b><spring:message code="actor.middleName"/></b>: <jstl:out value="${actor.middleName}"/> <br/>	 
		<b><spring:message code="actor.surname"/></b>: <jstl:out value="${actor.surname}"/> <br/>		 				 
		<b><spring:message code="actor.photo"/></b>: <jstl:out value="${actor.photo}"/> <br/>				 				 
		<b><spring:message code="actor.email"/></b>: <jstl:out value="${actor.email}"/> <br/>			 				 
		<b><spring:message code="actor.phone"/></b>: <jstl:out value="${actor.phone}"/> <br/>				 
	</div>
	
		<h3><spring:message code="actor.socialProfile"/>:</h3>
		<display:table name="socialProfiles" id="row" requestURI="actor/show.do" pagesize="5">
		    <display:column>
			<b><spring:message code="actor.socialProfile.nick"/></b>: <jstl:out value="${actor.socialProfiles.nick}"/> <br/>
			<b><spring:message code="actor.socialProfile.socialNetwork"/></b>: <jstl:out value="${actor.socialProfiles.socialNetwork}"/> <br/>
			<b><spring:message code="actor.socialProfile.link"/></b>: <jstl:out value="${actor.socialProfiles.link}"/> <br/>
			</display:column>
		</display:table>
		
	<%-- <security:authorize access="hasRole('CUSTOMER')">
			<spring:message code="actor.fixUpTasks"/>:<br/>
			<display:table name="fixUpTasks" id="row" requestURI="actor/show.do" pagesize="5">
	        <display:column>
			<b><spring:message code="actor.fixUpTask.ticker" /></b>: <jstl:out value="${fixUpTasks.ticker}"/> <br/>
			<b><spring:message code="actor.fixUpTask.description" /></b>: <jstl:out value="${fixUpTasks.description}"/> <br/>
			<b><spring:message code="actor.fixUpTask.moment" /></b>: <jstl:out value="${fixUpTasks.moment}"/> <br/>
			</display:column>
			</display:table>	
		</security:authorize>
		
		<security:authorize access="hasRole('HANDYWORKER')">
			<spring:message code="actor.applications"/>:<br/>
		<display:table name="applications" id="row" requestURI="actor/show.do" pagesize="5">
			<display:column>
			<b><spring:message code="actor.application.moment" /></b>: <jstl:out value="${applications.moment}"/> <br/>
			<b><spring:message code="actor.application.description" /></b>: <jstl:out value="${applications.description}"/> <br/>
			<b><spring:message code="actor.application.price" /></b>: <jstl:out value="${applications.price}"/> <br/>
			</display:column>
		</display:table>
		</security:authorize> --%>
	<br/>
	<a href="actor/edit.do"><spring:message code="actor.edit" /></a>
		
	<input type="button" name="back"
		value="<spring:message code="actor.show.back" />"
		onclick="javascript: window.location.replace('master.page')" />
	<br/>
		