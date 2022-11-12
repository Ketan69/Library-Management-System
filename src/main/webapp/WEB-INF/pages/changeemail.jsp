<%@page isELIgnored="false" %>
<html>
<head>

</head>
<body>
<a href="loginstudent">BACK</a>
<h1>Enter New Email</h1><br><br>

<form action="changeemail" method="post">
	<input type="text" name="email" placeholder="Enter New Email" required/><br><br>
    <button>submit</button><br><br>
    ${ result }
</form>




</body>


</html>