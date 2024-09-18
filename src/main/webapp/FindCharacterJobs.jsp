<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>CharacterJobRelation Detail</title>
</head>
<body>
	<h1>${messages.title}</h1>
	<%-- <h3>${messages.charactername}</h3> --%>
        <table border="1">
            <tr>
                <th>JobName</th>
                <th>JobLevel</th>
                <th>IsPlayed</th>
                <th>JobLevelCap</th>
                <th>isCurrentJob</th>
                <th>ChangeJob</th>
                
            </tr>
            <c:forEach items="${characterJobRelations}" var="characterJobRelation" varStatus="loop">
                <tr>
                    <td><c:out value="${characterJobRelation.getJob().getJobName()}" /></td>
                    <td><c:out value="${characterJobRelation.getJobLevel()}" /></td>
                    <td><c:out value="${characterJobRelation.isPlayed()}" /></td>
                    <td><c:out value="${characterJobRelation.getJob().getJobLevelCap()}" /></td>
                    <td><c:out value="${isCurrentJob[loop.index]}" /></td>
                    <td> <a href="updatecurrentjob?jobID=<c:out value="${characterJobRelation.getJob().getJobID()}"/>&characterID=<c:out value="${characterJobRelation.getCharacter().getCharacterID()}"/>">Update</a></td>
                     <%--  <!--  --><td><fmt:formatDate value="${blogPost.getCreated()}" pattern="MM-dd-yyyy hh:mm:sa"/></td>
                   <!--  --> <td><a href="findweaponattribute?weaponID=<c:out value="${weapon.getItemID()}"/>">OtherAttribute</a></td>
                    <td><c:out value="${weapon.getRequiredLevel()}" /></td>
                    <td><c:out value="${weapon.getjob().getJobName()}" /></td>
                    <td><a href="updateunitpriceandissellable?itemID=<c:out value="${weapon.getItemID()}"/>">Update</a></td> --%>
                    
                </tr>
            </c:forEach>
       </table>
</body>
</html>
