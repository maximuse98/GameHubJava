<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%--
  Created by IntelliJ IDEA.
  User: maxim
  Date: 13.10.2019
  Time: 10:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Game config</title>
    <style type="text/css">
        .tg  {border-collapse:collapse;border-spacing:0;border-color:#ccc;}
        .tg td{font-family:Arial, sans-serif;font-size:14px;padding:10px 5px;border-style:solid;border-width:1px;overflow:hidden;word-break:normal;border-color:#ccc;color:#333;background-color:#fff;}
        .tg th{font-family:Arial, sans-serif;font-size:14px;font-weight:normal;padding:10px 5px;border-style:solid;border-width:1px;overflow:hidden;word-break:normal;border-color:#ccc;color:#333;background-color:#f0f0f0;}
        .tg .tg-4eph{background-color:#f9f9f9}
    </style>
</head>

<c:url var="upload" value="/upload"/>

<body>
    <h2>
        Game manager page
    </h2>

    <div style="position: absolute; top: 20px; right: 20px; width: 300px; text-align:right;">
        <c:url value="/exit" var="logoutUrl"/>
        <c:url value="/games" var="gamesUrl"/>
        <form:form action="${logoutUrl}" method="post">
            <button type="submit">Exit</button>
        </form:form>
        <form:form action="${gamesUrl}" method="get">
            <button type="submit">Main</button>
        </form:form>
        Current user: ${pageContext.request.userPrincipal.name}
    </div>

    <c:if test="${!empty listGames}">
        <h3>Games List</h3>
        <table class="tg">
        <tr>
        <th width="80">Game ID</th>
            <th width="120">Game Name</th>
            <th width="120">Game Players</th>
            <th width="60">Drop game</th>
        </tr>
        <c:forEach items="${listGames}" var="game">
            <tr>
                <td>${game.id}</td>
                <td>${game.name}</td>
                <td>${game.playersCount}</td>
                <td>
                    <spring:url value="/delete/${game.id}" var="userUrl" />
                    <button onclick="location.href='${userUrl}'">Delete</button>
                </td>
            </tr>
        </c:forEach>
        </table>
    </c:if>
    <form action="${upload}" method="post" enctype="multipart/form-data">
        <table>
            <h3>Load game</h3>
            <tr>
                <td>Load json:<input type="file" name="game"/></td></tr><tr>
                <td>Load images:<input type="file" name="images" multiple="multiple" /></td>
            </tr>
            <tr><td><input type="submit" value="Upload" /></td></tr>
        </table>
    </form>
</body>
</html>
