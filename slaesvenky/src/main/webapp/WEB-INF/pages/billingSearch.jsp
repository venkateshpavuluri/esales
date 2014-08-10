<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Billing</title>
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
        function doAjaxPost() {
        // get the form values
       
        var billId = $('#billId').val();
        $("#billData ul li").remove();
        $.ajax({
        	type: "POST",
        	url: "/eSales/searchForBill.htm",
        	data: "billId=" + billId,
        	dataType : "json",
            mimeType: 'application/json',
        	success: function(data){
        	
        			//For billNumber
        			 $("#billNo").text(data.billId);
        			//$.datepicker.formatDate('dd M yy', new Date())
        			//BillDate
        			$("#billDate").text(data.billDate);
        			
        			
        	 $.each(data.billDetails, function(index, value){
        			
        			
					var tblRow = '<ul><li class="five-box">'+index+'</li>' 
					+'<li class="bil-desc-box">'+value.prodDesc+'</li>'
					+'<li class="five-box">'+value.quantity +'</li>'
					+'<li class="five-box">'+value.rate +'</li>'
					+'<li class="five-box">'+value.mrp +'</li>'
					+'<li class="five-box">'+value.discount +'</li>'
					+'<li class="five-box">'+value.vat +'</li>'
					+'<li class="five-box">'+value.amount +'</li></ul>'
					
					$("#billData").append(tblRow);	
        		}); 
        	 $("#totalQuantiy").text(data.totalQuantiy);
        	 $("#totalRate").text(data.totalRate);
        	 $("#totalMrp").text(data.totalMrp);
        	 $("#totalDiscount").text(data.totalDiscount);
        	 $("#totaltax").text(data.totaltax);
        	 $("#netAmt").text(data.netAmt);
        	 $("#totalPayment").val(data.totalPayment);
        	 $("#returnChange").val(data.returnChange);
        	 $("#chequeNo").val(data.chequeNo);
        	 $("#cardNo").val(data.cardNo);
        	 $("#mobileNo").val(data.mobileNo);
        	 
       		 // we have the response
      
        		
        },
        error: function(e){
        alert('Error: ' + e);
        }
     });
        }
        </script>

<script type="text/javascript">
$(document).ready(function() {
    //this calculates values automatically 
	$("#saveId").on("click", function () {
			
          $('#billingForm').attr('action', "<c:url value='/saveBill.htm'/>");
          $("#billingForm").submit();
          e.preventDefault();
      });

    $("#totalPayment").on("keydown keyup", function() {
        calculateSum();
    });
});

function calculateSum() {
    var sum = 0;
    //iterate through each textboxes and add the values
 	var totalAmount=$( "#netAmt" ).val();
 	//var totalAmount=1000.263;
        //add only if the value is number
       
        $("#totalPayment").each(function() {
        	
        if (!isNaN(this.value) && this.value.length != 0) {
            sum =parseFloat(this.value)-parseFloat(totalAmount);
        }
        else if (this.value.length != 0){   
        }
        });
 		
	$("#returnChange").val(sum.toFixed(2));
}


</script>

<script type="text/javascript">
	$(function() {
		$("#bdate,#edate").datepicker({
			changeDate : true,
			changeMonth : true,
			changeYear : true,
			showButtonPanel : false,
			dateFormat : 'dd-mm-yy'
		});
	});
</script>
<script>
  $(document).ready(function() {
	  
	  
    $('#submitId').click(function() {
      $('#msgInfo').hide();
      $('#editId').hide();
       
    });
   
</script>
<script>
function printDiv(divName) {
    var printContents = document.getElementById(divName).innerHTML;
    var originalContents = document.body.innerHTML;

    document.body.innerHTML = printContents;

    window.print();

    document.body.innerHTML = originalContents;
}

</script>
</head>
<body>
	<div class="wrapper">
		<div class="container">
			<div class="main_content">
				<div class="block">
							<h2>
								<span class="icon1">&nbsp;</span>Search For Bill
							</h2>
							<%--   <form name="cf_form" method="post" id="contact-form" class="form-horizontal" action="#" onsubmit="return validationequiry()"> --%>
							<div class="block-box-exp">
								
								<form:form action="" class="form-horizontal" commandName="getBillCmd" >
								
                      			 <div class="block-input">
									<label><spring:message code="label.billno" /></label>
											<form:select path="billId" cssClass="field size3" id="billId" onchange="doAjaxPost()">
												<form:option value="">---Select---</form:option>
												<form:options items="${billNums}"></form:options>
											</form:select>
									</div>
								</form:form>
							</div>
							<div class="block-footer">
								<aside class="block-footer-left sucessfully">
									<c:forEach var="success" items="${param.AddSus}">
										<div>
											<strong><spring:message code="label.success" /></strong>
											<spring:message code="label.bill" />
											<spring:message code="label.saveSuccess" />

										</div>
									</c:forEach>
									<c:forEach var="fail" items="${param.AddFail}">
										<div>
											<spring:message code="label.error" />
											<spring:message code="label.bill" />
											<spring:message code="label.saveFail" />

										</div>
									</c:forEach>
								</aside>
								<aside class="block-footer-right">
									<input class="btn-cancel" name="" value="Cancel" type="button">
									<input class="btn-save" name="" value="Submit" type="submit" id="searchId" onclick="doAjaxPost()">
								</aside>
							</div>
						</div>
					    <div class="block table-toop-space">
        <div class="head-box">
            <h2><span class="icon2" id="billId"></span></h2>
            <aside class="block-footer-right"> 
                <label id="billDate"></label>               
            </aside>
        </div>
        <div class="block-box-mid block-box-last-mid">
            <ul class="table-list">
                <li class="five-box">S.No
                    <ul>
                        <li><a class="top" href="#">&nbsp;</a></li>
                        <li><a class="bottom" href="#">&nbsp;</a></li>
                    </ul>
                </li>                
                <li class="bil-desc-box">Description
                    <ul>
                        <li><a class="top" href="#">&nbsp;</a></li>
                        <li><a class="bottom" href="#">&nbsp;</a></li>
                    </ul>
                </li>
                <li class="five-box">Quantity
                    <ul>
                        <li><a class="top" href="#">&nbsp;</a></li>
                        <li><a class="bottom" href="#">&nbsp;</a></li>
                    </ul>
                </li>
                <li class="five-box">Rate
                    <ul>
                        <li><a class="top" href="#">&nbsp;</a></li>
                        <li><a class="bottom" href="#">&nbsp;</a></li>
                    </ul>
                </li>                
                <li class="five-box">MRP
                    <ul>
                        <li><a class="top" href="#">&nbsp;</a></li>
                        <li><a class="bottom" href="#">&nbsp;</a></li>
                    </ul>
                </li>                 
                <li class="five-box">Discount
                    <ul>
                        <li><a class="top" href="#">&nbsp;</a></li>
                        <li><a class="bottom" href="#">&nbsp;</a></li>
                    </ul>
                </li> 
                <li class="five-box">VAT
                    <ul>
                        <li><a class="top" href="#">&nbsp;</a></li>
                        <li><a class="bottom" href="#">&nbsp;</a></li>
                    </ul>
                </li>                
                <li class="five-box">Amount
                    <ul>
                        <li><a class="top" href="#">&nbsp;</a></li>
                        <li><a class="bottom" href="#">&nbsp;</a></li>
                    </ul>
                </li>                
                <li class="ten-box">Delete</li>
                <li class="eleven-box last">Edit</li>
            </ul>
            <div class="table-list-blk data-grid-mid">
                <ul id="billData">
                   
                </ul>
                
                
                          
                <ul>
                    <div class="block-footer">
                        <ul>
                            
							<li class="five-box">&nbsp;</li>
							<li class="bil-desc-box"><b>Total</b></li>
							<li class="five-box"><b id="totalQuantiy"></b></li>
							<li class="five-box"><b id="totalRate"></b></li>
							<li class="five-box"><b id="totalMrp"></b></li>
							<li class="five-box"><b id="totalDiscount"></b></li>
							<li class="five-box"><b id="totaltax"></b></li>
							<li class="five-box last"><b id="netAmt">}</b></li>
							<li class="ten-box">&nbsp;</li>	
							<li class="eleven-box last">&nbsp;</li>
                        </ul>                     
                    </div>                
                </ul>
                
                <ul>
                    <div class="block-box-exp">
                        <div class="block-input">
                            <label>Payment Type</label>
                            <select id="addclient" name="addclient">
                                <option value="1">Cash</option>
                                <option value="2">Card</option>
                                <option value="3">Cheque</option>
                            </select> 
                        </div> 
                        <div class="block-input">
                            <label>Cash</label>
                            <input type="text" name="lmark" id="totalPayment">
                        </div>                 
                        <div class="block-input  last">
                            <label>Mobile No</label>
                            <input type="text" name="lmark" id="mobileNo">
                        </div>
                        <div class="block-input">
                            <label>Cheque No</label>
                            <input type="text" name="lmark" id="chequeNo">                  
                        </div>  
                        <div class="block-input">
                            <label>Card No</label>
                            <input type="text" name="lmark" id="cardNo">
                        </div>
                        <div class="block-input last">
                            <label>Return Change</label>
                            <input type="text" name="lmark" id="returnChange">
                        </div>                
                    </div>
                </ul>
            </div>
        </div>
        <div class="block-footer">
            <aside class="block-footer-left"><exptotal>Net Amount: 3800.00</exptotal></aside>
            <aside class="block-footer-right">
                <input class="btn-cancel" name="" value="Cancel" type="button">
                <input class="btn-save" name="" value="Save" type="submit">
            </aside>
        </div>
    </div>


			</div>
		</div>
	</div>
		
</body>

</html>