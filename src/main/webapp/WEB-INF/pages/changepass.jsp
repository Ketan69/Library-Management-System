<%@page isELIgnored="false" %>
<html>
<head>

</head>
<body>
<a href="loginstudent">BACK</a>
<h1>Enter New Password</h1><br><br>

<form action="changepass" method="post">
	<input type="password" name="password" placeholder="Enter New Password" required/><br><br>
    <button>submit</button><br><br>
    ${ result }
</form>




</body>


</html>