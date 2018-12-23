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
		<display:table name="fixUpTask" id="row" requestURI="fixUpTask/list.do">
				<display:column>
					<spring:message code="task.moment.format" var="momentFormat"/>
					<b>Ticker:</b> <jstl:out value="${fixUpTask.ticker}"/><br/>
					<b><spring:message code="task.description"/>: </b> <jstl:out value="${fixUpTask.description}"/><br/>
					<b><spring:message code="task.address"/>: </b> <jstl:out value="${fixUpTask.address}"/><br/>
					<b><spring:message code="task.startMoment"/>: </b> <fmt:formatDate value="${fixUpTask.startMoment}" pattern="${momentFormat}" /> <br/>
					<b><spring:message code="task.endMoment"/>: </b> <fmt:formatDate value="${fixUpTask.endMoment}" pattern="${momentFormat}" /> <br/>
				</display:column>
				<display:column>
					<b><spring:message code="task.category"/>: </b> <jstl:out value="${fixUpTask.category.name}"/><br/>
					<b><spring:message code="task.moment"/>: </b> <fmt:formatDate value="${fixUpTask.moment}" pattern="${momentFormat}" /> <br/>
					<b><spring:message code="task.maxPrice"/>: </b> <jstl:out value="${fixUpTask.maxPrice}"/><br/>
					<b><spring:message code="task.publisher"/>: </b> <a href="actor/profile.do?actorId=${fixUpTask.customer.id}"><jstl:out value="${fullName}"/></a>
				</display:column>		
			</display:table>
	
	<display:table name="fixUpTask.warranty" id="row" requestURI="fixUpTask/show.do">
				<display:column>
					<b><spring:message code="task.warranty.title"/>: </b> <jstl:out value="${fixUpTask.warranty.title}"/><br/>
					<b><spring:message code="task.warranty.terms"/>: </b><br/> <jstl:out value="${fixUpTask.warranty.terms}"/>
				</display:column>
				<display:column>
					<b><spring:message code="task.warranty.laws"/>:</b> <jstl:forEach var="law" items="${fixUpTask.warranty.laws}"> <jstl:out value="${law}"/><br/></jstl:forEach>
				</display:column>		
			</display:table>
		<br/>
		<h3><spring:message code="task.complaints"/>:</h3>
		<display:table name="complaints" id="row" requestURI="fixUpTask/show.do" pagesize="5">
			
			<display:column property="ticker" titleKey="task.ticker" />
			
			<display:column property="description" titleKey="task.description" />
			
			<display:column property="moment" titleKey="task.moment" />

			<display:column titleKey="task.complaint.customer">
				<a href="actor/profile.do?actorId=${fixUpTask.customer.id}"><jstl:out value="${fixUpTask.customer.userAccount.username}"/></a>
			</display:column>
		</display:table>
		
	<input type="button" name="back"
		value="<spring:message code="task.show.back" />"
		onclick="javascript: window.location.replace('')" />
	<br />
		
				
		
	