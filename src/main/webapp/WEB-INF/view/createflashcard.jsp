<%@include file="index.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<form action="${pageContext.request.contextPath}/flashcard/create" name="createflashcard" method="post">
<em style="color:green">${message}</em>
<h3>Please create new flashcard here</h3>
<table>
	<tr>
		<td>FlashCard Title</td>
		<td><input type="text" name="title" required placeholder="Please enter title" minlength="4"></td>
	</tr>
	<tr>
		<th>FlashCard Content</th>
		<td><textarea name="content" placeholder="Please enter the content for the flashcard here" wrap required minlength="4"></textarea></td>
	</tr>
</table>

		<button type="submit">Save FlashCard</button>

</form>

<!-- List of all flashcard in the system -->
<hr>
<h3>List of All FlashCard in the App</h3>
<table>
	<tr>
		<th>FlashCard </th>
		<th>FlashCard Title</th>
		<th>FlashCard Content</th>
	</tr>
	<c:forEach items="${flashcardtotalpool}" var="flashcard">
    <tr>
        <td><c:out value="${flashcard.id}"/></td>
        <td><c:out value="${flashcard.title}"/></td>
        <td><c:out value="${flashcard.content}"/></td>
    </tr>
</c:forEach>
</table>

</body>
</html>