<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Star Here</title>
</head>
<body>
<h1>FlashCard App MVP</h1>
<h2>You Current UserId is <strong>${userid}</strong></h2>
<h3>Instructions</h3>
<ol>
<li><a href="${pageContext.request.contextPath}/flashcard/init">Click here</a> to initialize. The userID will be stored as a cookie</li>
<li><a href="${pageContext.request.contextPath}/flashcard/run">Click here</a> to being Flashcard after initializing</li>
<li><a href="${pageContext.request.contextPath}/flashcard/all">Click here</a> to view all flashcards assigned to you if initialized</li>
</ol>

</body>
</html>