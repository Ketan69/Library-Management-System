<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page isELIgnored="false" %>
<html>
<head>

</head>
<body>
<a href="index">BACK</a>
<h1>Enter your Registered Email on which you wants password</h1>

<form action="sendmail" method=POST>
	<input type="email" name="email" placeholder="userId" required/><br><br>
	<input type="text" name="name" placeholder="Admin/User" required><br><br>
    <button>submit</button><br><br>
    ${ value }
</form>




</body>


</html>