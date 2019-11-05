<%--
  Created by IntelliJ IDEA.
  User: maxim
  Date: 01.10.2019
  Time: 12:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page session="false" %>
<html>
<head>
    <title>${gameName}</title>
    <style type="text/css">
        .tg  {border-collapse:collapse;border-spacing:0;border-color:#ccc;}
        .tg td{font-family:Arial, sans-serif;font-size:14px;padding:10px 5px;border-style:solid;border-width:1px;overflow:hidden;word-break:normal;border-color:#ccc;color:#333;background-color:#fff;}
        .tg th{font-family:Arial, sans-serif;font-size:14px;font-weight:normal;padding:10px 5px;border-style:solid;border-width:1px;overflow:hidden;word-break:normal;border-color:#ccc;color:#333;background-color:#f0f0f0;}
        .tg .tg-4eph{background-color:#f9f9f9}
    </style>
</head>
<body>
    <h1>
        ${gameName}
    </h1>

    <div style="position: absolute; top: 20px; right: 20px; width: 250px; text-align:right;">
        <spring:url value="/leave/${username}" var="userUrl" />
        <button onclick="location.href='${userUrl}'">Leave</button>
        Current user: ${username}
    </div>

    <c:forEach items="${scene.phrases}" var="phrase">
        ${phrase.speaker} - ${phrase.speech}
    </c:forEach>

    <br>
    <c:forEach items="${scene.choices}" var="choice">
        <spring:url value="/send/${username}/${choice.id}" var="userUrl" />
        <button onclick="location.href='${userUrl}'">${choice.caption}</button>
    </c:forEach>
    <c:if test="${scene.type == 'Result'}">
        <spring:url value="/games/${username}" var="exit" />
        <button onclick="location.href='${exit}'">End game</button>
    </c:if>
    <br>

    <c:forEach items="${scene.sprites}" var="sprite">
        <img src="/image/sprite/${username}/${sprite.id}" alt="image"/>
    </c:forEach>
    <img src="/image/background/${username}" alt="image"/>
</body>
</html>
