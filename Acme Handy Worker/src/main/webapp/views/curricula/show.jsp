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

<!-- 
	Recieves: 
	
	Curricula curricula, si tiene una / null si no la tiene (se le ofrece la posibilidad de crearla).
	
-->

<security:authorize access="hasRole('HANDYWORKER')">
	<jstl:if test="curricula == null">
		<spring:message code="curricula.notCreated"/>
		<a href="curricula/create.do"><spring:message code="curricula.create" /></a>
	</jstl:if>
	<jstl:if test="curricula != null">
		<h3><spring:message code="curricula.personalRecord"/></h3><br>
		<h4><a href="curricula/editPersonalRecord.do?personalRecordId=${curricula.personalRecord.id}"><spring:message code="curricula.editPersonalRecord"/></a></h4><br>
		<spring:message code="curricula.fullName"/>: <jstl:out value="${curricula.personalRecord.fullName }"/><br>
		<spring:message code="curricula.photo"/>: <jstl:out value="${curricula.personalRecord.photo }"/><br>
		<spring:message code="curricula.email"/>: <jstl:out value="${curricula.personalRecord.email }"/><br>
		<spring:message code="curricula.phone"/>: <jstl:out value="${curricula.personalRecord.phone }"/><br>
		<spring:message code="curricula.linkedInProfile"/>: <jstl:out value="${curricula.personalRecord.linkedInUrl}"/><br>
		
		<h3><spring:message code="curricula.endorserRecords"/></h3><br>
		
		<jstl:forEach var="i" items="${curricula.endorserRecords }">
		<h4><a href="curricula/editEndorserRecord.do?EndorserRecordId=${i.id}"><spring:message code="curricula.editEndorserRecord"/></a></h4><br>
		<h4><a href="curricula/deleteEndorserRecord.do?EndorserRecordId=${i.id}"><spring:message code="curricula.deleteEndorserRecord"/></a></h4><br>
		<spring:message code="curricula.endorserName"/>: <jstl:out value="${i.endorserName }"/><br>
		<spring:message code="curricula.email"/>: <jstl:out value="${i.email }"/><br>
		<spring:message code="curricula.phone"/>: <jstl:out value="${i.phone }"/><br>
		<spring:message code="curricula.linkedInProfile"/>: <jstl:out value="${i.linkedInProfile }"/><br>
		<spring:message code="curricula.comments"/>: <jstl:out value="${i.comments }"/><br>
		<hr>
		</jstl:forEach>
		
		
		<h3><spring:message code="curricula.educationRecords"/></h3><br>
		
		<jstl:forEach var="i" items="${curricula.educationRecords }">
		<h4><a href="curricula/editEducationRecord.do?EducationRecordId=${i.id}"><spring:message code="curricula.editEducationRecord"/></a></h4><br>
		<h4><a href="curricula/deleteEducationRecord.do?EducationRecordId=${i.id}"><spring:message code="curricula.deleteEducationRecord"/></a></h4><br>
		<spring:message code="curricula.diplomaTitle"/>: <jstl:out value="${i.diplomaTitle }"/><br>
		<spring:message code="curricula.startDate"/>: <jstl:out value="${i.startDate }"/><br>
		<spring:message code="curricula.endDate"/>: <jstl:out value="${i.endDate }"/><br>
		<spring:message code="curricula.institution"/>: <jstl:out value="${i.institution }"/><br>
		<spring:message code="curricula.attachment"/>: <jstl:out value="${i.attachment }"/><br>
		<spring:message code="curricula.comments"/>: <jstl:out value="${i.comments }"/><br>
		<hr>
		</jstl:forEach>
		
		<h3><spring:message code="curricula.professionalRecords"/></h3><br>
		
		<jstl:forEach var="i" items="${curricula.professionalRecords }">
		<h4><a href="curricula/editProfessionalRecord.do?ProffesionalRecordId=${i.id}"><spring:message code="curricula.editProfessionalRecord"/></a></h4><br>
		<h4><a href="curricula/deleteProfessionalRecord.do?ProffesionalRecordId=${i.id}"><spring:message code="curricula.deleteProfessionalRecord"/></a></h4><br>
		<spring:message code="curricula.companyName"/>: <jstl:out value="${i.companyName}"/><br>
		<spring:message code="curricula.startDate"/>: <jstl:out value="${i.startDate}"/><br>
		<spring:message code="curricula.endDate"/>: <jstl:out value="${i.endDate }"/><br>
		<spring:message code="curricula.role"/>: <jstl:out value="${i.role }"/><br>
		<spring:message code="curricula.attachment"/>: <jstl:out value="${i.attachment }"/><br>
		<spring:message code="curricula.comments"/>: <jstl:out value="${i.comments }"/><br>
		<hr>
		</jstl:forEach>
		
		<h3><spring:message code="curricula.miscellaneousRecords"/></h3><br>
		
		<jstl:forEach var="i" items="${curricula.miscellaneousRecords }">
		<h4><a href="curricula/editMiscellaneousRecord.do?MiscellaneousRecordId=${i.id}"><spring:message code="curricula.editProfessionalRecord"/></a></h4>
		<h4><a href="curricula/deleteMiscellaneousRecord.do?MiscellaneousRecordId=${i.id}"><spring:message code="curricula.deleteProfessionalRecord"/></a></h4>
		<spring:message code="curricula.title"/>: <jstl:out value="${i.title }"/><br>
		<spring:message code="curricula.attachment"/>: <jstl:out value="${i.attachment }"/><br>
		<spring:message code="curricula.comments"/>: <jstl:out value="${i.comments }"/><br>
		<hr>
		</jstl:forEach>
		
	</jstl:if>
	
</security:authorize>