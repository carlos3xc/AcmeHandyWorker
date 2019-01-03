<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

	<!-- PARAMETERS FROM CONTROLLER:
									 actor: Actor
					 				 socialProfile: SocialProfile
									 userAccount: UserAccount
									 fixUpTasks: Collection<FixUpTask>
									 -->
								
	 <display:table name="actor" id="row" requestURI="actor/show.do">
		<display:column>					
		<b><spring:message code="actor.name"/></b>: <jstl:out value="${actor.name}"/> <br/>
		<b><spring:message code="actor.middleName"/></b>: <jstl:out value="${actor.middleName}"/> <br/>	 
		<b><spring:message code="actor.surname"/></b>: <jstl:out value="${actor.surname}"/> <br/>		 				 
		<b><spring:message code="actor.photo"/></b>: <jstl:out value="${actor.photo}"/> <br/>				 				 
		<b><spring:message code="actor.email"/></b>: <jstl:out value="${actor.email}"/> <br/>			 				 
		<b><spring:message code="actor.phone"/></b>: <jstl:out value="${actor.phone}"/> <br/>				 
		<b><spring:message code="actor.username"/></b>: <jstl:out value="${actor.userAccount.username}"/> <br/>				 				 
		<b><spring:message code="actor.authority"/></b>: <jstl:out value="${actor.userAccount.authorities}"/> <br/>
		</display:column>
		</display:table>
		
		<spring:message code="actor.socialProfile"/>:<br/>
		<display:table name="socialProfiles" id="row" requestURI="actor/show.do" pagesize="5">
		    <display:column>
			<b><spring:message code="actor.socialProfile.nick"/></b>: <jstl:out value="${socialProfiles.nick}"/> <br/>
			<b><spring:message code="actor.socialProfile.socialNetwork"/></b>: <jstl:out value="${socialProfiles.socialNetwork}"/> <br/>
			<b><spring:message code="actor.socialProfile.link"/></b>: <jstl:out value="${socialProfiles.link}"/> <br/>
			</display:column>
		</display:table>
		
		<security:authorize access="hasRole('CUSTOMER')">
			<spring:message code="actor.fixUpTasks"/>:<br/>
			<display:table name="fixUpTasks" id="row" requestURI="actor/show.do" pagesize="5">
	
				<b><spring:message code="actor.fixUpTask.ticker" /></b>: <jstl:out value="${fixUpTasks.ticker}"/> <br/>
				<b><spring:message code="actor.fixUpTask.description" /></b>: <jstl:out value="${fixUpTasks.description}"/> <br/>
				<b><spring:message code="actor.fixUpTask.moment" /></b>: <jstl:out value="${fixUpTasks.moment}"/> <br/>
				
			</display:table>
		</security:authorize>
		
		<security:authorize access="hasRole('HANDYWORKER')">
			<spring:message code="actor.applications"/>:<br/>
		<display:table name="applications" id="row" requestURI="actor/show.do" pagesize="5">
			
			<b><spring:message code="actor.application.moment" /></b>: <jstl:out value="${applications.moment}"/> <br/>
			<b><spring:message code="actor.application.description" /></b>: <jstl:out value="${applications.description}"/> <br/>
			<b><spring:message code="actor.application.price" /></b>: <jstl:out value="${applications.price}"/> <br/>
			
		</display:table>
		</security:authorize>
	
	<h4><a href="actor/edit.do"><spring:message code="actor.edit" /></a></h4>
		
	<input type="button" name="back"
		value="<spring:message code="actor.show.back" />"
		onclick="javascript: window.location.replace('master.page')" />
	<br/>
		