<%@include file="index.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix = "fn" uri = "http://java.sun.com/jsp/jstl/functions" %>

<hr>
<h2>FlashCard Print</h2>
<p>${usermessage}<p>
<h3>User In Queue Pool</h3>
<table border="1">
<tr>
	<th>FlashCard UUID</th>
	<th>FlashCard Title</th>
	<th>FlashCard Content</th>
	<th>Bucket</th>
	<th>Review Time</th>
	<th>To Display</th>
	<th>Time Wrong</th>
	
</tr>
<c:forEach items="${flashcardlist}" var="flashcard">
    <tr>
    	<td><c:out value ="${fn:substring(flashcard.id,0,8)}"/></td>
        <td><c:out value="${flashcard.title}"/></td>
        <td><c:out value="${flashcard.content}"/></td>
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
	<th>FlashCard Title</th>
	<th>FlashCard Content</th>
	<th>Bucket</th>
	<th>Review Time</th>
	<th>To Display</th>
	<th>Time Wrong</th>
	
</tr>
<c:forEach items="${flashcardPendingPool}" var="flashcard">
    <tr>
    	<td><c:out value ="${fn:substring(flashcard.id,0,8)}"/></td>
    	<td><c:out value="${flashcard.title}"/></td>
        <td><c:out value="${flashcard.content}"/></td>
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
	<th>FlashCard Title</th>
	<th>FlashCard Content</th>
	<th>Bucket</th>
	<th>Review Time</th>
	<th>To Display</th>
	<th>Time Wrong</th>
	
</tr>
<c:forEach items="${flashcardNeverPool}" var="flashcard">
    <tr>
    	<td><c:out value ="${fn:substring(flashcard.id,0,8)}"/></td>
    	<td><c:out value="${flashcard.title}"/></td>
        <td><c:out value="${flashcard.content}"/></td>
        <td><c:out value="${flashcard.bucket}"/></td>
        <td><c:out value="${flashcard.reviewTime}"/></td>
        <td><c:out value="${flashcard.flagDisplay}"/></td>
        <td><c:out value="${flashcard.wrongCounter}"/></td>
    </tr>
</c:forEach>
</table>

</body>
</html>