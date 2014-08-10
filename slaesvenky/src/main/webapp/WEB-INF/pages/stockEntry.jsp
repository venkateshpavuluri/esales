<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>stock Entry</title>
<link rel="stylesheet" href="js/jqueryui.css" />
<link rel="stylesheet" href="css/jquery-ui.css" />
<link rel="stylesheet"
	href="js/jquery-ui-1.10.3/themes/base/jquery-ui.css" />
<script src="js/jquery-1.7.2.js"></script>
<script src="js/jquery-ui.js"></script>
<script type="text/javascript" src="js/pagination.js"></script>
<script type="text/javascript" src="js/jquery.validate.min.js"></script>
<script type="text/javascript" src="js/MntValidator.js"></script>

<style type="text/css">
.fading:hover {
	color: blue;
	text-decoration: underline
}
</style>
<script type="text/javascript">
	$(document)
			.ready(
					function() {
						var listofstock = ${listofstock};
						if (listofstock == "" || listofstock == null) {
							$("#stockdata ul li").remove();
							$("#noSortData").show();
						} else {
							$.each(listofstock, function(index, stockstart) {
								$("#noSortData").hide();
								var tblRow = "<ul>" + "<li class='stock-pcode-box'>"
										+ stockstart.categoryId+ stockstart.subcategoryId+ stockstart.productId 
										+ "</li>"
										+ "<li class='bil-desc-box-stock'>" 
										+ stockstart.category + " "+ stockstart.subcategory + " "+ stockstart.productName + "</li>"
										+ "<li class='five-box'>" 
										+ stockstart.cost 
										+ "</li>"
										+ "<li class='five-box'>"
										+ stockstart.quantity
										+ "</li>" 
										+ "<li class='five-box'>" 
										+ stockstart.mrp
										+ "</li>" 
										+ "<li class='five-box'>"
										+ stockstart.discount 
										+ "</li>"
										+ "<li class='five-box'>"
										+ stockstart.vat 
										+ "</li>"
										+ "<li class='ten-box'>"
										+ '<a href="stockDelete.htm?stock_Id='
										+ stockstart.stock_Id
										+ '" class="del">Delete</a>'
										+ "</li>"
										+ "<li class='eleven-box last'>"
										+ '<a href="stockEdit.htm?stock_Id='
										+ stockstart.stock_Id
										+ '" class="ico edit">Edit</a>'
										+ "</li>"
										+"</ul>";
								$(tblRow).appendTo("#stockdata");
								$(".del").click(function(event){
									return confirm('Do u want to Delete The Record?');
								});
							});
						}
						//AddForm Validations
						$('#submitId')
								.click(
										function(event) {
											//alert("hai");
											$('#addForm')
													.validate(
															{
																rules : {
																	productId : {
																		required : true,

																	},
																	cost : {
																		required : true,
																		number : true

																	},
																	quantity : {
																		required : true,
																		number : true

																	},
																	mrp : {
																		required : true,
																		number : true

																	}

																},
																messages : {
																	productId : {
																		required : "<font style='color: red;'><b>Product is Required</b></font>",
																	},
																	cost : {
																		required : "<font style='color: red;'><b>Cost is Required</b></font>",
																		number : "<font style='color: red;'><b>Only numerics are allowed</b></font>"

																	},
																	quantity : {
																		required : "<font style='color: red;'><b>Quantity is Required</b></font>",
																		number : "<font style='color: red;'><b>Only numerics are allowed</b></font>"

																	},
																	mrp : {
																		required : "<font style='color: red;'><b>MRP is Required</b></font>",
																		number : "<font style='color: red;'><b>Only numerics are allowed</b></font>"

																	}

																},

															});

										});
						$('#updateId')
								.click(
										function(event) {
											//alert("hai");
											$('#editForm')
													.validate(
															{
																rules : {
																	productId : {
																		required : true,

																	},
																	cost : {
																		required : true,
																		number : true

																	},
																	quantity : {
																		required : true,
																		number : true

																	},
																	mrp : {
																		required : true,
																		number : true

																	}

																},
																messages : {
																	productId : {
																		required : "<font style='color: red;'><b>Product is Required</b></font>",
																	},
																	cost : {
																		required : "<font style='color: red;'><b>Cost is Required</b></font>",
																		number : "<font style='color: red;'><b>Only numerics are allowed</b></font>"

																	},
																	quantity : {
																		required : "<font style='color: red;'><b>Quantity is Required</b></font>",
																		number : "<font style='color: red;'><b>Only numerics are allowed</b></font>"

																	},
																	mrp : {
																		required : "<font style='color: red;'><b>MRP is Required</b></font>",
																		number : "<font style='color: red;'><b>Only numerics are allowed</b></font>"

																	}

																},

															});

										});
						$('#messages').fadeIn().delay(3000).fadeOut('slow'); 

					});
	function getgrid(id) {
		var productId = $('#' + id).val();
		$("#stockdata ul li").remove();
		$
				.ajax({
					type : "POST",
					url : "stockSearchWithClient.htm",
					data : "productId=" + productId,
					dataType : "json",
					success : function(stockList) {
						if (stockList == "") {
							$("#stockdata ul li").remove();
							$("#noSortData").show();
							$("#equantity").val("");
							$("#quantity").val("").attr('readonly', false);
							$("#cost").val("").attr('readonly', false);
							$("#mrp").val("").attr('readonly', false);
							$("#discount").val("0").attr('readonly', false);
							$("#vat").val("0").attr('readonly', false);
						} else {
							$.each(stockList, function(index,stockstart) {
								$("#noSortData").hide();
								$("#stockId").val(stockstart.stock_Id);
								$("#equantity").val(stockstart.quantity);
								$("#quantity").val(stockstart.quantity);
								$("#cost").val(stockstart.cost).attr('readonly', true);
								$("#mrp").val(stockstart.mrp).attr('readonly', true);
								$("#discount").val(stockstart.discount).attr('readonly', true);
								$("#vat").val(stockstart.vat).attr('readonly', true);
								var tblRow = "<ul class='table-list'>" + "<li class='stock-pcode-box'>"
								+ stockstart.categoryId +stockstart.subcategoryId+stockstart.productId 
								+ "</li>"
								+ "<li class='bil-desc-box-stock'>" 
								+ stockstart.category + " "+ stockstart.subcategory + " "+ stockstart.productName + "</li>"
								+ "<li class='five-box'>" 
								+ stockstart.cost 
								+ "</li>"
								+ "<li class='five-box'>"
								+ stockstart.quantity
								+ "</li>" 
								+ "<li class='five-box'>" 
								+ stockstart.mrp
								+ "</li>" 
								+ "<li class='five-box'>"
								+ stockstart.discount 
								+ "</li>"
								+ "<li class='five-box'>"
								+ stockstart.vat 
								+ "</li>"
								+ "<li class='ten-box'>"
								+ '<a href="stockDelete.htm?stock_Id='
								+ stockstart.stock_Id
								+ '" class="del">Delete</a>'
								+ "</li>"
								+ "<li class='eleven-box last'>"
								+ '<a href="stockEdit.htm?stock_Id='
								+ stockstart.stock_Id
								+ '" class="ico edit">Edit</a>'
								+ "</li>"
								+"</ul>";
						$(tblRow).appendTo("#stockdata");
						$(".del").click(function(event){
							return confirm('Do u want to Delete The Record?');
						});
					});
							
						}

					},
					error : function(e) {

					}

				});

	}
</script>
<script type="text/javascript">
	/* function getQuantity() {
		alert("check");
		var productId = $("#productId").val();
		$.ajax({
			type : "POST",
			url : "forQuantity.htm",
			data : "productId=" + productId,
			dataType : "json",
			success : function(reStockList) {
				alert("ee "+reStockList);
					$.each(reStockList, function(index,stockli) {
						$("#equantity").val(stockli.quantity);
						$("#quantity").val(stockli.quantity);
						$("#cost").val(stockli.cost);
						$("#mrp").val(stockli.mrp);
						$("#discount").val(stockli.discount);
						$("#vat").val(stockli.vat);
			});
					
				
			},
			error : function(e) {

			},
		});

	} */
	/* function getQuantityEdit() {
		var productIdedit = $("#productIdedit").val();
		$.ajax({
			type : "POST",
			url : "forQuantityEdit.htm",
			data : "productIdedit=" + productIdedit,
			success : function(response) {
				$("#equantityedit").val(response);
			},
			error : function(e) {

			},
		});

	} */
</script>
</head>
<body>
	<!-- SET: WRAPPER -->
	<div class="wrapper">

		<!-- SET: CONTAINER -->
		<div class="container">
			<div class="main_content">
			<c:if test="${stockentryEdit==null}">
				<div class="block">
					<h2>
						<span class="icon1">&nbsp;</span>Add New Stock
					</h2>
					<form:form action="stockAdd.htm" commandName="STOCKTRANSFER"
						method="post" id="addForm">
						<div class="block-box-mid-stock">
						<form:hidden path="stock_Id" id="stockId"/>
							<div class="block-input">
								<label><spring:message code="label.product" /><span
									style="color: red;">*</span></label>
								<form:select path="productId" id="productId"
									onchange="getgrid(this.id)"
									cssClass="field size4">
									<form:option value="">--Select--</form:option>
									<form:options items="${product}" />
								</form:select>
							</div>
							<div class="block-input last">
								<label><spring:message code="label.eq" /></label> <input
									type="text" name="eq" id="equantity" readonly="readonly" />
							</div>
							<div class="block-input">
								<label><spring:message code="label.quantity" /><span
									style="color: red;">*</span></label>
								<form:input path="quantity" cssClass="field size3" />
							</div>
							<div class="block-input">
								<label><spring:message code="label.cost" /><span
									style="color: red;">*</span></label>
								<form:input path="cost" cssClass="field size3" />
							</div>
							<div class="block-input">
								<label><spring:message code="label.mrp" /><span
									style="color: red;">*</span></label>
								<form:input path="mrp" cssClass="field size3" />
							</div>
							<div class="block-input ">
								<label><spring:message code="label.discount" /></label>
								<form:input path="discount" id="discount" cssClass="field size3" />
							</div>
							<div class="block-input">
								<label><spring:message code="label.vat" /></label>
								<form:input path="vat" id="vat" cssClass="field size3" />
							</div>
						</div>
						<div class="block-footer">
						<div id="messages">
							<aside class="block-footer-left sucessfully"> <c:forEach
								var="success" items="${param.AddSus}">
								<strong><spring:message code="label.success" /> </strong>
													Stock
													<spring:message code="label.saveSuccess" />

							</c:forEach> <c:forEach var="fail" items="${param.AddFail}">
								<strong><spring:message code="label.error" /> </strong>
													Stock
													<spring:message code="label.saveFail" />

							</c:forEach>
							<c:forEach var="success"
									items="${param.UpdateSus}">
									<strong><spring:message code="label.success" /> </strong>
													Stock
													<spring:message code="label.updateSuccess" />

								</c:forEach> <c:forEach var="fail" items="${param.UpdateFail}">
									<strong><spring:message code="label.error" /> </strong>
													Stock
													<spring:message code="label.updateFail" />
								</c:forEach>


							<c:forEach var="success"
									items="${param.DeleteSus}">
									<strong><spring:message code="label.success" /> </strong>
													Stock
													<spring:message code="label.deleteSuccess" />
								</c:forEach> <c:forEach var="fail" items="${param.DeleteFail}">
									<strong><spring:message code="label.error" /> </strong>
													Stock
													<spring:message code="label.deleteFail" />
								</c:forEach>


							</aside>
							</div>
							<aside class="block-footer-right"> <input
								class="btn-cancel" name=""
								value="<spring:message code="label.clear" />" type="button">
							<input class="btn-save" name=""
								value="<spring:message code="label.save" />" id="submitId"
								type="submit"> </aside>
								
						</div>
					</form:form>
				</div>
				</c:if>
				
			<!-- Edit -->	
				<c:if test="${stockentryEdit!=null}">
				<div class="block">
					<h2>
						<span class="icon1">&nbsp;</span>Edit Stock
					</h2>
					<form:form action="stockUpdate.htm" commandName="STOCKTRANSFER"
							method="post" id="editForm">
						<div class="block-box-mid-stock">
							<div class="block-input">
							<form:hidden path="stock_Id"/>
								<label><spring:message code="label.product" /><span
									style="color: red;">*</span></label>
								<form:select path="productId" id="productId"
									onchange="getgrid(this.id)"
									cssClass="field size4">
									<form:option value="">--Select--</form:option>
									<form:options items="${product}" />
								</form:select>
							</div>
							<div class="block-input last">
								<label><spring:message code="label.eq" /></label> <input
									type="text" name="eq" id="equantity" readonly="readonly" />
							</div>
							<div class="block-input">
								<label><spring:message code="label.quantity" /><span
									style="color: red;">*</span></label>
								<form:input path="quantity" id="quantity" readonly="true" cssClass="field size3" />
							</div>
							<div class="block-input">
								<label><spring:message code="label.cost" /><span
									style="color: red;">*</span></label>
								<form:input path="cost" id="cost" cssClass="field size3" />
							</div>
							<div class="block-input">
								<label><spring:message code="label.mrp" /><span
									style="color: red;">*</span></label>
								<form:input path="mrp" id="mrp" cssClass="field size3" />
							</div>
							<div class="block-input ">
								<label><spring:message code="label.discount" /></label>
								<form:input path="discount" id="discount" cssClass="field size3" />
							</div>
							<div class="block-input">
								<label><spring:message code="label.vat" /></label>
								<form:input path="vat" id="vat" cssClass="field size3" />
							</div>
						</div>
						<div class="block-footer">
							<aside class="block-footer-left sucessfully"> <c:forEach
								var="success" items="${param.AddSus}">
								<strong><spring:message code="label.success" /> </strong>
													Stock
													<spring:message code="label.saveSuccess"  />

							</c:forEach> <c:forEach var="fail" items="${param.AddFail}">
								<strong><spring:message code="label.error" /> </strong>
													Stock
													<spring:message code="label.saveFail" />

							</c:forEach>
							<c:forEach var="success"
									items="${param.UpdateSus}">
									<strong><spring:message code="label.success" /> </strong>
													Stock
													<spring:message code="label.updateSuccess" />

								</c:forEach> <c:forEach var="fail" items="${param.UpdateFail}">
									<strong><spring:message code="label.error" /> </strong>
													Stock
													<spring:message code="label.updateFail" />
								</c:forEach>


							<c:forEach var="success"
									items="${param.DeleteSus}">
									<strong><spring:message code="label.success" /> </strong>
													Stock
													<spring:message code="label.deleteSuccess" />
								</c:forEach> <c:forEach var="fail" items="${param.DeleteFail}">
									<strong><spring:message code="label.error" /> </strong>
													Stock
													<spring:message code="label.deleteFail" />
								</c:forEach>


							</aside>
							<aside class="block-footer-right"> <input
								class="btn-cancel" name=""
								value="<spring:message code="label.clear" />" type="button">
							<input class="btn-save" name=""
								value="<spring:message code="label.update" />" id="updateId"
								type="submit"> </aside>
						</div>
					</form:form>
				</div>
				
				</c:if>
				
				<div class="block table-toop-space">
					<div class="head-box">
						<h2>
							<span class="icon2">&nbsp;</span>Current Stock
						</h2>
						<aside class="search-box"> 
						<form:form action="stockSearch.htm" commandName="STOCKTRANSFER"
									method="get">
									<input type="submit"  class="search-bnt" value="<spring:message code="label.search" />" />
								 <form:select
													path="productId" cssClass="search-input select">
													<form:option value="">--Select--</form:option>
													<form:options items="${searchproduct}" />
												</form:select>
								</form:form>
                          </aside>
					</div>
					<div class="block-box-stock block-box-last-stock" >
						<ul class="table-list">
							<li class="stock-pcode-box">Product Code
								<ul>
									<li><a class="top" href="#">&nbsp;</a></li>
									<li><a class="bottom" href="#">&nbsp;</a></li>
								</ul>
							</li>
							<li class="bil-desc-box-stock">Product
								<ul>
									<li><a class="top" href="#">&nbsp;</a></li>
									<li><a class="bottom" href="#">&nbsp;</a></li>
								</ul>
							</li>
							<li class="five-box">Cost
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
							<li class="ten-box">Delete</li>
							<li class="eleven-box last">Edit</li>
						</ul>
						<div align="center">
							<h1 id="noSortData" >No Data Found</h1>
						</div>
						<div class="table-list-blk data-grid-stock" id="stockdata">
						</div>
						
					</div>
					<div class="block-footer">
						<!-- <aside class="block-footer-left">Showing 1 to 10 of 57
						entries</aside>
						<aside class="block-footer-right">
						<ul class="pagenation">
							<li><a href="#">Next</a></li>
							<li><a href="#">3</a></li>
							<li><a href="#">2</a></li>
							<li><a class="active" href="#">1</a></li>
							<li><a href="#">Previous</a></li>
						</ul>
						</aside> -->
					</div>
				</div> 
			</div>
		</div>
	</div>
</body>
</html>