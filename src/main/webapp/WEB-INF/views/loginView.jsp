<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Login</title>
</head>
<body>

<jsp:include page="_menu.jsp"></jsp:include>

<h3>Login Page</h3>

<p style="color:red;">${errorString}</p>

<form method="POST" action="${pageContext.request.contextPath}/login">
<input type="hidden" name="redirectId" value="${param.redirectId}"/>
<label for="userName">User Name</label>
<br>
<input type="text" name="userName" value="${user.userName}">
<br>
<br>
<label for="password">Password</label>
<br>
<input type="password" name="password" value="${user.password}">
<br>
<br>
<input type="submit" value="Submit">
<br>
<a href="${pageContext.request.contextPath}">Cancel</a>
</form>

</body>
</html>