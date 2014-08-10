<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Billing</title>
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
$(document).ready(function() {
    //this calculates values automatically 
	$("#saveId").on("click", function () {
			
          $('#billingForm').attr('action', "<c:url value='/saveBill.htm'/>");
          $("#billingForm").submit();
          e.preventDefault();
      });

    $("#totalPayment").on("keydown keyup", function() {
        calculateSum();
    });
});

function calculateSum() {
    var sum = 0;
    //iterate through each textboxes and add the values
 	var totalAmount=$( "#netAmt" ).val();
 	//var totalAmount=1000.263;
        //add only if the value is number
       
        $("#totalPayment").each(function() {
        	
        if (!isNaN(this.value) && this.value.length != 0) {
            sum =parseFloat(this.value)-parseFloat(totalAmount);
        }
        else if (this.value.length != 0){   
        }
        });
 		
	$("#returnChange").val(sum.toFixed(2));
}


</script>
<style type="text/css">
	.table tbody{
	overflow-x:scroll;
	height:100%;
	background-color: blue;
	}
</style>
<script type="text/javascript">
	$(function() {
		$("#bdate,#edate").datepicker({
			changeDate : true,
			changeMonth : true,
			changeYear : true,
			showButtonPanel : false,
			dateFormat : 'dd-mm-yy'
		});
	});
</script>
<script type="text/javascript">
  $(document)
          .ready(
                  function() {
                  //AddForm Validations
                    $('#searchId')
                            .click(
                                    function(event) {
                                      //alert("hai");
                                      $('#searchForm')
                                              .validate(
                                                      {
                                                        rules: {
                                                        	productId: {
                                                            required: true,
                                                          },
                                                          cost: {
                                                            required: true,
                                                            /* alphanumeric: true,
                                                            specialcharacters: true */
                                                          },
                                                          quantity: {
                                                              required: true,
                                                            },
                                                            

                                                        },
                                                        messages: {
                                                        	productId: {
                                                            required: "<font style='color: red;'><b>ProductId is Required</b></font>",
                                                          },
                                                          cost: {
                                                            required: "<font style='color: red;'><b>Cost is Required</b></font>",
                                                            /* alphanumeric: "<font style='color: red;'><b>First letter should be an alphanumeric.</b></font>",
                                                            specialcharacters: "<font style='color: red;'><b>Special Characters except &,_ are not allowed </b></font>",
                                                        */   },
                                                        quantity: {
                                                            required: "<font style='color: red;'><b>Quantity is Required</b></font>",
                                                          },
                                                         
                                                        },

                                                      });
                                    });
                    $('#submitId')
                    .click(
                            function(event) {
                              //alert("hai");
                              $('#addForm')
                                      .validate(
                                              {
                                                rules: {
                                                	
                                                    paidbyCheque:{
                                                    	required: true,
                                                    },
                                                    paidbyCard:{
                                                    	required:true,
                                                    },
                                                    paidbyCash:{
                                                    	required:true,
                                                    },
                                                    chequeNo:{
                                                    	required:true,
                                                    },
                                                    cardNo:{
                                                    	required:true,
                                                    },
                                                    returnChange:{
                                                    	required:true,
                                                    },
                                                    mobileNo:{
                                                    	required:true,
                                                    },
                                                    
                                                },
                                                messages: {
                                                	
                                                  paidbyCheque: {
                                                      required: "<font style='color: red;'><b>Cheque is Required</b></font>",
                                                    },
                                                    paidbyCard: {
                                                        required: "<font style='color: red;'><b>Card is Required</b></font>",
                                                      },
                                                      paidbyCash: {
                                                          required: "<font style='color: red;'><b>Cash is Required</b></font>",
                                                        },
                                                        chequeNo: {
                                                            required: "<font style='color: red;'><b>ChequeNo is Required</b></font>",
                                                          },
                                                          cardNo: {
                                                              required: "<font style='color: red;'><b>CardNo is Required</b></font>",
                                                            },
                                                            returnChange: {
                                                                required: "<font style='color: red;'><b>Return Change is Required</b></font>",
                                                              },
                                                              mobileNo: {
                                                                  required: "<font style='color: red;'><b>Mobile Number is Required</b></font>",
                                                                },
                                                },
                                              });
                            });
                  });
</script>
<script>
  $(document).ready(function() {
	  
	  
    $('#submitId').click(function() {
      $('#msgInfo').hide();
      $('#editId').hide();
       
    });
   
</script>
<script>
function printDiv(divName) {
    var printContents = document.getElementById(divName).innerHTML;
    var originalContents = document.body.innerHTML;

    document.body.innerHTML = printContents;

    window.print();

    document.body.innerHTML = originalContents;
}

</script>
</head>
<body>
	<div class="wrapper">
		<div class="container">
			<div class="main_content">

				<c:if test="${empty billEdit}">
					<form:form action="getBillDetails.htm" commandName="getBillCmd"
						method="POST" id="billingForm" name="cf_form"
						class="form-horizontal">
						<div class="block">
							<h2>
								<span class="icon1">&nbsp;</span>Add New Bill
							</h2>
							<%--   <form name="cf_form" method="post" id="contact-form" class="form-horizontal" action="#" onsubmit="return validationequiry()"> --%>
							<div class="block-box-exp">
								
								<div class="block-input">
									<label><spring:message code="label.productCode" /></label>
									<!-- <input type="text" name="lmark" id="lmark"> -->
									<form:input path="productId" />
								</div>
								<div class="block-input">
									<label><spring:message code="label.quantity" /></label>
									<!-- <input type="text" name="lmark" id="lmark"> -->
									<form:input path="quantity" />
								</div>
								<div class="block-input  last">
									<label><spring:message code="label.rate" /></label>
									<!-- <input type="text" name="lmark" id="lmark"> -->
									<form:input path="cost" />
								</div>
                       <div class="block-input">
									<label><spring:message code="label.branchName" /></label>
									<!--  <select id="addclient" name="addclient">
                    </select>  -->

									<c:choose>
										<c:when test="${sessionScope.admin=='sales' }">
											<form:select path="branchId" cssClass="field size3">
												<form:options items="${branch}"></form:options>
											</form:select>
										</c:when>
										<c:otherwise>
											<form:select path="branchId" cssClass="field size3">
												<form:options items="${branchs}"></form:options>
											</form:select>
										</c:otherwise>
									</c:choose>
								</div>
							</div>
							<div class="block-footer">
								<aside class="block-footer-left sucessfully">
									<c:forEach var="success" items="${param.AddSus}">
										<div>
											<strong><spring:message code="label.success" /></strong>
											<spring:message code="label.bill" />
											<spring:message code="label.saveSuccess" />

										</div>
									</c:forEach>
									<c:forEach var="fail" items="${param.AddFail}">
										<div>
											<spring:message code="label.error" />
											<spring:message code="label.bill" />
											<spring:message code="label.saveFail" />

										</div>
									</c:forEach>
								</aside>
								<aside class="block-footer-right">
									<input class="btn-cancel" name="" value="Cancel" type="button">
									<input class="btn-save" name="" value="Save" type="submit"
										id="searchId">
								</aside>
							</div>
						</div>



						<div class="block table-toop-space">
							<div class="head-box">
				
								<h2>
									<span class="icon2">&nbsp;</span>Bill No: 786
								</h2>
								<c:set var="now" value="<%=new java.util.Date()%>" />
							<fmt:formatDate pattern="yyyy-MM-dd" value="${now}" var="curDate"/>
								<aside class="block-footer-right">
									<label>Date:<c:out value="${curDate}"/></label>
								</aside>
								 <form:hidden path="billDate" value="${curDate}"/>
							</div>
							<div class="block-box-mid block-box-last-mid">
								<ul class="table-list">
									<li class="five-box">S.No
										<ul>
											<li><a class="top" href="#">&nbsp;</a></li>
											<li><a class="bottom" href="#">&nbsp;</a></li>
										</ul>
									</li>
									<li class="bil-desc-box">Description
										<ul>
											<li><a class="top" href="#">&nbsp;</a></li>
											<li><a class="bottom" href="#">&nbsp;</a></li>
										</ul>
									</li>
									<li class="five-box">Quantity
										<ul>
											<li><a class="top" href="#">&nbsp;</a></li>
											<li><a class="bottom" href="#">&nbsp;</a></li>
										</ul>
									</li>
									<li class="five-box">Rate
										<ul>
											<li><a class="top" href="#">&nbsp;</a></li>
											<li><a class="bottom" href="#">&nbsp;</a></li>
										</ul>
									</li>
									<li class="five-box">MRP
										<ul>
											<li><a class="top" href="#">&nbsp;</a></li>
											<li><a class="bottom" href="#">&nbsp;</a></li>
										</ul>
									</li>
									<li class="five-box">Discount
										<ul>
											<li><a class="top" href="#">&nbsp;</a></li>
											<li><a class="bottom" href="#">&nbsp;</a></li>
										</ul>
									</li>
									<li class="five-box">VAT
										<ul>
											<li><a class="top" href="#">&nbsp;</a></li>
											<li><a class="bottom" href="#">&nbsp;</a></li>
										</ul>
									</li>
									<li class="five-box">Amount
										<ul>
											<li><a class="top" href="#">&nbsp;</a></li>
											<li><a class="bottom" href="#">&nbsp;</a></li>
										</ul>
									</li>
									<li class="ten-box">Delete</li>
									<li class="eleven-box last">Edit</li>
								</ul>
								<div class="table-list-blk data-grid-mid">
									<!-- <ul>
								<li class="five-box">1</li>
								<li class="bil-desc-box">Silk Saree</li>
								<li class="five-box">10</li>
								<li class="five-box">1000</li>
								<li class="five-box">1200</li>
								<li class="five-box">100</li>
								<li class="five-box">50</li>
								<li class="five-box">950</li>
								<li class="ten-box"><a href="#">Delete</a></li>
								<li class="eleven-box last"><a href="#">Edit</a></li>
							</ul> -->
						
									<c:set var="tquantity" value="0" />
									<c:set var="trate" value="0" />
									<c:set var="tmrp" value="0" />
									<c:set var="tdiscount" value="0" />
									<c:set var="tvat" value="0" />
									<c:set var="tamount" value="0" />
									<c:forEach var="grid" items="${billDetails}" varStatus="status">
										<c:set var="tquantity" value="${tquantity+grid.quantity}" />
										<c:set var="trate" value="${trate+grid.rate}" />
										<c:set var="tmrp" value="${tmrp+grid.mrp}" />
										<c:set var="tdiscount" value="${tdiscount+grid.discount}" />
										<c:set var="tvat" value="${tvat+grid.vat}" />
										<c:set var="tamount" value="${tamount+grid.amount}" />
										<ul>
											<li class="five-box">${status.count}</li>
											<li class="bil-desc-box">${grid.prodDesc}</li>
											<li class="five-box">${grid.quantity}</li>
											<li class="five-box">${grid.rate}</li>
											<li class="five-box">${grid.mrp}</li>
											<li class="five-box">${grid.discount}</li>
											<li class="five-box">${grid.vat}</li>
											<li class="five-box">${grid.amount}</li>
											<li class="ten-box"><a
												href="deleteBill.htm?prodId=<c:out value="${grid.prodId}"/>"
												onclick="return confirm('Do You Want To Delete This Record?')"><spring:message
														code="label.delete" /></a></li>
											<li class="eleven-box last"><a
												href="editBill.htm?prodId=<c:out value="${grid.prodId}"/>">Edit</a></li>

											<li hidden="true"><input type="hidden"
												name="billDetails[${status.index}].billDetailsId"
												value="<c:out value="${grid.billDetailsId}" />" /> <input
												type="hidden" name="billDetails[${status.index}].prodId"
												value="<c:out value="${grid.prodId}" />" /> <input
												type="hidden" name="billDetails[${status.index}].vat"
												value="<c:out value="${grid.vat}" />" /> <input
												type="hidden" name="billDetails[${status.index}].discount"
												value="<c:out value="${grid.discount}" />" /> <input
												type="hidden" name="billDetails[${status.index}].quantity"
												value="<c:out value="${grid.quantity}" />" /> <input
												type="hidden" name="billDetails[${status.index}].rate"
												value="<c:out value="${grid.rate}" />" /> <input
												type="hidden" name="billDetails[${status.index}].prodDesc"
												value="<c:out value="${grid.prodDesc}" />" /> <input
												type="hidden" name="billDetails[${status.index}].amount"
												value="<c:out value="${grid.amount}" />" /> <input
												type="hidden" name="billDetails[${status.index}].mrp"
												value="<c:out value="${grid.mrp}" />" /></li>
										</ul>
									</c:forEach>

									<ul>
										<div class="block-footer">
											<ul>
												<li class="five-box">&nbsp;</li>
												<li class="bil-desc-box"><b>Total</b></li>
												<li class="five-box"><b>${tquantity}</b></li>
												<li class="five-box"><b>${trate}</b></li>
												<li class="five-box"><b>${tmrp}</b></li>
												<li class="five-box"><b>${tdiscount}</b></li>
												<li class="five-box"><b>${tvat}</b></li>
												<li class="five-box"><b>${tamount}</b></li>
												<li class="ten-box">&nbsp;</li>
												<li hidden="true"><form:hidden path="netAmt"
														value="${tamount}" /> <form:hidden path="totaltax"
														value="${tvat}" /> <form:hidden path="totalDiscount"
														value="${tdiscount}" /> <form:hidden path="totalRate"
														value="${trate}" /> <form:hidden path="totalQuantiy"
														value="${tquantity}" /> <form:hidden path="totalMrp"
														value="${tmrp}" /></li>
												<li class="eleven-box last">&nbsp;</li>
											</ul>
										</div>
									</ul>

									<ul>
										<div class="block-box-exp">
											<div class="block-input">
												<label>Payment Type</label> <form:select path="paymentType">
												<form:option value="1">Cash</form:option>
												<form:option value="2">Card</form:option>
												<form:option value="3">Cheque</form:option>
												</form:select>  
											</div>
											<div class="block-input">
												<label>Cash</label>
												 <!-- <input type="text" name="lmark"
													id="lmark"> -->
												<form:input path="totalPayment" id="totalPayment" />
											</div>
											<div class="block-input  last">
												<label>Mobile No</label>
												<!--  <input type="text" name="lmark"
													id="lmark"> -->
												<form:input path="mobileNo" />
											</div>
											<div class="block-input">
												<label>Cheque No</label> 
												<!-- <input type="text" name="lmark"
													id="lmark"> -->
												<form:input path="chequeNo" />
											</div>
											<div class="block-input">
												<label>Card No</label> 
												<!-- <input type="text" name="lmark"
													id="lmark"> -->
												<form:input path="cardNo" />
											</div>
											<div class="block-input last">
												<label>Return Change</label> 
												<!-- <input type="text" name="lmark"
													id="lmark"> -->
												<form:input path="returnChange" />
											</div>
										</div>
									</ul>
								</div>
							</div>
							<div class="block-footer">
								<aside class="block-footer-left">
									<exptotal>Net Amount:<c:out value="${tamount}" /></exptotal>
								</aside>
								<aside class="block-footer-right">
									<input class="btn-cancel" name="" value="Cancel" type="button">
									<input class="btn-save" name="" id="saveId" name="" value="Save" type="submit">
								</aside>
							</div>
						</div>
					</form:form>
				</c:if>
				<c:if test="${ not empty billEdit}">
					<div class="block">
						<h2>
							<span class="icon1">&nbsp;</span>Edit Bill
						</h2>
						<%--   <form name="cf_form" method="post" id="contact-form" class="form-horizontal" action="#" onsubmit="return validationequiry()"> --%>
						<form:form action="billUpdate.htm" commandName="billEditCmd"
							method="post" id="editForm">
							<div class="block-box-exp">
								<div class="block-input">
									<label><spring:message code="label.stockId" /></label>
									<form:input path="prodId" />
								</div>
								<div class="block-input">
									<label><spring:message code="label.productName" /></label>
									<!-- <input type="text" name="lmark" id="lmark"> -->
									<form:input path="prodDesc" />

								</div>
								<div class="block-input">
									<label><spring:message code="label.quantity" /></label>
									<!-- <input type="text" name="lmark" id="lmark"> -->
									<form:input path="quantity" />
								</div>
								<div class="block-input  last">
									<label><spring:message code="label.rate" /></label>
									<!-- <input type="text" name="lmark" id="lmark"> -->
									<form:input path="rate" />
								</div>
								<div class="block-input  last">
									<label><spring:message code="label.vat" /></label>
									<!-- <input type="text" name="lmark" id="lmark"> -->
									<form:input path="vat" />
								</div>

							</div>


							<aside class="block-footer-right">
								<input class="btn-cancel" name="" value="Cancel" type="button">
								<input class="btn-save"  value="Save" type="submit"
									id="searchId">
							</aside>
						</form:form>
					</div>
         
       </c:if>
			</div>

		</div>
	</div>


	<%-- <div id="container">
		<div class="shell">
			<!-- Main -->
			<div id="main">
			
				<div class="cl">&nbsp;</div>

				<!-- Content -->
				<div id="content">
             	
					<!-- Box -->
						<c:if test="${empty billEdit}">
						<form:form action="getBillDetails.htm" commandName="getBillCmd" method="POST" id="billingForm">

							<div class="box">
								
								<!-- Box Head -->
								<div class="box-head">
									<h2>Search For Bill</h2>
								</div>
								<!-- End Box Head -->


								<div class="form" id="msgInfo">
									<table>
													<tr> 
										<td colspan="2"><c:forEach var="success"
												items="${param.AddSus}">
												<div class="alert-success" id="savemessage"
													style="color: green; font-size: 20px">
													<strong>Success </strong> Bill
													<spring:message code="label.saveSuccess" />

												</div>
											</c:forEach> <c:forEach var="fail" items="${param.AddFail}">
												<div class="alert-danger" id="failMsg"
													style="color: red; font-size: 20px">
													<strong>Error </strong> Bill
													<spring:message code="label.saveFail" />

												</div>
											</c:forEach></td>
										<td><c:forEach var="success" items="${param.UpdateSus}">
												<div class="alert-success" id="savemessage"
													style="color: green; font-size: 15px">
													<strong><spring:message code="label.success" /> </strong>
													<spring:message code="label.bill" />
													<spring:message code="label.updateSuccess" />

												</div>
											</c:forEach> <c:forEach var="fail" items="${param.UpdateFail}">
												<div class="alert-danger" id="successmessage"
													style="color: red; font-size: 20px">
													<strong><spring:message code="label.error" /> </strong>
													<spring:message code="label.bill" />
													<spring:message code="label.updateFail" />

												</div>
											</c:forEach></td> 
										<td colspan="2"><c:forEach var="success"
												items="${param.DeleteSus}">
												<div class="alert-success" id="DelSucMsg"
													style="color: green; font-size: 15px">
													<strong><spring:message code="label.success" /> </strong>
													<spring:message code="label.bill" />
													<spring:message code="label.deleteSuccess" />

												</div>
											</c:forEach> <c:forEach var="fail" items="${param.DeleteFail}">
												<div class="alert-danger" id="DelFailMsg"
													style="color: red; font-size: 15px">
													<strong><spring:message code="label.error" /> </strong>
													<spring:message code="label.bill" />
													<spring:message code="label.deleteFail" />

												</div>
											</c:forEach></td>
									</tr>
										<tr>
											
											<td><label><spring:message code="label.branchName" /></label></td>
											<td><label><spring:message code="label.productCode" /></label></td>
											<td><label><spring:message code="label.rate" /></label></td>
											<td><label><spring:message code="label.quantity" /></label></td>
										</tr>
										<tr>
											<c:choose>
											<c:when test="${sessionScope.admin=='sales' }">
												<td><form:select path="branchId" cssClass="field size3">
														<form:options items="${branch}"></form:options>
													</form:select></td>
											</c:when>
											<c:otherwise>
												<td><form:select path="branchId" cssClass="field size3">
														
														<form:options items="${branchs}"></form:options>
													</form:select></td>
											</c:otherwise>
										</c:choose>
											
											<td><form:input path="productId" class="field size4" /></td>
											<td><form:input path="cost" class="field size3" /></td>
											<td><form:input path="quantity" class="field size3" /></td>
										</tr>
									</table>

								</div>
								<!-- Form Buttons -->
								<div class="buttons">
									<c:forEach var="success"
												items="${DeleteSus}">
												<div class="alert-success" id="savemessage"
													style="color: green; font-size: 20px">
													<strong><spring:message code="label.success" /> </strong>
													<spring:message code="label.bill" />
													<spring:message code="label.deleteSuccess" />

												</div>
											</c:forEach> <c:forEach var="fail" items="${DeleteFail}">
												<div class="alert-danger" id="successmessage"
													style="color: red; font-size: 20px">
													<strong><spring:message code="label.error" /> </strong>
													<spring:message code="label.bill" />
													<spring:message code="label.deleteFail" />

												</div>
											</c:forEach>
							<c:forEach var="success"
												items="${UpdateSus}">
												<div class="alert-success" id="savemessage"
													style="color: green; font-size: 20px">
													<strong><spring:message code="label.success" /> </strong>
													<spring:message code="label.bill" />
													<spring:message code="label.updateSuccess" />

												</div>
											</c:forEach> <c:forEach var="fail" items="${UpdateFail}">
												<div class="alert-danger" id="successmessage"
													style="color: red; font-size: 20px">
													<strong><spring:message code="label.error" /> </strong>
													<spring:message code="label.bill" />
													<spring:message code="label.updateFail" />

												</div> 
											</c:forEach>
									<c:forEach var="success" items="${param.AddSus}">
										<div class="alert-success" id="savemessage">
											<strong><spring:message code="label.success" /></strong>
											<spring:message code="label.bill" />
											<spring:message code="label.saveSuccess" />

										</div>
									</c:forEach>
									<c:forEach var="fail" items="${param.AddFail}">
										<div class="alert-danger" id="successmessage">
											<strong><spring:message code="label.error" /> </strong>
											<spring:message code="label.bill" />
											<spring:message code="label.saveFail" />

										</div>
									</c:forEach>
									<input type="reset" class="button" value="Clear" /> <input
										type="submit" class="button" value="Submit" id="searchId" />
								</div>
								<!-- End Form Buttons -->


							</div>
							<!-- End Box -->
					
						<!-- Box -->
					<div class="box">
						<!-- Box Head -->
						
						<div class="box-head">
							<h2 class="left">BillNo:<c:out value="${autoId }"></c:out></h2>
							  <form:hidden path="billId" value="${autoId}"/>  
							<!-- <input type="text" name="billId" value="b008" /> -->
							<c:set var="now" value="<%=new java.util.Date()%>" />
							<fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${now}" var="curDate"/>
							<h2 class="right">Date:<c:out value="${curDate}"/></h2>
							 <form:hidden path="billDate" value="${curDate}"/>
						</div>
						<!-- End Box Head -->

						<!-- Table -->
						<jsp:scriptlet>
						     org.displaytag.decorator.TotalTableDecorator totals = new org.displaytag.decorator.TotalTableDecorator();
						     pageContext.setAttribute("totals", totals);
  						</jsp:scriptlet>
						<div class="table" >
							<display:table name="${billDetails}" id="billRow" requestURI="billHome.htm"  class="table"  varTotals="totals"  >
								<display:column title="Serial" sortable="true" > ${billRow_rowNum}</display:column> 
								<display:column property="stockId" title="StockId" sortable="true" media="html" />
								<display:column property="prodDesc" title="Product Name" sortable="true" media="html"  >
								<c:choose>
        								<c:when test="${method == 'Edit' and id == test.id}">
           									 <input type="text" name="billDetails[${billRow_rowNum-1}].prodDesc" style="padding: 0"
               														 value="<c:out value="${billRow.lastName}"/>" />
       									 </c:when>
       							 <c:otherwise><c:out value="${billRow.prodDesc}"/></c:otherwise>
       							 </c:choose>
								
								</display:column>
								<display:column property="mrp" title="MRP" sortable="true" media="html" total="true"/>
								<display:column property="quantity" title="Quantity" sortable="true"  media="html" total="true" />
								<display:column property="rate" title="Rate" sortable="true"  media="html" total="true"/>
								<display:column property="vat" title="VAT" sortable="true"  media="html" total="true"/>
								<display:column property="discount" title="Discount" sortable="true"  media="html" total="true"/>
								<display:column property="amount" title="Amount" sortable="true"  media="html" total="true"/>
								<display:column titleKey="label.edit" sortable="true">
									<c:set var="grid" value="${billRow}" scope="request"></c:set>
									<a href="editBill.htm?prodId=<c:out value="${billRow.prodId}"/>" 
									class="ico edit"><spring:message code="label.edit" /></a>
								</display:column>
								<display:column titleKey="label.delete" sortable="true">
									<a href="deleteBill.htm?prodId=<c:out value="${billRow.prodId}"/>" class="ico del"
										onclick="return confirm('Do You Want To Delete This Record?')"><spring:message code="label.delete" /></a>
								</display:column>
							 <display:column >
									<input type="hidden" name="billDetails[${billRow_rowNum-1}].billDetailsId" value="<c:out value="${billRow.billDetailsId}" />" />
									<input type="hidden" name="billDetails[${billRow_rowNum-1}].prodId" value="<c:out value="${billRow.prodId}" />" /> 
									<input type="hidden" name="billDetails[${billRow_rowNum-1}].vat" value="<c:out value="${billRow.vat}" />" />
									<input type="hidden" name="billDetails[${billRow_rowNum-1}].discount" value="<c:out value="${billRow.discount}" />" />
									<input type="hidden" name="billDetails[${billRow_rowNum-1}].quantity" value="<c:out value="${billRow.quantity}" />" />
									<input type="hidden" name="billDetails[${billRow_rowNum-1}].rate" value="<c:out value="${billRow.rate}" />" />
									<input type="hidden" name="billDetails[${billRow_rowNum-1}].prodDesc" value="<c:out value="${billRow.prodDesc}" />" />
									<input type="hidden" name="billDetails[${billRow_rowNum-1}].amount" value="<c:out value="${billRow.amount}" />" />
									<input type="hidden" name="billDetails[${billRow_rowNum-1}].mrp" value="<c:out value="${billRow.mrp}" />" />
						</display:column> 
						 <display:footer>
								    <tr>
								      <td colspan="2" align="center"><b>Total Bill:</b><form:hidden path="totalAmt" id="totalAmt" value="${totals.column9}" /></td>
								      <td><b><c:out value="${totals.column3}" /></b><form:hidden path="totalMrp" value="${totals.column3}" /></td>
								      <td><b><c:out value="${totals.column4}" /></b><form:hidden path="totalQuantiy" value="${totals.column4}" /></td>
								      <td><b><c:out value="${totals.column5}" /></b><form:hidden path="totalRate" value="${totals.column5}" /></td>  
								      <td><b><c:out value="${totals.column6}" /></b><form:hidden path="totaltax" value="${totals.column6}" /></td>  
								      <td><b><c:out value="${totals.column7}" /></b><form:hidden path="totalDiscount" value="${totals.column7}" /></td>  
								   <td><b><c:out value="${totals.column8}" /></b><form:hidden path="netAmt" id="netAmt" value="${totals.column8}" /></td> 
								  
							</tr>       
 						</display:footer>
						</display:table>
						</div>
						
						<!-- Table -->
							 <div class="form">
								<table>
									<tr><td></td><tr>
									<tr>
										<td><label><spring:message code="label.cheque" /></label></td>
										<td><label><spring:message code="label.card" /></label></td>
										<td><label><spring:message code="label.cash" /></label></td>
										
									</tr>
									<tr>
										<td><form:input path="paidbyCheque" class="field size4"/></td>
										<td><form:input path="paidbyCard" class="field size4"/></td>

										<td><form:input path="totalPayment" id="totalPayment" class="field size4"/></td>


									</tr>
									<tr>
										<td><label><spring:message code="label.chequeNo" /></label></td>
										<td><label><spring:message code="label.cardNo" /></label></td>
										<td><label><spring:message code="label.returnChange" /></label></td>
									</tr>
									<tr>
										<td><form:input path="chequeNo" class="field size4"/></td>
										<td><form:input path="cardNo" class="field size4"/></td>

										<td><form:input path="returnChange" id="returnChange" class="field size4" readonly="true" /></td>
									</tr>
									<tr>
										<td>&nbsp;&nbsp;</td>
										<td><label><spring:message code="label.mobileNo" /></label></td>
										<td><form:input path="mobileNo" class="field size4"/></td>
									</tr>
							</table>

							</div>
							<!-- Form Buttons -->
							<div class="buttons">
								<input type="reset" class="button" value="Clear" /> <input
									type="submit" class="button" value="Submit" id="saveId" />
							</div>
							<!-- End Form Buttons -->
						
						
					</div>
					
						</form:form>
					</c:if>
					
				 <c:if test="${ not empty billEdit}">
				 
				 	<c:out value="${billEdit}"></c:out>
				 	<h4>In Edit Mode</h4>
					<!--Edit Box  -->
					<div class="box">
						<!-- Box Head -->
						<div class="box-head">
							<h2>Edit Bill</h2>
						</div>
						<!-- End Box Head -->

						<form:form action="billUpdate.htm" commandName="billEditCmd"
							method="post" id="editForm">
							<div class="form">
								<table>
									<tr>
										<td><label><spring:message code="label.stockId" /></label></td>
										<td><label><spring:message code="label.productName" /></label></td>
										<td><label><spring:message code="label.quantity" /></label></td>
										</tr>
										<tr>
										<td><form:input path="prodId" cssClass="field size4" /></td>
										<td><form:input path="prodDesc" cssClass="field size4" /></td>
										<td><form:input path="quantity" cssClass="field size4" /></td>
										</tr>
										<tr><td><label><spring:message code="label.rate" /></label></td>
										<td><label><spring:message code="label.vat" /></label></td>
										<td><label><spring:message code="label.discount" /></label></td></tr>
										<tr><td><form:input path="rate" cssClass="field size4" /></td>
										<td><form:input path="vat" cssClass="field size4" /></td>
										<td><form:input path="discount" cssClass="field size4" /></td></tr>
								</table>
							</div>
							<div class="buttons">
								<input type="submit" class="button" value="<spring:message code="label.update"/>" id="updateId" />
							</div>
						</form:form>
					</div>	 
				</c:if> 
				<!--Edit Box End  -->
					<!-- End Box -->
					
				</div> 
				
				<!-- End Content -->
				<div class="cl">&nbsp;</div>
				
			</div>
			
			<!-- Main -->
			
		</div>
	</div> --%>

	
	<c:if test="${emptyProduct=='false'}">
				<script type="text/javascript">
					alert("Product Code is Not available");
			</script>
		</c:if>
	<c:if test="${LessQuantity=='quantityError'}">
				<script type="text/javascript">
					alert("Quantity is Not available");
			</script>
		</c:if> 
		
</body>

</html>