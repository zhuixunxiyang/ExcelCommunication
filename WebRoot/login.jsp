<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"
	xmlns:ui="http://java.sun.com/jsf/facelets"
	xmlns:h="http://java.sun.com/jsf/html"
	xmlns:c="http://java.sun.com/jsp/jstl/core">
	<head>
		<title>Login -- JSF Demo</title>
		<style type="text/css">
			body {
				width: 50%;
				margin: auto;
				padding: 50px 0;
			}
		</style>
	</head>

	<body>
		<c:if test="#{loginBean.msg}">
			<font color="red">#{loginBean.msg}</font>
		</c:if>
		<h:form id="loginForm">
			<h:outputLabel value="Username" /><h:inputText id="username" /><br />
			<h:outputLabel value="Password" /><h:inputSecret id="password" /><br />
			<h:commandButton id="submit" value="Login" action="#{loginBean.login}" />
		</h:form>
	</body>
</html>
