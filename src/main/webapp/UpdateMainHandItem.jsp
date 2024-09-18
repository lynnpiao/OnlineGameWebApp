<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Update MainHandItem</title>
</head>
<body>
	<!-- <h1>Update BlogUser</h1> -->
	<form action="updatemainhanditem" method="post">
	    <p>
			<!-- <label for="mainHandItemID">WeaponID</label> -->
			<input id="mainHandItemID" name="mainHandItemID" value="${fn:escapeXml(param.mainHandItemID)}" style="display: none;">
		</p>
		<p>
			<!-- <label for="characterID">CharacterID</label> -->
			<input id="characterID" name="characterID" value="${fn:escapeXml(param.characterID)}" style="display: none;">
		</p>
		<hr> </hr>
		<h3>Update MainHandItem Info</h3>
		<p>
			<label for="itemName">WeaponName</label>
			<input id="itemName" name="itemName" value="${weapon.getItemName()}">
		</p>
		<p>
			<label for="firstName">CharacterFirstName</label>
			<input id="firstName" name="firstName" value="${character.getFirstName()}">
		</p>
		<p>
			<label for="lastName">CharacterLastName</label>
			<input id="lastName" name="lastName" value="${character.getLastName()}">
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