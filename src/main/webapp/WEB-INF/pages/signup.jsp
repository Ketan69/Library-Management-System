<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page isELIgnored="false" %>    
<html>
<head>
<title>Creative Colorlib SignUp Form</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="application/x-javascript"> addEventListener("load", function() { setTimeout(hideURLbar, 0); }, false); function hideURLbar(){ window.scrollTo(0,1); } </script>
<!-- Custom Theme files -->
<link href="css/style.css" rel="stylesheet" type="text/css" media="all" />
<link href="//fonts.googleapis.com/css?family=Roboto:300,300i,400,400i,700,700i" rel="stylesheet">
</head>
<body>
<a href="index">BACK</a>
	<div class="main-w3layouts wrapper">
		<h1>Creative SignUp Form</h1>
		<div class="main-agileinfo">
			<div class="agileits-top">
				<form action="signup" method="post">
					<input  type="text" name="name" placeholder="Username" ><br>
					<input  type="email" name="email" placeholder="Email" ><br>
					<input  type="password" name="password" placeholder="Password" ><br>
					<input  type="tel" name="number" placeholder="Mobile Number"><br>
					<input type="submit" value="SIGNUP">
				</form>
				<p>Have an Account? <a href="index"> Login Now!</a></p>
				${ data }
			</div>
		</div>
	</div>
</body>
</html>