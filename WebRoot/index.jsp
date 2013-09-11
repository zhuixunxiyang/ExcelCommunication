<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"
	xmlns:ui="http://java.sun.com/jsf/facelets"
	xmlns:h="http://java.sun.com/jsf/html"
	xmlns:f="http://java.sun.com/jsf/core"
	xmlns:c="http://java.sun.com/jsp/jstl/core"
	xmlns:x="http://myfaces.apache.org/tomahawk">
	<head>
		<title>Name</title>
		<style type="text/css">
			body {
				width: 50%;
				margin: auto;
				padding: 50px 0;
			}
			
			.top {
				text-align: right;
				background-color: #BDD6EF;
				padding-right: 20px;
			}
		</style>

		<script>
			window.onload = function() {
				var users = "#{userBean.userList}";
				users = users.substr(1,users.length-2);
				var userList = new Array();
				userList = users.split(",");
				var userOl = document.getElementById("user_list");
				for ( var i = 0; i &lt; userList.length; i++ ) {
					var userLi = document.createElement("li");
					userLi.appendChild( document.createTextNode(userList[i]) );
					userOl.appendChild(userLi);
				}
			}
		</script>
	</head>

	<body>
		<div class="top">
			<h:form>
				<h:outputFormat value="Hello, {0}, Welcome to JSF!">
					<f:param value="#{sessionScope.user.name}" />
				</h:outputFormat>
				<h:commandLink value="Logout" action="#{logoutBean.logout}" />
			</h:form>
		</div>
		<hr />
		<div>
			<h:outputLabel value="List All Users:" />
			<ol id="user_list"></ol>
		</div>
		<hr />
		<div>
			<h:form id="importForm" enctype="multipart/form-data">
				<x:inputFileUpload id="import_file" value="#{userBean.uploadedFile}" storage="file" required="true"/>
				<h:commandLink value="Import from Excel" action="#{userBean.importFromExcel}" />
			</h:form>
			<h:form id="exportForm"><h:commandLink value="Exoprt to Excel" action="#{userBean.exportToExcel}" /></h:form>
		</div>
	</body>
</html>
