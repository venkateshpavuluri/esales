<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
    
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Sale Report</title>
<link rel="stylesheet" href="css1/style.css" type="text/css" media="all" />
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
						$('#branchId').val('');
						$('#sdate').val('');					
						//AddForm Validations
						$('#submitId')
								.click(
										function(event) {
											
											$('#reportForm')
													.validate(
															{
																rules : {
																	sdate : {
																		required : true
																	},
																	branchId : {
																		required : true
																	},

																},
																messages : {
																	sdate : "<font style='color: red;'><b>Date is Required</b></font>",
																	branchId : "<font style='color: red;'><b>Branch is Required</b></font>"

																},

															});

										});

					});
	
	function dateChange()
	{
		
		var fullDate = new Date($.now());
		var twoDigitMonth = (fullDate.getMonth()+1)+"";if(twoDigitMonth.length==1)  twoDigitMonth="0" +twoDigitMonth;
        var twoDigitDate = fullDate.getDate()+"";if(twoDigitDate.length==1) twoDigitDate="0" +twoDigitDate;
        var currentDate = fullDate.getFullYear() + "-" + twoDigitMonth + "-" + twoDigitDate;
        $("#dateId").val(currentDate);
	}
</script>
</head>
<body>
	<!-- SET: WRAPPER -->
<div class="wrapper"> 
  
  <!-- SET: CONTAINER -->
  <div class="container"> 	
		<div class="main_content">
    <div class="block">
     <h2><span class="icon1">&nbsp;</span>Sale Report</h2>
              <form:form action="dailySales.htm"  method="GET"
								commandName="saleCmd" id="reportForm" cssClass="form-horizontal">
								 <div class="block-box-exp">
								 <div class="block-input">
								  <label><spring:message code="label.branchId" /><span
												style="color: red;">*</span></label>
							<form:select path="branchId"  cssClass="select" onchange="dateChange()">
							<form:option value="">--Select--</form:option>
							<form:options items="${branchDetails}" />
							</form:select>
								</div>		
									<div class="block-input">
									 <label><spring:message code="label.date" /><span
												style="color: red;">*</span></label>
											<c:set var="now" value="<%=new java.util.Date()%>" />
											<fmt:formatDate pattern="yyyy-MM-dd " value="${now}" var="curDate"/>
											<form:input path="sdate" class="field size4"
												id="dateId" />
									
									</div>
								
								</div>
					 <div class="block-footer">
								 <aside class="block-footer-right">
                                     <input type="reset" class="btn-cancel" value="<spring:message code="label.clear"/>" />
                                     <input type="submit" class="btn-save" 	value="<spring:message code="label.submit"/>" id="submitId" />
                                </aside>
                  	</div>
						  </form:form>
							<div class="block table-toop-space">
							 <div class="head-box">
							</div> 
							 <div class="block-box-mid block-box-last-mid">
							 <c:if test="${categoryList!=null }">  
							<div class="table-list-blk data-grid-mid">
							   <div class="block-input">
							  
						    <ul>
						    <li> <label><spring:message code="label.cat" /></label></li>
							<li class="five-box"><c:out value="${cAmount }"></c:out></li>
									
							</ul>
							   </div>
								<c:forEach var="mGrid" items="${categoryList}" varStatus="status">
									<ul>
								<li class="five-box">${mGrid.catDesc}</li>
								<li class="bil-desc-box">${mGrid.cAmount}</li>
							 <li class="bil-desc-box">
							 
							 	<c:forEach var="subGrid" items="${mGrid.slist}" varStatus="status">
							 		<ul>
							 			<li>${subGrid.subCatDesc}</li>
							 			<li>${subGrid.subCatAmount}</li>
							 			<li>
							 				<c:forEach var="subsGrid" items="${subGrid.plist}" varStatus="status">
							 				<ul>
							 					<li>${subsGrid.prodDesc}</li>
							 					<li>${subsGrid.pAmount}</li>
							 				</ul>
							 				</c:forEach>
							 			</li>
							 		</ul>
							 
							 	</c:forEach>
							 </li> 
								
								
							</ul>
								</c:forEach>	
									
								</div>
								</c:if>
							</div> 
							
						</div>
				
					
    </div>
    
    </div>
</div>
</div>
</body>
</html>