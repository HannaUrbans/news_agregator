<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<%@ include file="page_elems/title.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title><%=title%>
    </title>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link
            href="https://fonts.googleapis.com/css2?family=Bad+Script&family=Caveat:wght@400..700&family=Montserrat:ital,wght@0,100..900;1,100..900&family=Oswald:wght@200..700&display=swap"
            rel="stylesheet">
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>


<div id="admin_account_page">
    <header>
        <div class="enter_button">
            <form action="Controller" method="Get">
                <button type="submit" name="command" value="GO_TO_INDEX_PAGE">Главная</button>
            </form>
        </div>
        <div class="enter_button">
            <form action="Controller" method="Get">
                <button type="submit" name="command" value="LOGOUT">Выйти</button>
            </form>
        </div>

    </header>
    <div class="body_center_flexbox">

        <div class="logged_user_profile">
            <div>
                <c:if test="${not (sessionScope.regRedirectFail eq null)}">
                    <div class="alert alert-danger">
                            ${sessionScope.regRedirectFail}
                        <c:remove var="regRedirectFail" scope="session"/>
                    </div>
                </c:if>
                <h4 class="greeting_message">
                    Добро пожаловать, ${sessionScope.nameFromDb}
                </h4>
            </div>

        </div>
    </div>
</div>
</body>
</html>
