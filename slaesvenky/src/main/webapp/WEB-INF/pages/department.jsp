<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Department</title>
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
						$('#searchIdEdit').val('');
						$('#addName').focus();
						//AddForm Validations
						$('#submitId')
								.click(
										function(event) {
											//alert("hai");
											$('#addForm')
													.validate(
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

											$('#addForm')
													.attr('action',
															"<c:url value='/deptAdd.htm'/>");
											$("#addForm").submit();
											event.preventDefault();

										});

						$('#updateId')
								.click(
										function(event) {
											$('#editForm')
													.validate(
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
											$('#editForm')
													.attr('action',
															"<c:url value='/deptUpdate.htm'/>");
											$("#editForm").submit();
											event.preventDefault();

										});
						$('#messagesId').fadeIn().delay(3000).fadeOut('slow');
					});
	/* function checkAll(id)
	{
		alert(id);
		var count=$("#checkCount").val();
		for(var i=1;i<=count;i++)
			{
		$("#"+i).prop("checked",true);
	}
	}
	 */

	function getDept(id) {
		var dept = $('#' + id).val();
		//alert(dept);
		$.ajax({
			type : "POST",
			url : "deptDuplCheck.htm",
			data : "deptName=" + dept,
			success : function(response) {
				if (response != "") {
					document.getElementById("duplmsg").style.display = "block";
					document.getElementById("addsus").style.display = "none";
					document.getElementById("upsus").style.display = "none";
					$('#submitId').attr('disabled', 'disabled');
					$('#submitId').hide();

				} else {
					document.getElementById("duplmsg").style.display = "none";
					$('#submitId').show();
					$('#submitId').removeAttr('disabled');
				}
			},
			error : function(e) {
				//alert('Error: ' + e);
			}
		});

	}

	function getDeptEditFun(id) {
		var dept = $('#' + id).val();
		var deptId = $('#deptIdEdit').val();
		//alert(deptId);
		$
				.ajax({
					type : "POST",
					url : "deptDuplCheckEdit.htm",
					data : "deptName=" + dept + "&deptId=" + deptId,
					success : function(response) {
						if (response != "") {
							document.getElementById("duplmsgEdit").style.display = "block";
							document.getElementById("addsus").style.display = "none";
							document.getElementById("upsus").style.display = "none";
							$('#updateId').attr('disabled', 'disabled');
							$('#updateId').hide();

						} else {
							document.getElementById("duplmsgEdit").style.display = "none";
							$('#updateId').show();
							$('#updateId').removeAttr('disabled');

						}
					},
					error : function(e) {
						//alert('Error: ' + e);
					}
				});

	}
	function checkAll(theForm) { // check all the checkboxes in the list
		for ( var i = 0; i < theForm.elements.length; i++) {
			var e = theForm.elements[i];
			var eName = e.name;
			if (eName != 'allbox' && (e.type.indexOf("checkbox") == 0)) {
				e.checked = theForm.allbox.checked;
			}
		}
	}
	function radios(clicked) {
		var i;
		var form = clicked.form;
		var checkboxes = form.elements[clicked.name];
		if (!clicked.checked || !checkboxes.length) {
			clicked.parentNode.parentNode.className = "";
			return false;
		}

		for (i = 0; i < checkboxes.length; i++) {
			if (checkboxes[i] != clicked) {
				checkboxes[i].checked = false;
				checkboxes[i].parentNode.parentNode.className = "";
			}
		}

		// highlight the row    
		clicked.parentNode.parentNode.className = "over";
	}
</script>

<script type="text/javascript">
	$(document)
			.ready(
					function() {
						var options = {
							currPage : 1,
							optionsForRows : [ 2, 3, 5 ],
							rowsPerPage : 5,
							firstArrow : (new Image()).src = "css/images/first.gif",
							prevArrow : (new Image()).src = "css/images/prev.gif",
							lastArrow : (new Image()).src = "css/images/last.gif",
							nextArrow : (new Image()).src = "css/images/next.gif",
							topNav : false
						};
						var dept = ${DeptList};
						//alert(dept);
						if (dept == "" || dept == null) {
							$('#tablePagination').remove();
							$("#userdata ul li").remove();
							$('#noSortData').show();

						} else {
							$('#noSortData').hide();
							/* var tHead = "<tr><th><spring:message code="label.dept" /></th><th><spring:message code="label.delete" /></th><th><spring:message code="label.edit" /></th></tr>";
							$(tHead).appendTo("#userdata thead"); */

							$
									.each(
											dept,
											function(i, deptObj) {
												//alert(i + ": " + deptObj.status);
												var tblRow = "<ul>"
														+ "<li class='dept-box'>"
														+ deptObj.deptName
														+ "</li>"
														+ "<li class='ten-box'>"
														+ "<a href='javascript:void(0)' id='"
														+ deptObj.deptId
														+ "' onclick='forDelete(this.id)' class='ico del'>Delete</a>"
														+ "</li>"
														+ "<li class='eleven-box last'>"
														+ '<a href="editDept.htm?deptId='
														+ deptObj.deptId
														+ '" class="ico edit">Edit</a>'
														+ '</li>' + '</ul>';
												$(tblRow).appendTo("#userdata");
											});
							$('#block-footer').tablePagination(options);

						}
					});

	function forDelete(id) {
		var count = 0;
		var res = confirm("Do You Want To Delete This Record?");
		if (res == true) {
			var deleteId = id;
			//alert(deleteId);
			$
					.ajax({
						type : "POST",
						url : "deleteDept.htm",
						data : "deleteId=" + deleteId,
						dataType : "json",
						success : function(deptList) {
							$("#userdata ul").remove();
							$("#userdata ul li").remove();
							if (deptList == "" || deptList == null) {
								$('#tablePagination').remove();
								$("#userdata").remove();
								$("#userdata ul li").remove();
								$('#noSortData').show();

							} else {
								var options = {
									currPage : 1,
									optionsForRows : [ 2, 3, 5 ],
									rowsPerPage : 5,
									firstArrow : (new Image()).src = "css/images/first.gif",
									prevArrow : (new Image()).src = "css/images/prev.gif",
									lastArrow : (new Image()).src = "css/images/last.gif",
									nextArrow : (new Image()).src = "css/images/next.gif",
									topNav : false
								};
								//alert("else=="+deptList);
								//$('#noSortData').hide();
								/* var tHead = "<tr><th><spring:message code="label.dept" /></th><th><spring:message code="label.delete" /></th><th><spring:message code="label.edit" /></th></tr>";
								$(tHead).appendTo("#userdata thead"); */
								
								$
										.each(
												deptList,
												function(i, deptObj) {
													if (deptObj.msg == "Success"
															&& count == 0) {
														count++;
														
														document.getElementById("deleteMsgSus").style.display = "block";
														
														document.getElementById("addsus").style.display = "none";
														
														document.getElementById("upsus").style.display = "none";

													} else if (count == 0) {
														count++;
														document
																.getElementById("deleteMsgFail").style.display = "block";
														document
																.getElementById("addsus").style.display = "none";
														document
																.getElementById("upsus").style.display = "none";
													} 
													var tblRow = "<ul>"
															+ "<li class='dept-box'>"
															+ deptObj.deptName
															+ "</li>"
															+ "<li class='ten-box'>"
															+ "<a href='javascript:void(0)' id='"
															+ deptObj.deptId
															+ "' onclick='forDelete(this.id)' class='ico del'>Delete</a>"
															+ "</li>"
															+ "<li class='eleven-box last'>"
															+ '<a href="editDept.htm?deptId='
															+ deptObj.deptId
															+ '" class="ico edit">Edit</a>'
															+ '</li>'
															+ '</ul>';
															//alert("dd=="+tblRow);
													$(tblRow).appendTo(
															"#userdata");
												});
								$('#block-footer').tablePagination(options);

							}
						},
						error : function(e) {
							//alert('Error: ' + e);
						}
					});

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
				<c:if test="${empty deptEdit}">
					<div class="block">
						<h2>
							<span class="icon1">&nbsp;</span>
							<spring:message code="label.adddept" />
						</h2>

						<form:form action="" commandName="deptCmd" method="post"
							id="addForm" cssClass="form-horizontal">
							<div class="block-box-small">
								<div class="block-input">
									<label><spring:message code="label.dept" /><span
												style="color: red">*</span></label>
									<form:input path="deptName" id="addName" maxlength="30"
										onkeyup="getDept(this.id)" />

								</div>
							</div>
							<div class="block-footer">
							<div id="messagesId">
								<div class="alert-danger" id="duplmsg" style="display: none;">
								<aside class="block-footer-left fail">
									 Warning : Department already Exists. Please
									try Some Other
									</aside>
								</div>


								<div  id="deleteMsgSus"
									style="display: none;">
								<aside class="block-footer-left sucessfully">
									<spring:message code="label.success" /> 
									Department
									<spring:message code="label.deleteSuccess" />
									</aside>
								</div>


								<div id="deleteMsgFail"
									style="display: none;">
									<aside class="block-footer-left fail">
									<strong><spring:message code="label.error" /> </strong>
									Department
									<spring:message code="label.deleteFail" />
								</aside>
								</div>

								
									<div id="addsus" style="display: block;">
									<c:forEach var="success" items="${param.AddSus}">
										<aside class="block-footer-left sucessfully">
											<spring:message code="label.success" />
											Department
											<spring:message code="label.saveSuccess" />
										</aside>

									</c:forEach>
									</div>
								
								<c:forEach var="fail" items="${param.AddFail}">
									<div >
									<aside class="block-footer-left fail">
										<strong><spring:message code="label.error" /> </strong>
										Department
										<spring:message code="label.saveFail" />
									</aside>
									</div>
								</c:forEach>
								<div  id="upsus" style="display: block;">
								<c:forEach var="success" items="${param.UpdateSus}">
									<aside class="block-footer-left sucessfully">
										<spring:message code="label.success" />
										Department
										<spring:message code="label.updateSuccess" />
										</aside>
								</c:forEach>
								</div>
								<c:forEach var="fail" items="${param.UpdateFail}">
									<div ><aside class="block-footer-left fail">
										<spring:message code="label.error" />
										Department
										<spring:message code="label.updateFail" />
										</aside>
									</div>
								</c:forEach>


								<c:forEach var="success" items="${param.DeleteSus}">
									<div class="alert-success">
										<strong><spring:message code="label.success" /> </strong>
										Department
										<spring:message code="label.deleteSuccess" />

									</div>
								</c:forEach>
								<c:forEach var="fail" items="${param.DeleteFail}">
									<div class="alert-danger">
										<strong><spring:message code="label.error" /> </strong>
										Department
										<spring:message code="label.deleteFail" />

									</div>
								</c:forEach>
								</div>
								<!--  <aside class="block-footer-left sucessfully">Sucessfully Message</aside> -->
								<aside class="block-footer-right">
									<input class="btn-cancel" name="" value="Cancel" type="reset">
									<input class="btn-save" name="" value="Save" type="submit"
										id="submitId">
								</aside>
							</div>
						</form:form>
					</div>

					<div class="block table-toop-space">
						<div class="head-box">
							<h2>
								<span class="icon2">&nbsp;</span>
								<spring:message code="label.curdept" />
							</h2>
							<aside class="search-box">
								<form:form action="searchDept.htm" commandName="deptCmd"
									method="get">
									<input class="search-bnt" name="" value="Search" type="submit">
									<form:input path="deptName" cssClass="search-input"
										maxlength="30" id="searchId" />

								</form:form>
							</aside>
						</div>
						<div class="block-box block-box-last">
							<ul class="table-list">
								<li class="dept-box"><spring:message code="label.dept" />
									<ul>
										<li><a class="top" href="#">&nbsp;</a></li>
										<li><a class="bottom" href="#">&nbsp;</a></li>
									</ul></li>
								<li class="ten-box"><spring:message code="label.delete" /></li>
								<li class="eleven-box last"><spring:message
										code="label.edit" /></li>
							</ul>
							<div class="table-list-blk data-grid-big" id="userdata"></div>
							<div align="center">
							<h4 id="noSortData" style="display: none">No Data Found</h4>
						</div>

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
				<c:if test="${not empty deptEdit}">
					<div class="block">
						<h2>
							<span class="icon1">&nbsp;</span>
							Edit Department
						</h2>

						<form:form action="#" commandName="deptCmd" method="post"
							id="editForm" cssClass="form-horizontal">
							<div class="block-box-small">
								<div class="block-input">
									<form:hidden path="deptId" id="deptIdEdit" />
									<label><spring:message code="label.dept" /></label>
									<form:input path="deptName" maxlength="30"
										onkeyup="getDeptEditFun(this.id)" />

								</div>
							</div>
							<div class="block-footer">
								
									<div class="alert-danger" id="duplmsgEdit" style="display: none;">
									<aside class="block-footer-left fail">Warning :
										Client already Exists. Please try Some Other</aside>
								</div>
								<aside class="block-footer-right">
									<a href="deptHome.htm"><input type="button"
										class="btn-cancel"
										value="<spring:message code="label.cancel"/>" id="cancel" /></a>
									<input class="btn-save" name="" value="Update" type="submit"
										id="updateId">
								</aside>
							</div>
						</form:form>
					</div>

					<div class="block table-toop-space">
						<div class="head-box">
							<h2>
								<span class="icon2">&nbsp;</span>
								<spring:message code="label.curdept" />
							</h2>
							<aside class="search-box">
								<form:form action="searchDept.htm" commandName="deptCmd"
									method="get">
									<input class="search-bnt" name="" value="Search" type="submit">
									<form:input path="deptName" cssClass="search-input"
										maxlength="30" id="searchIdEdit" />

								</form:form>
							</aside>
						</div>
						<div class="block-box block-box-last">
							<ul class="table-list">
								<li class="dept-box"><spring:message code="label.dept" />
									<ul>
										<li><a class="top" href="#">&nbsp;</a></li>
										<li><a class="bottom" href="#">&nbsp;</a></li>
									</ul></li>
								<li class="ten-box"><spring:message code="label.delete" /></li>
								<li class="eleven-box last"><spring:message
										code="label.edit" /></li>
							</ul>
							<div class="table-list-blk data-grid-big" id="userdata"></div>
							<div align="center">
							<h4 id="noSortData" style="display: none">No Data Found</h4>
						</div>

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
	</div>
</body>
</html>