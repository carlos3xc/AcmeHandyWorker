<%--
 * index.jsp
 *
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<!-- 
Recieves:
String name: name of the user logged in, Anonymous otherwise.
String welcomeMessageSpanish
String welcomeMessageEnglish
String systemName
Date moment
 -->
<h1>${systemName}</h1>
<div id="prefix"><spring:message code="welcome.greeting.prefix" /></div> ${name}<spring:message code="welcome.greeting.suffix" /><br>
<div id="welcomeMessage"></div><br>


<!-- <div id="url"></div>

<script type="text/javascript">
var url = window.location.href;
document.getElementById("url").innerHTML = url;
</script> -->



<script>
	var prefix = document.getElementById("prefix").innerHTML;
	
	if(prefix == "Greetings") {
		document.getElementById("welcomeMessage").innerHTML = "${welcomeMessageEnglish}";
	}
	
	if(prefix == "¡Saludos ") {
		document.getElementById("welcomeMessage").innerHTML = "${welcomeMessageSpanish}";
	}

</script>

