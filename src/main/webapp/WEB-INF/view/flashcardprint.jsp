<%@include file="index.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<hr>
<h2>FlashCard Print</h2>

<h3>User In Queue Pool</h3>
<table border="1">
<tr>
	<th>FlashCard UUID</th>
	<th>Bucket</th>
	<th>Review Time</th>
	<th>To Display</th>
	<th>Time Wrong</th>
	
</tr>
<c:forEach items="${flashcardlist}" var="flashcard">
    <tr>
        <td><c:out value="${flashcard.id}"/></td>
        <td><c:out value="${flashcard.bucket}"/></td>
        <td><c:out value="${flashcard.reviewTime}"/></td>
        <td><c:out value="${flashcard.flagDisplay}"/></td>
        <td><c:out value="${flashcard.wrongCounter}"/></td>
    </tr>
</c:forEach>
</table>

<h3>User FlashCard Pending Pool</h3>
<table border="1">
<tr>
	<th>FlashCard UUID</th>
	<th>Bucket</th>
	<th>Review Time</th>
	<th>To Display</th>
	<th>Time Wrong</th>
	
</tr>
<c:forEach items="${flashcardPendingPool}" var="flashcard">
    <tr>
        <td><c:out value="${flashcard.id}"/></td>
        <td><c:out value="${flashcard.bucket}"/></td>
        <td><c:out value="${flashcard.reviewTime}"/></td>
        <td><c:out value="${flashcard.flagDisplay}"/></td>
        <td><c:out value="${flashcard.wrongCounter}"/></td>
    </tr>
</c:forEach>
</table>

<h3>User FlashCard Never Pool</h3>
<table border="1">
<tr>
	<th>FlashCard UUID</th>
	<th>Bucket</th>
	<th>Review Time</th>
	<th>To Display</th>
	<th>Time Wrong</th>
	
</tr>
<c:forEach items="${flashcardNeverPool}" var="flashcard">
    <tr>
        <td><c:out value="${flashcard.id}"/></td>
        <td><c:out value="${flashcard.bucket}"/></td>
        <td><c:out value="${flashcard.reviewTime}"/></td>
        <td><c:out value="${flashcard.flagDisplay}"/></td>
        <td><c:out value="${flashcard.wrongCounter}"/></td>
    </tr>
</c:forEach>
</table>

