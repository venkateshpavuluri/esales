<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Department</title>
<link href="style.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="js/jqueryui.css" />
<link rel="stylesheet" href="css/jquery-ui.css" />
<link rel="stylesheet"
	href="js/jquery-ui-1.10.3/themes/base/jquery-ui.css" />
<script src="js/jquery-1.7.2.js"></script>
<script src="js/jquery-ui.js"></script>
<script type="text/javascript" src="js/jquery.validate.min.js"></script>
<script type="text/javascript" src="js/MntValidator.js"></script>
<script type="text/javascript">
	$(function() {
		$("#sdate").datepicker({
			changeDate : true,
			changeMonth : true,
			changeYear : true,
			showButtonPanel : false,
			dateFormat : 'yy-mm-dd'
		});

	});
</script>
<script type="text/javascript">
	$(document)
			.ready(
					function() {
						$('#branchId').val('');
						$('#sdate').val('');
						$('#format').val('');
						//AddForm Validations
						$('#submitId')
								.click(
										function(event) {
											//alert("hai");
											$('#sales')
													.validate(
															{
																rules : {
																	branchId : {
																		required : true,
																	},
																	sdate : {
																		required : true,
																		
																	},
																	format : {
																		required : true,
																		
																	}

																},
																messages : {
																	branchId : {
																		required : "<font style='color: red;'><b>Branch is Required</b></font>",
																	},
																	sdate : {
																		required : "<font style='color: red;'><b>Date is Required</b></font>",
																			},
																	format : {
																		required : "<font style='color: red;'><b>format is Required</b></font>",
																					}

																},

															});

										});

					});
</script>
</head>
<body>


	 <div id="container">
		<div class="shell">
			<!-- Main -->
			<div id="main">
				<div class="cl">&nbsp;</div>

				<!-- Content -->
				<div id="content">
             
					<!-- Box -->
					<div class="box">
						<!-- Box Head -->
						<div class="box-head">
							<h2>Sales Report Based on Location Wise </h2>
						</div>
						<!-- End Box Head -->
                       
						
							<div class="form" id="msgInfo">
							<form:form action="/salesReportLocWise.htm" id="sales" method="GET" commandName="salesCmd">
								<table class="tableGeneral">
							<tr>
							<td><spring:message code="label.reportFormat" /><font
									color="red"><strong>*</strong></font>:</td>
							<td><form:select path="format" cssClass="select">
							<form:option value="">--Select--</form:option>
							<form:option value="pdf">pdf</form:option>
							<form:option value="xls">xls</form:option>
							<form:option value="csv">csv</form:option>
							<form:option value="html">html</form:option>
							</form:select></td></tr>
							<tr>
							<td><spring:message code="label.branchId" />
							<font color="red"><strong>*</strong></font>:</td>
									<c:set value="admin" var="role"/>
												<c:choose>
													<c:when test="${sessionScope.admin eq role }">	
								<td><form:select path="branchId"  cssClass="select">
							<form:option value="">--Select--</form:option>
							<form:options items="${branchDetails}" />
							</form:select></td>
							</c:when>
							<c:otherwise>
							<td><form:select path="branchId" id="branchId" cssClass="select">
							<form:option value="">--Select--</form:option>
							<form:options items="${branchDetails}" />
							</form:select></td>
							</c:otherwise>
							</c:choose>
						
									</tr>
									<tr>
								<td><spring:message code="label.date" /><font
									color="red"><strong>*</strong></font>:</td>
									<td><form:input path="sdate" class="field size4" id="sdate"/></td>
							</tr>
							
						</table>
				
							<!-- Form Buttons -->
							<div class="buttons">
								<input type="reset" class="button" value="Clear" /> 
								<input type="submit" class="button" value="Submit" id="submitId"/>
							</div>
							<!-- End Form Buttons -->

						</form:form>
					</div>
					<!-- End Box -->
				</div>
			</div>
		</div>
	</div>
</div> 
</body>
</html>