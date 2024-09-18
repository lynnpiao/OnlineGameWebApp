<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Create A New Character</title>
</head>
<body>
	<h1>Create A New Character</h1>
	<form action="createcharacter" method="post">
		<p>
			<!-- <label for="accountID">AccountID</label> -->
			<input id="accountID" name="accountID" value="${fn:escapeXml(param.accountID)}" style="display: none;">
		</p>
		<p>
			<label for="userName">userName</label>
			<input id="userName" name="userName" value="${fn:escapeXml(param.userName)}" >
		</p>
		<hr> </hr>
		<h3>Character info needed to be fill out </h3>
		<p>
			<label for="firstName">FirstName</label>
			<input id="firstName" name="firstName" value="">
		</p>
		<p>
			<label for="lastName">LastName</label>
			<input id="lastName" name="lastName" value="">
		</p>
		<p>
			<label for="mainHandItemID">mainHandItemID</label>
			<input id="mainHandItemID" name="mainHandItemID" value="1">
		</p>
		<p>
			<input type="submit">
		</p>
	</form>
	<br/><br/>
	<p>
		<span id="successMessage"><b>${messages.success}</b></span>
	</p>
</body>
</html>