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

	<!-- PARAMETERS FROM CONTROLLER: fixUpTask: FixUpTask, task a editar
									 categories: Collection<Category>, colección de categorías
					 				 warranty: Warranty, garantía a añadir a la task 
					 				 -->
									 

<form:form action="customer/fixUpTask/edit.do" modelAttribute="fixUpTask">

	<form:hidden path="id" />
	<form:hidden path="version" />
	
	<form:hidden path="ticker" />
	<form:hidden path="moment" />
	<form:hidden path="customer" />		
	<form:hidden path="applications" />	
	<form:hidden path="complaints" />
	<jstl:if test="${fixUpTask.id ==0 }">
		<form:hidden path="warranty.isDraft"/>
	</jstl:if>
	<jstl:if test="${fixUpTask.id !=0 }">
		<form:hidden path="warranty"/>
	</jstl:if>
	
	<security:authorize access="hasRole('CUSTOMER')">
	
	<form:label path="description">
		<spring:message code="task.description" />:
	</form:label>
	<form:textarea path="description" />
	<form:errors cssClass="error" path="description" />
	<br />
	
	<form:label path="address">
		<spring:message code="task.address" />:
	</form:label>
	<form:input path="address" />
	<form:errors cssClass="error" path="address" />
	<br />
	
	<form:label path="maxPrice">
		<spring:message code="task.maxPrice" />:
	</form:label>
	<form:input path="maxPrice" />
	<form:errors cssClass="error" path="maxPrice" />
	<br />
	
	<form:label path="startMoment">
		<spring:message code="task.startMoment" />:
	</form:label>
	<form:input path="startMoment" placeholder="01/01/2001 12:00"/>
	<form:errors cssClass="error" path="startMoment" />
	<br />
	
	<form:label path="endMoment">
		<spring:message code="task.endMoment" />:
	</form:label>
	<form:input path="endMoment" placeholder="01/01/2001 12:00"/>
	<form:errors cssClass="error" path="endMoment" />
	<br />
	
	<form:label path="category">
		<spring:message code="task.category" />:
	</form:label>
	<form:select id="categories" path="category">
		<form:options items="${categories}" itemsLabel="name" itemsValue="id" />
		<form:option label = "-----" value="0" />
	</form:select>
	<form:errors cssClass="error" path="category" />
	<br />
	
	<jstl:if test="${fixUpTask.id ==0 }">
		<h3><spring:message code="task.warranty"/></h3>
			<form:label path="warranty.title">
				<spring:message code="task.warranty.title" />:
			</form:label>
			<form:input path="warranty.title" />
			<form:errors cssClass="error" path="warranty.title" />
			<br />
			
			<form:label path="warranty.terms">
				<spring:message code="task.warranty.terms" />:
			</form:label>
			<form:input path="warranty.terms" />
			<form:errors cssClass="error" path="warranty.terms" />
			<br />
			
			<form:label path="warranty.laws">
				<spring:message code="task.warranty.laws" />:
			</form:label>
			<form:textarea path="warranty.laws" placeholder="Separate the different laws with ;"/>
			<form:errors cssClass="error" path="warranty.laws" />
			<br />
	</jstl:if>
	
	<input type="submit" name="save" value="<spring:message code="task.save" />" />
	
	<jstl:if test="${fixUpTask.id != 0}">
		<input type="submit" name="delete"
			value="<spring:message code="fixUpTask.delete" />"
			onclick="return confirm('<spring:message code="fixUpTask.confirm.delete" />')" />&nbsp;
	</jstl:if>
				
	<input type="button" name="cancel"
		value="<spring:message code="task.cancel" />"
		onclick="javascript: window.location.replace('')" />
	<br />
	
	</security:authorize>

</form:form>