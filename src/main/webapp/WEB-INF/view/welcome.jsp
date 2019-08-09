<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Welcome</title>
</head>
<body>
<h1>FlashCard App MVP</h1>
<h2>You Current UserId is <strong>${userid}</strong></h2>
<h3>Summary</h3>
<p>The flashcards dictionary <strong>prepopulated</strong> on application start. From this pool, flashcards are picked and added 
to the user personal pool. Each user is defined by having its own unique <em>userid</em>.
<p>
<p>
	The user registration and user session mocking is achieved by creating a unique userId via the <strong>init</strong> end point. The
	endpoint creates a userid and stores as a cookie. Session management in the current state of the app is achieved with cookie.
</p>

<h3>Instructions</h3>
<ol>
<li><a href="${pageContext.request.contextPath}/flashcard/init">Click here</a> to initialize a userID. The userID will be stored as a cookie</li>
<li><a href="${pageContext.request.contextPath}/flashcard/run">Click here</a> to being Flashcard after initializing</li>
<li><a href="${pageContext.request.contextPath}/flashcard/all">Click here</a> to view all flashcards assigned to you if initialized</li>
</ol>
<br>
<!-- <em>Tip</em>
	<br>
	<p> To return to an other existing user session, refer to its <em>userid</em> by appending it the end of the domain. 
	<br><strong>For Example:</strong> localhost:8080/flashcard/user/{<strong>userid</strong>}
	
	 </p>
 -->
</body>
</html>