<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!DOCTYPE html>
<html dir="ltr" lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>My Sales Info</title>

<!-- SET: FAVICON -->
<link rel="shortcut icon" type="image/x-icon" href="images/favicon.ico">
<!-- END: FAVICON -->

<!-- SET: STYLESHEET -->
<link rel="stylesheet" href="<c:out value="${theme}"/>" type="text/css"
	media="all" />
<!-- END: STYLESHEET -->
<link rel="stylesheet" href="js/jqueryui.css" />
<link rel="stylesheet" href="css/jquery-ui.css" />
<link rel="stylesheet"
	href="js/jquery-ui-1.10.3/themes/base/jquery-ui.css" />
<script src="js/jquery-1.7.2.js"></script>
<script src="js/jquery-ui.js"></script>
<script type="text/javascript" src="js/pagination.js"></script>
<script type="text/javascript" src="js/jquery.validate.min.js"></script>
<script type="text/javascript" src="js/MntValidator.js"></script>
<!--[if lt IE 9]>
	<script src="js/html5.js"></script>
<![endif]-->
</head>
<body>
	<!-- SET: WRAPPER -->
	<div class="wrapper">
		<!-- SET: CONTAINER -->
		<div class="container">
			<!-- SET: HEADER -->
			<header id="header">
				<%
					HttpSession sess = request.getSession(false);
					if (sess.getAttribute("userId") == null) {
						RequestDispatcher dispatcher = request
								.getRequestDispatcher(".././pages/login.jsp");
						dispatcher.forward(request, response);
					}
				%>
				<div class="header-top">
					<h1 id="logo">
						<a href="index.html"><img src="<c:out value="${imgsrc}"/>"
							width="75" height="34" alt=""></a>
					</h1>
					<span class="cname"><%=session.getAttribute("clientName").toString()
					.toUpperCase()%></span>
					<ul class="list-header-right">
						<li><a href="logout.htm">Logout</a></li>
						<li><strong>Welcome Back</strong> <br> <%=session.getAttribute("loginUserFullName").toString()
					.toUpperCase()%></li>
						<li><strong>Address</strong><br> <c:out
								value="${address1}" /> <c:if test="${address2!=null}">
								,<c:out value="${address2}" />
							</c:if></li>
					</ul>
				</div>
				<nav>
					<c:if test="${admin == 'admin'}">
						<ul>
							<li><a class="<c:out value="${deptActive}"/>"
								href="deptHome.htm" id="menuId"><span><spring:message
											code="label.hdept" /></span></a></li>
							<li><a class="<c:out value="${clientActive}"/>"
								href="clientHome.htm"><span><spring:message
											code="label.hclient" /></span></a></li>
							<li><a class="<c:out value="${userActive}"/>"
								href="userHome.htm" id="menuId"><span><spring:message
											code="label.user" /></span></a></li>
							<li><a class="<c:out value="${branchActive}"/>"
								href="branchHome.htm"><span><spring:message
											code="label.hbranch" /></span></a></li>
							<li><a class="<c:out value="${perActive}"/>"
								href="permissionsHome.htm"><span><spring:message
											code="label.permissions" /> </span></a></li>
							<li><a class="<c:out value="${catActive}"/>"
								href="catHome.htm"><span><spring:message
											code="label.cat" /></span></a></li>
							<li><a class="<c:out value="${subCatActive}"/>"
								href="subCatHome.htm"><span><spring:message
											code="label.subCat" /></span></a></li>
							<li><a class="<c:out value="${prodActive}"/>"
								href="productHome.htm"><span><spring:message
											code="label.product" /> </span></a></li>
							<li><a href="#">Stock</a>
								<ul>
									<li><a href="stockEntry.htm">Stock Entry</a></li>
									<li><a href="stockAllocHome.htm"><spring:message
												code="label.stockAllocation" /></a></li>
									<li><a href="stockReturnHome.htm">Stock Return</a></li>
								</ul></li>
							<li><a class="<c:out value="${billActive}"/>"
								href="billHome.htm">Billing </a></li>
							<li><a class="<c:out value="${expActive}"/>"
								href="expensesHome.htm">Expenses</a></li>
							<li><a href="#">Reports</a>
								<ul>
									<li><a href="saleReportHome.htm">Today Sales</a></li>
									<li><a href="expenseReportHome.htm">Today Expences</a></li>
									<li><a href="stockReportHome.htm">Today Stock</a></li>
									<li><a href="todayReportHome.htm">Today All</a></li>
								</ul></li>
							<li><a href="#">Server</a>
								<ul>
									<li><a href="DbBackUphome.htm">Stock Upload</a></li>
									<li><a href="getDbBackUp.htm">Stock Download</a></li>
									<li><a href="#">Sales Upload</a></li>
								</ul></li>
							<li><a href="#">Search</a>
								<ul>
									<li><a href="billSearchHome.htm">Bill Search</a></li>

								</ul></li>
						</ul>
					</c:if>
					<c:if test="${ admin == 'clientadmin'}">
						<ul>
							<li><a class="<c:out value="${branchActive}"/>"
								href="branchHome.htm"><span><spring:message
											code="label.hbranch" /></span></a></li>
							<li><a class="<c:out value="${userActive}"/>"
								href="userHome.htm" id="menuId"><span><spring:message
											code="label.user" /></span></a></li>
							<li><a class="<c:out value="${perActive}"/>"
								href="permissionsHome.htm"><span><spring:message
											code="label.permissions" /> </span></a></li>
							<li><a class="<c:out value="${catActive}"/>"
								href="catHome.htm"><span><spring:message
											code="label.cat" /></span></a></li>
							<li><a class="<c:out value="${subCatActive}"/>"
								href="subCatHome.htm"><span><spring:message
											code="label.subCat" /></span></a></li>
							<li><a class="<c:out value="${prodActive}"/>"
								href="productHome.htm"><span><spring:message
											code="label.product" /> </span></a></li>
							<li><a href="#">Stock</a>
								<ul>
									<li><a class="active" href="stockEntry.htm">Stock
											Entry</a></li>
									<li><a href="stockAllocHome.htm">Stock Allocation</a></li>
									<li><a href="stockReturnHome.htm">Stock Return</a></li>
								</ul></li>
							<li><a class="<c:out value="${expActive}"/>"
								href="expensesHome.htm"><span><spring:message
											code="label.expenses" /></span></a></li>
							<li><a class="<c:out value="${billActive}"/>"
								href="billHome.htm"><span><spring:message
											code="label.hbilling" /></span></a></li>
							<li><a href="#">Reports</a>
								<ul>
									<li><a href="saleReportHome.htm">Today Sales</a></li>
									<li><a href="expenseReportHome.htm">Today Expences</a></li>
									<li><a href="stockReportHome.htm">Today Stock</a></li>
									<li><a href="todayReportHome.htm">Today All</a></li>
								</ul></li>
							<li><a href="#">Server</a>
								<ul>
									<li><a href="DbBackUphome.htm">Stock Upload</a></li>
									<li><a href="getDbBackUp.htm">Stock Download</a></li>
									<li><a href="getDbBackUp.htm">Sales Upload</a></li>
								</ul></li>
							<li><a href="#">Search</a>
								<ul>
									<li><a href="billSearchHome.htm">Bill Search</a></li>

								</ul></li>
						</ul>
					</c:if>
					<c:set value="supervisor" var="supervisor"></c:set>
					<c:if test="${ admin eq supervisor}">

						<ul>
							<li><a class="<c:out value="${catActive}"/>"
								href="catHome.htm"><span>Category</span></a></li>
							<li><a class="<c:out value="${subCatActive}"/>"
								href="subCatHome.htm"><span>Sub Category</span></a></li>
							<li><a class="<c:out value="${prodActive}"/>"
								href="productHome.htm"><span>Product</span></a></li>
							<li><a href="#">Stock</a>
								<ul>
									<li><a href="stockEntry.htm">Stock Entry</a></li>
									<li><a href="stockAllocHome.htm">Stock Allocation</a></li>
									<li><a href="stockReturnHome.htm">Stock Return</a></li>
								</ul></li>
							<li><a class="<c:out value="${expActive}"/>"
								href="expensesHome.htm"><span><spring:message
											code="label.expenses" /></span></a></li>
							<li><a class="<c:out value="${billActive}"/>"
								href="billHome.htm"><span><spring:message
											code="label.hbilling" /></span></a></li>
							<li><a href="#">Reports</a>
								<ul>
									<li><a href="saleReportHome.htm">Today Sales</a></li>
									<li><a href="expenseReportHome.htm">Today Expences</a></li>
									<li><a href="stockReportHome.htm">Today Stock</a></li>
									<li><a href="todayReportHome.htm">Today All</a></li>
								</ul></li>
							<li><a href="#">Server</a>
								<ul>
									<li><a href="DbBackUphome.htm">Stock Upload</a></li>
									<li><a href="getDbBackUp.htm">Stock Download</a></li>
									<li><a href="getDbBackUp.htm">Sales Upload</a></li>
								</ul></li>
							<li><a href="#">Search</a>
								<ul>
									<li><a href="billSearchHome.htm">Bill Search</a></li>

								</ul></li>
						</ul>
					</c:if>
					<c:set value="sales" var="sales"></c:set>
					<c:if test="${ admin eq sales}">
						<ul>
							<li><a class="<c:out value="${expActive}"/>"
								href="expensesHome.htm"><span><spring:message
											code="label.expenses" /></span></a></li>
							<li><a class="<c:out value="${billActive}"/>"
								href="billHome.htm"><span><spring:message
											code="label.hbilling" /></span></a></li>
							<li><a href="#">Reports</a>
								<ul>
									<li><a href="saleReportHome.htm">Today Sales</a></li>
									<li><a href="expenseReportHome.htm">Today Expences</a></li>
									<li><a href="stockReportHome.htm">Today Stock</a></li>
									<li><a href="todayReportHome.htm">Today All</a></li>
								</ul></li>
							<li><a href="#">Server</a>
								<ul>
									<li><a href="getDbBackUp.htm">Stock Download</a></li>
									<li><a href="getDbBackUp.htm">Sales Upload</a></li>
								</ul></li>
							<li><a href="#">Stock</a>
								<ul>

									<li><a href="stockReturnHome.htm">Stock Return</a></li>
								</ul></li>
							<li><a href="#">Search</a>
								<ul>
									<li><a href="billSearchHome.htm">Bill Search</a></li>

								</ul></li>
						</ul>
					</c:if>
				</nav>
			</header>
			<!-- END: HEADER -->
			<div id="page-title">
				<%-- <ul>
					<li>
							Home/
							
						</li>
					<li><marquee behavior="scroll" direction="left">
							<spring:message code="label.homeStatus" />
						</marquee></li>
				</ul>
			</div> --%>
				<ul>
					<li><h3>
							<aside id="selectedpath">
								Home/
								<c:out value="${tabName}" />
							</aside>
						</h3></li>
					<li><marquee behavior="scroll" direction="left">Welcome
							to mySalesInfo...</marquee></li>
				</ul>
			</div>
			<!-- END: CONTAINER -->

		</div>
	</div>
	<!-- END: WRAPPER -->
</body>
</html>
