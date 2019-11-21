<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page session="false" %>
<%--
  Created by IntelliJ IDEA.
  User: maxim
  Date: 02.10.2019
  Time: 17:30
  To change this template use File | Settings | File Templates.
--%>
<html>
<head>
    <title>GameHub</title>
</head>

<c:url var="register" value="/register" />

<body>

<form:form action="${register}" commandName="user" method="POST">
    <h1>Register</h1>
    <table>
        <tr>
            <td>
                <form:label path="login">
                    <spring:message text="Login:"/>
                </form:label>
            </td>
            <td>
                <form:input path="login" />
            </td>
        </tr>
        <tr>
            <td>
                <form:label path="password">
                    <spring:message text="Password:"/>
                </form:label>
            </td>
            <td>
                <form:password path="password" />
            </td>
        </tr>
        <tr>
            <td colspan="2">
                <input type="submit" value="<spring:message text="Register"/>" />
            </td>
        </tr>
    </table>
</form:form>

</body>
</html>
