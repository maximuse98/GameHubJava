<%--
  Created by IntelliJ IDEA.
  User: maxim
  Date: 16.11.2019
  Time: 13:46
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="maximuse98">

    <title>GameHub</title>
</head>

<c:url var="login" value="/j_spring_security_check" />

<body>

    <c:if test="${not empty logout}">
        <div class="logout">${logout}</div>
    </c:if>

    <form:form action="${login}" method="POST">
        <h1>Login</h1>
        <table>
        <tr>
            <td>Login:</td>
            <td><input type='text' name='username'></td>
        </tr>
        <tr>
            <td>Password:</td>
            <td><input type='password' name='password' /></td>
        </tr>
        <tr>
            <td colspan="2">
                <input type="submit" value="<spring:message text="Login"/>" />
            </td>
        </tr>
        </table>
    </form:form>

    <a href="/register">New account</a>
</body>

<c:if test="${not empty error}">
    <div class="error">${error}</div>
</c:if>

</html>
