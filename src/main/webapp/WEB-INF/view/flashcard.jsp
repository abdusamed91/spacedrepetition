<%@include file="index.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<hr>
<h2>FlashCard</h2>

	<!-- FlashCard Details -->
	<table >
	<tr>
		<th>FlashCard Key</th>
		<th>FlashCard Value</th>
	</tr>
	
	<tr>
		<td>FlashCard Number</td>
		<td>${cookie.flashcardNum.value}</td>
	</tr>
	<tr>
		<td>FlashCard UUID</td>
		<td>${flashcard.id} ... <em>temporary placeholder</em></td>
	</tr>
	<tr>
		<td>Bucket</td>
		<td>${flashcard.bucket}</td>
	</tr>
	<tr>
		<td>Review Time</td>
		<td>${reviewtime}</td>
	</tr>
	<tr>
		<td>Definition</td>
		<td>
			
	
    <c:if test="${showdef eq true }">
	
	FlashCard Content: <strong>${flashcard.content}</strong>
	
	<form action="${pageContext.request.contextPath}/flashcard/answer/0" name="igotitForm" method="post">
	<input type="submit" value="I got it!">
	</form>
	
	<form action="${pageContext.request.contextPath}/flashcard/answer/1" name="ididnotgetitForm" method="post">
	<input type="submit" value="I did not get it!">
	</form>
	
	</c:if>
	<c:if test="${showdef eq false }">
	<form action="${pageContext.request.contextPath}/flashcard/showdefinition" method="post" name="flashcardAction">
	<input type="submit" value="Show me the Definition!" />
	</form>
	</c:if>		
		
		
		</td>
	
	</tr>
	
	</table>
	
	
	
	