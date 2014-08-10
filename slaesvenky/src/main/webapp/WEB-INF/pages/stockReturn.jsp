<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Product</title>
<link rel="stylesheet" href="js/jqueryui.css" />
<link rel="stylesheet" href="css/jquery-ui.css" />
<link rel="stylesheet"
	href="js/jquery-ui-1.10.3/themes/base/jquery-ui.css" />
<script src="js/jquery-1.7.2.js"></script>
<script src="js/jquery-ui.js"></script>
<script type="text/javascript" src="js/jquery.validate.min.js"></script>
<script type="text/javascript" src="js/MntValidator.js"></script>

<script type="text/javascript">
function formSubmition()
{
	$("#addForm").submit();
	$('#userdata ul li').remove();
}
	
function validateStock(id)
{
	var total=$('#'+id+"total").val();
	var cu=$("#"+id).val();

if(parseFloat(total)<parseFloat(cu))
	{
	alert("Return stock value must be less than in stock");
	$("#"+id).val("0");
	}
	
	}
	
function getQuantity(id)
{
		evt = (evt) ? evt : window.event;
	    var charCode = (evt.which) ? evt.which : evt.keyCode;
	    if (charCode > 31 && (charCode < 48 || charCode > 57)) {
	    	alert("It allows only numbers");
	    	  return false;
	    };
				}
</script>
<style type="text/css">
.data-grid-big {
    height:85px;
}
.table-toop-space{
	margin-top:10px;	
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
					<h2>
						<span class="icon1">&nbsp;</span>Return Stock
					</h2>
					<form:form action="stockReturnSearch.htm" commandName="stockCmd"
						method="post" id="addForm" >
						<div class="block-box-exp">
							<div class="block-input">
								<c:set value="sales" var="clientIds" />
								<c:choose>
									<c:when test="${sessionScope.admin eq clientIds }">
									<label><spring:message code="label.branch" /></label>
										<form:select path="branchId" id="branchId" disabled="true"
											onchange="formSubmition(), clearGrid()" cssClass="field size3">
											<form:options items="${branchDeatails}" />
										</form:select>
										
									</c:when>
									<c:otherwise>
										<label><spring:message code="label.branch" /></label>
										<form:select path="branchId" id="branchId"
											onchange="formSubmition()" cssClass="field size3">
											<form:option value=" ">--Select--</form:option>
											<form:options items="${branchDeatails}" />
										</form:select>
									</c:otherwise>
								</c:choose>
							</div>

						</div>
						 <div class="block-footer">
							<aside class="block-footer-left sucessfully">

								<c:forEach var="success" items="${param.AddSus}">
									<div class="alert-success">
										<strong><spring:message code="label.success" /></strong>
										Stock Return
										<spring:message code="label.saveSuccess" />
									</div>
								</c:forEach>
								<c:forEach var="fail" items="${param.AddFail}">
									<div class="alert-danger">
										<strong><spring:message code="label.error" /> </strong> Stock
										Return
										<spring:message code="label.saveFail" />
									</div>
								</c:forEach>

							</aside>
							<%-- <aside class="block-footer-right">
								<input type="reset" class="btn-cancel"
									value="<spring:message code="label.clear"/>" /> <input
									type="submit" class="btn-save"
									value="<spring:message code="label.search"/>" id="submitId" />

							</aside> --%>
						</div> 
					</form:form>
				</div>
			</div>
			
			<div class="block table-toop-space">
				<div class="head-box">
					<h2>
						<span class="icon2">&nbsp;</span>Current Stock
					</h2>
				</div>
				<div class="">
					 <!--  <div class="block -box-big block-box-last-big">   -->
					 
						<form:form action="stockReturnSave.htm" commandName="stockCmd" method="post">
						
							<!--  <div class="block-box-big block-box-last-big">  -->
								<ul class="table-list">
									<li class="prod-name-box"><label><spring:message
												code="label.product" /></label>
										<ul>
											<li><a class="top" href="#">&nbsp;</a></li>
											<li><a class="bottom" href="#">&nbsp;</a></li>
										</ul></li>
									<li class="five-box"><label><spring:message
												code="label.inStock" /></label></li>
									<li class="five-box last"><label><spring:message
												code="label.returnStock" /></label></li>
								</ul>
								
									<c:choose>
										<c:when test="${not empty currentStockList}">
										<div id="userdata" class="table-list-blk data-grid-big">
											<ul>
												<c:forEach var="currentStockList"
													items="${currentStockList}">

													<li class="prod-name-box"><c:out
															value="${currentStockList.productName}"></c:out></li>
													<li class="five-box"><input type="text"
														value="${currentStockList.inStockQuantity}"
														id="${currentStockList.stockId}total" readonly="readonly"
														class="block-input input"></li>
													<li class="five-box last"><input type="text" 
														name="${currentStockList.stockId}${currentStockList.productId}"
														onkeyup="validateStock(this.id)"
														id="${currentStockList.stockId}"
														onkeyup="getQuantity(this.id)" ></li>
												</c:forEach>
											</ul>
											</div>
										</c:when>
										<c:otherwise>
											<div class="form">
												<label>Ther is no records for this branch </label>
											</div>
										</c:otherwise>
									 </c:choose>
								
								<div class="block-footer">
									<aside class="block-footer-right">
										<input type="submit" class="btn-save"
											value="<spring:message code="label.save"/>" id="submitId" />
									</aside>
								</div>
							 <!--  </div>  --> 
							
						</form:form>
					   <!-- </div>   --> 
				</div>
			</div>
		</div>
	</div>
	<!--  ---------------------------------from here----------------------------- -->
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
						<h2>
							<spring:message code="label.returnStock" />
						</h2>
					</div>
					<div class="form">
						<form:form action="stockReturnSearch.htm" commandName="stockCmd"
							method="post" id="branchsubmit">
							<table>
								<tr>
									<c:set value="clientadmin" var="clientIds" />
									<c:choose>
										<c:when test="${sessionScope.admin eq clientIds }">
											<td><label><spring:message code="label.branch" /></label>
											</td>
											<td><form:select path="branchId" id="branchId"
													onchange="formSubmition()" cssClass="field size3">
													<form:option value=" ">--Select--</form:option>
													<form:options items="${branchDeatails}" />
												</form:select></td>
										</c:when>
										<c:otherwise>
											<td><label><spring:message code="label.branch" /></label>
											</td>
											<td><form:select path="branchId" id="branchId"
													disabled="true" onchange="formSubmition()"
													cssClass="field size3">
													<form:options items="${branchDeatails}" />
												</form:select></td>
										</c:otherwise>
									</c:choose>

								</tr>
							</table>
							<table>
								<tr>
									<td colspan="2"><c:forEach var="success"
											items="${param.AddSus}">
											<div class="alert-success">
												<strong><spring:message code="label.success" /></strong>
												Stock Return
												<spring:message code="label.saveSuccess" />
											</div>
										</c:forEach></td>
									<td colspan="2"><c:forEach var="fail"
											items="${param.AddFail}">
											<div class="alert-danger">
												<strong><spring:message code="label.error" /> </strong>
												Stock Return
												<spring:message code="label.saveFail" />
											</div>
										</c:forEach></td>
								</tr>
							</table>
						</form:form>
					</div>
				</div>
				<!-- Box -->
				<div class="box">
					<!-- Box Head -->
					<div class="box-head">
						<h2 class="left">
							<spring:message code="label.currentStock" />
						</h2>
						<div class="right"></div>
					</div>
					<form:form action="stockReturnSave.htm" commandName="stockCmd"
						method="post">
						<c:choose>
							<c:when test="${not empty currentStockList}">
								<table class="table">
									<tr>
										<th><spring:message code="label.product" /></th>
										<th><spring:message code="label.inStock" /></th>
										<th><spring:message code="label.returnStock" /></th>
										</tr>
										<c:forEach var="currentStockList" items="${currentStockList}">
											<tr><td><c:out value="${currentStockList.productName}" ></c:out></td>
												<td><input type="text"	value="${currentStockList.inStockQuantity}"  id="${currentStockList.stockId}total" readonly="readonly"
													class="field size2"></td>
													<td><input type="text" name="${currentStockList.stockId}${currentStockList.productId}" onkeyup="validateStock(this.id)" id="${currentStockList.stockId}"
													class="field size2"></td>
													 </tr>
													
										</c:forEach>
									
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