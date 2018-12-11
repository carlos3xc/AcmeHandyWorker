<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

	<!-- PARAMETERS FROM CONTROLLER: fixUpTask: FixUpTask, task a mostrar
									 moment: Date, momento de creación de la task, bien formateada
					 				 startMoment: Date, momento de comienzo de la task, bien formateada
									 endMoment: Date, momento de finalización de la task, bien formateada
									 complaints: Collection<Complaint>, lista de complaints sobre la task correspondiente -->
									 
	<!-- PARAMETERS CREATED IN VIEW: customerName: String, unión del name,middleName y surname del customer que publica la task -->
					 
		<!-- Creamos el nombre completo del customer y creamos una variable customerName -->
		<jstl:set var="customerName" value="${fixUpTask.customer.name + fixUpTask.customer.middleName + fixUpTask.customer.surname }"/>
	<!-- 
		<fieldset>
			<table>
				<tr>
					<td><b>Ticker: </b> <jstl:out value="${fixUpTask.ticker}"/></td>
					<td><b><spring:message code="task.moment"/></b> <jstl:out value="${moment}"/></td>
				</tr>
				<tr>
					<td><b><spring:message code="task.description"/></b> <jstl:out value="${fixUpTask.description}"/></td>
					<td><b><spring:message code="task.maxPrice"/></b> <jstl:out value="${fixUpTask.maxPrice}"/></td>
				</tr>
				<tr>
					<td><b><spring:message code="task.address"/></b> <jstl:out value="${fixUpTask.address}"/></td>
					<td><b><spring:message code="task.category"/></b> <jstl:out value="${fixUpTask.category.name}"/></td>
				</tr>
				<tr>
					<td><b><spring:message code="task.startMoment"/></b> <jstl:out value="${startMoment}"/></td>
					<td><b><spring:message code="task.publisher"/></b> <a href="actor/profile.do?actorId=${fixUpTask.customer.id}"><jstl:out value="${customerName}"/></a></td>
				</tr>
				<tr>
					<td><b><spring:message code="task.endMoment"/></b> <jstl:out value="${endMoment}" /></td>
				</tr>
			</table>
			 -->
 		<display:table name="fixUpTask" id="row" requestURI="fixUpTask/list.do">
				<display:column>
					<b>Ticker:</b> <jstl:out value="${fixUpTask.ticker}"/>
					<b><spring:message code="task.description"/></b> <jstl:out value="${fixUpTask.description}"/>
					<b><spring:message code="task.address"/></b> <jstl:out value="${fixUpTask.address}"/>
					<b><spring:message code="task.startMoment"/></b> <jstl:out value="${startMoment}"/>
					<b><spring:message code="task.endMoment"/></b> <jstl:out value="${endMoment}" />
				</display:column>
				<display:column>
					<b><spring:message code="task.category"/></b> <jstl:out value="${fixUpTask.category.name}"/>
					<b><spring:message code="task.moment"/></b> <jstl:out value="${moment}"/>
					<b><spring:message code="task.maxPrice"/></b> <jstl:out value="${fixUpTask.maxPrice}"/>
					<b><spring:message code="task.publisher"/></b> <a href="actor/profile.do?actorId=${fixUpTask.customer.id}"><jstl:out value="${customerName}"/></a>
				</display:column>		
			</display:table>

		<br/>
		<h1><spring:message code="task.warranty"/>:</h1><br/>

		<display:table name="fixUpTask.warranty" id="row" requestURI="fixUpTask/list.do">
				<display:column>
					<b><spring:message code="task.warranty.title"/>:</b> <jstl:out value="${fixUpTask.warranty.title}"/>
					<b><spring:message code="task.warranty.terms"/>:</b><br/> <jstl:out value="${fixUpTask.warranty.terms}"/>
				</display:column>
				<display:column>
					<b><spring:message code="task.warranty.laws"/>:</b> <jstl:forEach var="law" items="${fixUpTask.warranty.laws}"> <jstl:out value="${law}"/><br/></jstl:forEach>
				</display:column>		
			</display:table>
		<br/>
		<h1><spring:message code="task.complaints"/>:</h1><br/>
		<display:table name="complaints" id="row" requestURI="fixUpTask/show.do" pagesize="5">
			
			<display:column property="ticker" titleKey="task.ticker" />
			
			<display:column property="description" titleKey="task.description" />
			
			<display:column property="moment" title="task.moment" />
			<!--  
			<spring:message code="task.complaint.attachments" var="attachmentsHeader"/>
			<display:column title="${tickerHeader}">
				<jstl:forEach var="a" items="${row.attachments}">
					<jstl:out value="${a}"/><br/>
				</jstl:forEach>
			</display:column>-->
			
			<display:column titleKey="task.complaint.customer">
				<a href="actor/profile.do?actorId=${fixUpTask.customer.id}"><jstl:out value="${fixUpTask.customer.username}"/></a>
			</display:column>
		</display:table>
		
	<input type="button" name="back"
		value="<spring:message code="task.show.back" />"
		onclick="javascript: window.location.replace('')" />
	<br />
		
				
		
	