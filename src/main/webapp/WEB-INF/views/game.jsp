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
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
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
<body>
    <h1>Gamehub</h1>

    <c:if test="${logout}">
        <script type="text/javascript">
            window.onload = performLogout;
            function performLogout() {
                document.forms[0].submit();
            }
        </script>
        <c:url value="/j_spring_security_logout" var="exitURL"/>
        <form:form action="${exitURL}" method="post"/>
        <!--<redirect url="/j_spring_security_logout"/>-->
    </c:if>

    <div style="position: absolute; top: 20px; right: 20px; width: 300px; text-align:right;">
        <c:url value="/exit" var="logoutUrl"/>
        <c:url value="/upload" var="uploadUrl"/>
        <form:form action="${logoutUrl}" method="post">
            <button type="submit">Exit</button>
        </form:form>
        <security:authorize access="hasRole('ROLE_ADMIN')">
            <form action="${uploadUrl}" method="get">
                <button type="submit">Admin</button>
            </form>
        </security:authorize>
        Current user: ${pageContext.request.userPrincipal.name}
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
                    <!--<th width="60">Begin session</th>-->
                </tr>
                <c:forEach items="${listGames}" var="game">
                    <tr>
                        <!--<td>${game.id}</td>-->
                        <td>${game.name}</td>
                        <td>${game.playersCount}</td>
                        <security:authorize access="hasRole('ROLE_USER')">
                            <td>
                                <spring:url value="/start/${game.id}" var="userUrl" />
                                <button onclick="location.href='${userUrl}'">Start</button>
                            </td>
                        </security:authorize>
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
                        <security:authorize access="hasRole('ROLE_USER')">
                            <c:if test="${session.usersCount<session.gameSize}">
                                <td>
                                    <spring:url value="/connect/${session.id}" var="connect" />
                                    <button onclick="location.href='${connect}'">Connect</button>
                                </td>
                            </c:if>
                        </security:authorize>
                    </tr>
                </c:forEach>
            </table>
        </c:if>
    </c:if>
</body>
</html>
