<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Update Weapon Related Info</title>
</head>
<body>
	<h1>Update UnitPrice And IsSellable</h1>
	<form action="updateunitpriceandissellable" method="post">
	    <p>
			<!-- <label for="itemID">ItemID</label> -->
			<input id="itemID" name="itemID" value="${fn:escapeXml(param.itemID)}" style="display: none;">
		</p>
		<p>
			<label for="itemName">ItemName</label>
			<input id="itemName" name="itemName" value="${item.getItemName()}" >
		</p>
		<hr> </hr>
		<h3>Price related info needed to be fill out </h3>
		<p>
			<label for="unitPrice">New UnitPrice</label>
			<input id="unitPrice" name="unitPrice" value="">
		</p>
		<p>
			<label for="isSellable">New IsSellable</label>
			<input id="isSellable" name="isSellable" value="1">
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