<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<html>
<head>

<link rel="stylesheet" href="<c:out value="${theme}"/>" type="text/css"
	media="all" />
</head>
<body>
	<!-- SET: WRAPPER -->
<div class="wrapper"> 
  
  <!-- SET: CONTAINER -->
  <div class="container"> 
		<div class="welcome">
			<spring:message code="label.welcome" />
		</div>
	</div>
	</div>
</body>
</html>