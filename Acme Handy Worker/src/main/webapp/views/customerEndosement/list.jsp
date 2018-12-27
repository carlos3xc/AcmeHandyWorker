+<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

	<!-- PARAMETERS FROM CONTROLLER:
									 customerEndosements Collection<CustomerEndosement>
									 customer Customer
									 handyWorker HandyWorker
									 -->
									 
		<security:authorize access="hasRole('CUSTOMER')">
		
			<h1><spring:message code="customerEndosement.customerEndosements"/>:</h1><br/>
			<display:table name="customerEndosements" id="row" requestURI="customerEndosements/customer/list.do" pagesize="5">
				
				<display:column titleKey="customerEndosements.options">
					<a href="fixUpTask/show.do?fixUpTaskId=${row.id}">
								<spring:message	code="task.show" />
					</a><br/>
					<jstl:if test="${row.customer == customer}"> 
						<jstl:if test="${row.warranty.isDraft}">
							<a href="fixUpTask/customer/edit.do?fixUpTaskId=${row.id}">
								<spring:message	code="task.edit" />
							</a><br/>	
						</jstl:if>
						<jsp:useBean id="today" class="java.util.Date"/>
						<jstl:if test="${row.startMoment > today}">
							<a href="fixUpTask/customer/delete.do?fixUpTaskId=${row.id}">
								<spring:message	code="task.delete" />
							</a><br/>			
						</jstl:if>			
					</jstl:if>
					<a href="complaint/customer/show.do?fixUpTaskId=${row.id}">
						<spring:message	code="task.complain" />
					</a><br/>
				</display:column>
				
				<display:column property="ticker" titleKey="task.ticker" />	
			
				<display:column property="description" titleKey="task.description" />
				
				<spring:message code="task.moment.format" var="formatMoment"/>
				<display:column property="moment" titleKey="task.moment" format="{0,date,${formatMoment} }"/>
				<display:column property="startMoment" titleKey="task.startMoment" format="{0,date,${formatMoment} }"/>
				
				<display:column property="maxPrice" titleKey="task.maxPrice" />	
			
			</display:table>
			
			<a href="fixUpTask/customer/create.do"><spring:message code="task.create"/></a>
			
		</security:authorize>
								
		
	<input type="button" name="back"
		value="<spring:message code="actor.show.back" />"
		onclick="javascript: window.location.replace('')" />
	<br />
		
				
		
	