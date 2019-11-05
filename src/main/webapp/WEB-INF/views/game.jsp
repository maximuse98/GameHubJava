<%--
  Created by IntelliJ IDEA.
  User: maxim
  Date: 01.10.2019
  Time: 19:54
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page session="false" %>
<html>
<head>
    <title>GameHub</title>
    <style type="text/css">
        .tg  {border-collapse:collapse;border-spacing:0;border-color:#ccc;}
        .tg td{font-family:Arial, sans-serif;font-size:14px;padding:10px 5px;border-style:solid;border-width:1px;overflow:hidden;word-break:normal;border-color:#ccc;color:#333;background-color:#fff;}
        .tg th{font-family:Arial, sans-serif;font-size:14px;font-weight:normal;padding:10px 5px;border-style:solid;border-width:1px;overflow:hidden;word-break:normal;border-color:#ccc;color:#333;background-color:#f0f0f0;}
        .tg .tg-4eph{background-color:#f9f9f9}
        .horizontal { display: inline;}
    </style>
</head>

    <h1>
        Gamehub v0.5
    </h1>

    <div style="position: absolute; top: 20px; right: 20px; width: 250px; text-align:right;">
        <spring:url value="/exit/${user.username}" var="userUrl" />
        <button onclick="location.href='${userUrl}'">Exit</button>
        Current user: ${user.username}
    </div>

    <c:if test="${!empty listUsers}">
        <h3>Users List</h3>
        <table class="tg">
            <tr>
                <th width="80">Username</th>
                <th width="120">Current session</th>
            </tr>
            <c:forEach items="${listUsers}" var="user">
                <tr>
                    <td>${user.username}</td>
                    <td>${user.currentGameName}</td>
                </tr>
            </c:forEach>
            <br>
        </table>
    </c:if>

    <c:if test="${!empty listGames}">
            <h3>Games List</h3>
            <table class="tg">
                <tr>
                    <!--<th width="80">Game ID</th>-->
                    <th width="120">Game Name</th>
                    <th width="120">Game Players</th>
                    <th width="60">Begin session</th>
                </tr>
                <c:forEach items="${listGames}" var="game">
                    <tr>
                        <!--<td>${game.id}</td>-->
                        <td>${game.name}</td>
                        <td>${game.playersCount}</td>
                        <td>
                            <spring:url value="/game/${user.username}/${game.id}" var="userUrl" />
                            <button onclick="location.href='${userUrl}'">Start</button>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        <c:if test="${!empty listSessions}">
            <h3>Sessions List</h3>
            <table class="tg">
                <tr>
                    <th width="80">Game Name</th>
                    <th width="120">Game Creator</th>
                    <th width="120">Players size</th>
                    <th width="60">Current players</th>
                </tr>
                <c:forEach items="${listSessions}" var="session">
                    <tr>
                        <td>${session.gameName}</td>
                        <td>${session.creatorUsername}</td>
                        <td>${session.gameSize}</td>
                        <td>${session.usersCount}</td>
                        <c:if test="${session.usersCount<session.gameSize}">
                            <td>
                                <spring:url value="/connect/${user.username}/${session.id}" var="connect" />
                                <button onclick="location.href='${connect}'">Connect</button>
                            </td>
                        </c:if>
                    </tr>
                </c:forEach>
            </table>
        </c:if>
    </c:if>
</body>
</html>
