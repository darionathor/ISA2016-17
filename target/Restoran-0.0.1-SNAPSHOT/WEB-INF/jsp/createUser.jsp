<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>Create Greeting</title>
</head>
<body>
	<div id="createUser">
		<c:url var="action" value="/users/create" />
		<form:form id="formUser" action="${action}" method="post"
			modelAttribute="user">
			<fieldset>
				<form:hidden path="id" />
				<form:label path="username">Username </form:label>
				<form:input path="username" />
				<form:errors path="username" />
				<form:label path="password">password </form:label>
				<form:input path="password" />
				<form:errors path="password" />
			</fieldset>
			<p>
				<button type="submit">Submit</button>
			</p>
		</form:form>
	</div>
</body>
</html>