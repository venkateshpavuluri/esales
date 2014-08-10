<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
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
     <h2><span class="icon1">&nbsp;</span>Bill Report</h2>
              <form:form action="dailySales.htm"  method="GET"
								commandName="salesCmd" id="reportForm" cssClass="form-horizontal">
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
    </div>
    <div class="block table-toop-space">
        <div class="head-box">
			    <h2><span class="icon2">&nbsp;</span>Bill Info </h2>
		 </div>
        <div class="block-box-big block-box-last-mid">
            <ul>
                <c:if test="${BillInfoList!=null }">
               <display:table name="BillInfoList" export="true" id="rowId" class="table"
											requestURI="dailySales.htm" pagesize="3">
											<display:column property="billId" sortable="true"
												title="billId" media="none" />
											<display:column property="billDate" sortable="true"
												titleKey="label.billDate" media="html" />
											<display:column property="mobileNo" sortable="true"
												titleKey="label.mobileNo" media="html" />
											<display:column property="totalMrp" sortable="true"
												titleKey="label.totalMrp" media="html" />
											<display:column property="total_Quantiy" sortable="true"
												titleKey="label.total_Quantiy" media="html" />
											<display:column property="totalRate" sortable="true"
												titleKey="label.totalRate" media="html" />
											<display:column property="totalDiscount" sortable="true"
												titleKey="label.totalDiscount" media="html" />
											<display:column property="netAmt" sortable="true"
												titleKey="label.netAmt" media="html" />
											<display:column property="totalPayment" sortable="true"
												titleKey="label.totalPayment" media="html" />
											<display:column property="returnChange" sortable="true"
												titleKey="label.returnChange" media="html" />
											<display:setProperty name="export.pdf" value="true" /> 
											<display:setProperty name="paging.banner.placement"
												value="bottom" />
											</display:table>
                             </c:if> 
                
            </ul>
          
        </div>
        
        <div class="block-footer">
            <aside class="block-footer-left">Showing 1 to 10 of 57 entries</aside>
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
    
    
    <div class="block table-toop-space">
        <div class="head-box">
			    <h2><span class="icon2">&nbsp;</span>Bill Details </h2>
		 </div>
        <div class="block-box-big block-box-last-mid">
            <ul>
                <c:if test="${BillDetailList!=null }">
               <display:table name="BillDetailList" export="true" id="rowId" class="table"
											requestURI="dailySales.htm" pagesize="3">
											<display:column property="billDetailId" sortable="true"
												title="billDetailId" media="none" />
											<display:column property="prodDesc" sortable="true"
												titleKey="label.prodDesc" media="html" />
											<display:column property="pdRate" sortable="true"
												titleKey="label.pdRate" media="html" />
											<display:column property="pdQuantity" sortable="true"
												titleKey="label.pdQuantity" media="html" />
											<display:column property="pdDiscount" sortable="true"
												titleKey="label.pdDiscount" media="html" />
											<display:column property="vat" sortable="true"
												titleKey="label.vat" media="html" />
											<display:column property="pdAmount" sortable="true"
												titleKey="label.pdAmount" media="html" />
											<display:setProperty name="export.pdf" value="true" />
											<display:setProperty name="paging.banner.placement"
												value="bottom" />
										</display:table>
                             </c:if> 
                
            </ul>
          
        </div>
        
        <div class="block-footer">
            <aside class="block-footer-left">Showing 1 to 10 of 57 entries</aside>
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