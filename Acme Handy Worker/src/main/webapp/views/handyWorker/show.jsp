<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

	<!-- PARAMETERS FROM CONTROLLER: handyWorker: HandyWorker
									 actor: Actor
					 				 socialProfile: SocialProfile
									 userAccount: UserAccount
									 applications: Collection<Application>
									 -->
									 	
		<fieldset>
			<table>
				<tr>
					<td><b><spring:message code="handyWorker.name"/></b> <jstl:out value="${name}"/></td>
				</tr>
				<tr>
					<td><b><spring:message code="handyWorker.middleName"/></b> <jstl:out value="${middleName}"/></td>
				</tr>
				<tr>
					<td><b><spring:message code="handyWorker.surname"/></b> <jstl:out value="${surname}"/></td>
				</tr>
				<tr>
					<td><b><spring:message code="handyWorker.photo"/></b> <jstl:out value="${photo}"/></td>
				</tr>
				<tr>
					<td><b><spring:message code="handyWorker.email"/></b> <jstl:out value="${email}"/></td>
				</tr>
				<tr>
					<td><b><spring:message code="handyWorker.phone"/></b> <jstl:out value="${phone}"/></td>
				</tr>
				<tr>
					<td><b><spring:message code="handyWorker.username"/></b> <jstl:out value="${username}"/></td>
				</tr>
				<tr>
					<td><b><spring:message code="handyWorker.authority"/></b> <jstl:out value="${authority}"/></td>
				</tr>
			</table>
		</fieldset>
		<br/>
		<h1><spring:message code="handyWorker.socialProfile"/>:</h1><br/>
		<fieldset>
			<table>
				<tr>
					<td><b><spring:message code="handyWorker.socialProfile.nick"/>:</b> <jstl:out value="${handyWorker.socialProfile.nick}"/></td>
				</tr>
				<tr>
					<td><b><spring:message code="handyWorker.socialProfile.socialNetwork"/>:</b> <jstl:forEach var="law" items="${handyWorker.socialProfile.socialNetwork}"> <jstl:out value="${law}"/><br/></jstl:forEach></td>
				</tr>
				<tr>
					<td><b><spring:message code="handyWorker.socialProfile.link"/>:</b><br/> <jstl:out value="${handyWorker.socialProfile.link}"/></td>
				</tr>
			</table>
		</fieldset>
		<br/>
		<h1><spring:message code="handyWorker.applications"/>:</h1><br/>
		<display:table name="applications" id="row" requestURI="handyWorker/show.do" pagesize="5">
			
			<display:column property="moment" titleKey="handyWorker.moment" />
			<display:column property="status" titleKey="handyWorker.description" />
			<display:column property="price" title="handyWorker.price" />
			
		</display:table>
		
	<input type="button" name="back"
		value="<spring:message code="handyWorker.show.back" />"
		onclick="javascript: window.location.replace('')" />
	<br />
		
				
		
	