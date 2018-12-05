<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
	
	<!-- PARAMETERS FROM CONTROLLER: fixUpTasks: Collection<FixUpTask>, tasks a mostrar
									 customer: Customer, customer logeado para comprobar si la task le pertenece -->
									 	
		<security:authorize access="hasRole('CUSTOMER')">
	
			<display:table name="fixUpTasks" id="row" requestURI="fixUpTask/customer/list.do" pagesize="5">
				
				<spring:message code="task.options" var="optionsHeader" />
				<display:column title="${optionsHeader}">
					<a href="fixUpTask/show.do?fixUpTaskId=${row.id}">
								<spring:message	code="task.show" />
					</a><br/>
					<jstl:if test="${row.customer == customer}"> <!-- customer SE PASARÁ DESDE EL CONTROLADOR INDICADO EL CUSTOMER LOGEADO -->
							<a href="fixUpTask/customer/edit.do?fixUpTaskId=${row.id}">
								<spring:message	code="task.edit" />
							</a><br/>							
					</jstl:if>
				</display:column>
				
				<spring:message code="task.ticker" var="tickerHeader"/>
				<display:column property="ticker" title="${tickerHeader}" />	
			
				<spring:message code="task.description"  var="descriptionHeader"/>
				<display:column property="description" title="${descriptionHeader}" />
				
				<spring:message code="task.moment"  var="momentHeader"/>
				<spring:message code="task.moment.format" var="formatMoment"/>
				<display:column property="moment" title="${momentHeader}" format="{0,date,${formatMoment} }"/>
				
				<spring:message code="task.startMoment"  var="startMomentHeader"/>
				<display:column property="startMoment" title="${momentHeader}" format="{0,date,${formatMoment} }"/>
				
				<spring:message code="task.maxPrice" var="maxPriceHeader"/>
				<display:column property="maxPrice" title="${maxPriceHeader}" />	
			
			</display:table>
		
		</security:authorize>
		
		<security:authorize access="hasRole('HANDYWORKER')">
	
			<display:table name="fixUpTasks" id="row" requestURI="fixUpTask/customer/list.do" pagesize="5">
				
				<spring:message code="task.options" var="optionsHeader" />
				<display:column title="${optionsHeader}">
					<a href="fixUpTask/show.do?fixUpTaskId=${row.id}">
						<spring:message	code="task.show" />
					</a><br/>
					
					<a href="application/handyWorker/apply.do?fixUpTaskId=${row.id}">
						<spring:message code="task.publisher"/>
					</a><br/>	
					
					<a href="actor/profile.do?actorId=${row.customer.id}">
						<spring:message code="task.apply"/>
					</a><br/>
								
				</display:column>
				
				<spring:message code="task.ticker" var="tickerHeader"/>
				<display:column property="ticker" title="${tickerHeader}" />	
			
				<spring:message code="task.description"  var="descriptionHeader"/>
				<display:column property="description" title="${descriptionHeader}" />
				
				<spring:message code="task.moment"  var="momentHeader"/>
				<spring:message code="task.moment.format" var="formatMoment"/>
				<display:column property="moment" title="${momentHeader}" format="{0,date,${formatMoment} }"/>
				
				<spring:message code="task.startMoment"  var="startMomentHeader"/>
				<display:column property="startMoment" title="${momentHeader}" format="{0,date,${formatMoment} }"/>
				
				<spring:message code="task.maxPrice" var="maxPriceHeader"/>
				<display:column property="maxPrice" title="${maxPriceHeader}" />	
			
			</display:table>
			<br/>
			
			<a href="fixUpTask/customer/edit.do"><spring:message code="task.create"/></a>
		
		</security:authorize>