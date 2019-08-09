<%@ page language="java" contentType="text/html; charset=UTF-8"pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Error</title>
</head>
<body>
<h1>Something bad might have happened</h1>

<p>Message: <h3> ${message} </h3></p>  
<a href="${pageContext.request.contextPath}/flashcard/">Return back to Welcome Page</a>
</body>
</html>