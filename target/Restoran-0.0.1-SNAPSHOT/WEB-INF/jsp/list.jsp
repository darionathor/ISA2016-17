<!DOCTYPE html>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Greetings : View all</title>
</head>
<body>
	<div id="greetings">
		<table>
			<tr>
				<th>ID</th>
				<th>username</th>
				<th>password</th>
				<th></th>
			</tr>
			<c:forEach items="${users}"  var="user">
				<tr>	
					<td><c:out value="${user.id}"/></td>				
					<td><c:out value="${user.username}"/></td>	
					<td><c:out value="${user.password}"/></td>
					<td><a href="<c:url value="/users/update/${user.id}"/>">Update</a></td>
					<td><a href="<c:url value="/users/delete/${user.id}"/>">Delete</a></td>
				</tr>
			</c:forEach>
		</table>
	</div>	
	<div id="newUser">
		<a href="<c:url value="/user/new"/>">Create new user</a>
	</div>
</body>
</html>
