<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page isELIgnored="false" %>    
 <%@page import="java.util.*" %>
<!DOCTYPE html>
<html>
<head>
<style>
table, th, td {
  border: 1px solid black;
  border-collapse: collapse;
}
table{
	width:100%;
}
</style>
</head>
<body>
<a href="loginstudent">BACK</a>
<%
	ArrayList<ArrayList<String>> list= (ArrayList<ArrayList<String>>)session.getAttribute("issuebooks");
	
	out.print("<table ><tr><th>Subject</th><th>Author Name</th><th>Price</th><th>Edition</th></tr>");	
	
	for(ArrayList<String> l:list)
	{
		out.println("<tr>");
		for(String s:l)
		{
			out.print("<td>"+s+"</td>");
			
		}
		out.println("</tr>");
	}
	out.println("</table>");
%>


<form action="withdraw" method=POST>
<input type="text" name="subject" placeholder="Enter book name" required><br><br>
<button>withdraw a book</button>
</form>

${result }


</body>
</html>