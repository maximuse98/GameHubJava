<%--
  Created by IntelliJ IDEA.
  User: maxim
  Date: 01.10.2019
  Time: 12:17
  To change this template use File | Settings | File Templates.
--%>
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

        <script src="${contextPath}/resources/js/ldld.js"></script>
        <script src="${contextPath}/resources/js/session.js"></script>
    </head>
    <body onload="setFooterColor('${gameColor}')">
        <div id="bg" >
            <img src="/image/background" alt=""/>
        </div>

        <header>
            <h1>${gameName}</h1>
            <div class="usrnm">
                <div class="login">${pageContext.request.userPrincipal.name} - ${userRole}</div>
            </div>
        </header>

        <div id="sprite">
            <c:forEach items="${scene.sprites}" var="sprite">
                <img src="/image/sprite/${sprite.id}" alt="image"/>
            </c:forEach>
        </div>

        <div class="footer" id="ftr" style="background-color: ${gameColor};">
            <c:if test="${scene.speaker != null}">
                <div class="footer2" style="background-color: ${gameColor};">${scene.speaker}</div>
            </c:if>
            <c:forEach items="${scene.phrases}" var="phrase">
                <p>${phrase.speech}</p>
            </c:forEach>
            <div class="chs">
                <c:forEach items="${scene.choices}" var="choice">
                    <spring:url value="/send/${choice.id}" var="sendUrl" />
                    <div class="btn btn-success" onclick="session();location.href='${sendUrl}';">
                            ${choice.caption}
                        <div class="ldld full">
                            <p>Wait other player</p>
                        </div>
                    </div>
                </c:forEach>
                <c:if test="${scene.type == 'Result'}">
                    <spring:url value="/leave" var="exit" />
                    <div class="btn btn-success" onclick="location.href='${exit}';">Finish game</div>
                </c:if>
                <c:if test="${scene.type == 'Normal'}">
                    <spring:url value="/next" var="next" />
                    <div class="btn btn-success" onclick="location.href='${next}';">Continue</div>
                </c:if>
                <spring:url value="/leave" var="userUrl" />
                <div class="close1" onclick="location.href='${userUrl}'"></div>
            </div>
        </div>
    </body>
</html>
