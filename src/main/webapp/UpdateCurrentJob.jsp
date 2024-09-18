<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Change Job Related Items</title>
</head>
<body>
	<h1>Qualified Weapons for the Job Change</h1>
        <table border="1">
            <tr>
                <th>Name</th>
                <th>ItemLevel</th>
                <th>IsSellable</th>
                <th>UnitPrice</th>
                <th>PhysicalDamage</th>
                <th>AutoAttack</th>
                <th>Delay</th>
                <th>JobRequiredLevel</th>
                <th>isCurrentWeapon</th>
                <th>Change</th>
            </tr>
            <c:forEach items="${weapons}" var="weapon" varStatus="loop">
                <tr>
                    <td><c:out value="${weapon.getItemName()}" /></td>
                    <td><c:out value="${weapon.getItemLevel()}" /></td>
                    <td><c:out value="${weapon.getIsSellable()}" /></td>
                    <td><c:out value="${weapon.getUnitPrice()}" /></td>
                    <td><c:out value="${weapon.getPhysicalDamage()}" /></td>
                    <td><c:out value="${weapon.getAutoAttack()}" /></td>
                    <td><c:out value="${weapon.getDelay()}" /></td>
                    <td><c:out value="${weapon.getRequiredLevel()}" /></td>  
                    <td><c:out value="${isCurrentWeapon[loop.index]}" /></td>    
                    <td><a href="updatemainhanditem?mainHandItemID=<c:out value="${weapon.getItemID()}"/>&characterID=<c:out value="${character.getCharacterID()}"/>">Update</a></td> 
                    <!--characterID=<c:out value="${fn:escapeXml(param.characterID)}"/>  -->
                    
                </tr>
            </c:forEach>
       </table>
    <%-- <hr> </hr>
    <h1>Disable the Current Weapon</h1>
       <c:if test="${message.weapondisable == '1'}">
           <h3>Current Weapon can be used for New Job</h3>
       </c:if>
       <c:if test="${message.weapondisable == '0'}">
           <table border="1">
            <tr>
                <th>Name</th>
                <th>ItemLevel</th>
                <th>IsSellable</th>
                <th>UnitPrice</th>
                <th>PhysicalDamage</th>
                <th>AutoAttack</th>
                <th>Delay</th>
                <th>JobRequiredLevel</th>
             </tr>
             <tr>
                    <td><c:out value="${disableweapon.getItemName()}" /></td>
                    <td><c:out value="${disableweapon.getItemLevel()}" /></td>
                    <td><c:out value="${disableweapon.getIsSellable()}" /></td>
                    <td><c:out value="${disableweapon.getUnitPrice()}" /></td>
                    <td><c:out value="${disableweapon.getPhysicalDamage()}" /></td>
                    <td><c:out value="${disableweapon.getAutoAttack()}" /></td>
                    <td><c:out value="${disableweapon.getDelay()}" /></td>
                    <td><c:out value="${disableweapon.getRequiredLevel()}" /></td>  
             </tr>
            </table>  
       </c:if> --%>
    <hr> </hr>
    <h1>Qualified Gears for the Job Change</h1>
        <table border="1">
            <tr>
                <th>Name</th>
                <th>ItemLevel</th>
                <th>IsSellable</th>
                <th>UnitPrice</th>
                <th>SlotType</th>
                <th>JobRequiredLevel</th>
                <th>isCurrentGear</th>
                <th>Change</th>
            </tr>
            <c:forEach items="${gears}" var="gear" varStatus="loop">
                <tr>
                    <td><c:out value="${gear.getItemName()}" /></td>
                    <td><c:out value="${gear.getItemLevel()}" /></td>
                    <td><c:out value="${gear.getIsSellable()}" /></td>
                    <td><c:out value="${gear.getUnitPrice()}" /></td>
                    <td><c:out value="${gear.getSlotType()}" /></td>
                    <td><c:out value="${gear.getRequiredLevel()}" /></td>
                    <td><c:out value="${isCurrentGear[loop.index]}" /></td>    
                    <td><a href="updatecharactergear?slotType=<c:out value="${gear.getSlotType()}" />&gearID=<c:out value="${gear.getItemID()}"/>&characterID=<c:out value="${character.getCharacterID()}"/>">Update</a></td>
                     <!--characterID=<c:out value="${fn:escapeXml(param.characterID)}"/>  -->
                </tr>
            </c:forEach>
       </table>
	<%-- <hr> </hr>
    <h1>Disable the Current Gear</h1>
       <c:if test="${message.geardisable=='1'}">
           <h3>Current Gear can be used for New Job</h3>
       </c:if>
       <c:if test="${message.geardisable =='0'}">
           <table border="1">
            <tr>
                <th>Name</th>
                <th>ItemLevel</th>
                <th>IsSellable</th>
                <th>UnitPrice</th>
                <th>SlotType</th>
                <th>JobRequiredLevel</th>
            </tr>
            <c:forEach items="${disablegears}" var="disablegear" >
                <tr>
                    <td><c:out value="${disablegear.getItemName()}" /></td>
                    <td><c:out value="${disablegear.getItemLevel()}" /></td>
                    <td><c:out value="${disablegear.getIsSellable()}" /></td>
                    <td><c:out value="${disablegear.getUnitPrice()}" /></td>
                    <td><c:out value="${disablegear.getSlotType()}" /></td>
                    <td><c:out value="${disablegear.getRequiredLevel()}" /></td>
                    
                </tr>
            </c:forEach>
             
           </table>  
       </c:if> --%>
<%-- 	<form action="updatecurrentjob" method="post">
		<p>
			<label for="username">UserName</label>
			<input id="username" name="username" value="${fn:escapeXml(param.ch)}">
		</p>
		<p>
			<label for="lastname">New LastName</label>
			<input id="lastname" name="lastname" value="">
		</p>
		<p>
			<input type="submit">
		</p>
	</form> --%>
	<br/><br/>
	<p>
		<span id="successMessage"><b>${messages.success}</b></span>
	</p>
</body>
</html>