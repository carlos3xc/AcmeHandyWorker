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
		List<Box> boxes - los boxes de un actor.		
-->

<security:authorize access="isAuthenticated()">
	<a href="box/edit.do"><spring:message code='box.create'/></a>
		<table style="width:'100%' border='0' align='center' ">
			<tr>
				<th>Boxes</th>
			</tr>
	<jstl:forEach var="i" items="boxes">
			<tr>
				<td>
				<jstl:out value="${i.name}"/><spring:message code ='box.listMessage'/>
				<a href="message/list.do?boxId=${i.id}"><spring:message code ='box.listMessages'/></a>
				<jstl:if test="${i.systemBox == false}">
				<a href="box/delete.do?boxId=${i.id}"><spring:message code ='box.delete'/></a>
				<a href="box/edit.do?boxId=${i.id}"><spring:message code ='box.edit'/></a>
				</jstl:if>
				</td>
				
			</tr>		
	</jstl:forEach>
	</table>
	<a href="box/edit.do"><spring:message code='box.create'/></a>
</security:authorize>