<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Dashboard</title>

<style>
table {
  font-family: arial, sans-serif;
  border-collapse: collapse;
  width: 100%;
}

td, th {
  border: 1px solid #dddddd;
  text-align: left;
  padding: 8px;
}

tr:nth-child(even) {
  background-color: #dddddd;
}
</style>
</head>

<body style="max-width:1200px;margin:auto">
<nav>
<h4>Current UserSession Id: <strong>${cookie.userid.value}</strong><h4> 
<a href="${pageContext.request.contextPath}/flashcard/">Return to Welcome</a>
</nav>
<hr>	


