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

<!-- 
	Recibe: Message message: el mensaje a editar.
			List<Actor> actors: todos los actores del sistema.
 -->

<form:form action="message/edit.do" modelAttribute="message">

	<form:hidden path="id" />
	<form:hidden path="version" />
	
	<form:hidden path="flagSpam" />
	<form:hidden path="moment" />
	<form:hidden path="sender"/>
	<form:hidden path="tags"/>
	
	<security:authorize access="isAuthenticated()">
	
	<form:label path="priority">
		<spring:message code="m.priority" />
	</form:label>
	
	<form:select path="priority">
		<form:option label="HIGH" value="HIGH"/>
		<form:option label="NEUTRAL" value="NEUTRAL"/>
		<form:option label="LOW" value="LOW"/>
	</form:select>
	<form:errors cssClass="error" path="priority" />
	<br />
	
	<form:label path="sender">
		<spring:message code="m.sender" />
	</form:label>
	
	<form:select path="sender">
		<form:options items="${actors}" itemLabel="userAccount.username" itemValue="id"/>
	</form:select>
	<form:errors cssClass="error" path="sender" />
	<br />
	
	<form:label path="subject">
		<spring:message code="m.subject" />
	</form:label>
	
	<form:input path="subject" />
	<form:errors cssClass="error" path="subject" />
	<br />
	
	<form:label path="body">
		<spring:message code="m.body" />
	</form:label>
	
	<form:textarea path="body" />
	<form:errors cssClass="error" path="body" />
	<br />  
	
	<!-- deberia ser una list y se le pide strings 
	<form:label path="tags">
		<spring:message code="m.tags" />:
	</form:label>
	
	<form:textarea path="tags" />
	<form:errors cssClass="error" path="tags" />
	<br />
	-->
	
	<input type="submit" name="save" value="<spring:message code="m" />" />
				
	<input type="button" name="cancel"
		value="<spring:message code="m.cancel" />"
		onclick="javascript: window.location.replace('')" />
	<br />
	
	</security:authorize>

</form:form>