<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>StockAllocation</title>
<link rel="stylesheet" href="js/jqueryui.css" />
<link rel="stylesheet" href="css/jquery-ui.css" />
<link rel="stylesheet"
	href="js/jquery-ui-1.10.3/themes/base/jquery-ui.css" />
<script src="js/jquery-1.7.2.js"></script>
<script src="js/jquery-ui.js"></script>
<script type="text/javascript" src="js/pagination.js"></script>
<script type="text/javascript" src="js/jquery.validate.min.js"></script>
<script type="text/javascript" src="js/MntValidator.js"></script>
<script type="text/javascript">
function formSubmition()
{
	$("#clientsubmit").submit();
}
	$(document)
			.ready(
					function() {
						$('#addName').val('');

						//AddForm Validations
						$('#submitId').click(function(event) {$('#addForm').validate(
															{
																rules : {
																	deptName : {
																		required : true,
																		alphanumeric : true,
																		specialcharacters : true
																	}
																},
																messages : {
																	deptName : {
																		required : "<font style='color: red;'><b>Department is Required</b></font>",
																		alphanumeric : "<font style='color: red;'><b>First letter should be an alphanumeric.</b></font>",
																		specialcharacters : "<font style='color: red;'><b>Special Characters except &,_ are not allowed </b></font>"
																	}
																},
															});
										});

						$('#updateId')
								.click(
										function(event) {
											$('#editForm')
													.validate(
															{
																rules : {
																	productName : {
																		required : true,
																		alphanumeric : true,
																		specialcharacters : true
																	}
																},
																messages : {
																	productName : {
																		required : "<font style='color: red;'><b>Department is Required</b></font>",
																		alphanumeric : "<font style='color: red;'><b>First letter should be an alphanumeric.</b></font>",
																		specialcharacters : "<font style='color: red;'><b>Special Characters except &,_ are not allowed </b></font>"
																	}
																},
															});
										});
					});
	function getQuantity(count,branchcount,evt)
{
		evt = (evt) ? evt : window.event;
	    var charCode = (evt.which) ? evt.which : evt.keyCode;
	    if (charCode > 31 && (charCode < 48 || charCode > 57)) {
	    	alert("It allows only numbers");
	    	$("#"+branchcount).val(0);
	        return false;
	    }
		var totalBranches=$("#branchCount").val();
		var totalBranchesQty=0;
		for(var i=1;i<=totalBranches;i++)
			{
			var pbqty=$("#"+count+""+i+'branch').val();
			if(pbqty=="")
				{
				pbqty=0;
				}
			totalBranchesQty=parseFloat(totalBranchesQty)+parseFloat(pbqty);
			}
		var totQuty=$("#"+count+'stock').val();
	var remainingQty=parseFloat(totQuty)-parseFloat(totalBranchesQty);
	if(remainingQty<0)
		{
		alert("Branch Quantity must be lessthan or equal to Total quantity");
		$("#"+branchcount).val(0);
		}
		}
	
	function clearAllBranches(id)
	{
		var totalBranches=$("#branchCount").val();
		for(var i=1;i<=totalBranches;i++)
			{
			$("#"+id+""+i+'branch').val('');
			}
	}
	function animaterow(id)
	{
		$('#'+id).attr("style","font-size:2px; color:red");
	//$('#'+id+'ani').attr("style","color:red");
	 	  $('#'+id+'ani').addClass('focus'); 
	}
	function deanimateRow(id)
	{
		$('#'+id).removeAttr("style");
		$('#'+id+'ani').removeAttr("style");
	}
</script>
<style>
.fading:hover {
    color: blue;
    text-decoration:underline
}
.focus {
    border: 2px solid #AA88FF;
    background-color: #FFEEAA;
}
</style>
</head>
<body>


<!-- SET: WRAPPER -->


	<div class="wrapper">

		<!-- SET: CONTAINER -->
		<div class="container">

<div class="main_content">

    <div class="block">
        		
        <h2><span class="icon1">&nbsp;</span>Select Client</h2>
      	<form:form action="stockAllocAdd.htm" commandName="stockAlloc"  method="post" id="clientsubmit"  class="form-horizontal">
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
						<form:select path="clientId" id="clientId" disabled="true" onchange="formSubmition()" cssClass="field size3">
						<form:option value=" ">--Select--</form:option>
						<form:options items="${clientDetails}"/>
						</form:select> 
						</c:otherwise>
						</c:choose>
						</div>
				</form:form>
           
    </div>
    	 <div class="block-footer">
								<c:forEach var="success" items="${param.AddSus}">
								   <aside class="block-footer-left sucessfully">
												<div class="alert-success" >
													<strong><spring:message code="label.success" /></strong>
														 Stock Allocation 
													<spring:message code="label.saveSuccess" />
												</div></aside>
											</c:forEach>
								
								<c:forEach var="fail" items="${param.AddFail}">
								<aside class="block-footer-left sucessfully">
												<div class="alert-danger">
													<strong><spring:message code="label.error" /> </strong>
												Stock Allocation 
													<spring:message code="label.saveFail" />
												</div></aside>
											</c:forEach>
												<c:forEach var="fail" items="${param.stockValidate}">
								<aside class="block-footer-left sucessfully">
												<div class="alert-danger">
													<strong><spring:message code="label.error" />
											Please Enter Stock
												</div></aside>
											</c:forEach>
            </div></div>
    	
    	<div class="block table-toop-space">
        <div ><h2><span class="icon2">&nbsp;</span>Stock Allocation</h2>       
</div>

            						<form:form action="stockAllocSave.htm" commandName="stockAlloc" method="post">
                     	    <div class="block-box-big block-box-last-big">
                <c:choose>
					<c:when test="${not empty stocAllocMap}">
			<ul class="table-list">
				<li class="stock-pcode-box"><spring:message code="label.productCode"/> 
				  <ul>
                        <li><a class="top" href="#">&nbsp;</a></li>
                        <li><a class="bottom" href="#">&nbsp;</a></li>
                    </ul>
				</li> 
				<li class="stock-product-box"><spring:message code="label.product"/> </li>
				<li class="stock-alloc-brnch-box"><spring:message code="label.inStock"/> - <spring:message code="label.mrp"></spring:message> </li>
				<c:set value="0" var="totalbranchs" />
				<c:forEach var="stForBrnches"  items="${stockForBranches}" varStatus="loop">
				<c:set value="${totalbranchs+1}" var="totalbranchs"/>
				<c:choose>
				<c:when test="${loop.last}">
					<li class="stock-alloc-brnch-box last"><c:out value="${stForBrnches.branchName}"></c:out> </li>
				</c:when>
				<c:otherwise>
					<li class="stock-alloc-brnch-box"><c:out value="${stForBrnches.branchName}"></c:out> </li>
				</c:otherwise>
				</c:choose>
				</c:forEach>
				</ul>
				<input type="hidden" id="branchCount" value="${totalbranchs}"/>
					<div  id="stock-alloca" class="table-list-blk data-grid-big">
				<c:forEach var="stocAllocMaps" items="${stocAllocMap}" varStatus="loop">
		
			<ul id="${stocAllocMaps.key.stockId}">
			  <li class="stock-pcode-box" onmouseover="animaterowi(this.title)" title="${stocAllocMaps.key.stockId}" id="${stocAllocMaps.key.stockId}ani"  onmouseout="deanimateRowi(this.title)">${stocAllocMaps.key.productId} </li>
			<li  class="stock-product-box"><c:out value=" ${stocAllocMaps.key.categoryName} ${stocAllocMaps.key.subCategoryName} ${stocAllocMaps.key.productName}"></c:out></li>
			<li class="stock-alloc-brnch-box"><input type="text" value="${stocAllocMaps.key.quantity}" readonly="readonly" >  <input type="hidden" value="${stocAllocMaps.key.totInStock}" name="${stocAllocMaps.key.stockId}" readonly="readonly" id="${stocAllocMaps.key.stockId}stock"> <input type="text" readonly="readonly" value="${stocAllocMaps.key.mrp}"/> </li>
	         <c:set value="0" var="countbrans"></c:set>
	         <c:set value=" " var="cc"></c:set>
					<c:forEach var="editBranches" varStatus="loop" items="${stocAllocMaps.value}">
				
					<c:set value="0" var="mrp"></c:set>
					<c:choose>
					<c:when test="${stocAllocMaps.key.stockId eq editBranches.stockId}">
					<c:choose>
					<c:when test="${editBranches.mrp!=0}">
					<c:set value="${countbrans+1}" var="countbrans"></c:set>
					<c:choose>
					<c:when test="${countbrans==totalbranchs}">
					<c:set value="0" var="countbrans"></c:set>
						<li class="stock-alloc-brnch-box last"><input type="text" title="${stocAllocMaps.key.stockId}" id="${stocAllocMaps.key.stockId}${editBranches.bransCount}branch" onkeyup="return getQuantity(this.title,this.id,event)" name="${stocAllocMaps.key.stockId}${editBranches.branchId}qty" value="${editBranches.quantity}" />
						<input type="text"   name="${stocAllocMaps.key.stockId}${editBranches.branchId}mrp" value="${editBranches.mrp}"/>
					 </li>
					</c:when>
					<c:otherwise>
						<li class="stock-alloc-brnch-box"><input type="text" title="${stocAllocMaps.key.stockId}" id="${stocAllocMaps.key.stockId}${editBranches.bransCount}branch" onkeyup="return getQuantity(this.title,this.id,event)" name="${stocAllocMaps.key.stockId}${editBranches.branchId}qty" value="${editBranches.quantity}" />
						<input type="text"  name="${stocAllocMaps.key.stockId}${editBranches.branchId}mrp" value="${editBranches.mrp}"/>
					 </li>
					</c:otherwise>
					</c:choose>
					</c:when>
					<c:otherwise>
					<c:set value="${countbrans+1}" var="countbrans"></c:set>
					<c:choose>
					<c:when test="${countbrans==totalbranchs}">
					<c:set value="0" var="countbrans"></c:set>
						<li class="stock-alloc-brnch-box last"><input type="text" title="${stocAllocMaps.key.stockId}" id="${stocAllocMaps.key.stockId}${editBranches.bransCount}branch" onkeyup="return getQuantity(this.title,this.id,event)" name="${stocAllocMaps.key.stockId}${editBranches.branchId}qty" value="${editBranches.quantity}"/>
					<input type="text"   name="${stocAllocMaps.key.stockId}${editBranches.branchId}mrp" value="${stocAllocMaps.key.mrp}"/>
					</li>
					</c:when>
					<c:otherwise>
						<li class="stock-alloc-brnch-box"><input type="text" title="${stocAllocMaps.key.stockId}" id="${stocAllocMaps.key.stockId}${editBranches.bransCount}branch" onkeyup="return getQuantity(this.title,this.id,event)" name="${stocAllocMaps.key.stockId}${editBranches.branchId}qty" value="${editBranches.quantity}" />
					<input type="text"   name="${stocAllocMaps.key.stockId}${editBranches.branchId}mrp" value="${stocAllocMaps.key.mrp}"/>
					</li>
					</c:otherwise>
					</c:choose>
					</c:otherwise>
					</c:choose>
					</c:when>
					</c:choose>
				</c:forEach>
				</ul>
					</c:forEach></div>
					
					</c:when>
					<c:otherwise>
						<div class="form"> 
						<label>Ther is no records for this client </label>
						</div>
					</c:otherwise>
					</c:choose>   
					</div>
					
						  <div class="block-footer">
            <aside class="block-footer-left"></aside>
            <aside class="block-footer-right">
                <input class="btn-cancel" name="" value="Cancel" type="button">
                <input class="btn-save" name="" value="Submit" type="submit">
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
			
						<!-- End Box Head -->
						<div class="box">
	<div class="box-head">
<h2><spring:message code="label.selectClient"/></h2>
	 </div>
	 <div class="form">
					<form:form action="stockAllocAdd.htm" commandName="stockAlloc"
							method="post" id="clientsubmit">
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
						<td><form:select path="clientId" id="clientId" disabled="true" onchange="formSubmition()" cssClass="field size3">
						<form:option value=" ">--Select--</form:option>
						<form:options items="${clientDetails}"/>
						</form:select> </td>
						</c:otherwise>
						</c:choose>
						
						</tr>
						</table>
								<table>
									<tr>
										<td colspan="2"><c:forEach var="success"
												items="${param.AddSus}">
												<div class="alert-success" >
													<strong><spring:message code="label.success" /></strong>
														 Stock Allocation 
													<spring:message code="label.saveSuccess" />
												</div>
											</c:forEach> </td>
										<td colspan="2"><c:forEach var="fail" items="${param.AddFail}">
												<div class="alert-danger">
													<strong><spring:message code="label.error" /> </strong>
												Stock Allocation 
													<spring:message code="label.saveFail" />
												</div>
											</c:forEach></td>
									</tr>
								</table>
						</form:form>	</div></div>
					<!-- Box -->
					<div class="pagingbox">
						<!-- Box Head -->
						<div class="box-head">
							<h2 class="left"> <spring:message code="label.currentStock"/> </h2>
							<div class="right">
							</div>
						</div>
					<form:form action="stockAllocSave.htm" commandName="stockAlloc" method="post">
					<c:choose>
					<c:when test="${not empty stocAllocMap}">
					<table class="table">
				<tr><th><spring:message code="label.product"/> </th> 
				<th><spring:message code="label.productCode"/> </th>
				<th><spring:message code="label.inStock"/> </th>
				<c:set value="0" var="totalbranchs" />
				<c:forEach var="stForBrnches"  items="${stockForBranches}">
				<c:set value="${totalbranchs+1}" var="totalbranchs"/>
				<th><c:out value="${stForBrnches.branchName}"></c:out> </th>
				</c:forEach>
				<input type="hidden" id="branchCount" value="${totalbranchs}"/>
				<c:forEach var="stocAllocMaps" items="${stocAllocMap}">
			<tr id="${stocAllocMaps.key.stockId}">
			<td><c:out value=" ${stocAllocMaps.key.categoryName} ${stocAllocMaps.key.subCategoryName} ${stocAllocMaps.key.productName}"></c:out></td>
		    <td class="fading" onmouseover="animaterow(this.title)" title="${stocAllocMaps.key.stockId}" id="${stocAllocMaps.key.stockId}ani"  onmouseout="deanimateRow(this.title)"><input type="text" value="${stocAllocMaps.key.productCode}" readonly="readonly"  class="field size4"> </td>
			<td><input type="text" value="${stocAllocMaps.key.quantity}" readonly="readonly" class="field size2">  <input type="hidden" value="${stocAllocMaps.key.totInStock}" name="${stocAllocMaps.key.stockId}" readonly="readonly" id="${stocAllocMaps.key.stockId}stock"> <input type="text" readonly="readonly" class="field size2" value="${stocAllocMaps.key.mrp}"/> </td>
	         <c:set value="0" var="countbrans"></c:set>
	         <c:set value=" " var="cc"></c:set>
					<c:forEach var="editBranches" varStatus="h" items="${stocAllocMaps.value}">
					<c:set value="${countbrans+1}" var="countbrans"></c:set>
					<c:set value="0" var="mrp"></c:set>
					<c:choose>
					<c:when test="${stocAllocMaps.key.stockId eq editBranches.stockId}">
					<c:choose>
					<c:when test="${editBranches.mrp!=0}">
					<td><input type="text" title="${stocAllocMaps.key.stockId}" id="${stocAllocMaps.key.stockId}${editBranches.bransCount}branch" onkeyup="return getQuantity(this.title,this.id,event)" name="${stocAllocMaps.key.stockId}${editBranches.branchId}qty" value="${editBranches.quantity}" class="field size2" />
						<input type="text"  class="field size2" name="${stocAllocMaps.key.stockId}${editBranches.branchId}mrp" value="${editBranches.mrp}"/>
					 </td>
					</c:when>
					<c:otherwise>
					<td><input type="text" title="${stocAllocMaps.key.stockId}" id="${stocAllocMaps.key.stockId}${editBranches.bransCount}branch" onkeyup="return getQuantity(this.title,this.id,event)" name="${stocAllocMaps.key.stockId}${editBranches.branchId}qty" value="${editBranches.quantity}" class="field size2" />
					<input type="text"  class="field size2" name="${stocAllocMaps.key.stockId}${editBranches.branchId}mrp" value="${stocAllocMaps.key.mrp}"/>
					</td>
					</c:otherwise>
					</c:choose>
					</c:when>
					</c:choose>
				</c:forEach>
					</c:forEach>
					</tr>
					</table>
					<div class="buttons">
						<input type="submit" value="Submit" class="button">
						</div>
					</c:when>
					<c:otherwise>
						<div class="form"> 
						<label>Ther is no records for this client </label>
						</div>
					</c:otherwise>
					</c:choose>
					
					</form:form>
					</div>
					<!-- End Box -->
			</div>
			<!-- End Content -->
			<div class="cl">&nbsp;</div>
		</div>
		<!-- Main -->
	</div> --%>
</body>
</html>