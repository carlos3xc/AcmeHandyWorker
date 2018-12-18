<%--
 * action-1.jsp
 *
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<form:form action="userAccount/admin/create.do"
	modelAttribute="userAccount">

	<form:hidden path="id" />
	<form:hidden path="version" />

	<spring:message code="admin.userAccount.username.example"
		var="username" />
	<spring:message code="admin.userAccount.password.example"
		var="password" />

	<form:label path="userAccount.username">
		<spring:message code="admin.userAccount.username" />:
	</form:label>
	<form:input path="userAccount.username" placeholder="${username}" />
	<form:errors cssClass="error" path="userAccount.username" />
	<br />

	<form:label path="userAccount.password">
		<spring:message code="admin.userAccount.password" />:
	</form:label>
	<form:input type="password" path="userAccount.password"
		placeholder="${password}" />
	<form:errors cssClass="error" path="userAccount.password" />
	<br />

	<form:select id="authorities.authority" path="userAccount.authorities">
		<form:option label="----" value="0" />
		<form:options items="${authorities}" itemLabel="authority" />
	</form:select>
	<form:errors cssClass="error" path="userAccount.authorities" />

	<input type="submit" name="save"
		value="<spring:message code="admin.save" />" />&nbsp; 

	<input type="button" name="cancel"
		value="<spring:message code="admin.cancel" />"
		onclick="javascript: window.location.replace('master.page')" />
	<br />

</form:form>
