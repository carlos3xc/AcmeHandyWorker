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

	<!-- PARAMETERS FROM CONTROLLER: finder: Finder, finder a editar
									 fixUpTasks: Collection<FixUpTask>
					 				 categories: Collection<Category>
					 				 -->
									 

<form:form action="finder/handy-worker/edit.do" modelAttribute="finder">

	<form:hidden path="id" />
	<form:hidden path="version" />
	
	<form:hidden path="moment" />
	
	<security:authorize access="hasRole('HANDYWORKER')">
	
	<form:label path="keyword">
		<spring:message code="finder.keyword" />:
	</form:label>
	<form:input path="keyword" />
	<form:errors cssClass="error" path="keyword" />
	<br />
	
	<form:label path="minPrice">
		<spring:message code="finder.minPrice" />:
	</form:label>
	<form:input path="minPrice" />
	<form:errors cssClass="error" path="minPrice" />
	<br />
	
	<form:label path="maxPrice">
		<spring:message code="task.maxPrice" />:
	</form:label>
	<form:input path="maxPrice" />
	<form:errors cssClass="error" path="maxPrice" />
	<br />
	
	<form:label path="startDate">
		<spring:message code="finder.startDate" />:
	</form:label>
	<form:input path="startDate" placeholder="01/01/2010 12:00"/>
	<form:errors cssClass="error" path="startDate" />
	<br />
	
	<form:label path="endDate">
		<spring:message code="finder.endDate" />:
	</form:label>
	<form:input path="endDate" placeholder="01/01/2010 12:00"/>
	<form:errors cssClass="error" path="endDate" />
	<br />
	
	<form:select id="categories" path="category">
		<form:option label = "-----" value="0" />
		<form:options items="${categories}" itemsLabel="name" itemsValue="id" />
	</form:select>
	<form:errors cssClass="error" path="category" />
	<br />
	
	<input type="submit" name="search" value="<spring:message code="finder.search" />" />
				
	<input type="button" name="cancel"
		value="<spring:message code="finder.cancel" />"
		onclick="javascript: window.location.replace('')" />
	<br />
	
	</security:authorize>

</form:form>