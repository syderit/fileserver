<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Check User Service</title>
</head>
<body>
<h3>Result:</h3>
<%
	if ("1".equals(request.getAttribute("result"))) {
%>
	User exists!
<% } else { %>
	User does not exist!
<% } %>
<br/><br/>
<input type="button" value="back" onclick="document.location='index.jsp'">
</body>
</html>