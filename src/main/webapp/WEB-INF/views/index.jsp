<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>GameHub</title>

    <link rel="icon" type="image/png" href="${contextPath}/resources/imgs/icons/game_1.ico"/>

    <link href="${contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
    <link href="${contextPath}/resources/css/common.css" rel="stylesheet">
    <link href="${contextPath}/resources/css/index.css" rel="stylesheet">

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
    <script src="${contextPath}/resources/js/bootstrap.min.js"></script>
</head>

<body>
    <div class="container">
        <form:form action="/j_spring_security_check" method="POST" class="form-signin">
            <h2 class="form-heading">Login</h2>
            <c:if test="${not empty message}">
                <div class="logout">${message}</div>
            </c:if>
            <div class="form-group ${error != null ? 'has-error' : ''}">
                <input type='text' name='username' class="form-control" autofocus="autofocus" placeholder="Username">
                <input type='password' name='password' class="form-control" placeholder="Password"/>
                <c:if test="${not empty error}">
                    <span>${error}</span>
                </c:if>
                <button type="submit" class="btn btn-lg btn-primary btn-block">Sign in</button>
            </div>
        </form:form>
        <h4 class="text-center"><a href="${contextPath}/registry">Create an account</a></h4>
    </div>
</body>
</html>
