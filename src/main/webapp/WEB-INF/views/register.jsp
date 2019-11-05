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
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>GameHub</title>
    <h1>Register</h1>
</head>

<c:url var="register" value="/games" />

<body>
<form:form action="${register}" commandName="user" method="POST">
    <table>
        <tr>
            <td>
                <form:label path="username">
                    <spring:message text="Username"/>
                </form:label>
            </td>
            <td>
                <form:input path="username" />
            </td>
        </tr>
        <!--<tr>
            <td>
                <form:label path="password">
                    <spring:message text="Password"/>
                </form:label>
            </td>
            <td>
                <form:input path="password" />
            </td>
        </tr>-->
        <tr>
            <td colspan="2">
                <input type="submit" value="<spring:message text="Register"/>" />
            </td>
        </tr>
    </table>
</form:form>
</body>
</html>
