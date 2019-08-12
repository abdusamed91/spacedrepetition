<%@include file="index.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix = "fn" uri = "http://java.sun.com/jsp/jstl/functions" %>
<c:set var = "trimUUID" value ="${fn:substring(flashcard.id,0,8)}" />
<hr>
<h2>FlashCard</h2>

	<!-- FlashCard Details -->
	<table>
	  <col width="20%">
  	  <col width="80%">
	<tr>
		<th>FlashCard Key</th>
		<th>FlashCard Value</th>
	</tr>
	
<%-- 	<tr>
		<td>FlashCard Number</td>
		<td>${cookie.flashcardNum.value}</td>
	</tr> --%>
		<tr>
		<td>FlashCard UUID</td>
		<td>${trimUUID}</td>
	</tr>
	<tr>
		<td>FlashCard Title</td>
		<td>${flashcard.title}</td>
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
	<!-- <input type="submit" value="I got it!"> -->
	<button type="submit"> I got it!</button>
	</form>
	
	<form action="${pageContext.request.contextPath}/flashcard/answer/1" name="ididnotgetitForm" method="post">
	<!-- <input type="submit" value="I did not get it!"> -->
	<button type="submit"> I did not get it!</button>
	</form>
	
	</c:if>
	<c:if test="${showdef eq false }">
	<form action="${pageContext.request.contextPath}/flashcard/showdefinition" method="post" name="flashcardAction">
	<!-- <input type="submit" value="Show me the Definition!" /> -->
	<button type="submit">Show me the Definition!</button>
	</form>
	</c:if>		
		
		
		</td>
	
	</tr>
	
	</table>
	
</body>
</html>
	