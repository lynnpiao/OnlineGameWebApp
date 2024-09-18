<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Find a User</title>
</head>
<body>
	<form action="findaccountcharacters" method='get'>
		<h1>1. Search for the Characters created by UserName</h1>
		<p>
			<label for="userName">userName</label>
			<input id="userName" name="userName" value="${fn:escapeXml(param.userName)}">
			
			<label for="sortedVaFindWeapons.javal">Select a sorted variable(ascending)</label>
			<select id="sortedVal" name="sortedVal">
            <option value="firstName">firstName</option>
            <option value="lastName">lastName</option>
            </select>
            
		</p>
		<p>
			<input type="submit">
			<br/><br/><br/>
			<span id="successMessage"><b>${messages.success}</b></span>
		</p>
	</form>
	<h2>Matching Characters</h2>
        <table border="1">
            <tr>
                <th>FirstName</th>
                <th>LastName</th>
                <th>MainHandWeapon Name</th>
                <th>MainHandWeapon Detail</th>
                <th>CurrentJob Name</th>
                <th>All Jobs</th>
                <th>Inventory</th>
                <!-- <th>Delete Character</th> -->
            </tr>
            <c:forEach items="${characters}" var="character" varStatus="loop">
                <tr>
                    <td><c:out value="${character.getFirstName()}" /></td>
                    <td><c:out value="${character.getLastName()}" /></td>
                    <td><c:out value="${character.getMainHandItem().getItemName()}" /></td>
                    <%-- <td><fmt:formatDate value="${blogUser.getDob()}" pattern="yyyy-MM-dd"/></td> --%>
                    <td><a href="findweapons?itemID=<c:out value="${character.getMainHandItem().getItemID()}"/>&firstName=<c:out value="${character.getFirstName()}"/>&lastName=<c:out value="${character.getLastName()}"/>">Detail</a></td>
                    <td><c:out value="${weapons[loop.index].getjob().getJobName()}" /></td>
                    <td><a href="findcharacterjobs?characterID=<c:out value="${character.getCharacterID()}"/>&currentjobID=<c:out value="${weapons[loop.index].getjob().getJobID()}"/>">Jobs</a></td>
                    <td><a href="findinventory?characterID=<c:out value="${character.getCharacterID()}"/>">Inventory</a></td>
                    <%-- <td><a href="deletecharacter?characterID=<c:out value="${character.getCharacterID()}"/>">Delete</a></td> --%>
                </tr>
            </c:forEach>
       </table>
      <hr> </hr>
      
    <h1>2. Create A New Character</h1>  
   	<br/>
	<div id="createCharacter"><a href="createcharacter?accountID=<c:out value="${accountID}"/>&userName=<c:out value="${fn:escapeXml(param.userName)}"/>">Create Character</a></div>
	<br/>
</body>
</html>
