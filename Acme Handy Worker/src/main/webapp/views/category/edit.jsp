<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<!-- Listing grid -->

<security:authorize access="hasRole('ADMIN')">

	<form:form action="category/administrator/edit.do"
		modelAttribute="categoryForm">
		<form:hidden path="id" />
		<form:hidden path="version" />
		<form:hidden path="hijos" />


		<form:label path="name">
			<spring:message code="category.name" />
		</form:label>
		<form:input path="name" />
		<form:errors cssClass="error" path="name" />
		<br />



		<jstl:if test="${nuevo==true }">
			<form:label path="categoryFather">
				<spring:message code="category.categories" />:
			</form:label>
			<form:select id="categories" path="categoryFather">
				<form:options items="${categories}" itemValue="id"
					itemLabel="name" />
			</form:select>
			<form:errors cssClass="error" path="categoryFather" />
			<br />
		</jstl:if>



		<input type="submit" name="save"
			value="<spring:message code="category.save"/>" />

		<input type="button" name="cancel"
			value="<spring:message code="category.cancel" />"
			onclick="window.location='category/administrator/list.do';" />
			
		<jstl:if test="${nuevo==false}">
			<input type="submit" name="delete"
				value="<spring:message code="category.delete"/>" />
		</jstl:if>

	</form:form>
</security:authorize>


