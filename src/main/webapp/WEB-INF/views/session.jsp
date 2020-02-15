<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page session="false" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<html>
    <head>
        <meta charset="utf-8">
        <title>${gameName}</title>

        <link rel="stylesheet" href="${contextPath}/resources/css/game.css">
        <link rel="stylesheet" href="${contextPath}/resources/css/bootstrap.min.css">
        <link rel="stylesheet" type="text/css" href="${contextPath}/resources/css/ldbtn.min.css">
        <link rel="stylesheet" type="text/css" href="${contextPath}/resources/css/ldld.css"/>
        <link rel="stylesheet" type="text/css" href="${contextPath}/resources/css/loading.min.css"/>

        <link rel="icon" type="image/png" href="${contextPath}/resources/imgs/icons/game_1.ico"/>

        <script src="${contextPath}/resources/js/ldld.js"></script>
        <script src="${contextPath}/resources/js/session.js"></script>
    </head>
    <body onload="setFooterColor('${gameColor}'); splitText('${scene.text}','${scene.ids}','${scene.speakers}','${scene.sprites}','${scene.backgrounds}','${scene.lastSceneType}');">
        <div id="background" ></div>

        <header>
            <h1>${gameName}</h1>
            <div class="username">
                <div class="login">${pageContext.request.userPrincipal.name}</div>
            </div>
        </header>

        <div id="sprite"></div>

        <div class="footer" id="ftr" style="background-color: ${gameColor};">
            <div id ="speaker" class="footer2" style="background-color: ${gameColor};"></div>
            <p id="text"></p>
            <div class="chs" id="choice">
                <c:forEach items="${scene.choices}" var="choise">
                    <div class="btn btn-success" style="display: none" onclick="session();window.location.href='/send/${choise.id}';">
                            ${choise.caption}
                        <div class="ldld full">
                            <p>Wait other player</p>
                        </div>
                    </div>
                </c:forEach>
                <div class="btn btn-success" id ="continue" onclick="nextPhrase();">Continue</div>
            </div>
            <div class="closeIcon" onclick="window.location.href='/leave';"></div>
        </div>
    </body>
</html>
