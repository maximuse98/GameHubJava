<%--
  Created by IntelliJ IDEA.
  User: maxim
  Date: 27.11.2019
  Time: 23:56
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>GameHub</title>

    <link href="${contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
    <link href="${contextPath}/resources/css/common.css" rel="stylesheet">
</head>

<style>
    body {
        background-image: url("${contextPath}/resources/imgs/error.png");
        background-repeat: no-repeat;
        background-attachment: fixed;
        background-position: center;
        background-size: 80% 100%;
        background-blend-mode: darken;
    }
</style>

<body>
    ${errorMsg}
</body>
