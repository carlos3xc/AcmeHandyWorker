<%--
 * edit.jsp
 *
 * Copyright (C) 2015 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

	<!-- PARAMETERS FROM CONTROLLER: actor: Actor
									 userAccount: Collection<UserAccount>
					 				 socialNetwork: SocialNetwork
					 				 -->
									 

<form:form action="actor/edit.do" modelAttribute="actor">

	<form:hidden path="id" />
	<form:hidden path="version" />
	
	<form:hidden path="isSuspicious" />
	<form:hidden path="isBanned" />
	<jstl:if test="${actor.id !=0 }">
		<form:hidden path="socialProfile"/>
		<form:hidden path="userAccount"/>
	</jstl:if>
	
	<security:authorize access="isAuthenticated">
	
	<form:label path="name">
		<spring:message code="actor.name" />:
	</form:label>
	<form:input path="name" />
	<form:errors cssClass="error" path="name" />
	<br />
	
	<form:label path="surname">
		<spring:message code="actor.surname" />:
	</form:label>
	<form:input path="surname" />
	<form:errors cssClass="error" path="surname" />
	<br />
	
	<form:label path="middleName">
		<spring:message code="actor.middleName" />:
	</form:label>
	<form:input path="middleName" />
	<form:errors cssClass="error" path="middleName" />
	<br />
	
	<form:label path="photo">
		<spring:message code="actor.photo" />:
	</form:label>
	<form:input path="photo" />
	<form:errors cssClass="error" path="photo" />
	<br />
	
	<form:label path="email">
		<spring:message code="actor.email" />:
	</form:label>
	<form:input path="email" />
	<form:errors cssClass="error" path="email" />
	<br />
	
	<form:label path="phone">
		<spring:message code="actor.phone" />:
	</form:label>
	<form:input path="phone" />
	<form:errors cssClass="error" path="phone" />
	<br />
	
	<form:label path="address">
		<spring:message code="actor.address" />:
	</form:label>
	<form:input path="address" />
	<form:errors cssClass="error" path="address" />
	<br />
	
	<h3><spring:message code="actor.userAccount"/></h3>
	
	<form:label path="username">
		<spring:message code="actor.username" />:
	</form:label>
	<form:input path="username" />
	<form:errors cssClass="error" path="username" />
	<br />
	
	<form:label path="password">
		<spring:message code="actor.password" />:
	</form:label>
	<form:password path="password" />
	<form:errors cssClass="error" path="password" />
	<br />
	
	<form:label path="confirmPassword">
		<spring:message code="actor.confirmPassword" />:
	</form:label>
	<form:password path="confirmPassword" />
	<form:errors cssClass="error" path="confirmPassword" />
	<br />
	
	<jstl:if test="${actor.id !=0 }">
		<h3><spring:message code="actor.socialProfile"/></h3>
			<form:label path="socialProfile.nick">
				<spring:message code="actor.socialProfile.nick" />:
			</form:label>
			<form:input path="socialProfile.nick" />
			<form:errors cssClass="error" path="socialProfile.nick" />
			<br />
			
			<form:label path="socialProfile.socialNetwork">
				<spring:message code="actor.socialProfile.socialNetwork" />:
			</form:label>
			<form:input path="socialProfile.socialNetwork" />
			<form:errors cssClass="error" path="socialProfile.socialNetwork" />
			<br />
			
			<form:label path="socialProfile.link">
				<spring:message code="actor.socialProfile.link" />:
			</form:label>
			<form:input path="socialProfile.link" />
			<form:errors cssClass="error" path="socialProfile.link" />
			<br />
	</jstl:if>
	
	<input type="submit" name="save" value="<spring:message code="actor.save" />" />
				
	<input type="button" name="cancel"
		value="<spring:message code="actor.cancel" />"
		onclick="javascript: window.location.replace('')" />
	<br />
	
	</security:authorize>

</form:form>