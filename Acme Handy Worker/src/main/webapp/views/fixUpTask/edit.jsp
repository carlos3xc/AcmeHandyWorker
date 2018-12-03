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

<form:form action="note/auditor/edit.do" modelAttribute="note">

	<form:hidden path="id" />
	<form:hidden path="version" />
	
	
	<form:hidden path="momentCreation" />
	<form:hidden path="reply" />
	<form:hidden path="momentResponse" />		
	
	<security:authorize access="hasRole('AUDITOR')">
	
	<form:label path="remark">
		<spring:message code="note.remark" />:
	</form:label>
	
	<form:textarea path="remark" />
	<form:errors cssClass="error" path="remark" />
	<br />
	
	<input type="submit" name="save" value="<spring:message code="note.save" />" />
				
	<input type="button" name="cancel"
		value="<spring:message code="note.cancel" />"
		onclick="javascript: window.location.replace('')" />
	<br />
	
	</security:authorize>

</form:form>