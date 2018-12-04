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

<form:form action="message/edit.do" modelAttribute="message">

	<form:hidden path="id" />
	<form:hidden path="version" />
	
	<form:hidden path="flagSpam" />
	<form:hidden path="moment" />
	<form:hidden path="sender"/>
	
	<security:authorize access="isAuthenticated()">
	
	<form:label path="priority">
		<spring:message code="message.priority" />:
	</form:label>
	
	<form:select path="priority">
		<form:option label="HIGH" value="HIGH"/>
		<form:option label="NEUTRAL" value="NEUTRAL"/>
		<form:option label="LOW" value="LOW"/>
	</form:select>
	<form:errors cssClass="error" path="priority" />
	<br />
	
	<!-- deberia ser un actor y se le pide una userAcc -->
	<form:label path="sender">
		<spring:message code="message.sender" />:
	</form:label>
	
	<form:input path="sender" />
	<form:errors cssClass="error" path="sender" />
	<br />
	
	<form:label path="subject">
		<spring:message code="message.subject" />:
	</form:label>
	
	<form:input path="subject" />
	<form:errors cssClass="error" path="subject" />
	<br />
	
	<form:label path="body">
		<spring:message code="message.body" />:
	</form:label>
	
	<form:textarea path="body" />
	<form:errors cssClass="error" path="body" />
	<br />
	
	<form:label path="tags">
		<spring:message code="message.tags" />:
	</form:label>
	
	<form:textarea path="tags" />
	<form:errors cssClass="error" path="tags" />
	<br />
	
	
	<input type="submit" name="save" value="<spring:message code="message.save" />" />
				
	<input type="button" name="cancel"
		value="<spring:message code="message.cancel" />"
		onclick="javascript: window.location.replace('')" />
	<br />
	
	</security:authorize>

</form:form>