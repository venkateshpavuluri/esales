<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Sub-Category</title>
<link rel="stylesheet" href="js/jqueryui.css" />
<link rel="stylesheet" href="css/jquery-ui.css" />
<link rel="stylesheet"
	href="js/jquery-ui-1.10.3/themes/base/jquery-ui.css" />
<script src="js/jquery-1.7.2.js"></script>
<script type="text/javascript" src="js/pagination.js"></script>
<script src="js/jquery-ui.js"></script>
<script type="text/javascript" src="js/jquery.validate.min.js"></script>
<script type="text/javascript" src="js/MntValidator.js"></script>

<script type="text/javascript">
	$(document)
			.ready(
					function() {
						$('#descId').val('');
						$('#descId').focus();
						//AddForm Validations
						$('#submitId')
								.click(
										function(event) {
											//alert("hai");
											$('#addForm')
													.validate(
															{
																rules : {
																	deptId : {
																		required : true,
																	},
																	subCategoryDesc : {
																		required : true,
																		alphanumeric : true,
																		specialcharacters : true
																	},
																	categoryId : {
																		required : true
																	}

																},
																messages : {
																	deptId : {
																		required : "<font style='color: red;'><b>Department is Required</b></font>",
																	},
																	subCategoryDesc : {
																		required : "<font style='color: red;'><b>Sub Category is Required</b></font>",
																		alphanumeric : "<font style='color: red;'><b>First letter should be an alphanumeric.</b></font>",
																		specialcharacters : "<font style='color: red;'><b>Special Characters except &,_ are not allowed </b></font>",
																	},
																	categoryId : {
																		required : "<font style='color: red;'><b>Category is Required</b></font>",
																	},

																},

															});
											$('#addForm')
													.attr('action',
															"<c:url value='/subCatAdd.htm'/>");
											$("#addForm").submit();
											event.preventDefault();
										});

						$('#updateId')
								.click(
										function(event) {
											//alert("hai");
											$('#editForm')
													.validate(
															{
																rules : {
																	deptId : {
																		required : true
																	},
																	subCategoryDesc : {
																		required : true,
																		alphanumeric : true,
																		specialcharacters : true
																	},
																	categoryId : {
																		required : true
																	}
																},
																messages : {
																	deptId : {
																		required : "<font style='color: red;'><b>Department is Required</b></font>",
																	},
																	subCategoryDesc : {
																		required : "<font style='color: red;'><b>Sub-Category is Required</b></font>",
																		alphanumeric : "<font style='color: red;'><b>First letter should be an alphanumeric.</b></font>",
																		specialcharacters : "<font style='color: red;'><b>Special Characters except &,_ are not allowed </b></font>",
																	},
																	categoryId : {
																		required : "<font style='color: red;'><b>Category is Required</b></font>",
																	},

																},
															});
											$('#editForm')
													.attr('action',
															"<c:url value='/subCatUpdate.htm'/>");
											$("#editForm").submit();
											event.preventDefault();
										});
					});
</script>

<script type="text/javascript">
	function populateCategory() {
		var id = $('#deptId').val();
		$.ajax({
			type : "POST",
			url : "populateCategory.htm",
			data : "deptId=" + id,
			dataType : "json",
			success : function(response) {
				if (response != "") {
					var optionsForClass = "";
					optionsForClass = $("#categoryId").empty();
					optionsForClass.append(new Option("--Select--", ""));
					$.each(response, function(i, subCategoryPopulateBean) {
						optionsForClass.append(new Option(
								subCategoryPopulateBean.categoryDesc,
								subCategoryPopulateBean.categoryId));
					});
				} else {
					optionsForClass = $("#categoryId").empty();
					optionsForClass.append(new Option("--Select--", ""));
				}
			},
			error : function(e) {
			}
		});

	}
</script>
<script type="text/javascript">
	function duplicateCheck() {
		var subcatDesc = $('#descId').val();
		$
				.ajax({
					type : "POST",
					url : "subCatDuplicate.htm",
					data : "subCategoryDesc=" + subcatDesc,
					/* dataType : "json",  */
					success : function(response) {
						//alert(response);
						if (response != "") {
							document.getElementById("dupMessage").style.display = "block";
							$('#submitId').attr('disabled', 'disabled');
							$('#submitId').hide();
						} else {
							document.getElementById("dupMessage").style.display = "none";
							$('#submitId').show();
							$('#submitId').removeAttr('disabled');
						}
					},
					error : function(e) {
					}
				});
	}
</script>
<script type="text/javascript">
	function duplicateEditCheck() {

		var subcatId = $('#subCategoryId').val();
		var subcatDesc = $('#subCategoryDesc').val();
		//alert(subcatId);
		$
				.ajax({
					type : "POST",
					url : "subCatDuplicate.htm",
					data : "subCategoryDesc=" + subcatDesc + "&subCategoryId="
							+ subcatId,
					/* dataType : "json",  */
					success : function(response) {
						//alert(response);
						if (response != "") {
							document.getElementById("dupMessage").style.display = "block";
							$('#updateId').attr('disabled', 'disabled');
							$('#updateId').hide();
						} else {
							document.getElementById("dupMessage").style.display = "none";
							$('#updateId').show();
							$('#updateId').removeAttr('disabled');
						}
					},
					error : function(e) {
					}
				});
	}
</script>
<script type="text/javascript">
	function onSelect() {
		/* var options = {
			currPage : 1,
			optionsForRows : [ 2, 3, 5 ],
			rowsPerPage : 5,
			firstArrow : (new Image()).src = "css/images/first.gif",
			prevArrow : (new Image()).src = "css/images/prev.gif",
			lastArrow : (new Image()).src = "css/images/last.gif",
			nextArrow : (new Image()).src = "css/images/next.gif",
			topNav : false
		}; */
		var catId = $('#categoryId').val();
		//alert(catId);
$("#userdata ul li").remove();
		$.ajax({
					type : "POST",
					url : "searchingBasedOnCategory.htm",
					data : "categoryId=" + catId,
					dataType : "json",
					success : function(SubCatList) {
						//alert("dfm");
						if (SubCatList == "") {
							$('#tablePagination').remove();
							$("#userdata ul li").remove();
							$('#noSortData').show();
						} else {

							$
									.each(
											SubCatList,
											function(i, subcatObj) {
												//alert(subcatObj.subCategoryDesc+subcatObj.categoryDesc); 
												var tblRow = "<ul>"
														/* + "<li class='cat-box'>"
														+ subcatObj.subCategoryCode
														+ "</li>" */
														+ "<li class='dept-box'>"
														+ subcatObj.categoryDesc
														+ "-"
														+ subcatObj.subCategoryDesc
														+ "</li>"
														+ "<li class='ten-box'>"
														+ '<a href="deleteSubCat.htm?subCategoryId='
														+ subcatObj.subCategoryId
														+ '" class="ico del" onclick="return confirm("Do You Want To Delete This Record?")">Delete</a>'
														+ '</li>'
														+ "<li class='eleven-box last'>"
														+ '<a href="editSubCat.htm?subCategoryId='
														+ subcatObj.subCategoryId
														+ '" class="ico edit">Edit</a>'
														+ '</li>'

														/* + "<td>"
														+ '<input type="radio" name="statuss"  id="sts" checked=""/>'
														+ '</td>' */

														+ '</ul>';
												$(tblRow).appendTo("#userdata");
											});
							/* $('#userdata').tablePagination(options);
							$('#sortlist').show(); */
						}
					},
					error : function(e) {
					}
				});
	}
</script>
<script type="text/javascript">
	$(document)
			.ready(
					function() {
						/* var options = {
							currPage : 1,
							optionsForRows : [ 2, 3, 5 ],
							rowsPerPage : 5,
							firstArrow : (new Image()).src = "css/images/first.gif",
							prevArrow : (new Image()).src = "css/images/prev.gif",
							lastArrow : (new Image()).src = "css/images/last.gif",
							nextArrow : (new Image()).src = "css/images/next.gif",
							topNav : false
						}; */
						var subcategoryList = ${SubCatList};
						//alert(subcategoryList);
						if (subcategoryList == "") {
							$('#tablePagination').remove();
							$("#userdata ul li").remove();
							$('#noSortData').show();
						} else {
							$('#noSortData').hide();
							/* var tHead =  "<tr><th><spring:message code="label.subCatCode" /><th><spring:message code="label.subCat" /></th><th><spring:message code="label.delete" /></th><th><spring:message code="label.edit" /></th></tr>";
							$(tHead).appendTo("#userdata thead"); */
							$
									.each(
											subcategoryList,
											function(i, subcatObj) {
												//alert(subcatObj.subCategoryDesc+subcatObj.categoryDesc); 
												var tblRow = "<ul>"
														/* + "<li class='prod-code-box'>"
														+ subcatObj.subCategoryCode
														+ "</li>" */
														+ "<li class='dept-box'>"
														+ subcatObj.categoryDesc
														+ "-"
														+ subcatObj.subCategoryDesc
														+ "</li>"
														+ "<li class='ten-box'>"
														+ '<a href="deleteSubCat.htm?subCategoryId='
														+ subcatObj.subCategoryId
														+ '" class="" onclick="return confirm("Do You Want To Delete This Record?")">Delete</a>'
														+ '</li>'
														+ "<li class='eleven-box last'>"
														+ '<a href="editSubCat.htm?subCategoryId='
														+ subcatObj.subCategoryId
														+ '" class="">Edit</a>'
														+ '</li>'

														/* + "<td>"
														+ '<input type="radio" name="statuss"  id="sts" checked=""/>'
														+ '</td>' */

														+ '</ul>';
												$(tblRow).appendTo("#userdata");
												
											});
							/* $('#userdata').tablePagination(options);
							$('#sortlist').show(); */
						}
						;
					});
</script>
</head>
<body>
	<div class="wrapper">
		<div class="container">
			<div class="main_content">
				<c:if test="${empty subCatEdit}">
					<div class="block">
						<h2>
							<span class="icon1">&nbsp;</span>
							<spring:message code="label.addNewSubCategory"></spring:message>
						</h2>
						<form:form action="" commandName="subCatCmd"
							cssClass="form-horizontal" method="post" id="addForm">
							<div class="block-box-small">
								<div class="block-input">
									<label>Category</label> <form:select path="categoryId" onchange="onSelect()">
										<form:option value="">--Select--</form:option>
										<form:option value="">all</form:option>
										<form:options items="${categroyDetails }"></form:options>
									</form:select>
								</div>
								<div class="block-input">
									<label>Sub Category</label><form:input path="subCategoryDesc" onkeyup="duplicateCheck()"  id="descId" />
								</div>
							</div>
							<div class="block-footer">
								<aside class="block-footer-left sucessfully">
								<c:forEach var="success" items="${param.AddSus}">
										<div class="alert-success">
											<strong><spring:message code="label.success" /></strong>
											Sub-Category
											<spring:message code="label.saveSuccess" />

										</div>
									</c:forEach>
									<c:forEach var="fail" items="${param.AddFail}">
										<div class="alert-danger">
											<strong><spring:message code="label.error" /> </strong>
											Sub-Category
											<spring:message code="label.saveFail" />

										</div>
									</c:forEach>
									<c:forEach var="success" items="${param.UpdateSus}">
										<div class="alert-success">
											<strong><spring:message code="label.success" /> </strong>
											Sub-Category
											<spring:message code="label.updateSuccess" />
										</div>
									</c:forEach>
									<c:forEach var="fail" items="${param.UpdateFail}">
										<div class="alert-danger">
											<strong><spring:message code="label.error" /> </strong>
											Sub-Category
											<spring:message code="label.updateFail" />
										</div>
									</c:forEach>
									<c:forEach var="success" items="${param.DeleteSus}">
										<div class="alert-success">
											<strong><spring:message code="label.success" /> </strong>
											Sub-Category
											<spring:message code="label.deleteSuccess" />
										</div>
									</c:forEach>
									<c:forEach var="fail" items="${param.DeleteFail}">
										<div class="alert-danger">
											<strong><spring:message code="label.error" /> </strong>
											Sub-Category
											<spring:message code="label.deleteFail" />
										</div>
									</c:forEach>
									<div class="alert-danger" id="dupMessage"
										style="display: none;">
										<strong><spring:message code="label.error" /> </strong>
										Sub-Category
										<spring:message code="label.duplicateCheck" />
									</div>
								</aside>
								<aside class="block-footer-right">
									<a href="subCatHome.htm"><input type="button"
										class="btn-cancel"
										value="<spring:message code="label.clear"/>" id="clearId" /></a>
									<input type="submit" class="btn-save"
										value="<spring:message code="label.save"/>" id="submitId" />
								</aside>
							</div>
						</form:form>
					</div>
					<!-- <div class="block"> -->
						<!-- Box Head -->

						<%-- <form:form action="" commandName="subCatCmd"
							cssClass="form-horizontal" method="post" id="addForm">
							<div class="block-box-exp">
								<div class="block-input">
									<label><spring:message code="label.dept" /></label> <label
										class="block-input label"><spring:message
											code="label.cat" /><span style="color: red">*</span></label>
									<form:select path="categoryId" onchange="onSelect()"
										cssClass="block-input select">
										<form:option value="">--Select--</form:option>
										<form:option value="">all</form:option>
										<form:options items="${categroyDetails }"></form:options>
									</form:select>
								</div>
								<div class="block-input">
									<label class="block-input label"><spring:message
											code="label.subcat" /><span style="color: red">*</span></label>

									<form:select path="deptId" cssClass="field size3"
										onchange="populateCategory()">
										<form:option value="">--Select--</form:option>

										<form:options items="${departmentDetails }"></form:options>
									</form:select>

									<form:input path="subCategoryDesc" onkeyup="duplicateCheck()"
										cssClass="block-input input" id="descId" />


								</div>
							</div>
							<div class="block-footer">
								<aside class="block-footer-left sucessfully">
									<c:forEach var="success" items="${param.AddSus}">
										<div class="alert-success">
											<strong><spring:message code="label.success" /></strong>
											Sub-Category
											<spring:message code="label.saveSuccess" />

										</div>
									</c:forEach>
									<c:forEach var="fail" items="${param.AddFail}">
										<div class="alert-danger">
											<strong><spring:message code="label.error" /> </strong>
											Sub-Category
											<spring:message code="label.saveFail" />

										</div>
									</c:forEach>
									<c:forEach var="success" items="${param.UpdateSus}">
										<div class="alert-success">
											<strong><spring:message code="label.success" /> </strong>
											Sub-Category
											<spring:message code="label.updateSuccess" />
										</div>
									</c:forEach>
									<c:forEach var="fail" items="${param.UpdateFail}">
										<div class="alert-danger">
											<strong><spring:message code="label.error" /> </strong>
											Sub-Category
											<spring:message code="label.updateFail" />


										</div>
									</c:forEach>
									<c:forEach var="success" items="${param.DeleteSus}">
										<div class="alert-success">
											<strong><spring:message code="label.success" /> </strong>
											Sub-Category
											<spring:message code="label.deleteSuccess" />

										</div>
									</c:forEach>
									<c:forEach var="fail" items="${param.DeleteFail}">
										<div class="alert-danger">
											<strong><spring:message code="label.error" /> </strong>
											Sub-Category
											<spring:message code="label.deleteFail" />
										</div>
									</c:forEach>
									<div class="alert-danger" id="dupMessage"
										style="display: none;">
										<strong><spring:message code="label.error" /> </strong>
										Sub-Category
										<spring:message code="label.duplicateCheck" />
									</div>
								</aside>
								<aside class="block-footer-right">
									<a href="subCatHome.htm"><input type="button"
										class="btn-cancel"
										value="<spring:message code="label.clear"/>" id="clearId" /></a>
									<input type="submit" class="btn-save"
										value="<spring:message code="label.save"/>" id="submitId" />
								</aside>

							</div>
						</form:form> --%>

					<!-- </div> -->

					<div class="block table-toop-space">
						<div class="head-box">
							<h2>
								<span class="icon2">&nbsp;</span><spring:message code="label.currentSubCategory"/>
							</h2>
							<aside class="search-box">
								<form:form action="subCatSearch.htm" commandName="subCatCmd"
									method="get">
							<input type="submit" class="search-bnt"
										value="<spring:message code="label.search"/>" />
									<form:input path="subCategoryDesc" class="search-input" id="searchIds"/>
								</form:form>
							</aside>
						</div>
						<div class="block-box-big block-box-last-big">
							<ul class="table-list">
								<li class="dept-box"><spring:message code="label.subCat"/>
									<ul>
										<li><a class="top" href="#">&nbsp;</a></li>
										<li><a class="bottom" href="#">&nbsp;</a></li>
									</ul>
								</li>
								<li class="ten-box"><spring:message code="label.delete"/></li>
								<li class="eleven-box last"><spring:message code="label.edit"/></li>
							</ul>
							<div class="table-list-blk data-grid-big" id="userdata"></div>
						</div>
						<div class="block-footer">
							<aside class="block-footer-left">Showing 1 to 10 of 57
								entries</aside>
							<aside class="block-footer-right">
								<ul class="pagenation">
									<li><a href="#">Next</a></li>
									<li id="userdata"><a href="#">3</a></li>
									<li id="userdata"><a href="#">2</a></li>
									<li id="userdata"><a class="active" href="#">1</a></li>
									<li id="userdata"><a href="#">Previous</a></li>
								</ul>
							</aside>
						</div>
					</div>
				</c:if>
			</div>
			<c:if test="${not empty subCatEdit}">

				<div class="head-box">
					<h2>
						<spring:message code="label.editSubCategory" />
					</h2>
				</div>
				<form:form action="" commandName="subCatCmd" method="post"
					id="editForm" cssClass="form-horizontal">
					<div class="block-box-exp">
						<div class="block-input">
							<form:hidden path="subCategoryId" />
							<label><spring:message code="label.subCat" /><span
								style="color: red;">*</span></label>
							<form:input path="subCategoryDesc" onkeyup="duplicateEditCheck()" />

						</div>
					</div>
					<div class="block-footer">
						<aside class="block-footer-left sucessfully">
							<div class="alert-danger" id="dupMessage" style="display: none;">
								<strong><spring:message code="label.error" /> </strong>
								Sub-Category
								<spring:message code="label.duplicateCheck" />

							</div>
						</aside>
						<aside class="block-footer-right">
							<a href="subCatHome.htm"><input type="button"
								class="btn-cancel" value="<spring:message code="label.cancel"/>"
								id="cancelId" /></a> <input type="submit" class="btn-save"
								value="<spring:message code="label.update"/>" id="updateId" />
						</aside>
					</div>
				</form:form>


				<div class="block table-toop-space">
						<div class="head-box">
							<h2>
								<span class="icon2">&nbsp;</span><spring:message code="label.currentSubCategory"/>
							</h2>
							<aside class="search-box">
								<form:form action="subCatSearch.htm" commandName="subCatCmd"
									method="get">
							<input type="submit" class="search-bnt"
										value="<spring:message code="label.search"/>" />
									<form:input path="subCategoryDesc" class="search-input" id="searchIds"/>
								</form:form>
							</aside>
						</div>
						<div class="block-box-big block-box-last-big">
							<ul class="table-list">
								<li class="dept-box"><spring:message code="label.subCat"/>
									<ul>
										<li><a class="top" href="#">&nbsp;</a></li>
										<li><a class="bottom" href="#">&nbsp;</a></li>
									</ul>
								</li>
								<li class="ten-box"><spring:message code="label.delete"/></li>
								<li class="eleven-box last"><spring:message code="label.edit"/></li>
							</ul>
							<div class="table-list-blk data-grid-big" id="userdata"></div>
						</div>
						<div class="block-footer">
							<aside class="block-footer-left">Showing 1 to 10 of 57
								entries</aside>
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
			</c:if>
		</div>
	</div>
</body>
</html>