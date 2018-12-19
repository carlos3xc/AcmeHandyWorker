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
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<form:form action="actor/edit.do" modelAttribute="actor">

	<form:hidden path="id" />
	<form:hidden path="version" />

	<form:hidden path="name" />
	<form:hidden path="surname" />
	<form:hidden path="middleName" />
	<form:hidden path="photo" />
	<form:hidden path="email" />
	<form:hidden path="phone" />
	<form:hidden path="address" />

	<form:hidden path="socialProfiles" />
	<form:hidden path="userAccount" />

	<form:label path="isSuspicious">
		<spring:message code="actor.isSuspicious" />:
	</form:label>
	<form:checkbox path="isSuspicious" />
	<form:errors cssClass="error" path="isSuspicious" />
	<br />

	<form:label path="isBanned">
		<spring:message code="actor.isBanned" />:
	</form:label>
	<form:checkbox path="isBanned" />
	<form:errors cssClass="error" path="isBanned" />
	<br />

	<input type="submit" name="save"
		value="<spring:message code="admin.save" />" />

	<input type="button" name="cancel"
		value="<spring:message code="admin.cancel" />"
		onclick="javascript: window.location.replace('actor/admin/list.do')" />
	<br />

</form:form>