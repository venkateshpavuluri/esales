<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<HTML>
<HEAD>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="css/demos.css" />
<link rel="stylesheet" href="js/jqueryui.css" />
<link rel="stylesheet" href="css/jquery-ui.css" />
<link rel="stylesheet"
	href="js/jquery-ui-1.10.3/themes/base/jquery-ui.css" />
<script src="js/jquery-1.7.2.js"></script>
<script src="js/jquery-ui.js"></script>
<script type="text/javascript" src="js/jquery.validate.min.js"></script>
<script type="text/javascript" src="js/MntValidator.js"></script>
<style type="text/css">
.required {
	color: red;
	font-style: Bold;
}

.error {
	border: 2px solid #f00;
}
</style>
<script type="text/javascript">
	$(document).ready(function() {
		$('#submitId').click(function(event) {
			$('#loginForm').validate({
				rules : {
					userName : {
						required : true,
						alphanumeric : true,
						specialcharacters : true

					},
					password : {
						required : true
					}

				},

				errorPlacement : function() {
					return false;
				},
			/* messages : {
				userName : {
					required : "<font style='color: red;'><b>User Name is Required</b></font>",
					alphanumeric : "<font style='color: red;'><b>First letter should be an alphanumeric.</b></font>",
					specialcharacters : "<font style='color: red;'><b>Special Characters except &,_ are not allowed </b></font>"
				},
				password:{required : "<font style='color: red;'><b>Password is Required</b></font>",}

			}, */

			});

		});
	});
</script>
</HEAD>
<BODY>
	<div class="header"></div>
	<div class="content">
		<div class="leftpanel">
			<div class="flip-container"
				ontouchstart="this.classList.toggle('hover');">
				<div class="flipper">
					<div class="front" id="leftpanel">
						<!-- front content -->
						<table class="table">
							<tr>
								<td>
									<!--<p class="vbox">Integration</P>
<p class="vbox">Stock Optimization</P>
<p class="vbox">Improved Customer Service</P>--> <img
									src="css/images/147852.png" class="imagealign" />

								</td>
								<td></td>
							</tr>
						</table>
					</div>
					<div class="back">
						<!-- back content -->
						<div class="contentdata">PROVIDES OPERATIONAL EXCELLENCE
							WITH AN INTEGRATED RETAIL PLANNING SOLUTION</div>

						<div class="contentdata1">SIMPLIFIES THE RETAIL PLANNING
							PROCESS TO GAIN GREATER VISIBILITY AND CONTROL OF YOUR BUSINESS</div>
					</div>
				</div>
			</div>

		</div>

	</div>
	<div class="rightpanel">
		<div>
			<%-- <div align="center">
				<c:forEach var="FailMessage" items="${FailMessage}">
					<font color="red" size="4"> <c:out value="${FailMessage}"></c:out></font>
				</c:forEach>
			</div> --%>
			<form method="post" action="loginUser.htm" id="loginForm">
				<div class="form">
					<table class="tableGeneral">
						<!--  <tr>
							<td>Company ID</td>
							<td><input type="text" placeholder="Company ID"></td> 
						</tr> -->
						<tr>
							<td colspan="2"><c:forEach var="FailMessage"
									items="${FailMessage}">
									<font color="red" size="5"> <c:out
											value="${FailMessage}"></c:out></font>
								</c:forEach></td>
						</tr>
						<tr>
							<td colspan="2"><spring:message code="label.lang" /> : <a
								href="?lang=en">English</a>|<a href="?lang=hi">Hindi</a>|<a
								href="?lang=te">Telugu</a></td>
						</tr>
						<tr>
							<td><label><spring:message code="label.username" /></label></td>
							<td><input type="text" name="userName" value=""
								placeholder="User Name" maxlength="30"></td>
						</tr>
						<tr>
							<td><label><spring:message code="label.password" /></label></td>
							<td><input type="password" name="password" value=""
								placeholder="Password" maxlength="20"></td>
						</tr>
						<tr>
							<td><input type="reset" class="button"
								value="<spring:message code="label.clear" />" /> <input
								type="submit" class="button"
								value="<spring:message code="label.login" /> " id="submitId" /></td>
						</tr>
					</table>
				</div>
			</form>
			<div class="forgot">
				<spring:message code="label.forgot" />
			</div>
		</div>
		<div class="data">
			<spring:message code="label.autusers" />
		</div>
	</div>
</BODY>

</HTML>






