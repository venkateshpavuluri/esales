<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Permissions</title>
<link rel="stylesheet" href="js/jqueryui.css" />
<link rel="stylesheet" href="css/jquery-ui.css" />
<link rel="stylesheet"
	href="js/jquery-ui-1.10.3/themes/base/jquery-ui.css" />
<script src="js/jquery-1.7.2.js"></script>
<script src="js/jquery-ui.js"></script>
<script type="text/javascript" src="js/pagination.js"></script>
<script type="text/javascript" src="js/jquery.validate.min.js"></script>
<script type="text/javascript" src="js/MntValidator.js"></script>
<script>
function formSubmition()
{
	$("#clientsubmit").submit();
}



function checkAllFields(id,count,elem)
{

	var val=$("#"+id).val();
/* var roleName=$("#roleName").val();
alert(roleName); */
	 if (elem.checked)
	    {
		
	    	for( var i=1;i<=count;i++)
	    		{
	    		$("#"+id+""+i).attr("checked",true);
	    		}
	    }
	    else
	    {
	    	for( var i=1;i<=count;i++)
	    		{
	    		$("#"+id+""+i).attr("checked",false);
	    		}
	    }
}
</script>
</head>
<body>

<!-- SET: WRAPPER -->


	<div class="wrapper">

		<!-- SET: CONTAINER -->
		<div class="container">
	
<div class="main_content">

    <div class="block">
        		
        <h2><span class="icon1">&nbsp;</span>Select Client</h2>
        	<form:form action="permissionsClient.htm" id="clientsubmit" commandName="permissionsCmd" class="form-horizontal"  method="post">
					
					 <div class="block-box-small">
					   <div class="block-input">
							<c:set value="mntClient" var="clientIds"/>
						<c:choose>
						<c:when test="${sessionScope.clientNames eq clientIds }">
					<label><spring:message code="label.client"/></label>  
						<form:select path="clientId" id="clientId" onchange="formSubmition()" cssClass="field size3">
						<form:option value=" ">--Select--</form:option>
						<form:options items="${clientDetails}"/>
						</form:select> 
						</c:when>
						<c:otherwise>
					<label><spring:message code="label.client"/></label> 
					<form:select path="clientId" disabled="true" id="clientId" onchange="formSubmition()" cssClass="field size3">
						<form:option value=" ">--Select--</form:option>
						<form:options items="${clientDetails}"/>
						</form:select> 
						</c:otherwise>
						</c:choose>
						</div>
				</form:form>
           
    </div>
    	 <div class="block-footer">
                	<c:forEach var="success"
									items="${param.AddSus}">
									    <aside class="block-footer-left sucessfully">
									<div class="alert-success">
									 
										<strong><spring:message code="label.success" /></strong>
									 <spring:message code="label.permissions"/>
										<spring:message code="label.saveSuccess" />
									</div></aside>
								</c:forEach> <c:forEach var="fail" items="${param.AddFail}">
								   <aside class="block-footer-left sucessfully">
									<div class="alert-danger">
									  
										<strong><spring:message code="label.error" /> </strong>
										<spring:message code="label.permissions"/>
										<spring:message code="label.saveFail" /> 
									</div></aside>
								</c:forEach>
            </div></div>
    	
    	<div class="block table-toop-space">
        <div ><h2><span class="icon2">&nbsp;</span>Permissions</h2>       
</div>
            	<form:form action="permissionsSave.htm" commandName="permissionsCmd" method="post">
            	<div class="block-box-big block-box-last-big">
							<c:choose>
							<c:when test="${not empty listOfUsers}">
									<input type="hidden" id="roleName" name="roleName" value="${sessionScope.admin}">
								  <ul class="table-list">
							 <li class="user-fname-box">Users
						 <ul>
                        <li><a class="top" href="#">&nbsp;</a></li>
                        <li><a class="bottom" href="#">&nbsp;</a></li>
                    </ul></li>
				            <li class="six-box">Role </li>
				             <c:set value="0" var="allBrachesCount"/>
							<c:forEach items="${listOfBranches}"  var="branches">
							     <c:set value="${allBrachesCount+1}" var="allBrachesCount"/>
							<li class="six-box"><c:out value="${branches.branchName}"></c:out></li>
							</c:forEach>
							<li class="eleven-box last"><spring:message code="label.checkoruncheck"/> </li>
					</ul>
					<div class="table-list-blk data-grid-big">
							 <c:forEach var="editPermissionsk" items="${editPermissions}">
							<ul>
							 <li class="user-fname-box"><c:out value="${editPermissionsk.key.userName} "></c:out> </li>
							 <li class="six-box">${editPermissionsk.key.roleName} </li>
							 	 <c:set var="count" value="0"></c:set>
							 	 	 <c:set value="0" var="brasCheckedCount"/>
							 <c:forEach items="${listOfBranches}" var="branchesList">
							 	 <c:set var="count" value="${count+1 }"></c:set>
							 	  <c:forEach items="${editPermissionsk.value}" var="permEdit">
							 	  <c:set var="branchCheck" value="${branchesList.branchId}check"></c:set>
							 	   <c:set var="branchUncheck" value="${branchesList.branchId}uncheck"></c:set>
							 	  <c:choose>
							 	  <c:when test="${permEdit.userId eq editPermissionsk.key.userId and permEdit.branchId eq branchCheck}">
							 	  	 <c:set value="${brasCheckedCount+1}" var="brasCheckedCount"/>
							 	   <li class="six-box"><input type="checkbox" class="checkbox" checked="checked" id="${editPermissionsk.key.clientUserId}${count}" name="${editPermissionsk.key.clientUserId}${branchesList.branchId}"> </li>
							 	  </c:when>
							 	  <c:otherwise>
							 	  <c:choose>
							 	   <c:when test="${permEdit.userId eq editPermissionsk.key.userId and permEdit.branchId eq branchUncheck}">
							 	   <li class="six-box"><input type="checkbox" class="checkbox"  id="${editPermissionsk.key.clientUserId}${count}" name="${editPermissionsk.key.clientUserId}${branchesList.branchId}"> </li>
							 	  </c:when>
							 	  </c:choose>
							 	  </c:otherwise>
							 	  </c:choose> 
							  </c:forEach>
							 </c:forEach>
							 <c:choose>
							 <c:when test="${allBrachesCount eq brasCheckedCount }">
							 <li class="six-box"><input type="checkbox" class="checkbox" checked="checked" id="${editPermissionsk.key.clientUserId}" name="${editPermissionsk.key.clientUserId}${branchesList.branchId}"  onchange="checkAllFields(this.id,${count},this)" > </li> 
							 </c:when>
							 <c:otherwise>
							 <li class="six-box"><input type="checkbox" class="checkbox" id="${editPermissionsk.key.clientUserId}" name="${editPermissionsk.key.clientUserId}${branchesList.branchId}"  onchange="checkAllFields(this.id,${count},this)" > </li> 
							 </c:otherwise>
							 </c:choose>
							 </ul>
	 </c:forEach>	
	 
	  </div>

							<!-- <div class="buttons"> 
							<input type="submit" value="Submit" class="button"></div> -->
						</c:when></c:choose>	
								 <c:if test="${empty listOfUsers }">
		<div> 
						<label> There is no Permissions per this client</label>
						</div>
					</c:if> 
				
					</div>
						  <div class="block-footer">
            <aside class="block-footer-left"></aside>
            <aside class="block-footer-right">
                <input class="btn-cancel" name="" value="Cancel" type="button">
                <input class="btn-save" name="" value="Save" type="submit">
            </aside>
        </div>
						</form:form>
					
	
        </div>
        
    </div>
</div>
</div>




	<%-- <div class="shell">
		<!-- Main -->
		<div id="main">
			<div class="cl">&nbsp;</div>
			<!-- Content -->
			<div id="content">
				<!-- Box -->
					<!-- End Box Head --><div class="box">
					 	<div class="box-head">
							<h2><spring:message code="label.selectClient"/> </h2>
						</div>
							<div class="form">
						<form:form action="permissionsClient.htm" id="clientsubmit" commandName="permissionsCmd" method="post">
						<table>
						<tr>
							<c:set value="mntClient" var="clientIds"/>
						<c:choose>
						<c:when test="${sessionScope.clientNames eq clientIds }">
						<td><label><spring:message code="label.client"/></label>  </td>
						<td><form:select path="clientId" id="clientId" onchange="formSubmition()" cssClass="field size3">
						<form:option value=" ">--Select--</form:option>
						<form:options items="${clientDetails}"/>
						</form:select> </td>
						</c:when>
						<c:otherwise>
						<td><label><spring:message code="label.client"/></label>  </td>
						<td><form:select path="clientId" disabled="true" id="clientId" onchange="formSubmition()" cssClass="field size3">
						<form:option value=" ">--Select--</form:option>
						<form:options items="${clientDetails}"/>
						</form:select> </td>
						</c:otherwise>
						</c:choose>
						</tr>
						</table>
						<table>
						<tr>
							<td colspan="2"></td>
							<td colspan="2"><c:forEach var="success"
									items="${param.AddSus}">
									<div class="alert-success">
										<strong><spring:message code="label.success" /></strong>
									 <spring:message code="label.permissions"/>
										<spring:message code="label.saveSuccess" />
									</div>
								</c:forEach> <c:forEach var="fail" items="${param.AddFail}">
									<div class="alert-danger">
										<strong><spring:message code="label.error" /> </strong>
										<spring:message code="label.permissions"/>
										<spring:message code="label.saveFail" />
									</div>
								</c:forEach></td>
						</tr>
					</table>
						</form:form>
							 </div></div>
						<!-- Box Head -->
							<div class="box-head">
							<h2 class="left">
								<spring:message code="label.permissions" />
							</h2>
						</div>
					<!-- Second tag -->
						<div class="table">
						<form:form action="permissionsSave.htm" commandName="permissionsCmd" method="post">
							<input type="hidden" id="roleName" name="roleName" value="${sessionScope.admin}">
							<c:choose>
							<c:when test="${not empty listOfUsers}">
							<table class="table">
							<tr><th><spring:message code="label.users"/> </th> 
				             <th>Role </th>
				             <c:set value="0" var="allBrachesCount"/>
							<c:forEach items="${listOfBranches}"  var="branches">
							     <c:set value="${allBrachesCount+1}" var="allBrachesCount"/>
							<th><c:out value="${branches.branchName}"></c:out></th>
							</c:forEach>
							<th><spring:message code="label.checkoruncheck"/> </th>
							 </tr>
							 <c:forEach var="editPermissionsk" items="${editPermissions}">
							 <tr>
							 <td><c:out value="${editPermissionsk.key.userName} "></c:out> </td>
							 <td>${editPermissionsk.key.roleName} </td>
							 	 <c:set var="count" value="0"></c:set>
							 	 	 <c:set value="0" var="brasCheckedCount"/>
							 <c:forEach items="${listOfBranches}" var="branchesList">
							 	 <c:set var="count" value="${count+1 }"></c:set>
							 	  <c:forEach items="${editPermissionsk.value}" var="permEdit">
							 	  <c:set var="branchCheck" value="${branchesList.branchId}check"></c:set>
							 	   <c:set var="branchUncheck" value="${branchesList.branchId}uncheck"></c:set>
							 	  <c:choose>
							 	  <c:when test="${permEdit.userId eq editPermissionsk.key.userId and permEdit.branchId eq branchCheck}">
							 	  	 <c:set value="${brasCheckedCount+1}" var="brasCheckedCount"/>
							 	   <td><input type="checkbox" class="checkbox" checked="checked" id="${editPermissionsk.key.clientUserId}${count}" name="${editPermissionsk.key.clientUserId}${branchesList.branchId}"> </td>
							 	  </c:when>
							 	  <c:otherwise>
							 	  <c:choose>
							 	   <c:when test="${permEdit.userId eq editPermissionsk.key.userId and permEdit.branchId eq branchUncheck}">
							 	   <td><input type="checkbox" class="checkbox"  id="${editPermissionsk.key.clientUserId}${count}" name="${editPermissionsk.key.clientUserId}${branchesList.branchId}"> </td>
							 	  </c:when>
							 	  </c:choose>
							 	  </c:otherwise>
							 	  </c:choose> 
							  </c:forEach>
							 </c:forEach>
							 <c:choose>
							 <c:when test="${allBrachesCount eq brasCheckedCount }">
							 <td><input type="checkbox" class="checkbox" checked="checked" id="${editPermissionsk.key.clientUserId}" name="${editPermissionsk.key.clientUserId}${branchesList.branchId}"  onchange="checkAllFields(this.id,${count},this)" > </td> 
							 </c:when>
							 <c:otherwise>
							 <td><input type="checkbox" class="checkbox" id="${editPermissionsk.key.clientUserId}" name="${editPermissionsk.key.clientUserId}${branchesList.branchId}"  onchange="checkAllFields(this.id,${count},this)" > </td> 
							 </c:otherwise>
							 </c:choose>
							   
	 </c:forEach>
</table>
							<div class="buttons"> 
							<input type="submit" value="Submit" class="button"></div>
						</c:when></c:choose>	
							<c:if test="${empty listOfUsers }">
			<div class="form"> 
						<label> There is no Permissions per this client</label>
						</div>
					</c:if>
						</form:form>
						</div>
							 </div> 
					<!-- End Box -->
			</div>
			<!-- End Content -->
			<div class="cl">&nbsp;</div>
		</div> --%>
		<!-- Main -->

</body>
</html>