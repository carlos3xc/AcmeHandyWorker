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
	Recieves: List<Messages> messages - los mensajes de un box y un actor correspondientes.
-->

<security:authorize access="isAuthenticated()">
	<a href="message/create.do"><spring:message code='message.create' /></a>

	<display:table name="messages" id="row" requestURI="message/list.do" pagesize="5">

		<display:column titleKey="message.messages">
			<table border='1' style="width:100%">
				<tr>
				<td>
					<spring:message code='message.priority'/><jstl:out value="${row.priority}" />
				</td>
				</tr>
				
				<tr>
				<td>
					<spring:message code='message.sender' /><jstl:out value="${row.sender.userAccount.username} \n" /> 
					<spring:message code='message.subject' /><jstl:out value="${row.subject}" />
				</td>
				</tr>
				
				<tr>
				<td>
					<jstl:out value="${row.body}" />
				</td>
				</tr>
				
				<tr>
				<td>
					<a href="message/delete.do?messageId=${row.id}"><spring:message code='message.delete' /></a>
				</td>
				</tr>
			</table>
		</display:column>

	</display:table>
	<!-- edit method must diferentiate between no attributes where 
	the sender will be obtained via the create method. -->
	<a href="message/create.do"><spring:message code='message.create' /></a>
</security:authorize>