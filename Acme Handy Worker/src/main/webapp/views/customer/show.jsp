<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

	<!-- PARAMETERS FROM CONTROLLER: customer: Customer
									 actor: Actor
					 				 socialProfile: SocialProfile
									 userAccount: UserAccount
									 fixUpTasks: Collection<FixUpTask>
									 -->
									 	
		<fieldset>
			<table>
				<tr>
					<td><b><spring:message code="customer.name"/></b> <jstl:out value="${name}"/></td>
				</tr>
				<tr>
					<td><b><spring:message code="customer.middleName"/></b> <jstl:out value="${middleName}"/></td>
				</tr>
				<tr>
					<td><b><spring:message code="customer.surname"/></b> <jstl:out value="${surname}"/></td>
				</tr>
				<tr>
					<td><b><spring:message code="customer.photo"/></b> <jstl:out value="${photo}"/></td>
				</tr>
				<tr>
					<td><b><spring:message code="customer.email"/></b> <jstl:out value="${email}"/></td>
				</tr>
				<tr>
					<td><b><spring:message code="customer.phone"/></b> <jstl:out value="${phone}"/></td>
				</tr>
				<tr>
					<td><b><spring:message code="customer.username"/></b> <jstl:out value="${username}"/></td>
				</tr>
				<tr>
					<td><b><spring:message code="customer.authority"/></b> <jstl:out value="${authority}"/></td>
				</tr>
			</table>
		</fieldset>
		<br/>
		<h1><spring:message code="customer.socialProfile"/>:</h1><br/>
		<fieldset>
			<table>
				<tr>
					<td><b><spring:message code="customer.socialProfile.nick"/>:</b> <jstl:out value="${customer.socialProfile.nick}"/></td>
				</tr>
				<tr>
					<td><b><spring:message code="customer.socialProfile.socialNetwork"/>:</b> <jstl:forEach var="law" items="${customer.socialProfile.socialNetwork}"> <jstl:out value="${law}"/><br/></jstl:forEach></td>
				</tr>
				<tr>
					<td><b><spring:message code="customer.socialProfile.link"/>:</b><br/> <jstl:out value="${customer.socialProfile.link}"/></td>
				</tr>
			</table>
		</fieldset>
		<br/>
		<h1><spring:message code="customer.fixUpTasks"/>:</h1><br/>
		<display:table name="fixUpTasks" id="row" requestURI="customer/show.do" pagesize="5">

			<display:column property="ticker" titleKey="customer.ticker" />
			<display:column property="description" titleKey="customer.description" />
			<display:column property="moment" title="customer.moment" />
			
		</display:table>
	<input type="button" name="back"
		value="<spring:message code="customer.show.back" />"
		onclick="javascript: window.location.replace('')" />
	<br />
		
				
		
	