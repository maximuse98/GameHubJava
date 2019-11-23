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
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>GameHub</title>

    <link href="${contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
    <link href="${contextPath}/resources/css/common.css" rel="stylesheet">
</head>

<style>
    body {
        background-image: url("${contextPath}/resources/imgs/background.png");
        background-repeat: no-repeat;
        background-attachment: fixed;
        background-size: 100% 100%;
    }
    .error {
        color: red;
    }
</style>

<c:url var="register" value="/register" />

<body>
    <div class="container">
        <form:form action="${register}" commandName="user" method="POST" class="form-signin">
            <h2 class="form-heading">Register</h2>
            <div class="form-group">

                <form:input path="username" class="form-control" autofocus="autofocus" placeholder="Username"/>
                <form:password path="password" class="form-control" placeholder="Password"/>
                <form:password path="passwordConfirm" class="form-control" placeholder="Confirm password"/>

                <form:errors path="passwordConfirm" cssClass="error">
                    <form:errors path="password" cssClass="error">
                        <form:errors id="errorName" path="username" cssClass="error" />
                    </form:errors>
                </form:errors>

                <c:if test="${not empty error}">
                    <div class="error">${error}</div>
                </c:if>

                <button type="submit" class="btn btn-lg btn-primary btn-block">Register</button>
            </div>
        </form:form>
    </div>

    <h4 class="text-center"><a href="${contextPath}/login">Back to login</a></h4>

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
    <script src="${contextPath}/resources/js/bootstrap.min.js"></script>
</body>
</html>
