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
<title>Client Info</title>
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
						$('#clientNameId').val('');
						$('#searchIdEdit').val('');
						$('#clientNameId').focus();
						$('#cityId').val('');
						$('#stateId').val('');
						$('#pinId').val('');
						$('#phoneId').val('');
						$('#contactPersonId').val('');
						$('#contactNoId').val('');
						$('#themeId').val('');
						$('#addressId').val('');
						$('#addrId').val('');
						$('#landmarkId').val('');

						$('#regDate,#regDateEdit').datepicker({
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
																	deptId : {
																		required : true
																	},
																	clientName : {
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
																	pin : {
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
																	theme : {
																		required : true

																	},
																	email : {
																		required : true,
																		email:true

																	},
																	address : {
																		required : true
																	},
																	landmark : {
																		required : true
																	}

																},
																messages : {
																	deptId : {
																		required : "<font style='color: red;'><b>Department is Required</b></font>",
																	},
																	clientName : {
																		required : "<font style='color: red;'><b>Client Name is Required</b></font>",
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
																	pin : {
																		required : "<font style='color: red;'><b>Pin Code is Required</b></font>",
																		number : "<font style='color: red;'><b>It allows only Numbers</b></font>"

																	},
																	phone : {
																		required : "<font style='color: red;'><b>Phone is Required</b></font>",
																		number : "<font style='color: red;'><b>It allows only Numbers</b></font>",
																		minlength : "<font style='color: red;'><b>Length must be 10 Numbers</b></font>"

																	},
																	email : {
																		required : "<font style='color: red;'><b>Email is Required</b></font>",
																		email:"<font style='color: red;'><b>It is Not a Email Format</b></font>"

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
																	theme : {
																		required : "<font style='color: red;'><b>Theme is Required</b></font>",
																	},
																	address : {
																		required : "<font style='color: red;'><b>Address1 is Required</b></font>",
																	},
																	landmark : {
																		required : "<font style='color: red;'><b>Landmark is Required</b></font>",
																	},

																},

															});

											$('#addForm')
													.attr('action',
															"<c:url value='/clientAdd.htm'/>");
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
																	deptId : {
																		required : true
																	},
																	clientName : {
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
																	pin : {
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
																	theme : {
																		required : true,

																	},
																	email : {
																		required : true,
																		email:true

																	},
																	address : {
																		required : true
																	},
																	landmark : {
																		required : true
																	}

																},
																messages : {

																	deptId : {
																		required : "<font style='color: red;'><b>Department is Required</b></font>",
																	},

																	clientName : {
																		required : "<font style='color: red;'><b>Client Name is Required</b></font>",
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
																	pin : {
																		required : "<font style='color: red;'><b>Pin Code is Required</b></font>",
																		number : "<font style='color: red;'><b>It allows only Numbers</b></font>"

																	},
																	phone : {
																		required : "<font style='color: red;'><b>Phone is Required</b></font>",
																		number : "<font style='color: red;'><b>It allows only Numbers</b></font>",
																		minlength : "<font style='color: red;'><b>Length must be 10 Numbers</b></font>"

																	},
																	email : {
																		required : "<font style='color: red;'><b>Email is Required</b></font>",
																		email:"<font style='color: red;'><b>It is Not a Email Format</b></font>"

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
																	theme : {
																		required : "<font style='color: red;'><b>Theme is Required</b></font>",
																	},
																	address : {
																		required : "<font style='color: red;'><b>Address1 is Required</b></font>",
																	},
																	landmark : {
																		required : "<font style='color: red;'><b>Landmark is Required</b></font>",
																	},

																},

															});

											$('#editForm')
													.attr('action',
															"<c:url value='/clientUpdate.htm'/>");
											$("#editForm").submit();
											event.preventDefault();

										});
						$('#messagesId').fadeIn().delay(3000).fadeOut('slow');

					});

	function getClient() {
		var client = $('#clientNameId').val();
		var deptId = $('#deptIds').val();
		if (deptId != "" && client != "" && client != null) {
			$
					.ajax({
						type : "POST",
						url : "clientDuplCheck.htm",
						data : "clientName=" + client + "&deptId=" + deptId,
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
	}
	function getClientEdit() {
		var client = $('#clientNameEdit').val();
		var clientId = $('#clientIdEdit').val();
		var deptId = $('#deptIdEdit').val();
		//alert(client);
		if (deptId != "" && client != "" && client != null) {
			$
					.ajax({
						type : "POST",
						url : "clientDuplCheckEdit.htm",
						data : "clientName=" + client + "&clientId=" + clientId
								+ "&deptId=" + deptId,
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
	}

	function searchClients(id) {
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

		var deptId = $('#' + id).val();
		//alert(deptId);
		$("#userdata ul li").remove();
		$
				.ajax({
					type : "POST",
					url : "searchOnClient.htm",
					data : "deptId=" + deptId,
					dataType : "json",
					success : function(clientList) {
						//alert(clientList);
						if (clientList == "" || clientList == null) {
							$('#tablePagination').remove();
							$("#userdata ul li").remove();
							$('#noSortData').show();

						} else {
							$('#noSortData').hide();
							/* var tHead = "<tr><th><spring:message code="label.client" /></th><th><spring:message code="label.city" /></th><th><spring:message code="label.state" /></th> <th><spring:message code="label.pin" /></th><th><spring:message code="label.phone" /></th><th><spring:message code="label.bcp" /></th><th><spring:message code="label.bcno" /></th><th><spring:message code="label.baddr" /></th><th><spring:message code="label.email" /></th><th><spring:message code="label.delete" /></th><th><spring:message code="label.edit" /></th></tr>";
							$(tHead).appendTo("#userdata thead"); */
							$
									.each(
											clientList,
											function(i, clientObj) {
												//alert(i + ": " + clientObj.clientName);
												var tblRow = "<ul>" 
														+ "<li class='one-box'>"
														+ clientObj.clientName
														+ "</li>"
														+ "<li class='two-box'>"
														+ clientObj.city
														+ "</li>"
														+ "<li class='three-box'>"
														+ clientObj.state
														+ "</li>"
														+ "<li class='four-box'>"
														+ clientObj.pin
														+ "</li>"
														+ "<li class='five-box'>"
														+ clientObj.phone
														+ "</li>"
														+ "<li class='six-box'>"
														+ clientObj.contactPerson
														+ "</li>"
														+ "<li class='seven-box'>"
														+ clientObj.contactNo
														+ "</li>"
														+ "<li class='eight-box'>"
														+ clientObj.address
														+ "</li>"
														+ "<li class='nine-box'>"
														+ clientObj.email
														+ "</li>"
														+ "<li class='ten-box'>"
														+ "<a href='javascript:void(0)' id='"
														+ clientObj.clientId
														+ "' onclick='forDelete(this.id)' class='ico del'>Delete</a>"
														+ "</li>"
														+ "<li class='eleven-box last'>"
														+ '<a href="editClient.htm?clientId='
														+ clientObj.clientId
														+ '" class="ico edit">Edit</a>'
														+ "</li>"
														+ "</ul>";
												$(tblRow).appendTo(
														"#userdata");
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

						var client = ${clientList};
						//alert(client);
						if (client == "" || client == null) {
							$('#tablePagination').remove();
							$("#userdata ul li").remove();
							$('#noSortData').show();

						} else {
							$('#noSortData').hide();
							/* var tHead = "<tr><th><spring:message code="label.client" /></th><th><spring:message code="label.city" /></th><th><spring:message code="label.state" /></th> <th><spring:message code="label.pin" /></th><th><spring:message code="label.phone" /></th><th><spring:message code="label.bcp" /></th><th><spring:message code="label.bcno" /></th><th><spring:message code="label.baddr" /></th><th><spring:message code="label.email" /></th><th><spring:message code="label.delete" /></th><th><spring:message code="label.edit" /></th></tr>";
							$(tHead).appendTo("#userdata thead"); */

							$
									.each(
											client,
											function(i, clientObj) {
												//alert(i + ": " + clientObj.status);
												var tblRow = "<ul>" 
													+ "<li class='one-box'>"
													+ clientObj.clientName
													+ "</li>"
													+ "<li class='two-box'>"
													+ clientObj.city
													+ "</li>"
													+ "<li class='three-box'>"
													+ clientObj.state
													+ "</li>"
													+ "<li class='four-box'>"
													+ clientObj.pin
													+ "</li>"
													+ "<li class='five-box'>"
													+ clientObj.phone
													+ "</li>"
													+ "<li class='six-box'>"
													+ clientObj.contactPerson
													+ "</li>"
													+ "<li class='seven-box'>"
													+ clientObj.contactNo
													+ "</li>"
													+ "<li class='eight-box'>"
													+ clientObj.address
													+ "</li>"
													+ "<li class='nine-box'>"
													+ clientObj.email
													+ "</li>"
													+ "<li class='ten-box'>"
													+ "<a href='javascript:void(0)' id='"
													+ clientObj.clientId
													+ "' onclick='forDelete(this.id)' class='ico del'>Delete</a>"
													+ "</li>"
													+ "<li class='eleven-box last'>"
													+ '<a href="editClient.htm?clientId='
													+ clientObj.clientId
													+ '" class="ico edit">Edit</a>'
													+ "</li>"
													+ "</ul>";
												$(tblRow).appendTo(
														"#userdata");
											});
							$('#block-footer').tablePagination(options);

						}
					});

	function forDelete(id) {
		var count = 0;
		var res = confirm("Do You Want To Delete This Record?");
		if (res == true) {
			var deleteId = id;
			var deptId = $('#deptIds').val();
			//alert(deptId);
			$
					.ajax({
						type : "POST",
						url : "deleteClient.htm",
						data : "deleteId=" + deleteId + "&deptId=" + deptId,
						dataType : "json",
						success : function(client) {
							//alert(client);
							$('#tablePagination').remove();
							$("#userdata ul li").remove();
							if (client == "" || client == null) {
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
								/* var tHead = "<tr><th><spring:message code="label.client" /></th><th><spring:message code="label.city" /></th><th><spring:message code="label.state" /></th> <th><spring:message code="label.pin" /></th><th><spring:message code="label.phone" /></th><th><spring:message code="label.bcp" /></th><th><spring:message code="label.bcno" /></th><th><spring:message code="label.baddr" /></th><th><spring:message code="label.email" /></th><th><spring:message code="label.delete" /></th><th><spring:message code="label.edit" /></th></tr>";
								$(tHead).appendTo("#userdata thead"); */
								$
										.each(
												client,
												function(i, clientObj) {
													if (clientObj.msg == "Success"
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

													//alert(i + ": " + clientObj.status);
													var tblRow = "<ul>"
														+ "<li class='one-box'>"
														+ clientObj.clientName
														+ "</li>"
														+ "<li class='two-box'>"
														+ clientObj.city
														+ "</li>"
														+ "<li class='three-box'>"
														+ clientObj.state
														+ "</li>"
														+ "<li class='four-box'>"
														+ clientObj.pin
														+ "</li>"
														+ "<li class='five-box'>"
														+ clientObj.phone
														+ "</li>"
														+ "<li class='six-box'>"
														+ clientObj.contactPerson
														+ "</li>"
														+ "<li class='seven-box'>"
														+ clientObj.contactNo
														+ "</li>"
														+ "<li class='eight-box'>"
														+ clientObj.address
														+ "</li>"
														+ "<li class='nine-box'>"
														+ clientObj.email
														+ "</li>"
														+ "<li class='ten-box'>"
														+ "<a href='javascript:void(0)' id='"
														+ clientObj.clientId
														+ "' onclick='forDelete(this.id)' class='ico del'>Delete</a>"
														+ "</li>"
														+ "<li class='eleven-box last'>"
														+ '<a href="editClient.htm?clientId='
														+ clientObj.clientId
														+ '" class="ico edit">Edit</a>'
														+ "</li>"
														+ "</ul>";
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
<style type="text/css">
.box1 {
	background: #fbfcfc;
	height: 100%;
	padding: 1px;
	width: 120%;
	margin-bottom: 20px;
}
</style>
</head>
<body>

	<!-- SET: WRAPPER -->
	<div class="wrapper">
		<!-- SET: CONTAINER -->
		<div class="container">
		
			<div class="main_content">
				<c:if test="${empty clientEdit}">
					<div class="block">
						<h2>
							<span class="icon1">&nbsp;</span>
							<spring:message code="label.addclient" />
						</h2>
						<form:form name="cf_form" method="post" id="addForm"
							class="form-horizontal" action="#" commandName="clientCmd" enctype="multipart/form-data">
							<div class="block-box">
								<div class="block-input">
									<label><spring:message code="label.cdept" /><span
										style="color: red">*</span></label>
									<form:select path="deptId" id="deptIds"
										onchange="getClient(),searchClients(this.id)">
										<form:option value="">--Select--</form:option>
										<form:options items="${deptIds}"></form:options>
									</form:select>
								</div>
								<div class="block-input">
									<label><spring:message code="label.clientname" /><span
										style="color: red">*</span></label>
									<form:input path="clientName" id="clientNameId" maxlength="30"
										onkeyup="getClient()" />
								</div>
								<div class="block-input last">
									<label><spring:message code="label.city" /><span
										style="color: red">*</span></label>
									<form:input path="city" id="cityId" maxlength="20" />
								</div>
								<div class="block-input">
									<label><spring:message code="label.state" /><span
										style="color: red">*</span></label>
									<form:input path="state" id="stateId" maxlength="20" />
								</div>
								<div class="block-input">
									<label><spring:message code="label.pin" /><span
										style="color: red">*</span></label>
									<form:input path="pin" id="pinId" maxlength="6" />
								</div>
								<div class="block-input last">
									<label><spring:message code="label.phone" /><span
										style="color: red">*</span></label>
									<form:input path="phone" id="phoneId" maxlength="12" />
								</div>
								<div class="block-input">
									<label><spring:message code="label.ccp" /><span
										style="color: red">*</span></label>
									<form:input path="contactPerson" id="contactPerson"
										maxlength="30" />
								</div>
								<div class="block-input">
									<label><spring:message code="label.ccontact" /><span
										style="color: red">*</span></label>
									<form:input path="contactNo" id="contactNoId" maxlength="12" />
								</div>
								<div class="block-input last">
									<label><spring:message code="label.email" /><span
										style="color: red">*</span></label>
									<form:input path="email" id="emailId" maxlength="30" />
								</div>
								<div class="block-input">
									<label><spring:message code="label.theme" /><span
										style="color: red">*</span></label>
									<form:select path="theme" id="themeId">
										<form:option value="">--Select--</form:option>
										<form:options items="${themeDetails }"></form:options>
									</form:select>
								</div>
								<div class="block-input">
									<label><spring:message code="label.caddr" /><span
										style="color: red">*</span></label>
									<form:input path="address" id="addressId" maxlength="40" />
								</div>
								<div class="block-input last">
									<label><spring:message code="label.caddrs" /></label>
									<form:input path="addr" id="addrId" maxlength="30" />
								</div>
								<div class="block-input">
									<label><spring:message code="label.cland" /><span
										style="color: red">*</span></label>
									<form:input path="landmark" id="landmarkId" maxlength="20" />
								</div>
								<div class="block-input">
									<label><spring:message code="label.image" /></label>
									<form:input type="file" path="clientlogo" />
								</div>
							</div>
							<div class="block-footer">
							<div id="messagesId">

								<div class="alert-danger" id="duplmsg" style="display: none;">
									<aside class="block-footer-left fail">Warning :
										Client already Exists. Please try Some Other</aside>
								</div>


								<div id="deleteMsgSus" style="display: none;">
									<aside class="block-footer-left sucessfully">
										<spring:message code="label.success" />
										Client
										<spring:message code="label.deleteSuccess" />
									</aside>
								</div>


								<div id="deleteMsgFail" style="display: none;">
									<aside class="block-footer-left fail">
										<strong><spring:message code="label.error" /> </strong>
										Client
										<spring:message code="label.deleteFail" />
									</aside>
								</div>

							<div id="addsus" style="display: block;">
								<c:forEach var="success" items="${param.AddSus}">
										<aside class="block-footer-left sucessfully">
											<spring:message code="label.success" />
											Client
											<spring:message code="label.saveSuccess" />
										</aside>
								</c:forEach>
								</div>
								<c:forEach var="fail" items="${param.AddFail}">
									<div>
										<aside class="block-footer-left fail">
											<strong><spring:message code="label.error" /> </strong>
											Client
											<spring:message code="label.saveFail" />
										</aside>
									</div>
								</c:forEach>
								<div id="upsus" style="display: block;">
								<c:forEach var="success" items="${param.UpdateSus}">
									
										<aside class="block-footer-left sucessfully">
											<spring:message code="label.success" />
											Client
											<spring:message code="label.updateSuccess" />
										</aside>
								
								</c:forEach>
									</div>
								<c:forEach var="fail" items="${param.UpdateFail}">
									<div>
										<aside class="block-footer-left fail">
											<spring:message code="label.error" />
											Client
											<spring:message code="label.updateFail" />
										</aside>
									</div>
								</c:forEach>


								<c:forEach var="success" items="${param.DeleteSus}">
									<div class="alert-success">
										<strong><spring:message code="label.success" /> </strong>
										Client
										<spring:message code="label.deleteSuccess" />

									</div>
								</c:forEach>
								<c:forEach var="fail" items="${param.DeleteFail}">
									<div class="alert-danger">
										<strong><spring:message code="label.error" /> </strong>
										Client
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

					
				</c:if>
				
				 <c:if test="${not empty clientEdit}">
					<div class="block">
						<h2>
							<span class="icon1">&nbsp;</span>
							Edit Client
						</h2>
						<form:form  method="post" id="editForm"
							class="form-horizontal" action="#" commandName="clientCmd" enctype="multipart/form-data">
							<div class="block-box">
								<div class="block-input">
								<form:hidden path="clientId" id="clientIdEdit" />
									<label><spring:message code="label.cdept" /><span
										style="color: red">*</span></label>
									<form:select path="deptId" id="deptIdEdit"
										onchange="getClientEdit(),searchClients(this.id)">
										<form:option value="">--Select--</form:option>
										<form:options items="${deptIds}"></form:options>
									</form:select>
								</div>
								<div class="block-input">
									<label><spring:message code="label.clientname" /><span
										style="color: red">*</span></label>
									<form:input path="clientName" id="clientNameEdit" maxlength="30"
										onkeyup="getClientEdit()" />
								</div>
								<div class="block-input last">
									<label><spring:message code="label.city" /><span
										style="color: red">*</span></label>
									<form:input path="city"  maxlength="20" />
								</div>
								<div class="block-input">
									<label><spring:message code="label.state" /><span
										style="color: red">*</span></label>
									<form:input path="state"  maxlength="20" />
								</div>
								<div class="block-input">
									<label><spring:message code="label.pin" /><span
										style="color: red">*</span></label>
									<form:input path="pin" maxlength="6" />
								</div>
								<div class="block-input last">
									<label><spring:message code="label.phone" /><span
										style="color: red">*</span></label>
									<form:input path="phone"  maxlength="12" />
								</div>
								<div class="block-input">
									<label><spring:message code="label.ccp" /><span
										style="color: red">*</span></label>
									<form:input path="contactPerson" 
										maxlength="30" />
								</div>
								<div class="block-input">
									<label><spring:message code="label.ccontact" /><span
										style="color: red">*</span></label>
									<form:input path="contactNo"  maxlength="12" />
								</div>
								<div class="block-input last">
									<label><spring:message code="label.email" /><span
										style="color: red">*</span></label>
									<form:input path="email"  maxlength="30" />
								</div>
								<div class="block-input">
									<label><spring:message code="label.theme" /><span
										style="color: red">*</span></label>
									<form:select path="theme" >
										<form:option value="">--Select--</form:option>
										<form:options items="${themeDetails }"></form:options>
									</form:select>
								</div>
								
								<div class="block-input">
									<label><spring:message code="label.caddr" /><span
										style="color: red">*</span></label>
									<form:input path="address" maxlength="40" />
								</div>
								<div class="block-input last">
									<label><spring:message code="label.caddrs" /></label>
									<form:input path="addr" id="addrId" maxlength="30" />
								</div>
								<div class="block-input">
									<label><spring:message code="label.cland" /><span
										style="color: red">*</span></label>
									<form:input path="landmark"  maxlength="20" />
								</div>
								<div class="block-input">
									<label><spring:message code="label.image" /></label>
									<form:input type="file" path="clientlogo" />
									<form:hidden path="editPath"/>
								</div>
							</div>
							<div class="block-footer">

								<div class="alert-danger" id="duplmsgEdit" style="display: none;">
									<aside class="block-footer-left fail">Warning :
										Client already Exists. Please try Some Other</aside>
								</div>

								<!--  <aside class="block-footer-left sucessfully">Sucessfully Message</aside> -->
								<aside class="block-footer-right">
									<a href="clientHome.htm"><input type="button" class="btn-cancel"
									value="<spring:message code="label.cancel"/>" id="cancel" /></a>
									<input class="btn-save" name="" value="Update" type="submit"
										id="updateId">
								</aside>
							</div>
						</form:form>
					</div>

					<%-- <div class="block table-toop-space">
						<div class="head-box">
							<h2>
								<span class="icon2">&nbsp;</span>
								<spring:message code="label.curclient" />
							</h2>
							<aside class="search-box">
								<form:form action="searchClient.htm" commandName="clientCmd"
									method="get">
									<input class="search-bnt" name="" value="Search" type="submit">
									<form:input path="clientName" cssClass="search-input"
										maxlength="30" id="searchIdEdit" />

								</form:form>
							</aside>
						</div>
						<div class="block-box-big block-box-last-big" id="divheader">
							<ul class="table-list">
                	<li class="one-box"><spring:message code="label.client"/>
                    <ul>
                        <li><a class="top" href="#">&nbsp;</a></li>
                        <li><a class="bottom" href="#">&nbsp;</a></li>
                    </ul>
                </li>
                <li class="two-box"><spring:message code="label.city"/>
                    <ul>
                        <li><a class="top" href="#">&nbsp;</a></li>
                        <li><a class="bottom" href="#">&nbsp;</a></li>
                    </ul>
                </li>
                <li class="three-box"><spring:message code="label.state"/>
                    <ul>
                        <li><a class="top" href="#">&nbsp;</a></li>
                        <li><a class="bottom" href="#">&nbsp;</a></li>
                    </ul>
                </li>
                <li class="four-box"><spring:message code="label.pin"/>
                    <ul>
                        <li><a class="top" href="#">&nbsp;</a></li>
                        <li><a class="bottom" href="#">&nbsp;</a></li>
                    </ul>
                </li>
                <li class="five-box"><spring:message code="label.phone"/>
                    <ul>
                        <li><a class="top" href="#">&nbsp;</a></li>
                        <li><a class="bottom" href="#">&nbsp;</a></li>
                    </ul>
                </li>
                <li class="six-box"><spring:message code="label.bcp"/>
                    <ul>
                        <li><a class="top" href="#">&nbsp;</a></li>
                        <li><a class="bottom" href="#">&nbsp;</a></li>
                    </ul>
                </li>
                <li class="seven-box"><spring:message code="label.bcno"/>
                    <ul>
                        <li><a class="top" href="#">&nbsp;</a></li>
                        <li><a class="bottom" href="#">&nbsp;</a></li>
                    </ul>
                </li>
                <li class="eight-box"><spring:message code="label.baddr"/>
                    <ul>
                        <li><a class="top" href="#">&nbsp;</a></li>
                        <li><a class="bottom" href="#">&nbsp;</a></li>
                    </ul>
                </li>
                <li class="nine-box"><spring:message code="label.email"/>
                    <ul>
                        <li><a class="top" href="#">&nbsp;</a></li>
                        <li><a class="bottom" href="#">&nbsp;</a></li>
                    </ul>
                </li>
                <li class="ten-box"><spring:message code="label.delete"/></li>
                <li class="eleven-box last"><spring:message code="label.edit"/></li>
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
					</div>  --%>
				</c:if> 
				<div class="block table-toop-space">
						<div class="head-box">
							<h2>
								<span class="icon2">&nbsp;</span>
								<spring:message code="label.curclient" />
							</h2>
							<aside class="search-box">
								<form:form action="searchClient.htm" commandName="clientCmd"
									method="get">
									<input class="search-bnt" name="" value="Search" type="submit">
									<form:input path="clientName" cssClass="search-input"
										maxlength="30" id="searchId" />

								</form:form>
							</aside>
						</div>
						<div class="block-box block-box-last" id="divheader">
							<ul class="table-list">
                <li class="one-box"><spring:message code="label.client"/>
                    <ul>
                        <li><a class="top" href="#">&nbsp;</a></li>
                        <li><a class="bottom" href="#">&nbsp;</a></li>
                    </ul>
                </li>
                <li class="two-box"><spring:message code="label.city"/>
                    <ul>
                        <li><a class="top" href="#">&nbsp;</a></li>
                        <li><a class="bottom" href="#">&nbsp;</a></li>
                    </ul>
                </li>
                <li class="three-box"><spring:message code="label.state"/>
                    <ul>
                        <li><a class="top" href="#">&nbsp;</a></li>
                        <li><a class="bottom" href="#">&nbsp;</a></li>
                    </ul>
                </li>
                <li class="four-box"><spring:message code="label.pin"/>
                    <ul>
                        <li><a class="top" href="#">&nbsp;</a></li>
                        <li><a class="bottom" href="#">&nbsp;</a></li>
                    </ul>
                </li>
                <li class="five-box"><spring:message code="label.phone"/>
                    <ul>
                        <li><a class="top" href="#">&nbsp;</a></li>
                        <li><a class="bottom" href="#">&nbsp;</a></li>
                    </ul>
                </li>
                <li class="six-box"><spring:message code="label.bcp"/>
                    <ul>
                        <li><a class="top" href="#">&nbsp;</a></li>
                        <li><a class="bottom" href="#">&nbsp;</a></li>
                    </ul>
                </li>
                <li class="seven-box"><spring:message code="label.bcno"/>
                    <ul>
                        <li><a class="top" href="#">&nbsp;</a></li>
                        <li><a class="bottom" href="#">&nbsp;</a></li>
                    </ul>
                </li>
                <li class="eight-box"><spring:message code="label.baddr"/>
                    <ul>
                        <li><a class="top" href="#">&nbsp;</a></li>
                        <li><a class="bottom" href="#">&nbsp;</a></li>
                    </ul>
                </li>
                <li class="nine-box"><spring:message code="label.email"/>
                    <ul>
                        <li><a class="top" href="#">&nbsp;</a></li>
                        <li><a class="bottom" href="#">&nbsp;</a></li>
                    </ul>
                </li>
                <li class="ten-box"><spring:message code="label.delete"/></li>
                <li class="eleven-box last"><spring:message code="label.edit"/></li>
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
			</div>
		</div>
	</div>
</body>
</html>