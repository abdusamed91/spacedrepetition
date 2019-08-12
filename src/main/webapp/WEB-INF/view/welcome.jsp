<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Welcome</title>
</head>
<body style="max-width:1200px;margin:auto">
<h1>FlashCard App MVP</h1>
<h2>You Current UserId is <strong>${userid}</strong></h2>
<h3>Notes</h3>
<p>The initial pool of flashcards is prefilled with dummy data on application start. From this pool, flashcards are placed in users pool. Each user as 
three set of pool.
<ol>
<li>Flashcard in current view i.e Enqueue pool </li>
<li>Flashcard temporary frozen i.e pending pool </li>
<li>Flashcard permanently frozen i.e forgotten pool</li>
</ol>
To emulate as close to real life application with user name and registration, <em>userid</em> is used to keep track of user progress
in the web application. Each userid in the current state of application is essence, a unique user with its own set of aforementioned sets.
<p>
<p>
Session management in the application is done via cookie where the userid is saved. This userid is sent across the application and is used 
as a reference point in identifying, <em>who is using the application</em>.
</p>

<h3>Instructions to Emulate normal User Behavior. </h3>
<ol>
<li><a href="${pageContext.request.contextPath}/flashcard/init">Click here</a> to initialize a userID [user registering]. Emulates user registration</li>
<li><a href="${pageContext.request.contextPath}/flashcard/run">Click here</a> to create queue and view flashcard after userid is generated. Emulates start of flashcards</li>
<li><a href="${pageContext.request.contextPath}/flashcard/all">Click here</a> to view current state of flashcard for the user. Emulates expected view of user's dashboard</li>
</ol>
<h4>Extra features for Admin</h4>
<ol>
<li>
	<a href="${pageContext.request.contextPath}/flashcard/create">Click here</a> to Create new FlashCards and <strong> view all
	flashcards currently stored in the app</strong>
</li>
</ol>
<br>
<!-- <em>Tip</em>
	<br>
	<p> To return to an other existing user session, refer to its <em>userid</em> by appending it the end of the domain. 
	<br><strong>For Example:</strong> localhost:8080/flashcard/user/{<strong>userid</strong>}
	
	 </p>
 -->
 
 <code>Author: Abdusamed Ahmed</code>
</body>
</html>