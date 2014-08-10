<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Role</title>
<link rel="stylesheet" href="js/jqueryui.css" />
<link rel="stylesheet" href="css/jquery-ui.css" />
<link rel="stylesheet"
	href="js/jquery-ui-1.10.3/themes/base/jquery-ui.css" />
<script src="js/jquery-1.7.2.js"></script>
<script src="js/jquery-ui.js"></script>
<script type="text/javascript" src="js/jquery.validate.min.js"></script>
<script type="text/javascript" src="js/MntValidator.js"></script>

<script type="text/javascript">
	$(document)
			.ready(
					function() {
						$('#ctdescId').val('');

						//AddForm Validations
						$('#submitId')
								.click(
										function(event) {
											//alert("hai");
											$('#addForm')
													.validate(
															{
																rules : {

																	roleName : {
																		required : true,
																		alphanumeric : true,
																		specialcharacters : true
																	}

																},
																messages : {

																	roleName : {
																		required : "<font style='color: red;'><b>Role name is required</b></font>",
																		alphanumeric : "<font style='color: red;'><b>First letter should be an alphanumeric.</b></font>",
																		specialcharacters : "<font style='color: red;'><b>Special Characters except &,_ are not allowed </b></font>",
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

																	roleName : {
																		required : true,
																		alphanumeric : true,
																		specialcharacters : true
																	}

																},
																messages : {

																	roleName : {
																		required : "<font style='color: red;'><b>Role name is required</b></font>",
																		alphanumeric : "<font style='color: red;'><b>First letter should be an alphanumeric.</b></font>",
																		specialcharacters : "<font style='color: red;'><b>Special Characters except &,_ are not allowed </b></font>"
																	}

																},

															});

										});

					});
</script>
</head>
<body>
	<div class="shell">
		<!-- Main -->
		<div id="main">
			<div class="cl">&nbsp;</div>
			<!-- Content -->
			<div id="content">
				<!-- Box -->
				<c:if test="${RoleEdit==null}">
					<div class="box">
						<!-- Box Head -->
						<div class="box-head">
							<h2>Add New Role</h2>
						</div>
						<!-- End Box Head -->

						<form:form action="roleAdd.htm" commandName="roleCmd"
							method="post" id="addForm">
							<div class="form">
								<table>

									<tr>
										<td><label><spring:message code="label.role" /></label></td>
									</tr>
									<tr>
										<td><form:input path="roleName" cssClass="field size4"
												id="roleNameId" /></td>
									</tr>
								</table>
							</div>
							<div class="buttons">
								<table>
									<tr>
										<td colspan="2"><c:forEach var="success"
												items="${param.AddSus}">
												<div class="alert-success">
													<strong><spring:message code="label.success" /></strong>
													Role
													<spring:message code="label.saveSuccess" />

												</div>
											</c:forEach> <c:forEach var="fail" items="${param.AddFail}">
												<div class="alert-danger">
													<strong><spring:message code="label.error" /> </strong>
													Role
													<spring:message code="label.saveFail" />

												</div>
											</c:forEach></td>
										<td colspan="2"><c:forEach var="success"
												items="${param.UpdateSus}">
												<div class="alert-success">
													<strong><spring:message code="label.success" /> </strong>
													Role
													<spring:message code="label.updateSuccess" />
												</div>
											</c:forEach> <c:forEach var="fail" items="${param.UpdateFail}">
												<div class="alert-danger">
													<strong><spring:message code="label.error" /> </strong>
													Role
													<spring:message code="label.updateFail" />


												</div>
											</c:forEach></td>


										<td colspan="2"><c:forEach var="success"
												items="${param.DeleteSus}">
												<div class="alert-success">
													<strong><spring:message code="label.success" /> </strong>
													Role
													<spring:message code="label.deleteSuccess" />

												</div>
											</c:forEach> <c:forEach var="fail" items="${param.DeleteFail}">
												<div class="alert-danger">
													<strong><spring:message code="label.error" /> </strong>
													Role
													<spring:message code="label.deleteFail" />

												</div>
											</c:forEach></td>


									</tr>
								</table>

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
							<h2 class="left">Current Roles</h2>
							<div class="right">
								<form:form action="searchRole.htm" commandName="roleCmd"
									method="get">
									<label>Search Role</label>
									<form:input path="roleName" class="field small-field" />
									<input type="submit" class="button"
										value="<spring:message code="label.search"/>" />
								</form:form>
							</div>
						</div>
						<!-- End Box Head -->

						<!-- Table -->
						<div class="table">
							<display:table name="RoleList" id="RoleIds"
								requestURI="roleHome.htm" class="table" pagesize="5">
								<display:column property="roleId" title="Role Id"
									sortable="true" media="none" />

								<display:column property="roleName" titleKey="label.role"
									sortable="true" media="html" />

								<display:column titleKey="label.delete" sortable="true">
									<a
										href="deleteRole.htm?roleId=<c:out value="${RoleIds.roleId}"/>"
										class="ico del"
										onclick="return confirm('Do You Want To Delete This Record?')"><spring:message
											code="label.delete" /></a>
								</display:column>
								<display:column titleKey="label.edit" sortable="true">
									<a
										href="editRole.htm?roleId=<c:out value="${RoleIds.roleId}"/>"
										class="ico edit"><spring:message code="label.edit" /></a>
								</display:column>
								<%-- <display:column title="All/None" sortable="true" href="">
								<input type="checkbox" name="display" value="1" />
							</display:column> --%>
								<display:setProperty name="paging.banner.placement"
									value="bottom" />
							</display:table>
						</div>
						<!--End Table -->

					</div>
					<!-- End Box -->
				</c:if>
				<c:if test="${RoleEdit!=null}">
					<!--Edit Box  -->


					<div class="box">
						<!-- Box Head -->
						<div class="box-head">
							<h2>Edit Role</h2>
						</div>
						<!-- End Box Head -->

						<form:form action="roleUpdate.htm" commandName="roleCmd"
							method="post" id="editForm">
							<div class="form">
								<table>
									<tr>
										<%-- <td><label><spring:message code="label.dept" /></label></td> --%>
										<td><label><spring:message code="label.role" /></label></td>
									</tr>
									<form:hidden path="roleId" />
									<tr>
										<%-- <td><form:select path="deptId" cssClass="field size4">
												<form:option value="">--Select--</form:option>
												<form:options items="${deptDeatails }"></form:options>
											</form:select></td> --%>
										<td><form:input path="roleName" cssClass="field size4" /></td>
									</tr>

								</table>
							</div>
							<div class="buttons">
								<a href="roleHome.htm"><input type="button" class="button"
									value="<spring:message code="label.cancel"/>" id="cancelId" /></a>
								<input type="submit" class="button"
									value="<spring:message code="label.update"/>" id="updateId" />

							</div>
						</form:form>

					</div>

				</c:if>
				<!--Edit Box End  -->

			</div>
			<!-- End Content -->

			<div class="cl">&nbsp;</div>
		</div>
		<!-- Main -->
	</div>

</body>
</html>