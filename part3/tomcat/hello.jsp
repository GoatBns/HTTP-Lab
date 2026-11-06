<html>
<head>
<title>Sample Application JSP Page</title>
</head>
<body bgcolor=white>
<% int a = 6, b = 7; %>
<p>6 x 7 = <%= a * b %></p>
<p>Server time: <%= new java.util.Date() %></p>
<% for (int i = 1; i <= 3; i++) { %>
  <p>Line number <%= i %></p>
<% } %>
<table border="0">
<tr>
<td align=center>
<img src="images/tomcat.gif">
</td>
<td>
<h1>Sample Application JSP Page</h1>
This is the output of a JSP page that is part of the Hello, World
application.
</td>
</tr>
</table>

<%= new String("Hello!") %>

</body>
</html>
