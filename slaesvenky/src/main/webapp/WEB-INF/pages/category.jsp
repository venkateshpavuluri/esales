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
<title>Category</title>
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
						$('#ctdescId').val('');
						$('#ctdescId').focus();
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
																	categoryDesc : {
																		required : true,
																		alphanumeric : true,
																		specialcharacters : true
																	}

																},
																messages : {
																	deptId : {
																		required : "<font style='color: red;'><b>Department is Required</b></font>",
																	},
																	categoryDesc : {
																		required : "<font style='color: red;'><b>Category is Required</b></font>",
																		alphanumeric : "<font style='color: red;'><b>First letter should be an alphanumeric.</b></font>",
																		specialcharacters : "<font style='color: red;'><b>Special Characters except &,_ are not allowed </b></font>",
																	}

																},

															});
											$('#addForm')
													.attr('action',
															"<c:url value='/catAdd.htm'/>");
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
																	categoryDesc : {
																		required : true,
																		alphanumeric : true,
																		specialcharacters : true
																	}

																},
																messages : {
																	deptId : {
																		required : "<font style='color: red;'><b>Department is Required</b></font>",
																	},
																	categoryDesc : {
																		required : "<font style='color: red;'><b>Category is Required</b></font>",
																		alphanumeric : "<font style='color: red;'><b>First letter should be an alphanumeric.</b></font>",
																		specialcharacters : "<font style='color: red;'><b>Special Characters except &,_ are not allowed </b></font>"
																	}

																},

															});

											$('#editForm')
													.attr('action',
															"<c:url value='/catUpdate.htm'/>");
											$("#editForm").submit();
											event.preventDefault();
										});

					});
</script>
<script type="text/javascript">
	function duplicateChecks() {
		var catDesc = $('#ctdescId').val();
		$
				.ajax({
					type : "POST",
					url : "catDuplicate.htm",
					data : "catDesc=" + catDesc,
					/* dataType : "json",  */
					success : function(response) {
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
		var categoryDesc = $('#categoryDesc').val();
		var categoryId = $('#categoryId').val();
		$
				.ajax({
					type : "POST",
					url : "catEditDuplicate.htm",
					data : "categoryDesc=" + categoryDesc + "&categoryId="
							+ categoryId,
					/* dataType : "json",  */
					success : function(response) {
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
		var deptId = $('#deptId').val();
		$("#userdata ul li").remove();
		$
				.ajax({
					type : "POST",
					url : "searchingBasedOnDepartment.htm",
					data : "deptId=" + deptId,
					dataType : "json",
					success : function(CatList) {
						//alert("dfm");
						if (CatList == "") {
							/* $('#userdata').tablePagination({});
							$("#userdata thead th").remove();
							$("#userdata tbody tr").remove();
							$('#noSortData').show();
							$('#displayTag').hide(); */
						} else {
							/* $('#displayTag').hide();
							$('#noSortData').hide();

							var tHead = "<tr><th><spring:message code="label.catCode" /><th><spring:message code="label.cat" /></th><th><spring:message code="label.delete" /></th><th><spring:message code="label.edit" /></th></tr>";
							$(tHead).appendTo("#userdata thead"); */

							$
									.each(
											CatList,
											function(i, categoryObj) {
												//alert(i + ": " +categoryObj.categotyDesc+""+categoryObj.categoryId);
												var tblRow = "<ul>"
														/* + "<li class='dept-box'>"
														+ categoryObj.categoryCode
														+ "</li>" */
														+ "<li class='dept-box'>"
														+ categoryObj.categoryDesc
														+ "</li>"
														+ "<li class='ten-box'>"
														+ '<a href="deleteCat.htm?categoryId='
														+ categoryObj.categoryId
														+ '" class="" onclick="return confirm("Do You Want To Delete This Record?")">Delete</a>'
														+ '</li>'
														+ "<li class='eleven-box last'>"
														+ '<a href="editCat.htm?categoryId='
														+ categoryObj.categoryId
														+ '" class="">Edit</a>'
														+ '</li>'
														/* + "<td>"
														+ '<input type="radio" name="statuss" onchange="checkFun()" id="st" checked=""/>'
														+ '</td>' */

														+ '</ul>';
												//alert(tblRow);
												$(tblRow).appendTo(	"#userdata");
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

						var categoryList = ${CatList};
						if (categoryList == "") {
							$('#tablePagination').remove();
							$("#userdata ul li").remove();
							$('#noSortData').show();
						} else {
							$('#noSortData').hide();
							/* var tHead = "<tr><th><spring:message code="label.catCode" /><th><spring:message code="label.cat" /></th><th><spring:message code="label.delete" /></th><th><spring:message code="label.edit" /></th></tr>";
							$(tHead).appendTo("#userdata thead"); */
							$
									.each(
											categoryList,
											function(i, catObj) {
												//alert(catObj.categoryCode);
												var tblRow = "<ul>"
														/* + "<li class='prod-code-box'>"
														+catObj.categoryCode
														+ "</li>" */
														+ "<li class='dept-box'>"
														+ catObj.categoryDesc
														+ "</li>"
														+ "<li class='ten-box'>"
														+ '<a href="deleteCat.htm?categoryId='
														+ catObj.categoryId
														+ '" class="" onclick="return confirm("Do You Want To Delete This Record?")">Delete</a>'
														+ '</li>'
														+ "<li class='eleven-box last'>"
														+ '<a href="editCat.htm?categoryId='
														+ catObj.categoryId
														+ '" class="">Edit</a>'
														+ '</li>'

														/* + "<td>"
														+ '<input type="radio" name="statuss"  id="sts" checked=""/>'
														+ '</td>' */

														+ '</ul>';
												$(tblRow).appendTo("#userdata");
												//alert(tblRow);
											});
							/* $('#userdata').tablePagination(options);
							$('#sortlist').show(); */
						}
						;
					});
</script>
<script type="text/javascript">
	$(document).ready(function() {
		$('#ctdescId').focus();
	});
</script>
</head>
<body>
	<div class="wrapper">
		<div class="container">
			<div class="main_content">
				<c:if test="${empty catEdit}">
					<div class="block">
						<h2>
							<span class="icon1">&nbsp;</span>
							<spring:message code="label.addNewCategory"></spring:message>
						</h2>
						<!-- End Box Head -->
						<form:form action="" commandName="catCmd" method="post"
							id="addForm" cssClass="form-horizontal">
							<div class="block-box-exp">
								<c:set value="mntClient" var="clientIds" />
								<c:choose>
									<c:when test="${sessionScope.clientNames eq clientIds }">
										<div class="block-input">
											<label><spring:message code="label.dept" /><span
												style="color: red;">*</span></label>
											<form:select path="deptId"
												onchange="onSelect()">
												<form:option value="">--Select--</form:option>
												<form:option value="">--all--</form:option>
												<form:options items="${deptDeatails}"></form:options>
											</form:select>
										</div>
										<div class="block-input">
											<label><spring:message code="label.cat" /><span
												style="color: red;">*</span></label>
											<form:input path="categoryDesc" onkeyup="duplicateChecks()"
												 id="ctdescId" />
										</div>
									</c:when>
									<c:otherwise>
										<div class="block-input">
											<label><spring:message code="label.cat" /><span
												style="color: red;">*</span></label>
											<form:input path="categoryDesc" onkeyup="duplicateChecks()"
												id="ctdescId" />
										</div>
									</c:otherwise>
								</c:choose>

								<%-- <form:errors path="categoryDesc" /> --%>


							</div>
							<div class="block-footer">
								<aside class="block-footer-left sucessfully">

									<c:forEach var="success" items="${param.AddSus}">
										<div class="alert-success">
											<strong><spring:message code="label.success" /></strong>
											Category
											<spring:message code="label.saveSuccess" />
										</div>
									</c:forEach>
									<c:forEach var="fail" items="${param.AddFail}">
										<div class="alert-danger">
											<strong><spring:message code="label.error" /> </strong>
											Category
											<spring:message code="label.saveFail" />

										</div>
									</c:forEach>
									<c:forEach var="success" items="${param.UpdateSus}">
										<div class="alert-success">
											<strong><spring:message code="label.success" /> </strong>
											Category
											<spring:message code="label.updateSuccess" />

										</div>
									</c:forEach>
									<c:forEach var="fail" items="${param.UpdateFail}">
										<div class="alert-danger">
											<strong><spring:message code="label.error" /> </strong>
											Category
											<spring:message code="label.updateFail" />

										</div>
									</c:forEach>
									<c:forEach var="success" items="${param.DeleteSus}">
										<div class="alert-success">
											<strong><spring:message code="label.success" /> </strong>
											Category
											<spring:message code="label.deleteSuccess" />

										</div>
									</c:forEach>
									<c:forEach var="fail" items="${param.DeleteFail}">
										<div class="alert-danger">
											<strong><spring:message code="label.error" /> </strong>
											Category
											<spring:message code="label.deleteFail" />
										</div>
									</c:forEach>
									<div id="dupMessage" style="display: none;"
										class="alert-danger">
										<strong><spring:message code="label.error" /> </strong>
										Category
										<spring:message code="label.duplicateCheck" />
									</div>
								</aside>
								<aside class="block-footer-right">
									<a href="catHome.htm"><input type="button"
										class="btn-cancel"
										value="<spring:message code="label.clear"/>" id="cancelId" /></a>
									<input type="submit" class="btn-save"
										value="<spring:message code="label.save"/>" id="submitId" />
								</aside>
							</div>
						</form:form>
					</div>

					

					<%-- <div class="block table-toop-space">
						<!-- Box Head -->
						<div class="head-box">
							<h2>
								<span class="icon2">&nbsp;</span>
								<spring:message code="label.currentCategory" />
							</h2>
							<aside class="search-box">
								<form:form action="searchCat.htm" commandName="catCmd"
									method="get">
									<label><spring:message code="label.searchCategory"></spring:message></label>
									<input type="submit" class="search-bnt"
										value="<spring:message code="label.search"/>" />
									<form:input path="categoryDesc" id="addSearchId"
										class="search-input" />
								</form:form>
							</aside>
						</div>
						<div>
							<ul class="table-list">
								<li class="prod-code-box"><spring:message code="label.catCode" /></li>
							    <li class="dept-box"><spring:message code="label.cat" /></li>
								<li class="ten-box"><spring:message code="label.delete" /></li>
								<li class="eleven-box last"><spring:message code="label.edit" /></li>
							</ul>
							<div class="table-list-blk data-grid-big" id="userdata"></div>
						</div>
						<div class="block-footer">
						<!-- <aside class="block-footer-left">Showing 1 to 10 of 57 entries</aside> -->
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
					</div> --%>

				</c:if>
			</div>
			<c:if test="${not empty catEdit }">

				<div class="head-box">
					<h2>
						<spring:message code="label.editCategory"></spring:message>
					</h2>
				</div>
				<!-- End Box Head -->

				<form:form action="" commandName="catCmd" method="post"
					id="editForm" cssClass="form-horizontal">
					<div class="block-box-exp">
					<div class="block-input">
					<form:hidden path="categoryId" />
											<label><spring:message code="label.cat" /><span
												style="color: red;">*</span></label>
											<form:input path="categoryDesc" onkeyup="duplicateChecks()"
												cssClass="block-input input" />
										</div>
						
					</div>
					<div class="block-footer">
						<aside class="block-footer-left sucessfully">
							<div id="dupMessage" style="display: none;" class="alert-danger">
								<strong><spring:message code="label.error" /> </strong>
								Category
								<spring:message code="label.duplicateCheck" />
							</div>
						</aside>
						<aside class="block-footer-right">
							<a href="catHome.htm"><input type="button" class="btn-cancel"
								value="<spring:message code="label.cancel"/>" id="cancelId" /></a>
							<input type="submit" class="btn-save"
								value="<spring:message code="label.update"/>" id="updateId" />
						</aside>
					</div>
				</form:form>


				<!-- Box -->
				
			</c:if>
			<!--Edit Box End  -->
<div class="block table-toop-space">
						<div class="head-box">
							<h2>
								<span class="icon2">&nbsp;</span>Current Categories
							</h2>
							<aside class="search-box">
								<input class="search-bnt" name="" value="Search" type="button">
								<input class="search-input" name="" type="text">
							</aside>
						</div>
						<div class="block-box-big block-box-last-big">
							<ul class="table-list">
								<li class="dept-box">Category Name
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
		</div>
		<!-- End Content -->
	</div>
</body>
</html>