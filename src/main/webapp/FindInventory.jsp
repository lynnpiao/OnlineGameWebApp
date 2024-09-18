<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Inventory</title>
</head>
<body>
	<h1>${messages.title}</h1>
	<%-- <h3>${messages.charactername}</h3> --%>
        <table border="1">
            <tr>
                <th>InventoryPosition</th>
                <th>ItemName</th>
                <th>ItemQuantity</th>
                <th>maxStackSize</th>
                <th>ItemLevel</th>
                <th>IsSellable</th>
                <th>UnitPrice</th>
                <th>ItemCategory</th>
            </tr>
            <c:forEach items="${inventorys}" var="inventory" varStatus="loop">
                <tr>
                    <td><c:out value="${inventory.getInventoryPosition()}" /></td>
                    <td><c:out value="${inventory.getItem().getItemName()}" /></td>
                    <td><c:out value="${inventory.getItemQuantity()}" /></td>
                    <td><c:out value="${inventory.getItem().getMaxStackSize()}" /></td>
                    <td><c:out value="${inventory.getItem().getItemLevel()}" /></td>
                    <td><c:out value="${inventory.getItem().getIsSellable()}" /></td>
                    <td><c:out value="${inventory.getItem().getUnitPrice()}" /></td>
                    <td><c:out value="${itemCategorys[loop.index]}" /></td>
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
