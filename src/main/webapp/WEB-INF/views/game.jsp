<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<%@ page session="false" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title>GameHub</title>

    <link rel="stylesheet" href="${contextPath}/resources/css/assets/demo.css">
    <link rel="stylesheet" href="${contextPath}/resources/css/assets/header-login-signup.css">
    <link href='http://fonts.googleapis.com/css?family=Cookie' rel='stylesheet' type='text/css'>

    <link rel="icon" type="image/png" href="${contextPath}/resources/imgs/icons/favicon.ico"/>
    <link rel="stylesheet" type="text/css" href="${contextPath}/resources/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="${contextPath}/resources/css/font-awesome.min.css">
    <link rel="stylesheet" type="text/css" href="${contextPath}/resources/css/perfect-scrollbar.css">
    <link rel="stylesheet" type="text/css" href="${contextPath}/resources/css/games/util.css">
    <link rel="stylesheet" type="text/css" href="${contextPath}/resources/css/games/main.css">
</head>

<body>
    <header class="header-login-signup">
        <div class="header-limiter">
            <h1><a>Game<span>Hub</span></a></h1>
            <ul style="padding-top: 10px">
                <li><a>${username}</a></li>
                <li><a href="${pageContext.request.contextPath}/j_spring_security_logout">Logout</a></li>
            </ul>
        </div>
    </header>
    <div class="container-table100">
        <div class="wrap-table100">
            <c:if test="${!empty listGames}">
                <div class="table100 ver1 fs-160 m-b-250 m-l-0">
                    <div class="table100-head">
                        <table>
                            <thead>
                                <tr class="row100 head">
                                    <th class="cell100 column1">Game name</th>
                                    <th class="cell100 column2">Players</th>
                                    <th class="cell100 column3"></th>
                                </tr>
                            </thead>
                        </table>
                    </div>
                    <div class="table100-body js-pscroll">
                        <table>
                            <tbody>
                                <c:forEach items="${listGames}" var="game">
                                    <tr class="row100 body">
                                        <td class="cell100 column1">${game.name}</td>
                                        <td class="cell100 column2">${game.playersCount}</td>
                                        <td class="cell100 column3" >
                                            <div class="button_cont">
                                                <a class="example_b" href="/start/${game.id}" target="_blank" rel="nofollow noopener">Start</a>
                                            </div>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
            </c:if>

            <c:if test="${!empty listSessions}">
                <div class="table100 ver1 ">
                    <div class="table100-head">
                        <table>
                            <thead>
                            <tr class="row100 head">
                                <th class="cell100 column4">Game Name</th>
                                <th class="cell100 column5">Creator</th>
                                <th class="cell100 column6">Game size</th>
                                <th class="cell100 column7">Current players</th>
                                <th class="cell100 column8"></th>
                            </tr>
                            </thead>
                        </table>
                    </div>
                    <div class="table100-body js-pscroll">
                        <table>
                            <tbody>
                                <c:forEach items="${listSessions}" var="session">
                                    <tr class="row100 body">
                                        <td class="cell100 column4">${session.gameName}</td>
                                        <td class="cell100 column5">${session.creatorUsername}</td>
                                        <td class="cell100 column6">${session.gameSize}</td>
                                        <td class="cell100 column7">${session.usersCount}</td>
                                        <c:if test="${session.usersCount<session.gameSize}">
                                            <td class="cell100 column8" >
                                                <div class="button_cont">
                                                    <a class="example_b" href="/connect/${session.id}" target="_blank" rel="nofollow noopener">Connect</a>
                                                </div>
                                            </td>
                                        </c:if>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
            </c:if>
        </div>
    </div>
</body>
</html>
