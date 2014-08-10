<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<body>
	<!--Header-->
	<div id="">
		<tiles:insertAttribute name="header" />
	</div>
	<%-- <div id="menu">
		<tiles:insertAttribute name="menu" />
	</div> --%>
	<table width="100%">
		<tr>
			<%-- <td id="leftMenu" class="contextMenu" valign="top"><tiles:insertAttribute
					name="menu" /></td> --%>

			<td valign="top">
				<div id="container">
					<tiles:insertAttribute name="body" />
				</div>
			</td>
		</tr>
	</table>
	<!--Footer-->
	<div >
		<tiles:insertAttribute name="footer" />
	</div>
</body>
</html>