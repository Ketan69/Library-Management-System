<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page isELIgnored="false" %>    
<!DOCTYPE html>
<html>
<head>

</head>
<body>
<a href="loginstudent">BACK</a>
<h1>Enter New UserName</h1><br><br>

<form action="changeusername" method="post">
	<input type="text" name="userName" placeholder="Enter New UserName" required/><br><br>
    <button>submit</button><br><br>
    ${ result }
</form>




</body>


</html>