<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

	<!-- PARAMETERS FROM CONTROLLER: sponsor: Sponsor
									 actor: Actor
					 				 socialProfile: SocialProfile
									 userAccount: UserAccount
									 -->
		<fieldset>
			<table>
				<tr>
					<td><b><spring:message code="sponsor.name"/></b> <jstl:out value="${name}"/></td>
				</tr>
				<tr>
					<td><b><spring:message code="sponsor.middleName"/></b> <jstl:out value="${middleName}"/></td>
				</tr>
				<tr>
					<td><b><spring:message code="sponsor.surname"/></b> <jstl:out value="${surname}"/></td>
				</tr>
				<tr>
					<td><b><spring:message code="sponsor.photo"/></b> <jstl:out value="${photo}"/></td>
				</tr>
				<tr>
					<td><b><spring:message code="sponsor.email"/></b> <jstl:out value="${email}"/></td>
				</tr>
				<tr>
					<td><b><spring:message code="sponsor.phone"/></b> <jstl:out value="${phone}"/></td>
				</tr>
				<tr>
					<td><b><spring:message code="sponsor.username"/></b> <jstl:out value="${username}"/></td>
				</tr>
				<tr>
					<td><b><spring:message code="sponsor.authority"/></b> <jstl:out value="${authority}"/></td>
				</tr>
			</table>
		</fieldset>
		<br/>
		<h1><spring:message code="sponsor.socialProfile"/>:</h1><br/>
		<fieldset>
			<table>
				<tr>
					<td><b><spring:message code="sponsor.socialProfile.nick"/>:</b> <jstl:out value="${sponsor.socialProfile.nick}"/></td>
				</tr>
				<tr>
					<td><b><spring:message code="sponsor.socialProfile.socialNetwork"/>:</b> <jstl:forEach var="law" items="${sponsor.socialProfile.socialNetwork}"> <jstl:out value="${law}"/><br/></jstl:forEach></td>
				</tr>
				<tr>
					<td><b><spring:message code="sponsor.socialProfile.link"/>:</b><br/> <jstl:out value="${sponsor.socialProfile.link}"/></td>
				</tr>
			</table>
		</fieldset>
		<br/>
	<input type="button" name="back"
		value="<spring:message code="sponsor.show.back" />"
		onclick="javascript: window.location.replace('')" />
	<br />
		
				
		
	