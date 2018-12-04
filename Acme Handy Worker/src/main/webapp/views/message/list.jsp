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
		<table style="width:'100%' border='0' align='center' ">
			<tr>
				<th>Messages</th>
			</tr>
	<jstl:forEach var="i" items="messages">
			<tr>
				<td>
				<a href="message/delete.do?messageId=${i.id}"><spring:message code ='message.delete'/></a>
				</td>
				<td>
				<spring:message code= 'message.priority'/><jstl:out value="${i.priority}"/>
				</td>
				<td>
				<spring:message code= 'message.sender'/><jstl:out value="${i.sender.userAccount.username} \n"/>
				<spring:message code= 'message.subject'/><jstl:out value="${i.subject}"/>
				</td>
				<td>
				<jstl:out value="${i.body}"/>
				</td>
			</tr>		
	</jstl:forEach>
	</table>
	<!-- edit method must diferentiate between no attributes where 
	the sender will be obtained via the create method. -->
	<a href="message/edit.do"><spring:message code ='message.create'/></a>
</security:authorize>