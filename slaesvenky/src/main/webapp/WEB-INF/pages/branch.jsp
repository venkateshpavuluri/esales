<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Branch</title>
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

						$('#branchNameId').val('');
						$('#searchIdEdit').val('');
						$('#branchNameId').focus();
						$('#cityId').val('');
						$('#stateId').val('');
						$('#pinId').val('');
						$('#phoneId').val('');
						$('#contactPersonId').val('');
						$('#contactNoId').val('');
						$('#addressId').val('');
						$('#addrId').val('');
						$('#landmarkId').val('');
						$('#branchThemeId').val('');
						$('#emailIds').val('');

						$('#endDateId,#startDateId').datepicker({
							changeMonth : true,
							changeYear : true,
							dateFormat : "dd-mm-yy"

						});

						//AddForm Validations
						$('#submitId')
								.click(
										function(event) {
											$('#addForm')
													.validate(
															{
																rules : {
																	branchName : {
																		required : true,
																		alphanumeric : true,
																		specialcharacters : true

																	},
																	city : {
																		required : true,
																		alphanumeric : true,
																		specialcharacters : true
																	},
																	state : {
																		required : true,
																		alphanumeric : true,
																		specialcharacters : true
																	},
																	pinNo : {
																		required : true,
																		number : true
																	},
																	phone : {
																		required : true,
																		number : true,
																		minlength : 10
																	},
																	contactPerson : {
																		required : true,
																		alphanumeric : true,
																		specialcharacters : true
																	},
																	contactNo : {
																		required : true,
																		number : true,
																		minlength : 10
																	},

																	address1 : {
																		required : true
																	},
																	clientId : {
																		required : true
																	},
																	landmark : {
																		required : true
																	},
																	branchTheme : {
																		required : true
																	},
																	emailId : {
																		required : true,
																		email:true
																	}

																},
																messages : {
																	branchName : {
																		required : "<font style='color: red;'><b>branch Name is Required</b></font>",
																		alphanumeric : "<font style='color: red;'><b>First letter should be an alphanumeric.</b></font>",
																		specialcharacters : "<font style='color: red;'><b>Special Characters except &,_ are not allowed </b></font>"
																	},
																	city : {
																		required : "<font style='color: red;'><b>City is Required</b></font>",
																		alphanumeric : "<font style='color: red;'><b>First letter should be an alphanumeric.</b></font>",
																		specialcharacters : "<font style='color: red;'><b>Special Characters except &,_ are not allowed </b></font>"
																	},
																	state : {
																		required : "<font style='color: red;'><b>State is Required</b></font>",
																		alphanumeric : "<font style='color: red;'><b>First letter should be an alphanumeric.</b></font>",
																		specialcharacters : "<font style='color: red;'><b>Special Characters except &,_ are not allowed </b></font>"
																	},
																	pinNo : {
																		required : "<font style='color: red;'><b>Pin Code is Required</b></font>",
																		number : "<font style='color: red;'><b>It allows only Numbers</b></font>",
																		minlength : "<font style='color: red;'><b>Length must be 10 Numbers</b></font>"

																	},
																	phone : {
																		required : "<font style='color: red;'><b>Phone is Required</b></font>",
																		number : "<font style='color: red;'><b>It allows only Numbers</b></font>"

																	},
																	contactPerson : {
																		required : "<font style='color: red;'><b>Contact Person is Required</b></font>",
																		alphanumeric : "<font style='color: red;'><b>First letter should be an alphanumeric.</b></font>",
																		specialcharacters : "<font style='color: red;'><b>Special Characters except &,_ are not allowed </b></font>"
																	},
																	contactNo : {
																		required : "<font style='color: red;'><b>Contact No is Required</b></font>",
																		number : "<font style='color: red;'><b>It allows only Numbers</b></font>",
																		minlength : "<font style='color: red;'><b>Length must be 10 Numbers</b></font>"

																	},
																	branchType : {
																		required : "<font style='color: red;'><b>Branch Type is Required</b></font>",
																	},
																	address1 : {
																		required : "<font style='color: red;'><b>Address1 is Required</b></font>",
																	},
																	clientId : {
																		required : "<font style='color: red;'><b>Client Name is Required</b></font>",
																	},
																	landmark : {
																		required : "<font style='color: red;'><b>Landmark is Required</b></font>",
																	},
																	branchTheme : {
																		required : "<font style='color: red;'><b>Branch Theme is Required</b></font>",
																	},
																	emailId : {
																		required : "<font style='color: red;'><b>Email is Required</b></font>",
																		email:"<font style='color: red;'><b>It is Not a Email Format</b></font>"

																	},
																	
																},

															});

											$('#addForm')
													.attr('action',
															"<c:url value='/branchAdd.htm'/>");
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
																	branchName : {
																		required : true,
																		alphanumeric : true,
																		specialcharacters : true

																	},
																	city : {
																		required : true,
																		alphanumeric : true,
																		specialcharacters : true
																	},
																	state : {
																		required : true,
																		alphanumeric : true,
																		specialcharacters : true
																	},
																	pinNo : {
																		required : true,
																		number : true
																	},
																	phone : {
																		required : true,
																		number : true,
																		minlength : 10
																	},
																	contactPerson : {
																		required : true,
																		alphanumeric : true,
																		specialcharacters : true
																	},
																	contactNo : {
																		required : true,
																		number : true,
																		minlength : 10
																	},

																	address1 : {
																		required : true
																	},
																	clientId : {
																		required : true
																	},
																	landmark : {
																		required : "<font style='color: red;'><b>Landmark is Required</b></font>",
																	},
																	branchTheme : {
																		required : "<font style='color: red;'><b>Branch Theme is Required</b></font>",
																	},
																	emailId : {
																		required : true,
																		email:true
																	}

																},
																messages : {
																	branchName : {
																		required : "<font style='color: red;'><b>branch Name is Required</b></font>",
																		alphanumeric : "<font style='color: red;'><b>First letter should be an alphanumeric.</b></font>",
																		specialcharacters : "<font style='color: red;'><b>Special Characters except &,_ are not allowed </b></font>"
																	},
																	city : {
																		required : "<font style='color: red;'><b>City is Required</b></font>",
																		alphanumeric : "<font style='color: red;'><b>First letter should be an alphanumeric.</b></font>",
																		specialcharacters : "<font style='color: red;'><b>Special Characters except &,_ are not allowed </b></font>"
																	},
																	state : {
																		required : "<font style='color: red;'><b>State is Required</b></font>",
																		alphanumeric : "<font style='color: red;'><b>First letter should be an alphanumeric.</b></font>",
																		specialcharacters : "<font style='color: red;'><b>Special Characters except &,_ are not allowed </b></font>"
																	},
																	pinNo : {
																		required : "<font style='color: red;'><b>Pin Code is Required</b></font>",
																		number : "<font style='color: red;'><b>It allows only Numbers</b></font>"

																	},
																	phone : {
																		required : "<font style='color: red;'><b>Phone is Required</b></font>",
																		number : "<font style='color: red;'><b>It allows only Numbers</b></font>",
																		minlength : "<font style='color: red;'><b>Length must be 10 Numbers</b></font>"

																	},
																	contactPerson : {
																		required : "<font style='color: red;'><b>Contact Person is Required</b></font>",
																		alphanumeric : "<font style='color: red;'><b>First letter should be an alphanumeric.</b></font>",
																		specialcharacters : "<font style='color: red;'><b>Special Characters except &,_ are not allowed </b></font>"
																	},
																	contactNo : {
																		required : "<font style='color: red;'><b>Contact No is Required</b></font>",
																		number : "<font style='color: red;'><b>It allows only Numbers</b></font>",
																		minlength : "<font style='color: red;'><b>Length must be 10 Numbers</b></font>"

																	},

																	address1 : {
																		required : "<font style='color: red;'><b>Address1 is Required</b></font>",
																	},
																	clientId : {
																		required : "<font style='color: red;'><b>Client Name is Required</b></font>",
																	},
																	landmark : {
																		required : "<font style='color: red;'><b>Landmark is Required</b></font>",
																	},
																	branchTheme : {
																		required : "<font style='color: red;'><b>Branch Theme is Required</b></font>",
																	},
																	emailId : {
																		required : "<font style='color: red;'><b>Email is Required</b></font>",
																		email:"<font style='color: red;'><b>It is Not a Email Format</b></font>"

																	},
																	

																},

															});

											$('#editForm')
													.attr('action',
															"<c:url value='/branchUpdate.htm'/>");
											$("#editForm").submit();
											event.preventDefault();

										});
						$('#messagesId').fadeIn().delay(3000).fadeOut('slow');

					});

	function checkFun() {
		//alert("hai");
	}

	function getbranches(id) {

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

		var clientsId = $('#' + id).val();
		//alert(clientsId);
		$("#userdata ul li").remove();
		$
				.ajax({
					type : "POST",
					url : "searchOnBranch.htm",
					data : "clientId=" + clientsId,
					dataType : "json",
					success : function(branchList) {
						if (branchList == "" || branchList == null) {
							$('#tablePagination').remove();
							$("#userdata ul li").remove();
							$('#noSortData').show();

						} else {
							$('#noSortData').hide();
							/* var tHead = "<tr><th><spring:message code="label.branchname" /></th><th><spring:message code="label.city" /></th><th><spring:message code="label.state" /></th> <th><spring:message code="label.pin" /></th><th><spring:message code="label.phone" /></th><th><spring:message code="label.bcp" /></th><th><spring:message code="label.bcno" /></th><th><spring:message code="label.baddr" /></th><th><spring:message code="label.baddrs" /></th><th><spring:message code="label.delete" /></th><th><spring:message code="label.edit" /></th></tr>";
							$(tHead).appendTo("#userdata thead"); */

							$
									.each(
											branchList,
											function(i, branchObj) {
												//alert(i + ": " + branchObj.status);
												var tblRow = "<ul>"
														+ "<li class='one-box'>"
														+ branchObj.branchName
														+ "</li>"
														+ "<li class='two-box'>"
														+ branchObj.city
														+ "</li>"
														+ "<li class='three-box'>"
														+ branchObj.state
														+ "</li>"
														+ "<li class='four-box'>"
														+ branchObj.pinNo
														+ "</li>"
														+ "<li class='five-box'>"
														+ branchObj.phone
														+ "</li>"
														+ "<li class='six-box'>"
														+ branchObj.contactPerson
														+ "</li>"
														+ "<li class='seven-box'>"
														+ branchObj.contactNo
														+ "</li>"
														+ "<li class='eight-box'>"
														+ branchObj.address1
														+ "</li>"
														+ "<li class='nine-box'>"
														+ branchObj.address2
														+ "</li>"
														+ "<li class='ten-box'>"
														+ "<a href='javascript:void(0)' id='"
														+ branchObj.branchId
														+ "' onclick='forDelete(this.id)' class='ico del'>Delete</a>"
														+ "</li>"
														+ "<li class='eleven-box'>"
														+ '<a href="editBranch.htm?branchId='
														+ branchObj.branchId
														+ '" class="ico edit">Edit</a>'
														+ "</li>" + "</ul>";
												$(tblRow).appendTo("#userdata");
											});
							$('#block-footer').tablePagination(options);

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

						var branch = ${branchList};
						if (branch == "" || branch == null) {
							$('#tablePagination').remove();
							$("#userdata ul li").remove();
							$('#noSortData').show();
						} else {
							$('#noSortData').hide();
							/* var tHead = "<tr><th><spring:message code="label.branchname" /></th><th><spring:message code="label.city" /></th><th><spring:message code="label.state" /></th> <th><spring:message code="label.pin" /></th><th><spring:message code="label.phone" /></th><th><spring:message code="label.bcp" /></th><th><spring:message code="label.bcno" /></th><th><spring:message code="label.baddr" /></th><th><spring:message code="label.baddrs" /></th><th><spring:message code="label.delete" /></th><th><spring:message code="label.edit" /></th></tr>";
							$(tHead).appendTo("#userdata thead"); */
							$
									.each(
											branch,
											function(i, branchObj) {
												//alert(i + ": " + branchObj.branchId);
												var tblRow = "<ul>"
														+ "<li class='one-box'>"
														+ branchObj.branchName
														+ "</li>"
														+ "<li class='two-box'>"
														+ branchObj.city
														+ "</li>"
														+ "<li class='three-box'>"
														+ branchObj.state
														+ "</li>"
														+ "<li class='four-box'>"
														+ branchObj.pinNo
														+ "</li>"
														+ "<li class='five-box'>"
														+ branchObj.phone
														+ "</li>"
														+ "<li class='six-box'>"
														+ branchObj.contactPerson
														+ "</li>"
														+ "<li class='seven-box'>"
														+ branchObj.contactNo
														+ "</li>"
														+ "<li class='eight-box'>"
														+ branchObj.address1
														+ "</li>"
														+ "<li class='nine-box'>"
														+ branchObj.address2
														+ "</li>"

														+ "<li class='ten-box'>"
														+ "<a href='javascript:void(0)' id='"
														+ branchObj.branchId
														+ "' onclick='forDelete(this.id)' class='ico del'>Delete</a>"
														+ "</li>"
														+ "<li class='eleven-box'>"
														+ '<a href="editBranch.htm?branchId='
														+ branchObj.branchId
														+ '" class="ico edit">Edit</a>'
														+ "</li>" + "</ul>";
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
			var clId = $('#clientsId').val();
			//alert(clId);
			$
					.ajax({
						type : "POST",
						url : "deleteBranch.htm",
						data : "deleteId=" + deleteId + "&clId=" + clId,
						dataType : "json",
						success : function(branch) {
							//alert(branch);
							$('#tablePagination').remove();
							$("#userdata ul li").remove();
							if (branch == "" || branch == null) {
								$('#tablePagination').remove();
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
								$('#noSortData').hide();
								/* var tHead = "<tr><th><spring:message code="label.branchname" /></th><th><spring:message code="label.city" /></th><th><spring:message code="label.state" /></th> <th><spring:message code="label.pin" /></th><th><spring:message code="label.phone" /></th><th><spring:message code="label.bcp" /></th><th><spring:message code="label.bcno" /></th><th><spring:message code="label.baddr" /></th><th><spring:message code="label.baddrs" /></th><th><spring:message code="label.delete" /></th><th><spring:message code="label.edit" /></th></tr>";
								$(tHead).appendTo("#userdata thead"); */
								$
										.each(
												branch,
												function(i, branchObj) {
													if (branchObj.msg == "Success"
															&& count == 0) {
														count++;
														document
																.getElementById("deleteMsgSus").style.display = "block";
														document
																.getElementById("addsus").style.display = "none";
														document
																.getElementById("upsus").style.display = "none";

													} else if (count == 0) {
														count++;
														document
																.getElementById("deleteMsgFail").style.display = "block";
														document
																.getElementById("addsus").style.display = "none";
														document
																.getElementById("upsus").style.display = "none";
													}

													//alert(i + ": " + branchObj.branchId);
													var tblRow = "<ul>"
															+ "<li class='one-box'>"
															+ branchObj.branchName
															+ "</li>"
															+ "<li class='two-box'>"
															+ branchObj.city
															+ "</li>"
															+ "<li class='three-box'>"
															+ branchObj.state
															+ "</li>"
															+ "<li class='four-box'>"
															+ branchObj.pinNo
															+ "</li>"
															+ "<li class='five-box'>"
															+ branchObj.phone
															+ "</li>"
															+ "<li class='six-box'>"
															+ branchObj.contactPerson
															+ "</li>"
															+ "<li class='seven-box'>"
															+ branchObj.contactNo
															+ "</li>"
															+ "<li class='eight-box'>"
															+ branchObj.address1
															+ "</li>"
															+ "<li class='nine-box'>"
															+ branchObj.address2
															+ "</li>"

															+ "<li class='ten-box'>"
															+ "<a href='javascript:void(0)' id='"
															+ branchObj.branchId
															+ "' onclick='forDelete(this.id)' class='ico del'>Delete</a>"
															+ "</li>"
															+ "<li class='eleven-box'>"
															+ '<a href="editBranch.htm?branchId='
															+ branchObj.branchId
															+ '" class="ico edit">Edit</a>'
															+ "</li>" + "</ul>";
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

	function getBranch(id) {
		var branch = $('#' + id).val();
		//alert(dept);
		$.ajax({
			type : "POST",
			url : "branchDuplCheck.htm",
			data : "branchName=" + branch,
			success : function(response) {
				if (response != "") {
					document.getElementById("duplmsg").style.display = "block";
					$('#submitId').hide();
					$('#submitId').attr('disabled', 'disabled');
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

	function getBranchEdit(id) {
		var branch = $('#' + id).val();
		var branchId = $('#branchIdEdit').val();
		//alert(deptId);
		$
				.ajax({
					type : "POST",
					url : "branchDuplCheckEdit.htm",
					data : "branchName=" + branch + "&branchId=" + branchId,
					success : function(response) {
						if (response != "") {
							document.getElementById("duplmsgEdit").style.display = "block";
							$('#updateId').hide();
							$('#updateId').attr('disabled', 'disabled');
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
</script>
</head>
<body>
	<!-- SET: WRAPPER -->
	<div class="wrapper">
		<!-- SET: CONTAINER -->
		<div class="container">

			<div class="main_content">
				<c:if test="${empty branchEdit}">
					<div class="block">
						<h2>
							<span class="icon1">&nbsp;</span>
							<spring:message code="label.addbranch" />
						</h2>
						<form:form method="post" id="addForm" class="form-horizontal"
							action="#" commandName="branchCmd" enctype="multipart/form-data">
							<div class="block-box">
								<div class="block-input">
									<label><spring:message code="label.bclient" /><span
										style="color: red">*</span></label>
									<c:choose>
										<c:when test="${sessionScope.admin=='clientadmin'}">

											<form:select path="clientId" id="clientsId"
												onchange="getbranches(this.id)" disabled="true">
												<c:choose>
													<c:when test="${not empty sessionScope.clientId}">
														<c:set var="cId" scope="session"
															value="${sessionScope.clientId }" />
														<form:options items="${clientIds}"></form:options>
													</c:when>

													<c:otherwise>
														<form:option value="">--Select--</form:option>
														<form:options items="${clientIds}"></form:options>
													</c:otherwise>
												</c:choose>
											</form:select>
										</c:when>
										<c:otherwise>
											<form:select path="clientId" id="clientsId"
												onchange="getbranches(this.id)">
												<c:choose>
													<c:when test="${not empty sessionScope.clientId}">
														<c:set var="cId" scope="session"
															value="${sessionScope.clientId }" />
														<form:options items="${clientIds}"></form:options>
													</c:when>

													<c:otherwise>
														<form:option value="">--Select--</form:option>
														<form:options items="${clientIds}"></form:options>
													</c:otherwise>
												</c:choose>
											</form:select>
										</c:otherwise>
									</c:choose>
								</div>
								<div class="block-input">
									<label><spring:message code="label.branchname" /><span
										style="color: red">*</span></label>
									<form:input path="branchName" id="branchNameId" maxlength="30"
										onkeyup="getBranch(this.id)" />
								</div>
								<div class="block-input last">
									<label><spring:message code="label.bcp" /><span
										style="color: red">*</span></label>
									<form:input path="contactPerson" id="contactPersonId"
										maxlength="30" />
								</div>
								<div class="block-input">
									<label><spring:message code="label.state" /><span
										style="color: red">*</span></label>
									<form:input path="state" id="stateId" maxlength="30" />
								</div>

								<div class="block-input">
									<label><spring:message code="label.city" /><span
										style="color: red">*</span></label>
									<form:input path="city" id="cityId" maxlength="30" />
								</div>

								<div class="block-input last">
									<label><spring:message code="label.pin" /><span
										style="color: red">*</span></label>
									<form:input path="pinNo" id="pinId" maxlength="6" />
								</div>
								<div class="block-input">
									<label><spring:message code="label.phone" /><span
										style="color: red">*</span></label>
									<form:input path="phone" id="phoneId" maxlength="12" />
								</div>

								<div class="block-input">
									<label><spring:message code="label.bcno" /><span
										style="color: red">*</span></label>
									<form:input path="contactNo" cssClass="field size3"
										id="contactNoId" maxlength="12" />
								</div>

								<div class="block-input last">
									<label><spring:message code="label.bland" /><span
										style="color: red">*</span></label>
									<form:input path="landmark" id="landmarkId" maxlength="30" />
								</div>
								<div class="block-input">
									<label><spring:message code="label.baddr" /><span
										style="color: red">*</span></label>
									<form:input path="address1" id="addressId" maxlength="40" />
								</div>
								<div class="block-input">
									<label><spring:message code="label.baddrs" /></label>
									<form:input path="address2" id="addrId" maxlength="30" />
								</div>

								<div class="block-input last">
									<label><spring:message code="label.btheme" /><span
										style="color: red">*</span></label>
									<form:select path="branchTheme" cssClass="field size3">
										<form:option value="">--Select--</form:option>
										<form:options items="${themeDetails }"></form:options>
									</form:select>
								</div>
								<div class="block-input">
									<label><spring:message code="label.image" /></label>
									<form:input type="file" path="clientlogo" />
								</div>

								<div class="block-input">
									<label><spring:message code="label.bemail" /><span
										style="color: red">*</span></label>
									<form:input path="emailId" id="emailIds" maxlength="30"/>
								</div>

							</div>
							<div class="block-footer">
							<div id="messagesId">
								<div class="alert-danger" id="duplmsg" style="display: none;">
									<aside class="block-footer-left fail">Warning : Client
										already Exists. Please try Some Other</aside>
								</div>


								<div id="deleteMsgSus" style="display: none;">
									<aside class="block-footer-left sucessfully">
										<spring:message code="label.success" />
										Branch
										<spring:message code="label.deleteSuccess" />
									</aside>
								</div>


								<div id="deleteMsgFail" style="display: none;">
									<aside class="block-footer-left fail">
										<strong><spring:message code="label.error" /> </strong>
										Branch
										<spring:message code="label.deleteFail" />
									</aside>
								</div>

								<div id="addsus" style="display: block;">
									<c:forEach var="success" items="${param.AddSus}">
										<aside class="block-footer-left sucessfully">
											<spring:message code="label.success" />
											Branch
											<spring:message code="label.saveSuccess" />
										</aside>
									</c:forEach>
								</div>
								<c:forEach var="fail" items="${param.AddFail}">
									<div>
										<aside class="block-footer-left fail">
											<strong><spring:message code="label.error" /> </strong>
											Branch
											<spring:message code="label.saveFail" />
										</aside>
									</div>
								</c:forEach>
								<div id="upsus" style="display: block;">
									<c:forEach var="success" items="${param.UpdateSus}">

										<aside class="block-footer-left sucessfully">
											<spring:message code="label.success" />
											Branch
											<spring:message code="label.updateSuccess" />
										</aside>

									</c:forEach>
								</div>
								<c:forEach var="fail" items="${param.UpdateFail}">
									<div>
										<aside class="block-footer-left fail">
											<spring:message code="label.error" />
											Branch
											<spring:message code="label.updateFail" />
										</aside>
									</div>
								</c:forEach>


								<c:forEach var="success" items="${param.DeleteSus}">
									<div class="alert-success">
										<strong><spring:message code="label.success" /> </strong>
										Branch
										<spring:message code="label.deleteSuccess" />

									</div>
								</c:forEach>
								<c:forEach var="fail" items="${param.DeleteFail}">
									<div class="alert-danger">
										<strong><spring:message code="label.error" /> </strong>
										Branch
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
								<spring:message code="label.curbranch" />
							</h2>
							<aside class="search-box">
								<form:form action="searchBranch.htm" commandName="branchCmd"
									method="get">
									<input class="search-bnt" name="" value="Search" type="submit">
									<form:input path="branchName" cssClass="search-input"
										maxlength="30" id="searchId" />

								</form:form>
							</aside>
						</div>
						<div class="block-box block-box-last">
							<ul class="table-list">
								<li class="one-box"><spring:message code="label.branchname" />
									<ul>
										<li><a class="top" href="#">&nbsp;</a></li>
										<li><a class="bottom" href="#">&nbsp;</a></li>
									</ul></li>
								<li class="two-box"><spring:message code="label.city" />
									<ul>
										<li><a class="top" href="#">&nbsp;</a></li>
										<li><a class="bottom" href="#">&nbsp;</a></li>
									</ul></li>
								<li class="three-box"><spring:message code="label.state" />
									<ul>
										<li><a class="top" href="#">&nbsp;</a></li>
										<li><a class="bottom" href="#">&nbsp;</a></li>
									</ul></li>
								<li class="four-box"><spring:message code="label.pin" />
									<ul>
										<li><a class="top" href="#">&nbsp;</a></li>
										<li><a class="bottom" href="#">&nbsp;</a></li>
									</ul></li>
								<li class="five-box"><spring:message code="label.phone" />
									<ul>
										<li><a class="top" href="#">&nbsp;</a></li>
										<li><a class="bottom" href="#">&nbsp;</a></li>
									</ul></li>
								<li class="six-box"><spring:message code="label.bcp" />
									<ul>
										<li><a class="top" href="#">&nbsp;</a></li>
										<li><a class="bottom" href="#">&nbsp;</a></li>
									</ul></li>
								<li class="seven-box"><spring:message code="label.bcno" />
									<ul>
										<li><a class="top" href="#">&nbsp;</a></li>
										<li><a class="bottom" href="#">&nbsp;</a></li>
									</ul></li>
								<li class="eight-box"><spring:message code="label.baddr" />
									<ul>
										<li><a class="top" href="#">&nbsp;</a></li>
										<li><a class="bottom" href="#">&nbsp;</a></li>
									</ul></li>
								<li class="nine-box"><spring:message code="label.baddrs" />
									<ul>
										<li><a class="top" href="#">&nbsp;</a></li>
										<li><a class="bottom" href="#">&nbsp;</a></li>
									</ul></li>
								<li class="ten-box"><spring:message code="label.delete" /></li>
								<li class="eleven-box last"><spring:message
										code="label.edit" /></li>
							</ul>
							<div class="table-list-blk" id="userdata"></div>
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

				<c:if test="${not empty branchEdit}">
					<div class="block">
						<h2>
							<span class="icon1">&nbsp;</span> Edit Branch
						</h2>
						<form:form method="post" id="editForm" class="form-horizontal"
							action="#" commandName="branchCmd" enctype="multipart/form-data">
							<div class="block-box">
								<div class="block-input">
									<form:hidden path="branchId" id="branchIdEdit" />
									<form:hidden path="branchCode" id="branchCodeEdit" />
									<label><spring:message code="label.bclient" /><span
										style="color: red">*</span></label>
									<c:choose>
										<c:when test="${sessionScope.admin=='clientadmin'}">

											<form:select path="clientId" id="clientIdEdit"
												onchange="getbranches(this.id)" disabled="true">
												<c:choose>
													<c:when test="${not empty sessionScope.clientId}">
														<c:set var="cId" scope="session"
															value="${sessionScope.clientId }" />
														<form:options items="${clientIds}"></form:options>
													</c:when>

													<c:otherwise>
														<form:option value="">--Select--</form:option>
														<form:options items="${clientIds}"></form:options>
													</c:otherwise>
												</c:choose>
											</form:select>
										</c:when>
										<c:otherwise>
											<form:select path="clientId" id="clientsId"
												onchange="getbranches(this.id)">
												<c:choose>
													<c:when test="${not empty sessionScope.clientId}">
														<c:set var="cId" scope="session"
															value="${sessionScope.clientId }" />
														<form:options items="${clientIds}"></form:options>
													</c:when>

													<c:otherwise>
														<form:option value="">--Select--</form:option>
														<form:options items="${clientIds}"></form:options>
													</c:otherwise>
												</c:choose>
											</form:select>
										</c:otherwise>
									</c:choose>
								</div>
								<div class="block-input">
									<label><spring:message code="label.branchname" /><span
										style="color: red">*</span></label>
									<form:input path="branchName" id="branchNameEdit"
										maxlength="30" onkeyup="getBranchEdit(this.id)" />
								</div>
								<div class="block-input last">
									<label><spring:message code="label.bcp" /><span
										style="color: red">*</span></label>
									<form:input path="contactPerson" maxlength="30" />
								</div>
								<div class="block-input">
									<label><spring:message code="label.state" /><span
										style="color: red">*</span></label>
									<form:input path="state" maxlength="30" />
								</div>

								<div class="block-input">
									<label><spring:message code="label.city" /><span
										style="color: red">*</span></label>
									<form:input path="city" maxlength="30" />
								</div>

								<div class="block-input last">
									<label><spring:message code="label.pin" /><span
										style="color: red">*</span></label>
									<form:input path="pinNo" maxlength="6" />
								</div>
								<div class="block-input">
									<label><spring:message code="label.phone" /><span
										style="color: red">*</span></label>
									<form:input path="phone" maxlength="12" />
								</div>

								<div class="block-input">
									<label><spring:message code="label.bcno" /><span
										style="color: red">*</span></label>
									<form:input path="contactNo" cssClass="field size3"
										maxlength="12" />
								</div>

								<div class="block-input last">
									<label><spring:message code="label.bland" /><span
										style="color: red">*</span></label>
									<form:input path="landmark" maxlength="30" />
								</div>
								<div class="block-input">
									<label><spring:message code="label.baddr" /><span
										style="color: red">*</span></label>
									<form:input path="address1" maxlength="40" />
								</div>
								<div class="block-input">
									<label><spring:message code="label.baddrs" /></label>
									<form:input path="address2" maxlength="30" />
								</div>

								<div class="block-input last">
									<label><spring:message code="label.btheme" /><span
										style="color: red">*</span></label>
									<form:select path="branchTheme">
										<form:option value="">--Select--</form:option>
										<form:options items="${themeDetails }"></form:options>
									</form:select>
								</div>
								<div class="block-input">
									<label><spring:message code="label.image" /></label>
									<form:input type="file" path="clientlogo" />
									<form:hidden path="editPath" />
								</div>
								<div class="block-input">
									<label><spring:message code="label.bemail" /><span
										style="color: red">*</span></label>
									<form:input path="emailId"  maxlength="30"/>
								</div>

							</div>
							<div class="block-footer">
								<div class="alert-danger" id="duplmsgEdit"
									style="display: none;">
									<aside class="block-footer-left fail">Warning : Branch
										already Exists. Please try Some Other</aside>
								</div>

								<!--  <aside class="block-footer-left sucessfully">Sucessfully Message</aside> -->
								<aside class="block-footer-right">
									<a href="branchHome.htm"><input type="button"
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
								<spring:message code="label.curbranch" />
							</h2>
							<aside class="search-box">
								<form:form action="searchBranch.htm" commandName="branchCmd"
									method="get">
									<input class="search-bnt" name="" value="Search" type="submit">
									<form:input path="branchName" cssClass="search-input"
										maxlength="30" id="searchIdEdit" />

								</form:form>
							</aside>
						</div>
						<div class="block-box block-box-last">
							<ul class="table-list">
								<li class="one-box"><spring:message code="label.branchname" />
									<ul>
										<li><a class="top" href="#">&nbsp;</a></li>
										<li><a class="bottom" href="#">&nbsp;</a></li>
									</ul></li>
								<li class="two-box"><spring:message code="label.city" />
									<ul>
										<li><a class="top" href="#">&nbsp;</a></li>
										<li><a class="bottom" href="#">&nbsp;</a></li>
									</ul></li>
								<li class="three-box"><spring:message code="label.state" />
									<ul>
										<li><a class="top" href="#">&nbsp;</a></li>
										<li><a class="bottom" href="#">&nbsp;</a></li>
									</ul></li>
								<li class="four-box"><spring:message code="label.pin" />
									<ul>
										<li><a class="top" href="#">&nbsp;</a></li>
										<li><a class="bottom" href="#">&nbsp;</a></li>
									</ul></li>
								<li class="five-box"><spring:message code="label.phone" />
									<ul>
										<li><a class="top" href="#">&nbsp;</a></li>
										<li><a class="bottom" href="#">&nbsp;</a></li>
									</ul></li>
								<li class="six-box"><spring:message code="label.bcp" />
									<ul>
										<li><a class="top" href="#">&nbsp;</a></li>
										<li><a class="bottom" href="#">&nbsp;</a></li>
									</ul></li>
								<li class="seven-box"><spring:message code="label.bcno" />
									<ul>
										<li><a class="top" href="#">&nbsp;</a></li>
										<li><a class="bottom" href="#">&nbsp;</a></li>
									</ul></li>
								<li class="eight-box"><spring:message code="label.baddr" />
									<ul>
										<li><a class="top" href="#">&nbsp;</a></li>
										<li><a class="bottom" href="#">&nbsp;</a></li>
									</ul></li>
								<li class="nine-box"><spring:message code="label.baddrs" />
									<ul>
										<li><a class="top" href="#">&nbsp;</a></li>
										<li><a class="bottom" href="#">&nbsp;</a></li>
									</ul></li>
								<li class="ten-box"><spring:message code="label.delete" /></li>
								<li class="eleven-box last"><spring:message
										code="label.edit" /></li>
							</ul>
							<div class="table-list-blk" id="userdata"></div>
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
			<!-- Main -->
		</div>
	</div>
</body>
</html>