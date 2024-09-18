<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Weapon Detail</title>
</head>
<body>
	<h1>${messages.title}</h1>
        <table border="1">
            <tr>
                <th>Name</th>
                <th>ItemLevel</th>
                <th>IsSellable</th>
                <th>UnitPrice</th>
                <th>PhysicalDamage</th>
                <th>AutoAttack</th>
                <th>Delay</th>
                <!-- <th>OtherAttribute</th> -->
<!--                 <th>JobRequiredLevel</th>
                <th>CurrentJob</th> -->
                <th>JobRequiredLevel</th>
                <th>UpdateUnitPriceAndIsSellable</th>
            </tr>
            <tr>
                    <td><c:out value="${weapons.getItemName()}" /></td>
                    <td><c:out value="${weapons.getItemLevel()}" /></td>
                    <td><c:out value="${weapons.getIsSellable()}" /></td>
                    <td><c:out value="${weapons.getUnitPrice()}" /></td>
                    <td><c:out value="${weapons.getPhysicalDamage()}" /></td>
                    <td><c:out value="${weapons.getAutoAttack()}" /></td>
                    <td><c:out value="${weapons.getDelay()}" /></td>
                    <td><c:out value="${weapons.getRequiredLevel()}" /></td>
                    <td><a href="updateunitpriceandissellable?itemID=<c:out value="${weapons.getItemID()}"/>">Update</a></td>
            </tr>
                    
                 
       </table>
</body>
</html>
