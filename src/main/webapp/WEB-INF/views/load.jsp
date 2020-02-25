<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title>GameHub Config</title>

    <link rel="stylesheet" type='text/css' href="${contextPath}/resources/css/assets/demo.css">
    <link rel="stylesheet" type='text/css' href="${contextPath}/resources/css/assets/header-login-signup.css">
    <link rel='stylesheet' type='text/css' href='http://fonts.googleapis.com/css?family=Cookie'>

    <link rel="icon" type="image/png" href="${contextPath}/resources/imgs/icons/favicon.ico"/>

    <link rel="stylesheet" type="text/css" href="${contextPath}/resources/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="${contextPath}/resources/css/font-awesome.min.css">
    <link rel="stylesheet" type="text/css" href="${contextPath}/resources/css/perfect-scrollbar.css">
    <link rel="stylesheet" type="text/css" href="${contextPath}/resources/css/games/util.css">
    <link rel="stylesheet" type="text/css" href="${contextPath}/resources/css/games/main.css">

    <style type="text/css">
        .form-style-5{
            max-width: 1000px;
            margin: 10px auto;
            padding: 20px;
            background: #f4f7f8;
            border-radius: 8px;
            font-family: Lato-Bold, sans-serif;
            font-size: 18px;

            color: #eeeeee;
            line-height: 1.4;
        }
        .form-style-5 fieldset{
            border: none;
        }
        .form-style-5 legend {
            font-size: 1.4em;
            margin-bottom: 10px;
        }
        .form-style-5 label {
            display: block;
            margin-bottom: 8px;
        }
        .form-style-5 input[type="file"],
        .form-style-5 textarea,
        .form-style-5 select {
            border: none;
            border-radius: 4px;
            font-size: 15px;
            outline: 0;
            padding: 10px;
            width: 100%;
            box-sizing: border-box;
            -webkit-box-sizing: border-box;
            -moz-box-sizing: border-box;
            background: #e8eeef;
            color:#8a97a0;
            -webkit-box-shadow: 0 1px 0 rgba(0,0,0,0.03) inset;
            box-shadow: 0 1px 0 rgba(0,0,0,0.03) inset;
            margin: 0 0 30px;
        }
        .form-style-5 input[type="file"]:focus,
        .form-style-5 textarea:focus,
        .form-style-5 select:focus{
            background: #d2d9dd;
        }
        .form-style-5 select{
            -webkit-appearance: menulist-button;
            height:35px;
        }

        .form-style-5 input[type="submit"],
        .form-style-5 input[type="button"]
        {
            position: relative;
            display: block;
            padding: 19px 39px 18px 39px;
            color: #FFF;
            background: #1abc9c;
            font-size: 18px;
            text-align: center;
            font-style: normal;
            width: 100%;
            border: 1px solid #16a085;
            margin: 0 auto 10px;
        }
        .form-style-5 input[type="submit"]:hover,
        .form-style-5 input[type="button"]:hover
        {
            background: #109177;
        }
    </style>
    <script type="application/javascript">
        function load(){
            document.getElementById("uploadForm").submit();
        }
    </script>
</head>
<body>
    <header class="header-login-signup">
        <div class="header-limiter">
            <h1><a>Game<span>Hub</span> Admin Page</a></h1>
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
                                <th class="cell100 column9">ID</th>
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
                                        <td class="cell100 column9">${game.id}</td>
                                        <td class="cell100 column1">${game.name}</td>
                                        <td class="cell100 column2">${game.playersCount}</td>
                                        <td class="cell100 column3" >
                                            <div class="button_cont">
                                                <a class="example_b2" href="/delete/${game.id}" target="_blank" rel="nofollow noopener">Delete</a>
                                            </div>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
            </c:if>
            <form id="uploadForm" action="${pageContext.request.contextPath}/upload" class="form-style-5" method="POST" enctype="multipart/form-data">
                <table>
                    <tr>
                        <td>Load json:<input type="file" name="game"/>
                        <td>Load images:<input type="file" name="images" multiple="multiple" />
                        <td>
                            <div class="button_cont">
                                <a class="example_b" onclick="load();" type="submit" target="_blank" rel="nofollow noopener">Upload</a>
                            </div>
                        </td>
                    </tr>
                    <!--<input type="submit" value="Upload" />-->
                </table>
            </form>
        </div>
    </div>
</body>
</html>
