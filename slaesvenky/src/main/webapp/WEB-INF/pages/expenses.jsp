<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Expenses</title>
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
						var listofexpenses = ${listofexpenses};
						if (listofexpenses == "" || listofexpenses ==null) {
							$("#expensesdata ul li").remove();
						} else {
							var count=0;
							$.each(listofexpenses, function(index,exp) {
								var tblRow = "<ul>"
									+ "<li class='exp-desc-box'>"
									+ exp.description
									+ "</li>"
									+ "<li class='nine-box'>"
									+ exp.amount
									+ "</li>"
									+ "<li class='ten-box'>"
									+'<a href="expDelete.htm?expenses_Id='
									+ exp.expenses_Id
									+'" class="del">Delete</a>'
									+ '</li>'
									+ "<li class='eleven-box'>"
									+'<a href="expEdit.htm?expenses_Id='
									+ exp.expenses_Id
									+'"class="ico edit">Edit</a>'
									+ '</li>'
									+ '</ul>';
									count+=exp.amount;
								$(tblRow).appendTo("#expensesdata");
											});
							$(".del").click(function(event){
								return confirm('Do u want to Delete The Record?');
							});
							document.getElementById('exptotal').value=count;
						   }
						
					});
</script>
<script type="text/javascript">
$(document)
.ready(
		function() {
			$('#subId')
			.click(
					function(event) {
						$('#addForm')
								.validate(
										{
											rules : {
												
												description : {
													required : true,

												},
												amount : {
													required : true,
													number : true

												},
												branchId:{
													required:true,
												},
												
											},
											messages : {
												
												description : {
													required : "<font style='color: red;'><b>Description is Required</b></font>",
												},
												amount : {
													required : "<font style='color: red;'><b>Amount is Required</b></font>",
													number : "<font style='color: red;'><b>Only numerics are allowed</b></font>"

												},
												branchId:{
													required :"<font style='color: red;'><b>Branch is Required</b></font>",
												},
												

											},

										});

					});
$('#updateId')
.click(
		function(event) {
			$('#editForm')
					.validate(
							{
								rules : {
									
									description : {
										required : true,

									},
									amount : {
										required : true,
										number : true

									},
									branchId:{
										required:true,
									},
									
									
								},
								messages : {
									
									description : {
										required : "<font style='color: red;'><b>Description is Required</b></font>",
									},
									amount : {
										required : "<font style='color: red;'><b>Amount is Required</b></font>",
										number : "<font style='color: red;'><b>Only numerics are allowed</b></font>"

									},
									branchId:{
										required :"<font style='color: red;'><b>Branch is Required</b></font>",
									},
									
									

								},

							});

		});
$('#messages').fadeIn().delay(3000).fadeOut('slow'); 
		});
</script>
<script type="text/javascript">
function getgrid(id)
{
	 var branchId = $('#' + id).val();
	 $("#expensesdata ul li").remove();
		$.ajax({
			type : "POST",
			url : "expsearchwithbranch.htm",
			data : "branchId=" + branchId,
			dataType: "json",
			success : function(expList) {
				if(expList== ""){
					$("#expensesdata ul li").remove();
				} else{
					var count=0;
					$.each(expList, function(index,exp) {
						var tblRow = "<ul>"
							+ "<li class='exp-desc-box'>"
							+ exp.description
							+ "</li>"
							+ "<li class='nine-box'>"
							+ exp.amount
							+ "</li>"
							+ "<li class='ten-box'>"
							+'<a href="expDelete.htm?expenses_Id='
							+ exp.expenses_Id
							+'" class="del">Delete</a>'
							+ '</li>'
							+ "<li class='eleven-box'>"
							+'<a href="expEdit.htm?expenses_Id='
							+ exp.expenses_Id
							+'"class="ico edit">Edit</a>'
							+ '</li>'
							+ '</ul>';
							count+=exp.amount;
						$(tblRow).appendTo("#expensesdata");
									});
					$(".del").click(function(event){
						return confirm('Do u want to Delete The Record?');
					});
					document.getElementById('exptotal').value=count;
				   }
			
				},
			error : function(e) {
				
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
  <c:if test="${expensesEdit==null}">
<div class="main_content">
    <div class="block">
        <h2><span class="icon1">&nbsp;</span>Add New Expenses</h2>
     <%--    <form  method="post"  action="expAdd.htm" > --%>
          	<form:form action="expAdd.htm" commandName="EXPENSES" method="post" id="addForm"  cssClass="form-horizontal"> 
            <div class="block-box-exp">
                <div class="block-input">
                    <label><spring:message code="label.branch" /><span
												style="color: red;">*</span></label>
                    	<c:choose>
											<c:when test="${sessionScope.admin=='sales' }">
												<form:select path="branchId" id="branchId"  cssClass="field size3">

														<form:options items="${branchs}"></form:options>

													</form:select>
											</c:when>
											<c:otherwise>
												<form:select path="branchId" id="branchId" onchange="getgrid(this.id)" cssClass="field size3">
														<form:option value="">--Select--</form:option>
														<form:options items="${branch}"></form:options>
													</form:select>
											</c:otherwise>
										</c:choose> 
                </div> 
                <div class="block-input">
                    <label><spring:message code="label.desc" /><span
												style="color: red;">*</span></label>
                   <form:input path="description" cssClass="field size3" />
                </div>                 
                <div class="block-input  last">
                   <label><spring:message code="label.amount" /><span
												style="color: red;">*</span></label>
                   <form:input path="amount" cssClass="field size3" />
                </div>
                <div class="block-input">
                    <label><spring:message code="label.billno" /></label>
                    <form:input path="billno" cssClass="field size3" />
                </div>                
            </div>
            <div class="block-footer">
            <div id="messages">
               <aside class="block-footer-left sucessfully">
										<c:forEach var="success"
												items="${param.AddSus}">
											
													<strong><spring:message code="label.success" /> </strong>
													Expenses
													<spring:message code="label.saveSuccess" />
											</c:forEach> <c:forEach var="fail" items="${param.AddFail}">
													<strong><spring:message code="label.error" /> </strong>
													Expenses
													<spring:message code="label.saveFail" />

											</c:forEach>
										<c:forEach var="success"
												items="${param.UpdateSus}">
												
													<strong><spring:message code="label.success" /> </strong>
													Expenses
													<spring:message code="label.updateSuccess" />

												
											</c:forEach> <c:forEach var="fail" items="${param.UpdateFail}">
													<strong><spring:message code="label.error" /> </strong>
													Expenses
													<spring:message code="label.updateFail" />

											</c:forEach>
										<c:forEach var="success"
												items="${param.DeleteSus}">
													<strong><spring:message code="label.success" /> </strong>
													Expenses
													<spring:message code="label.deleteSuccess" />

											</c:forEach> <c:forEach var="fail" items="${param.DeleteFail}">
													<strong><spring:message code="label.error" /> </strong>
													Expenses
													<spring:message code="label.deleteFail" />

											</c:forEach>
												
               </aside> 
               </div>
                <aside class="block-footer-right">
                    <input type="reset" class="btn-cancel"
									value="<spring:message code="label.clear"/>" /> <input
									type="submit" class="btn-save"
									value="<spring:message code="label.save"/>" id="subId" />
                </aside>
            </div>
        </form:form>
    </div>
    </div>
    </c:if>
    <!--Edit  -->
    <c:if test="${expensesEdit!=null}">
    <div class="main_content">
    <div class="block">
        <h2><span class="icon1">&nbsp;</span>Edit Expenses</h2>
     <%--    <form  method="post"  action="expAdd.htm" > --%>
          	<form:form action="expUpdate.htm" commandName="EXPENSES"
							method="post" id="editForm">
            <div class="block-box-exp">
                <div class="block-input">
                	<form:hidden path="expenses_Id" />
                    <label><spring:message code="label.branch" /><span
												style="color: red;">*</span></label>
                    	<c:choose>
											<c:when test="${sessionScope.admin=='sales' }">
												<form:select path="branchId" id="branchId"  cssClass="field size3">

														<form:options items="${branchs}"></form:options>

													</form:select>
											</c:when>
											<c:otherwise>
												<form:select path="branchId" id="branchId" onchange="getgrid(this.id)" cssClass="field size3">
														<form:option value="">--Select--</form:option>
														<form:options items="${branch}"></form:options>
													</form:select>
											</c:otherwise>
										</c:choose> 
                </div> 
                <div class="block-input">
                    <label><spring:message code="label.desc" /><span
												style="color: red;">*</span></label>
                   <form:input path="description" cssClass="field size3" />
                </div>                 
                <div class="block-input  last">
                   <label><spring:message code="label.amount" /><span
												style="color: red;">*</span></label>
                   <form:input path="amount" cssClass="field size3" />
                </div>
                <div class="block-input">
                    <label><spring:message code="label.billno" /></label>
                    <form:input path="billno" cssClass="field size3" />
                </div>                
            </div>
            <div class="block-footer">
               <aside class="block-footer-left sucessfully">
										<c:forEach var="success"
												items="${param.AddSus}">
													<strong>Success </strong> Expenses
													<spring:message code="label.saveSuccess" />
											</c:forEach> <c:forEach var="fail" items="${param.AddFail}">
													<strong>Error </strong> Expenses
													<spring:message code="label.saveFail" />
											</c:forEach>
											
               </aside> 
                <aside class="block-footer-right">
                    <input type="reset" class="btn-cancel"
									value="<spring:message code="label.clear"/>" /> <input
									type="submit" class="btn-save"
									value="<spring:message code="label.update"/>" id="updateId" />
                </aside>
            </div>
        </form:form>
    </div>
    </div>
    </c:if>
    <div class="block table-toop-space">
						<div class="head-box">
							<h2>
								<span class="icon2">&nbsp;</span>
								Current Expenses
							</h2>
							<aside class="search-box">
								
								<c:set var="now" value="<%=new java.util.Date()%>" />
							<fmt:formatDate pattern="dd-MM-yyyy" value="${now}" var="curDate"/>
							<h2 class="right">Date : <c:out value="${curDate}"/></h2>
							
							</aside>
						</div>

				<div class="block-box block-box-last">
					<ul class="table-list">
						<li class="exp-desc-box">Description
							<ul>
								<li><a class="top" href="#">&nbsp;</a></li>
								<li><a class="bottom" href="#">&nbsp;</a></li>
							</ul>
						</li>
						<li class="nine-box">Amount
							<ul>
								<li><a class="top" href="#">&nbsp;</a></li>
								<li><a class="bottom" href="#">&nbsp;</a></li>
							</ul>
						</li>
						<li class="ten-box">Delete</li>
						<li class="eleven-box last">Edit</li>
					</ul>

					<div class="table-list-blk data-grid-mid" id="expensesdata"></div>

				</div>
				 <div class="block-footer">
          <aside class="block-footer-left" > <exptotal>Total Expenses:<input type="text" readonly="readonly" id="exptotal"/></exptotal></aside>
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
				<!-- <div class="block-footer">
            <aside class="block-footer-left"></aside>
            <aside class="block-footer-right">
                
            </aside>
        </div> -->
    </div>
    </div>
    </div>
    </body>
</html>