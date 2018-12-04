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
Double avgFixUpPerUser, minFixUpPerUser, maxFixUpPerUser, stdvFixUpPerUser :
The average, the minimum, the maximum, and the standard deviation of the number of fix-up tasks per user.

Double avgApplicationsPerFixUp, minApplicationsPerFixUp, maxApplicationsPerFixUp, stdvApplicationsPerFixUp:
The average, the minimum, the maximum, and the standard deviation of the number of applications per fix-up task.

Double avgMaxPricePerFixUp, minMaxPricePerFixUp, maxMaxPricePerFixUp, stdvMaxPricePerFixUp:
The average, the minimum, the maximum, and the standard deviation of the maximum price of the fix-up tasks.

Double avgPriceOfferedApplication, minPriceOfferedApplication, maxPriceOfferedApplication, stdvPriceOfferedApplication:
The average, the minimum, the maximum, and the standard deviation of the price offered in the applications.

Double avgComplaintsPerFixUp, minComplaintsPerFixUp, maxComplaintsPerFixUp, stdvComplaintsPerFixUp:
The minimum, the maximum, the average, and the standard deviation of the number of complaints per fix-up task.

Double avgNotesPerReport, minNotesPerReport, maxNotesPerReport, NotesPerReport:
The minimum, the maximum, the average, and the standard deviation of the number of notes per referee report.

Double ratioPendingApplications - The ratio of pending applications.
Double ratioAcceptedApplications - The ratio of accepted applications.
Double ratioRejectedApplications - The ratio of rejected applications.
Double ratioOvertimeApplications - The ratio of pending applications that cannot change its status because their time period's elapsed.
Double ratioFixUpComplaint - The ratio of fix-up tasks with a complaint.
Double topThreeCustomerComplaints - The top-three customers in terms of complaints.
Double topThreeHandyWorkersComplaints - The top-three handy workers in terms of complaints.

List<Customer> customerPublishers10 - The listing of customers who have published at least 10% more fix-up tasks than the average, ordered by number of applications.
List<HandyWorker> handyWorkersPublishers10 - The listing of handy workers who have got accepted at least 10% more ap-plications than the average, ordered by number of applications.
	
-->

<security:authorize access="hasRole('ADMINISTRATOR')">
	Data Table:
	<table style="width:'100%' border='0' align='center' ">
			<tr>
				<th>Type</th>
				<th>Fix-up task per user</th>
				<th>Applications per Fix-up task</th>
				<th>Maximum price of the Fix-up tasks</th>
				<th>Price offered in application</th>
				<th>Complaints per fix-up task</th>
				<th>Notes per referee report</th>
			</tr>
			<tr>
				<td>Average:</td>
				<td><jstl:out value="${avgFixUpPerUser}"/></td>
				<td><jstl:out value="${avgApplicationsPerFixUp}"/></td>
				<td><jstl:out value="${avgMaxPricePerFixUp}"/></td>
				<td><jstl:out value="${avgPriceOfferedApplication}"/></td>
				<td><jstl:out value="${avgComplaintsPerFixUp}"/></td>
				<td><jstl:out value="${avgNotesPerReport}"/></td>
			</tr>
			<tr>
				<td>Minimum:</td>
				<td><jstl:out value="${minFixUpPerUser}"/></td>
				<td><jstl:out value="${minApplicationsPerFixUp}"/></td>
				<td><jstl:out value="${minMaxPricePerFixUp}"/></td>
				<td><jstl:out value="${minPriceOfferedApplication}"/></td>
				<td><jstl:out value="${minComplaintsPerFixUp}"/></td>
				<td><jstl:out value="${minNotesPerReport}"/></td>
			</tr>	
			<tr>
				<td>Maximum:</td>
				<td><jstl:out value="${maxFixUpPerUser}"/></td>
				<td><jstl:out value="${maxApplicationsPerFixUp}"/></td>
				<td><jstl:out value="${maxMaxPricePerFixUp}"/></td>
				<td><jstl:out value="${maxPriceOfferedApplication}"/></td>
				<td><jstl:out value="${maxComplaintsPerFixUp}"/></td>
				<td><jstl:out value="${maxNotesPerReport}"/></td>
			</tr>
			<tr>
				<td>Standard deviation:</td>
				<td><jstl:out value="${stdvFixUpPerUser}"/></td>
				<td><jstl:out value="${stdvApplicationsPerFixUp}"/></td>
				<td><jstl:out value="${stdvMaxPricePerFixUp}"/></td>
				<td><jstl:out value="${stdvPriceOfferedApplication}"/></td>
				<td><jstl:out value="${stdvComplaintsPerFixUp}"/></td>
				<td><jstl:out value="${stdvNotesPerReport}"/></td>
			</tr>
	</table>
	
	
	Ratios:
	<table style="width:'100%' border='0' align='center' ">
		<tr>
			<th>type:</th>
			<th>ratio:</th>
		</tr>
		<tr>
			<th>Ratio of pending applications:</th>
			<th><jstl:out value="${ratioPendingApplications}"/></th>
		</tr>
		<tr>
			<th>Ratio of accepted applications:</th>
			<th><jstl:out value="${ratioAcceptedApplications}"/></th>
		</tr>
		<tr>
			<th>Ratio of rejected applications:</th>
			<th><jstl:out value="${ratioRejectedApplications}"/></th>
		</tr>
		<tr>
			<th>Ratio of overtimed applications</th>
			<th><jstl:out value="${ratioOvertimeApplications}"/></th>
		</tr>
		<tr>
			<th>Ratio of fix-up task with a complaint</th>
			<th><jstl:out value="${ratioFixUpComplaint}"/></th>
		</tr>
		<tr>
			<th>Top three customers with complaints</th>
			<th><jstl:out value="${topThreeCustomerComplaints}"/></th>
		</tr>
		<tr>
			<th>Top three HandyWorkers with complaints</th>
			<th><jstl:out value="${topThreeHandyWorkersComplaints}"/></th>
		</tr>
	</table>
	
	Top publishers:
	- Customers
	<table style="width:'100%' border='0' align='center' ">
		<tr>
			<th>Customers who published 10% more than average:</th>		
		</tr>
		<jstl:forEach var="i" items="${customerPublishers10}">
		<tr>
			<td><jstl:out value="${i.userAccount.username}"/> </td>
		</tr>			
		</jstl:forEach>
	</table>
	<br>
	 - HandyWorkers
	<table style="width:'100%' border='0' align='center' ">
		<tr>
			<th>HandyWorkers who got accepted 10% more than average:</th>		
		</tr>
		<jstl:forEach var="i" items="${handyWorkerPublishers10}">
		<tr>
			<td><jstl:out value="${i.userAccount.username}"/></td>
		</tr>			
		</jstl:forEach>
	</table>


</security:authorize>