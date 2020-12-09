<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/stylesheets/signup.css"/>
    <title>Gamified Marketing</title>
</head>
<body>
<h1>Gamified Marketing</h1>
<hr/>
<h2>Log In</h2>
<form>
    <input type="text" id="username" name="username" placeholder="Username">
    <input type="password" id="password" name="password" placeholder="Password">
    <input type="submit" id="submitButton" value="Submit">
</form>
<a href="${pageContext.request.contextPath}/signup"> Go to signup page</a>
</body>
</html>

