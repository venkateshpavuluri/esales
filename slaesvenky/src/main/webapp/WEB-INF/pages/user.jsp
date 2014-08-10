<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>User</title>
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
						$("#fname").focus();
						$('#addName').val('');
						$('#usermsg').hide();
						$('#usermsgedit').hide();
						//AddForm Validations
						$('#submitId')
								.click(
										function(event) {
											//alert("hai");
											$('#addForm')
													.validate(
															{
																rules : {
																	username : {
																		required : true,
																		alphanumeric : true,
																		specialcharacters : true

																	},
																	password : {
																		required : true,
																	},
																	cpwd : {
																		equalTo : "#password"
																	},
																	firstname : {
																		required : true,
																		alphanumeric : true,
																		specialcharacters : true

																	},
																	lname : {
																		required : true,
																		alphanumeric : true,
																		specialcharacters : true

																	},
																	email : {
																		required : true,
																		email : true,

																	},
																	roleId : {
																		required : true,
																	},
																	clienId : {
																		required : true,

																	}

																},
																messages : {
																	username : {
																		required : "<font style='color: red;'><b>User Name is Required</b></font>",
																		alphanumeric : "<font style='color: red;'><b>First letter should be an alphanumeric.</b></font>",
																		specialcharacters : "<font style='color: red;'><b>Special Characters except &,_ are not allowed </b></font>"
																	},
																	password : {
																		required : "<font style='color: red;'><b>Password is Required</b></font>",
																	},
																	cpwd : {
																		equalTo : "<font style='color: red;'><b>Both Should be the same</b></font>",
																	},
																	firstname : {
																		required : "<font style='color: red;'><b>First Name is Required</b></font>",
																		alphanumeric : "<font style='color: red;'><b>First letter should be an alphanumeric.</b></font>",
																		specialcharacters : "<font style='color: red;'><b>Special Characters except &,_ are not allowed </b></font>"
																	},
																	lname : {
																		required : "<font style='color: red;'><b>Last Name is Required</b></font>",
																		alphanumeric : "<font style='color: red;'><b>First letter should be an alphanumeric.</b></font>",
																		specialcharacters : "<font style='color: red;'><b>Special Characters except &,_ are not allowed </b></font>"
																	},
																	email : {
																		required : "<font style='color: red;'><b>Email is Required</b></font>",
																		email : "<font style='color: red;'><b>Should be in Email Format</b></font>",
																		specialcharacters : "<font style='color: red;'><b>Special Characters except &,_ are not allowed </b></font>"
																	},
																	roleId : {
																		required : "<font style='color: red;'><b>Role  is Required</b></font>",
																	},
																	clienId : {
																		required : "<font style='color: red;'><b>Client  is Required</b></font>",
																	}

																},

															});
											$('#addForm')
													.attr('action',
															"<c:url value='/userAdd.htm'/>");
											$("#addForm").submit();
											event.preventDefault();

										});
						$('#updatebutton')
								.click(
										function(event) {
											$('#updateForm')
													.validate(
															{
																rules : {
																	username : {
																		required : true,
																		alphanumeric : true,
																		specialcharacters : true

																	},
																	password : {
																		required : true,
																	},
																	cpwd : {
																		equalTo : "#password"
																	},
																	firstname : {
																		required : true,
																		alphanumeric : true,
																		specialcharacters : true

																	},
																	lname : {
																		required : true,
																		alphanumeric : true,
																		specialcharacters : true

																	},
																	email : {
																		required : true,
																		email : true,

																	},
																	roleId : {
																		required : true,
																	},
																	clientId : {
																		required : true,
																	},

																},
																messages : {
																	username : {
																		required : "<font style='color: red;'><b>User Name is Required</b></font>",
																		alphanumeric : "<font style='color: red;'><b>First letter should be an alphanumeric.</b></font>",
																		specialcharacters : "<font style='color: red;'><b>Special Characters except &,_ are not allowed </b></font>"
																	},
																	password : {
																		required : "<font style='color: red;'><b>Password is Required</b></font>",
																	},
																	cpwd : {
																		equalTo : "<font style='color: red;'><b>Both Should be the same</b></font>",
																	},
																	firstname : {
																		required : "<font style='color: red;'><b>First Name is Required</b></font>",
																		alphanumeric : "<font style='color: red;'><b>First letter should be an alphanumeric.</b></font>",
																		specialcharacters : "<font style='color: red;'><b>Special Characters except &,_ are not allowed </b></font>"
																	},
																	lname : {
																		required : "<font style='color: red;'><b>Last Name is Required</b></font>",
																		alphanumeric : "<font style='color: red;'><b>First letter should be an alphanumeric.</b></font>",
																		specialcharacters : "<font style='color: red;'><b>Special Characters except &,_ are not allowed </b></font>"
																	},
																	email : {
																		required : "<font style='color: red;'><b>Email is Required</b></font>",
																		email : "<font style='color: red;'><b>Should be in Email Format</b></font>",
																		specialcharacters : "<font style='color: red;'><b>Special Characters except &,_ are not allowed </b></font>"
																	},
																	roleId : {
																		required : "<font style='color: red;'><b>Role  is Required</b></font>",

																	},
																	clienId : {
																		required : "<font style='color: red;'><b>Client is Required</b></font>",

																	},

																},

															});
											$('#updateForm')
													.attr('action',
															"<c:url value='/userUpdate.htm'/>");
											$("#updateForm").submit();
											event.preventDefault();

										});

					});

	function getgrid(id) {
		var roleId = $('#roleId').val();
		var clientId = $("#clientId").val();
		$("#userdata ul li").remove();
		$
				.ajax({
					type : "POST",
					url : "searchWithUser.htm",
					data : "roleId=" + roleId + "&clientId=" + clientId,
					dataType : "json",
					success : function(ListData) {
						if (ListData == "" || ListData == null) {
							$("#userdata ul li").remove();
							$('#noSortData').show();
						} else {
							$('#noSortData').hide();
							$
									.each(
											ListData,
											function(index, user) {
												if (user.status == true) {
													var tblRow = "<ul>"
															+ "<li class='user-fname-box'>"
															+ user.username
															+ "</li>"
															+ "<li class='user-fname-box'>"
															+ user.firstname
															+ "</li>"
															+ "<li class='user-fname-box'>"
															+ user.lname
															+ "</li>"
															+ "<li class='six-box'>"
															+ user.roleName
															+ "</li>"
															+ "<li class='seven-box'>"
															+ user.contactno
															+ "</li>"
															+ "<li class='nine-box'>"
															+ user.email
															+ "</li>"
															+ "<li class='ten-box'>"
															+ '<a href="userDelete.htm?user_Id='
															+ user.userId
															+ '" class="del">Delete</a>'
															+ '</li>'
															+ "<li class='eleven-box'>"
															+ '<a href="userEdit.htm?user_Id='
															+ user.userId
															+ '" class="ico edit">Edit</a>'
															+ '</li>'
															+ "<li class='eleven-box last'>"
															+ '<input type="checkbox" id="'
															+ user.userId
															+ '" checked="checked" onchange="check(this,this.id)" name="'
															+ user.userId
															+ '"'
															+ 'value='
															+ user.status
															+ '/>'
															+ "</li>"
															+ '</ul>';
													$(tblRow).appendTo(
															"#userdata");
													
												} else {
													var tblRow = "<ul>"
															+ "<li class='user-fname-box'>"
															+ user.username
															+ "</li>"
															+ "<li class='user-fname-box'>"
															+ user.firstname
															+ "</li>"
															+ "<li class='user-fname-box'>"
															+ user.lname
															+ "</li>"
															+ "<li class='six-box'>"
															+ user.roleName
															+ "</li>"
															+ "<li class='seven-box'>"
															+ user.contactno
															+ "</li>"
															+ "<li class='nine-box'>"
															+ user.email
															+ "</li>"
															+ "<li class='ten-box'>"
															+ '<a href="userDelete.htm?user_Id='
															+ user.userId
															+ '" class="ico del">Delete</a>'
															+ '</li>'
															+ "<li class='eleven-box'>"
															+ '<a href="userEdit.htm?user_Id='
															+ user.userId
															+ '" class="ico edit">Edit</a>'
															+ '</li>'
															+ "<li class='eleven-box last'>"
															+ '<input type="checkbox" id="'
															+ user.userId
															+ '"  onchange="check(this,this.id)" name="'
															+ user.userId
															+ '"'
															+ 'value='
															+ user.status
															+ '/>'
															+ "</li>"
															+ '</ul>';
													$(tblRow).appendTo(
															"#userdata");
													
												}

											});
							$(".del")
							.click(
									function(
											event) {
										return confirm('Do u want to Delete The Record?');
									});

						}
					},
					error : function(e) {

					}

				});

	}
	function getgridedit(id) {
		var roleId = $('#roleIdedit').val();
		var clientId = $("#clientIdedit").val();
		$("#userdata ul li").remove();
		$
				.ajax({
					type : "POST",
					url : "searchWithUser.htm",
					data : "roleId=" + roleId + "&clientId=" + clientId,
					dataType : "json",
					success : function(ListData) {
						if (ListData == "" || ListData == null) {
							$("#userdata ul li").remove();
							$('#noSortData').show();
						} else {
							$('#noSortData').hide();
							$
									.each(
											ListData,
											function(index, user) {
												if (user.status == true) {
													var tblRow = "<ul>"
															+ "<li class='user-fname-box'>"
															+ user.username
															+ "</li>"
															+ "<li class='user-fname-box'>"
															+ user.firstname
															+ "</li>"
															+ "<li class='user-fname-box'>"
															+ user.lname
															+ "</li>"
															+ "<li class='nine-box'>"
															+ user.email
															+ "</li>"
															+ "<li class='seven-box'>"
															+ user.contactno
															+ "</li>"
															+ "<li class='ten-box'>"
															+ '<a href="userDelete.htm?user_Id='
															+ user.userId
															+ '" class="del">Delete</a>'
															+ '</li>'
															+ "<li class='eleven-box'>"
															+ '<a href="userEdit.htm?user_Id='
															+ user.userId
															+ '" class="ico edit">Edit</a>'
															+ '</li>'
															+ "<li class='eleven-box last'>"
															+ '<input type="checkbox" id="'
															+ user.userId
															+ '" checked="checked" onchange="check(this,this.id)" name="'
															+ user.userId
															+ '"'
															+ 'value='
															+ user.status
															+ '/>'
															+ "</li>"
															+ '</ul>';
													$(tblRow).appendTo(
															"#userdata");
												} else {
													var tblRow = "<ul>"
															+ "<li class='user-fname-box'>"
															+ user.username
															+ "</li>"
															+ "<li class='user-fname-box'>"
															+ user.firstname
															+ "</li>"
															+ "<li class='user-fname-box'>"
															+ user.lname
															+ "</li>"
															+ "<li class='nine-box'>"
															+ user.email
															+ "</li>"
															+ "<li class='seven-box'>"
															+ user.contactno
															+ "</li>"
															+ "<li class='ten-box'>"
															+ '<a href="userDelete.htm?user_Id='
															+ user.userId
															+ '" class="ico del">Delete</a>'
															+ '</li>'
															+ "<li class='eleven-box'>"
															+ '<a href="userEdit.htm?user_Id='
															+ user.userId
															+ '" class="ico edit">Edit</a>'
															+ '</li>'
															+ "<li class='eleven-box last'>"
															+ '<input type="checkbox" id="'
															+ user.userId
															+ '"  onchange="check(this,this.id)" name="'
															+ user.userId
															+ '"'
															+ 'value='
															+ user.status
															+ '/>'
															+ "</li>"
															+ '</ul>';
													$(tblRow).appendTo(
															"#userdata");
												
												}

											});
							$(".del")
							.click(
									function(
											event) {
										return confirm('Do u want to Delete The Record?');
									});

						}
					},
					error : function(e) {

					}

				});

	}

	function check(elem, userId) {
		var uId = userId;
		var value = 0;
		if (elem.checked) {
			$.ajax({

				type : "POST",

				url : "saveStatus.htm",

				data : "uId=" + uId,

				success : function(response) {
				},
				error : function(e) {
					// alert('Error: ' + e);

				}

			})

		} else {
			$.ajax({

				type : "POST",

				url : "saveStatus.htm",

				data : "uId=" + uId + "&value=" + value,

				success : function(response) {
				},
				error : function(e) {
					// alert('Error: ' + e);

				}

			})
		}
	}
</script>
<script type="text/javascript">
	function duplicatecheckAjax() {
		var uname = $('#uname').val();
		$
				.ajax({
					type : "POST",

					url : "userDuplicateCheck.htm",

					data : "uname=" + uname,

					success : function(response) {

						var checkResponse = "Warning ! UserName is already exists. Please try some other name";

						if (checkResponse == response) {

							document.getElementById("usermsg").style.display = "block";
							$('#submitId').hide();
							$('#submitId').attr('disabled', 'disabled');
						} else {
							document.getElementById("usermsg").style.display = "none";
							$('#submitId').show();
							$('#submitId').removeAttr('disabled');
						}
					},
					error : function(e) {
						//alert('Error: ' + e);
					}
				});

	}
	function duplicatecheckAjaxEdit() {
		var unameedit = $('#unameedit').val();
		var userid = $('#userid').val();
		$
				.ajax({
					type : "POST",

					url : "userDuplicateCheckEdit.htm",

					data : "unameedit=" + unameedit + "&userid=" + userid,

					success : function(response) {
						var checkResponse = "Warning ! UserName is already exists. Please try some other name";
						if (checkResponse == response) {
							document.getElementById("usermsgedit").style.display = "block";
							$('#updatebutton').hide();
							$('#updatebutton').attr('disabled', 'disabled');
						} else {
							document.getElementById("usermsgedit").style.display = "none";
							$('#updatebutton').show();
							$('#updatebutton').removeAttr('disabled');
						}
					},
					error : function(e) {
						//alert('Error: ' + e);
					}
				});

	}
</script>
<script type="text/javascript">
	$(document)
			.ready(
					function() {
						var listofuserswithId = ${listofuserswithId};
						if (listofuserswithId == ""
								|| listofuserswithId == null) {
							$("#userdata ul li").remove();
							$('#noSortData').show();
						} else {
							$('#noSortData').hide();
							$
									.each(
											listofuserswithId,
											function(index, value) {
												if (value.status == true) {
													var tblRow = "<ul>"
															+ "<li class='user-fname-box'>"
															+ value.username
															+ "</li>"
															+ "<li class='user-fname-box'>"
															+ value.firstname
															+ "</li>"
															+ "<li class='user-fname-box'>"
															+ value.lname
															+ "</li>"
															+ "<li class='six-box'>"
															+ value.roleName
															+ "</li>"
															+ "<li class='seven-box'>"
															+ value.contactno
															+ "</li>"
															+ "<li class='nine-box'>"
															+ value.email
															+ "</li>"
															+ "<li class='ten-box'>"
															+ '<a href="userDelete.htm?user_Id='
															+ value.userId
															+ '" class="del">Delete</a>'
															+ '</li>'
															+ "<li class='eleven-box'>"
															+ '<a href="userEdit.htm?user_Id='
															+ value.userId
															+ '" class="ico edit">Edit</a>'
															+ '</li>'
															+ "<li class='eleven-box last'>"
															+ '<input type="checkbox" id="'
															+ value.userId
															+ '" checked="checked" onchange="check(this,this.id)" name="'
															+ value.userId
															+ '"'
															+ 'value='
															+ value.status
															+ '/>'
															+ "</li>"
															+ '</ul>';
													$(tblRow).appendTo(
															"#userdata");
												} else {
													var tblRow = "<ul>"
															+ "<li class='user-fname-box'>"
															+ value.username
															+ "</li>"
															+ "<li class='user-fname-box'>"
															+ value.firstname
															+ "</li>"
															+ "<li class='user-fname-box'>"
															+ value.lname
															+ "</li>"
															+ "<li class='six-box'>"
															+ value.roleName
															+ "</li>"
															+ "<li class='seven-box'>"
															+ value.contactno
															+ "</li>"
															+ "<li class='nine-box'>"
															+ value.email
															+ "</li>"
															+ "<li class='ten-box'>"
															+ '<a href="userDelete.htm?user_Id='
															+ value.userId
															+ '" class="del">Delete</a>'
															+ '</li>'
															+ "<li class='eleven-box'>"
															+ '<a href="userEdit.htm?user_Id='
															+ value.userId
															+ '" class="ico edit">Edit</a>'
															+ '</li>'
															+ "<li class='eleven-box last'>"
															+ '<input type="checkbox" id="'
															+ value.userId
															+ '"  onchange="check(this,this.id)" name="'
															+ value.userId
															+ '"'
															+ 'value='
															+ value.status
															+ '/>'
															+ "</li>"
															+ '</ul>';
													$(tblRow).appendTo(
															"#userdata");
												}
												
											});
							$(".del").click(function(event) {
								   return confirm('Do u want to Delete The Record?');
														});
						}
					});
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
				<c:if test="${userEdit==null}">
					<div class="block">
						<h2>
							<span class="icon1">&nbsp;</span>Add New User
						</h2>
						<form:form action="" commandName="USER" method="post" id="addForm">
							<div class="block-box">
								<div class="block-input">
									<label><spring:message code="label.client" /><span
										style="color: red;">*</span></label>
									<c:choose>
										<c:when test="${sessionScope.admin=='clientadmin' }">


											<form:select path="clientId" disabled="true"
												cssClass="field size3">
												<c:choose>
													<c:when test="${not empty sessionScope.clientId}">

														<form:options items="${client}"></form:options>
													</c:when>

													<c:otherwise>
														<form:option value="">--Select--</form:option>
														<form:options items="${client}"></form:options>
													</c:otherwise>
												</c:choose>
											</form:select>
										</c:when>
										<c:otherwise>
											<form:select path="clientId" id="clientId"
												onchange="getgrid(this.id)" cssClass="field size3">
												<c:choose>
													<c:when test="${not empty sessionScope.clientId}">
														<form:option value="">--Select--</form:option>
														<form:options items="${client}"></form:options>
													</c:when>

													<c:otherwise>

														<form:options items="${client}"></form:options>
													</c:otherwise>
												</c:choose>
											</form:select>
										</c:otherwise>
									</c:choose>
								</div>
								<div class="block-input">
									<label><spring:message code="label.role" /><span
										style="color: red;">*</span></label>
									<form:select path="roleId" id="roleId"
										onchange="getgrid(this.id)" cssClass="field size3">
										<form:option value="">--Select--</form:option>
										<form:options items="${role}" />
									</form:select>
								</div>
								<div class="block-input last">
									<label><spring:message code="label.contactno" /><span
										style="color: red;">*</span></label>
									<form:input path="contactno" />
								</div>
								<div class="block-input">
									<label><spring:message code="label.fname" /><span
										style="color: red;">*</span></label>
									<form:input path="firstname" id="fname" />
								</div>
								<div class="block-input">
									<label><spring:message code="label.lname" /><span
										style="color: red;">*</span></label>
									<form:input path="lname" />
								</div>
								<div class="block-input last">
									<label><spring:message code="label.email" /><span
										style="color: red;">*</span></label>
									<form:input path="email" />
								</div>
								<div class="block-input">
									<label><spring:message code="label.uname" /><span
										style="color: red;">*</span></label>
									<form:input path="username" id="uname"
										onkeyup="duplicatecheckAjax();" />
								</div>
								<div class="block-input">
									<label><spring:message code="label.pwd" /><span
										style="color: red;">*</span></label>
									<form:password path="password" />
								</div>
								<div class="block-input last">
									<label><spring:message code="label.cpwd" /><span
										style="color: red;">*</span></label>
									<form:password path="cpwd" />
								</div>
								<div class="block-input">
									<label><spring:message code="label.hq" /></label>
									<form:input path="hintq" />
								</div>
								<div class="block-input">
									<label><spring:message code="label.ha" /></label>
									<form:input path="hintans" />
								</div>
								<div class="block-input last">
									<label><spring:message code="label.address" /><span
										style="color: red;">*</span></label>
									<form:textarea path="address" />
								</div>
							</div>
							<div class="block-footer">
								<aside class="block-footer-left sucessfully">
								<div id="usermsg">
									<strong> Warning : </strong> UserName already Exists. Please
									try Some Other
								</div>
								<c:forEach var="success" items="${param.AddSus}">
									<strong><spring:message code="label.success" /> </strong>
													User
													<spring:message code="label.saveSuccess" />

								</c:forEach> <c:forEach var="fail" items="${param.AddFail}">
									<strong><spring:message code="label.error" /> </strong>
													User
													<spring:message code="label.saveFail" />

								</c:forEach> <c:forEach var="success" items="${param.UpdateSus}">
									<strong><spring:message code="label.success" /> </strong>
													User
													<spring:message code="label.updateSuccess" />
								</c:forEach> <c:forEach var="fail" items="${param.UpdateFail}">
									<strong><spring:message code="label.error" /> </strong>
													User
													<spring:message code="label.updateFail" />
								</c:forEach> <c:forEach var="success" items="${param.DeleteSus}">
									<strong><spring:message code="label.success" /> </strong>
													User
													<spring:message code="label.deleteSuccess" />
								</c:forEach> <c:forEach var="fail" items="${param.DeleteFail}">
									<strong><spring:message code="label.error" /> </strong>
													User
													<spring:message code="label.deleteFail" />
								</c:forEach> </aside>
								<aside class="block-footer-right"> <input
									class="btn-cancel" name=""
									value="<spring:message code="label.clear"/>" type="button">
								<input class="btn-save" name=""
									value="<spring:message code="label.save"/>" id="submitId"
									type="submit"> </aside>
							</div>
						</form:form>
					</div>
				</c:if>

				<!-- Edit -->
				<c:if test="${userEdit!=null}">
					<div class="block">
						<h2>
							<span class="icon1">&nbsp;</span>Edit User
						</h2>
						<form:form action="" commandName="USER" method="post"
							id="updateForm">
							<div class="block-box">
								<div class="block-input">
									<form:hidden path="userId" id="userid" />
									<label><spring:message code="label.client" /><span
										style="color: red;">*</span></label>

									<c:choose>
										<c:when test="${sessionScope.admin=='clientadmin' }">
											<form:select path="clientIdedit" id="clientIdedit"
												disabled="true" cssClass="field size3">
												<c:choose>
													<c:when test="${not empty sessionScope.clientId}">

														<form:options items="${client}"></form:options>
													</c:when>

													<c:otherwise>
														<form:option value="">--Select--</form:option>
														<form:options items="${client}"></form:options>
													</c:otherwise>
												</c:choose>
											</form:select>
										</c:when>
										<c:otherwise>
											<form:select path="clientIdedit"
												onchange="getgridedit(this.id)" cssClass="field size3">
												<c:choose>
													<c:when test="${not empty sessionScope.clientId}">
														<form:option value="">--Select--</form:option>
														<form:options items="${client}"></form:options>
													</c:when>
													<c:otherwise>
														<form:options items="${client}"></form:options>
													</c:otherwise>
												</c:choose>
											</form:select>
										</c:otherwise>
									</c:choose>
								</div>
								<div class="block-input">
									<label><spring:message code="label.role" /><span
										style="color: red;">*</span></label>
									<form:select path="roleId" id="roleIdedit"
										onchange="getgridedit(this.id)" cssClass="field size3">
										<form:option value="">--Select--</form:option>
										<form:options items="${role}" />
									</form:select>
								</div>
								<div class="block-input last">
									<label><spring:message code="label.contactno" /><span
										style="color: red;">*</span></label>
									<form:input path="contactno" />
								</div>
								<div class="block-input">
									<label><spring:message code="label.fname" /><span
										style="color: red;">*</span></label>
									<form:input path="firstname" id="fname" />
								</div>
								<div class="block-input">
									<label><spring:message code="label.lname" /><span
										style="color: red;">*</span></label>
									<form:input path="lname" />
								</div>
								<div class="block-input last">
									<label><spring:message code="label.email" /><span
										style="color: red;">*</span></label>
									<form:input path="email" />
								</div>
								<div class="block-input">
									<label><spring:message code="label.uname" /><span
										style="color: red;">*</span></label>
									<form:input path="username" id="uname"
										onkeyup="duplicatecheckAjax();" />
								</div>
								<div class="block-input">
									<label><spring:message code="label.pwd" /><span
										style="color: red;">*</span></label>
									<form:password path="password" />
								</div>
								<div class="block-input last">
									<label><spring:message code="label.cpwd" /><span
										style="color: red;">*</span></label>
									<form:password path="cpwd" />
								</div>
								<div class="block-input">
									<label><spring:message code="label.hq" /></label>
									<form:input path="hintq" />
								</div>
								<div class="block-input">
									<label><spring:message code="label.ha" /></label>
									<form:input path="hintans" />
								</div>
								<div class="block-input last">
									<label><spring:message code="label.address" /><span
										style="color: red;">*</span></label>
									<form:textarea path="address" />
								</div>
							</div>
							<div class="block-footer">
								<aside class="block-footer-left sucessfully">
								<div id="usermsgedit">
									<strong> Warning : </strong> UserName already Exists. Please
									try Some Other
								</div>
								<c:forEach var="success" items="${param.AddSus}">
									<strong><spring:message code="label.success" /> </strong>
													User
													<spring:message code="label.saveSuccess" />

								</c:forEach> <c:forEach var="fail" items="${param.AddFail}">
									<strong><spring:message code="label.error" /> </strong>
													User
													<spring:message code="label.saveFail" />

								</c:forEach> <c:forEach var="success" items="${param.UpdateSus}">
									<strong><spring:message code="label.success" /> </strong>
													User
													<spring:message code="label.updateSuccess" />
								</c:forEach> <c:forEach var="fail" items="${param.UpdateFail}">
									<strong><spring:message code="label.error" /> </strong>
													User
													<spring:message code="label.updateFail" />
								</c:forEach> <c:forEach var="success" items="${param.DeleteSus}">
									<strong><spring:message code="label.success" /> </strong>
													User
													<spring:message code="label.deleteSuccess" />
								</c:forEach> <c:forEach var="fail" items="${param.DeleteFail}">
									<strong><spring:message code="label.error" /> </strong>
													User
													<spring:message code="label.deleteFail" />
								</c:forEach> </aside>
								<aside class="block-footer-right"> <input
									class="btn-cancel" name=""
									value="<spring:message code="label.clear"/>" type="button">
								<input class="btn-save" name=""
									value="<spring:message code="label.update"/>" id="updatebutton"
									type="submit"> </aside>
							</div>
						</form:form>
					</div>
				</c:if>
			</div>
			<div class="block table-toop-space">
				<div class="head-box">
					<h2>
						<span class="icon2">&nbsp;</span>Current Users
					</h2>
					<aside class="search-box"> <form:form
						action="userSearch.htm" commandName="USER" method="get">
						<input type="submit" class="search-bnt"
							value="<spring:message code="label.search"/>" />
						<form:input path="uname" class="search-input" id="searchIds" />
					</form:form> </aside>


				</div>
				<div class="block-box block-box-last">
					<ul class="table-list">
						<li class="user-fname-box">User Name
							<ul>
								<li><a class="top" href="#">&nbsp;</a></li>
								<li><a class="bottom" href="#">&nbsp;</a></li>
							</ul>
						</li>
						<li class="user-fname-box">First Name
							<ul>
								<li><a class="top" href="#">&nbsp;</a></li>
								<li><a class="bottom" href="#">&nbsp;</a></li>
							</ul>
						</li>
						<li class="user-fname-box">Last Name
							<ul>
								<li><a class="top" href="#">&nbsp;</a></li>
								<li><a class="bottom" href="#">&nbsp;</a></li>
							</ul>
						</li>
						<li class="six-box">Role
							<ul>
								<li><a class="top" href="#">&nbsp;</a></li>
								<li><a class="bottom" href="#">&nbsp;</a></li>
							</ul>
						</li>

						<li class="seven-box">Contact No
							<ul>
								<li><a class="top" href="#">&nbsp;</a></li>
								<li><a class="bottom" href="#">&nbsp;</a></li>
							</ul>
						</li>
						<li class="nine-box">Email
							<ul>
								<li><a class="top" href="#">&nbsp;</a></li>
								<li><a class="bottom" href="#">&nbsp;</a></li>
							</ul>
						</li>
						<li class="ten-box">Delete</li>
						<li class="eleven-box">Edit</li>
						<li class="four-box last">Status</li>
					</ul>
					<div class="table-list-blk" id="userdata"></div>
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
						</aside>-->
				</div>
			</div>
		</div>
	</div>
</body>
</html>