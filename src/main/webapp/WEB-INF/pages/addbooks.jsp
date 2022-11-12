<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" %>
<%@ page isELIgnored="false" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<a href="loginadmin">BACK</a>
<br><br>
<form action="addbook">
<input type="text" name="subject" placeholder="Enter Subject Of Book" required><br><br>
<input type="text" name="author" placeholder="Enter Author Name Of Book" required><br><br>
<input type="text" name="edition" placeholder="Enter Edition" required><br><br>
<input type="text" name="price" placeholder="Enter Price" required><br><br>
<button>Add book</button><br><br>
</form>
${ result }
</body>
</html>