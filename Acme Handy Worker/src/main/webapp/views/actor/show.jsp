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
									 	
		<b><spring:message code="actor.name"/></b> <jstl:out value="${name}"/>
		<b><spring:message code="actor.middleName"/></b> <jstl:out value="${middleName}"/>
				 
		<b><spring:message code="actor.surname"/></b> <jstl:out value="${surname}"/> 		 				 
		<b><spring:message code="actor.photo"/></b> <jstl:out value="${photo}"/> 				 				 
		<b><spring:message code="actor.email"/></b> <jstl:out value="${email}"/> 			 				 
		<b><spring:message code="actor.phone"/></b> <jstl:out value="${phone}"/> 				 
		<b><spring:message code="actor.username"/></b> <jstl:out value="${username}"/> 				 				 
		<b><spring:message code="actor.authority"/></b> <jstl:out value="${authority}"/> 
		<br/>
		
		<display:table name="socialProfiles" id="row" requestURI="actor/show.do" pagesize="5">
			<display:column property="nick"	titleKey="actor.socialProfile.nick"/>
			<display:column property="socialNetwork" titleKey="actor.socialProfile.socialNetwork"/>
			<display:column property="link" titleKey="actor.socialProfile.link"/>
		</display:table>
		
		<security:authorize access="hasRole('CUSTOMER')">
			<h1><spring:message code="actor.fixUpTasks"/>:</h1><br/>
			<display:table name="fixUpTasks" id="row" requestURI="actor/show.do" pagesize="5">
	
				<display:column property="ticker" titleKey="actor.fixUpTask.ticker" />
				<display:column property="description" titleKey="actor.fixUpTask.description" />
				<display:column property="moment" title="actor.fixUpTask.moment" />
				
			</display:table>
		</security:authorize>
		
		<security:authorize access="hasRole('HANDYWORKER')">
			<h1><spring:message code="actor.applications"/>:</h1><br/>
		<display:table name="applications" id="row" requestURI="actor/show.do" pagesize="5">
			
			<display:column property="moment" titleKey="actor.application.moment" />
			<display:column property="status" titleKey="actor.application.description" />
			<display:column property="price" title="actor.application.price" />
			
		</display:table>
		</security:authorize>
		
	<input type="button" name="back"
		value="<spring:message code="actor.show.back" />"
		onclick="javascript: window.location.replace('')" />
	<br />
		
				
		
	