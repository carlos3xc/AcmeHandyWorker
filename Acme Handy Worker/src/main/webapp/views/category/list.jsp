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

	<display:table pagesize="5" class="category" name="categories"
		requestURI="category/administrator/list.do" id="row">
	
		<!-- Action links -->
	
		<!-- Attributes -->
	
			
		<jstl:if test="${row.name != 'CATEGORY' }">
			<display:column>
				<a href="category/administrator/edit.do?categoryId=${row.id}"> 
					<spring:message	code="category.edit" />
				</a>
			</display:column>
		</jstl:if>
			<jstl:if test="${row.name == 'CATEGORY' }">
			<display:column>
				
			</display:column>
		</jstl:if>
		
	
		<spring:message code="category.name" var="nameHeader" />
		<display:column property="name" title="${nameHeader}" />
	
		<spring:message code="category.name" var="categ" />
		<display:column property="categories" title="${category.categories} " />
	
	
	</display:table>
	
	
	<div>
		<a href="category/administrator/create.do"> <spring:message
				code="category.create" />
		</a>
	</div>

</security:authorize>
