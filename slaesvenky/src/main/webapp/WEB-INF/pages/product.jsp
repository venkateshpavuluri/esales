<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Product</title>
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
	$(document)
			.ready(
					function() {
						$('#addName').val('');

						//AddForm Validations
						$('#submitId')
								.click(
										function(event) {
											//alert("hai");
											$('#addForm')
													.validate(
															{
																rules : {
														
															productName : {
																required : true,
																alphanumeric : true,
																specialcharacters : true
															},
															 subCategoryId: {
																required : true,
															},
															 categoryId: {
																	required : true,
																},
															
																},
																messages : {
																categoryId : {
																	required : "<font style='color: red;'><b>Category is Required</b></font>",
																},
																productName : {
																	required : "<font style='color: red;'><b>Product is Required</b></font>",
																	alphanumeric : "<font style='color: red;'><b>First letter should be an alphanumeric.</b></font>",
																	specialcharacters : "<font style='color: red;'><b>Special Characters except &,_ are not allowed </b></font>"
																},
																		subCategoryId : {
																			required : "<font style='color: red;'><b>SubCategory is Required</b></font>",
																		} ,

																},

															});
											$('#addForm').attr('action', "<c:url value='/productAdd.htm'/>");
											$("#addForm").submit();
											event.preventDefault();

										});

						$('#updateId').click(function(event) {
											$('#editForm').validate(
															{
																rules : {
																	productName : {
																		required : true,
																		alphanumeric : true,
																		specialcharacters : true
																	},
																	
																},
																messages : {
																	productName : {
																		required : "<font style='color: red;'><b>Product is Required</b></font>",
																		alphanumeric : "<font style='color: red;'><b>First letter should be an alphanumeric.</b></font>",
																		specialcharacters : "<font style='color: red;'><b>Special Characters except &,_ are not allowed </b></font>"
																	},
																},
															});
											$('#editForm').attr('action', "<c:url value='/updateProduct.htm'/>");
											$("#editForm").submit();
											event.preventDefault();
										});

					});
	function getCategorys(id) {
		var deptId = $("#" + id).val();
		$.ajax({
			type : "POST",
			url : "forCategorys.htm",
			data : "deptId=" + deptId,
			dataType : "json",
			success : function(response) {
				var optionsForClass = "";
				optionsForClass = $("#categoryId").empty();
				optionsForClass.append(new Option("--Select--", ""));
				$.each(response, function(i, tests) {
					optionsForClass.append(new Option(tests.categoryName,
							tests.categoryId));
				});
			},
			error : function(e) {
			},
			statusCode : {
				406 : function() {
					alert("page not found");
				}
			}
		});
	}
	function getSubCategorys(id) {
		var catId = $("#" + id).val();
		$("#userdata thead th").remove();
		$("#userdata tbody tr").remove();
		$.ajax({
			type : "POST",
			url : "forSubCategorys.htm",
			data : "catId=" + catId,
			dataType : "json",
			success : function(response) {
				var optionsForClass = "";
				optionsForClass = $("#subCategoryId").empty();
				optionsForClass.append(new Option("--Select--", ""));
				optionsForClass.append(new Option("ALL", "all"));
				$.each(response, function(i, tests) {
					optionsForClass.append(new Option(tests.subCategoryName,
							tests.subCategoryId));
				});
			},
			error : function(e) {
			},
			statusCode : {
				406 : function() {
			/* 		alert("page not found"); */
				}
			}
		});
	}
	function duplicateCheck(id) {
		var productName = $('#'+id).val();
		var subCatId = $('#subCategoryId').val()
		$.ajax({
					type : "POST",
					url : "checkProAddDuplicate.htm",
					data : "productName=" + productName + "&subCatId="+subCatId,
					success : function(response) {
						if (response != "") {
							document.getElementById("productMessage").style.display = "block";
							//$('#salesUpDuplMessage').html(response);
							$('#submitId').hide();
							$('#submitId').attr('disabled', 'disabled');
						} else {
							document.getElementById("productMessage").style.display = "none";
							$('#submitId').removeAttr('disabled');
							$('#submitId').show();
						}
					},
					error : function(e) {
				/* 		alert('Error' + e); */
					}
				});
	}
	function editDuplicateCheckEdit(id) {
		var productName = $('#'+id).val();
		var productId = $('#productIdEdit').val()
		$.ajax({
					type : "POST",
					url : "checkProUpDuplicate.htm",
					data : "productName=" + productName + "&productId="+productId,
					success : function(response) {
						if (response != "") {
							document.getElementById("productMessageEdit").style.display = "block";
							//$('#salesUpDuplMessage').html(response);
							$('#updateId').hide();
							$('#updateId').attr('disabled', 'disabled');
						} else {
							document.getElementById("productMessageEdit").style.display = "none";
							$('#updateId').removeAttr('disabled');
							$('#updateId').show();
						}
					},
					error : function(e) {
				/* 		alert('Error' + e); */
					}
				});
	}
	function getAllProductsSearch(id) {
		var catId = $("#"+id).val();
		$("#userdata ul li").remove();
		$.ajax({
			type : "POST",
			url : "allProductsSearch.htm",
			data : "catId=" + catId,
			dataType : "json",
			success : function(response) {
				//alert(response);
							if(response==""){
								
							}
							else{
							var count=0;
							$.each(response, function(i, productObj) {
								count++;
								var tblRow = "<ul>" + "<li  class='prod-code-box' onmouseover='animaterow("+count+")' onmouseout='deanimateRow("+count+")'>" +productObj.productId
								 + "</li>"
								+ "<li class='prod-name-box'>" +productObj.categoryName+" "+productObj.subCategoryName+" "+ productObj.productName+ "</li>"
										+ "<li class='ten-box'>"
										+ '<a href="deleteProduct.htm?productId='
										+ productObj.productId
										+ '" class="del">Delete</a>' 
										+ '</li >'
										+ "<li class='eleven-box last'>"
										+ '<a href="editProduct.htm?productId='
										+ productObj.productId
										+ '" class="ico edit">Edit</a>' 
										+ '</li >'
										
										+ '</ul>';
								$(tblRow).appendTo("#userdata");
								
							});
							$(".del").click(function(event){
								return confirm('Do u want to Delete The Record?');
							});
							/* $('#userdata').tablePagination(options); */
							}
			},
			error : function(e) {
			},
			statusCode : {
				406 : function() {
					alert("page not found");
				}
			}
		});
	}
	function animaterow(id)
	{
		$('#'+id+'animate').attr("style","border-bottom-color: green;font-size:152px; color:red");
	}
	
	function deanimateRow(id)
	{

		$('#'+id+'animate').removeAttr("style");
	}
</script>
<script type="text/javascript">
$(document).ready(function(){
	$("#userdata ul li").remove();
	 var options = {
			currPage : 1,
			optionsForRows : [2,3,5],
			rowsPerPage : 5,
			firstArrow : (new Image()).src = "css/images/first.gif",
			prevArrow : (new Image()).src = "css/images/prev.gif",
			lastArrow : (new Image()).src = "css/images/last.gif",
			nextArrow : (new Image()).src = "css/images/next.gif",
			topNav : false
		}; 
		var product = ${productList};
		if(product==""){
	
		}else{
		$('#noSortData').hide();
		/* var tHead = "<tr><th>Product Code</th><th>Product Name</th><th>Edit</th><th>Delete</th> </tr>";
		$(tHead).appendTo("#userdata thead"); */
		var count=0;
		$.each(product, function(i, productObj) {
			count++;
			var tblRow = "<ul  id='"+count+"animate'>" + "<li class='prod-code-box' onmouseover='animaterowj("+count+")' onmouseout='deanimateRowj("+count+")'>" + productObj.productId
			 + "</li >"
			+ "<li class='prod-name-box'>" +productObj.categoryName+" "+productObj.subCategoryName+" "+productObj.productName+"</li>"
					+ "<li class='ten-box'>"
					+ '<a href="deleteProduct.htm?productId='
					+productObj.productId
					+ '" class="del" >Delete</a>' 
					+ '</li>'
					+ "<li class='eleven-box last'>"
					+ '<a href="editProduct.htm?productId='
					+ productObj.productId
					+ '" class="ico edit">Edit</a>' 
					+ '</li>'
					
					+ '</ul>';
			$(tblRow).appendTo("#userdata");
		
		});
		$(".del").click(function(event){
			return confirm('Do u want to Delete The Record?');
		});

		}
});
</script>
</head>
<body>
<!-- SET: WRAPPER -->
	<div class="wrapper">

		<!-- SET: CONTAINER -->
		<div class="container">
		
<!-- 		<div id="page-title">
				<ul>
					<li><h3>Home/Product</h3></li>
					<li><marquee behavior="scroll" direction="left">Welcome
							to mySalesInfo...</marquee></li>
				</ul>
			</div> -->
<div class="main_content">
    <div class="block">
        		<c:if test="${ empty productEdit}">
        <h2><span class="icon1">&nbsp;</span>Add New Product</h2>
           <form:form action="" commandName="productCmd" method="post" id="addForm" cssClass="form-horizontal">
           
            <div class="block-box-small">
                <div class="block-input">
                    <label>Category</label>
                  <form:select path="categoryId" id="categoryId"
												onchange="getSubCategorys(this.id)" cssClass="field size3">
												<form:option value="">--Select--</form:option>
												<form:options items="${categorys}"/>
											</form:select>
                </div> 
                <div class="block-input">
                    <label>Sub Category</label>
                   <c:choose>
											<c:when test="${ empty sessionScope.categoryId }">
										<form:select path="subCategoryId" id="subCategoryId" onchange="getAllProductsSearch(this.id)"
												cssClass="field size3">
												<form:option value="">--Select--</form:option>
											</form:select>
											</c:when>
											<c:otherwise>
										<form:select path="subCategoryId" id="subCategoryId" onchange="getAllProductsSearch(this.id)"
												cssClass="field size3">
												<form:option value="">--Select--</form:option>
												<form:option value="all">ALL</form:option>
												<form:options items="${subCategorys}"/>
											</form:select>
											</c:otherwise>
											</c:choose>
                </div>                 
                <div class="block-input  last">
                    <label>Product</label>
                <form:input path="productName" onkeyup="duplicateCheck(this.id)" cssClass="field size3"
												id="addName" />
                    
                </div>
            </div>
            <div class="block-footer">
               
								
								<div class="alert-danger" id="productMessage" style="display: none">
								<aside class="block-footer-left fail">
									<strong> <spring:message code="label.warning" /></strong>
									<spring:message code="label.productName" />
									<spring:message code="label.duplicateCheck" /> </aside>
							</div>
								<div class="alert-danger" id="productMessageEdit" style="display: none">
								<aside class="block-footer-left fail">
									<strong> <spring:message code="label.warning" /></strong>
									<spring:message code="label.productName" />
									<spring:message code="label.duplicateCheck" /> </aside>
							</div>
								<c:forEach var="success" items="${DeleteSus}">
									<aside class="block-footer-left sucessfully">
								<div class="alert-success">
										<strong><spring:message code="label.success" /> </strong>
										Product 
										<spring:message code="label.deleteSuccess" />
									</div> </aside>
								</c:forEach>
								<c:forEach var="fail" items="${DeleteFail}">
							<div class="alert-danger">
									<aside class="block-footer-left fail">
										<strong><spring:message code="label.error" /> </strong>
									Product 
										<spring:message code="label.deleteFail" /> </aside>
									</div>
								</c:forEach>
								<c:forEach var="success" items="${UpdateSus}">
							<aside class="block-footer-left sucessfully">			
									<div>
										<strong><spring:message code="label.success" /> </strong>
										Product 
										<spring:message code="label.updateSuccess" /> 
									</div></aside>
								</c:forEach>
								<c:forEach var="fail" items="${UpdateFail}">
								<aside class="block-footer-left fail">
								<div class="alert-danger">
								
										<strong><spring:message code="label.error" /> </strong>
									Product 
										<spring:message code="label.updateFail" />
									</div> </aside>
								</c:forEach>
								<c:forEach var="success" items="${param.AddSus}">
																<aside class="block-footer-left sucessfully">
								<div>
										<strong><spring:message code="label.success" /></strong>
										Product 
										<spring:message code="label.saveSuccess" /> 
									</div></aside>
								</c:forEach>
								<c:forEach var="fail" items="${param.AddFail}">
										<aside class="block-footer-left fail">
									<div class="alert-danger">
									
										<strong><spring:message code="label.error" /> </strong>
									Product 
										<spring:message code="label.saveFail" /> 
									</div></aside>
								</c:forEach>
          
                <aside class="block-footer-right">
                    <input class="btn-cancel" name="" value="Cancel" type="button">
                    <input class="btn-save" name="" value="Save" id="submitId" type="submit">
                </aside>
            </div>
        </form:form></c:if>
    </div>
    		<c:if test="${productEdit!=null}">
    		
    		<div class="block">
        <h2><span class="icon1">&nbsp;</span>Edit Product</h2>
         
           	<form:form action="" commandName="productCmd"
							method="post" id="editForm" cssClass="form-horizontal">
           	<form:hidden path="productId" id="productIdEdit"/>
            <div class="block-box-small">
              <%--   <div class="block-input">
                    <label>Category</label>
                  <form:select path="categoryId" id="categoryId"
												onchange="getSubCategorys(this.id)" cssClass="field size3">
												<form:option value="">--Select--</form:option>
												<form:options items="${categorys}"/>
											</form:select>
                </div>  --%>
               <%--  <div class="block-input">
                    <label>Sub Category</label>
                   <c:choose>
											<c:when test="${ empty sessionScope.categoryId }">
										<form:select path="subCategoryId" id="subCategoryId" onchange="getAllProductsSearch(this.id)"
												cssClass="field size3">
												<form:option value="">--Select--</form:option>
											</form:select>
											</c:when>
											<c:otherwise>
										<form:select path="subCategoryId" id="subCategoryId" onchange="getAllProductsSearch(this.id)"
												cssClass="field size3">
												<form:option value="">--Select--</form:option>
												<form:option value="all">ALL</form:option>
												<form:options items="${subCategorys}"/>
											</form:select>
											</c:otherwise>
											</c:choose>
                </div>  --%>                
                <div class="block-input  last">
                    <label>Product</label>
             <form:input path="productName" id="productNameEdit" onkeyup="editDuplicateCheckEdit(this.id)" cssClass="field size3" />
                    
                </div>
                </div>
                <div class="block-footer">
               
                <div class="alert-danger" id="productMessageEdit" style="display: none">
								<aside class="block-footer-left fail">
									<strong> <spring:message code="label.warning" /></strong>
									<spring:message code="label.productName" />
									<spring:message code="label.duplicateCheck" /> </aside>
							</div>
             
                 <aside class="block-footer-right">
                    <a href="productHome.htm"><input type="button" class="btn-cancel"
									value="<spring:message code="label.cancel"/>" id="cancel" /></a>
                    <input class="btn-save" name="" value="Update" id="updateId" type="submit">
                </aside>   </div>
                </form:form>
            </div>
    		
    		
    		</c:if>
    
    
    <div class="block table-toop-space">
        <div class="head-box"><h2><span class="icon2">&nbsp;</span>Current Products </h2>
        
        <aside class="search-box">        
         
								<form:form action="searchProduct.htm" commandName="productCmd"
									method="get">
										<input type="submit" class="search-bnt"  value="<spring:message code="label.search"/>" />
									<form:input path="productNameSearch" id="productNameSearch"  cssClass="search-input"/>
								
								</form:form>
						
            </aside></div>
            	 <div class="block-box-big block-box-last-big">
            	 
            <ul class="table-list">
                <li class="prod-code-box">Product Code
                    <ul>
                        <li><a class="top" href="#">&nbsp;</a></li>
                        <li><a class="bottom" href="#">&nbsp;</a></li>
                    </ul>
                </li>                
                <li class="prod-name-box">Product Name
                    <ul>
                        <li><a class="top" href="#">&nbsp;</a></li>
                        <li><a class="bottom" href="#">&nbsp;</a></li>
                    </ul>
                </li>
                <li class="ten-box">Delete</li>
                <li class="eleven-box last">Edit</li>
            </ul>
							
        <div class="table-list-blk data-grid-big" id="userdata"></div>
        </div>
        <div class="block-footer">
        
            <aside class="block-footer-left">Showing 1 to 10 of 57 entries</aside>
            <aside class="block-footer-right">
                <ul class="pagenation">
                    <li><a href="#">Next</a></li>
                    <li><a href="#">3</a></li>
                    <li><a href="#">2</a></li>
                    <li><a class="active" href="#">1</a></li>
                    <li><a href="#">Previous</a></li>
                </ul>
            </aside>
        </div>
    </div>
</div>

</div></div>





	<%-- <div class="shell">
		<!-- Main -->
		<div id="main">
			<div class="cl">&nbsp;</div>
			<!-- Content -->
			<div id="content">
				<!-- Box -->
				<c:if test="${productEdit==null}">
					<div class="box">
						<!-- Box Head -->
						<div class="box-head">
							<h2>
								<spring:message code="label.addNewProduct" />
							</h2>
						</div>
						<!-- End Box Head -->
						<form:form action="" commandName="productCmd"
							method="post" id="addForm">
							<div class="form">
								<table>
									<tr>
										<td><label><spring:message code="label.dept" /></label></td>
										<td><label><spring:message code="label.cat" /><span style="color: red">*</span></label></td>
										<td><label><spring:message code="label.subCat" /><span style="color: red">*</span></label></td>
										<td><label><spring:message code="label.product" /><span style="color: red">*</span></label></td>
									</tr>
									<tr>
										<td><form:select path="departmentId" id="departmentId"
												onchange="getCategorys(this.id)" cssClass="field size3">
												<form:option value=" ">--Select--</form:option>
												<form:options items="${departments}" />
											</form:select></td>
										<td><form:select path="categoryId" id="categoryId"
												onchange="getSubCategorys(this.id)" cssClass="field size3">
												<form:option value="">--Select--</form:option>
												<form:options items="${categorys}"/>
											</form:select></td>
											<c:choose>
											<c:when test="${ empty sessionScope.categoryId }">
										<td><form:select path="subCategoryId" id="subCategoryId" onchange="getAllProductsSearch(this.id)"
												cssClass="field size3">
												<form:option value="">--Select--</form:option>
											</form:select></td>
											</c:when>
											<c:otherwise>
											<td><form:select path="subCategoryId" id="subCategoryId" onchange="getAllProductsSearch(this.id)"
												cssClass="field size3">
												<form:option value="">--Select--</form:option>
												<form:option value="all">ALL</form:option>
												<form:options items="${subCategorys}"/>
											</form:select></td>
											</c:otherwise>
											</c:choose>
											
										<td><form:input path="productName" onkeyup="duplicateCheck(this.id)" cssClass="field size3"
												id="addName" /></td>
									</tr>
								</table>
							</div>
							<div class="buttons">
							<table>
							
								<tr style="border-bottom-color: green">
							<td colspan="2" id="productMessage" style="display: none;">
								<div class="alert-danger">
									<strong> <spring:message code="label.warning" /></strong>
									<spring:message code="label.productName" />
									<spring:message code="label.duplicateCheck" />
								</div>
							</td>
						</tr>
							
								<c:forEach var="success" items="${DeleteSus}">
								<td>	<div class="alert-success">
										<strong><spring:message code="label.success" /> </strong>
										Product 
										<spring:message code="label.deleteSuccess" />
									</div></td>
								</c:forEach>
								<c:forEach var="fail" items="${DeleteFail}">
								<td>	<div class="alert-danger">
										<strong><spring:message code="label.error" /> </strong>
									Product 
										<spring:message code="label.deleteFail" />
									</div></td>
								</c:forEach>
								<c:forEach var="success" items="${UpdateSus}">
									<td><div class="alert-success">
										<strong><spring:message code="label.success" /> </strong>
										Product 
										<spring:message code="label.updateSuccess" />
									</div></td>
								</c:forEach>
								<c:forEach var="fail" items="${UpdateFail}">
									<td><div class="alert-danger">
										<strong><spring:message code="label.error" /> </strong>
									Product 
										<spring:message code="label.updateFail" />
									</div></td>
								</c:forEach>
								<c:forEach var="success" items="${param.AddSus}">
									<td><div class="alert-success">
										<strong><spring:message code="label.success" /></strong>
										Product 
										<spring:message code="label.saveSuccess" />
									</div></td>
								</c:forEach>
								<c:forEach var="fail" items="${param.AddFail}">
									<td><div class="alert-danger">
										<strong><spring:message code="label.error" /> </strong>
									Product 
										<spring:message code="label.saveFail" />
									</div></td>
								</c:forEach></table>
								<input type="reset" class="button"
									value="<spring:message code="label.clear"/>" /> <input
									type="submit" class="button"
									value="<spring:message code="label.save"/>" id="submitId" />
							</div>
						</form:form>
					</div>
					<!-- Box -->
					<div class="box">
						<!-- Box Head -->
						<div class="box-head">
							<h2 class="left">
								<spring:message code="label.currentProducts" />
							</h2>
							<div class="right">
								<form:form action="searchProduct.htm" commandName="productCmd"
									method="get">
									<label><spring:message code="label.searchProduct" /></label>
									<form:input path="productName" id="productName"  class="field small-field" />
									<input type="submit" class="button"
										value="<spring:message code="label.search"/>" />
								</form:form>
							</div>
						</div>
						<!--End Table -->
						<c:if test="${not empty productsList }">
							</c:if>
							<table id="userdata" border="1" class="table">
							<thead>
							</thead>
							<tbody>
							</tbody>
						</table>
					</div>
					<!-- End Box -->
				</c:if>
				<c:if test="${productEdit!=null}">
					<!--Edit Box  -->
					<div class="box">
						<!-- Box Head -->
						<div class="box-head">
							<h2>
								<spring:message code="label.editProduct" />
							</h2>
						</div>
						<!-- End Box Head -->
						<form:form action="" commandName="productCmd"
							method="post" id="editForm">
							<div class="form">
								<table>
									<tr>
										<td><label><spring:message code="label.product" /></label></td>
									</tr>
									<form:hidden path="productId" id="productIdEdit"/>
									<tr>
										<td><form:input path="productName" id="productNameEdit" onkeyup="editDuplicateCheckEdit(this.id)" cssClass="field size3" /></td>
									</tr>
									<tr>
									<td colspan="2" id="productMessageEdit" style="display: none;">
								<div class="alert-danger">
									<strong> <spring:message code="label.warning" /></strong>
									<spring:message code="label.productName" />
									<spring:message code="label.duplicateCheck" />
								</div>
							</td></tr>
								</table>
							</div>
							<div class="buttons">
							<a href="productHome.htm"><input type="button" class="button"
									value="<spring:message code="label.cancel"/>" id="cancel" /></a>
								<input type="submit" class="button"
									value="<spring:message code="label.update"/>" id="updateId" />
							</div>
						</form:form>
					</div>
						<!-- Box -->
					<div class="box">
						<!-- Box Head -->
						<div class="box-head">
							<h2 class="left">
								<spring:message code="label.currentProducts" />
							</h2>
							<div class="right">
								<form:form action="searchProduct.htm" commandName="productCmd"
									method="get">
									<label><spring:message code="label.searchProduct" /></label>
									<form:input path="productName" id="productName"  class="field small-field" />
									<input type="submit" class="button"
										value="<spring:message code="label.search"/>" />
								</form:form>
							</div>
						</div>
						<!--End Table -->
						<c:if test="${not empty productsList }">
							</c:if>
							<table id="userdata" border="1" class="table">
							<thead>
							</thead>
							<tbody>
							</tbody>
						</table>
					</div>
					<!-- End Box -->
				</c:if>
				<!--Edit Box End  -->
			</div>
			<!-- End Content -->
			<div class="cl">&nbsp;</div>
		</div>
		<!-- Main -->
	</div>
</body> --%>
</html>