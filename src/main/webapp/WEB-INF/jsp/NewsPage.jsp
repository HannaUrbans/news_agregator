<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="page_elems/title.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
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
<header>
    <div class="buttons_block">
        <form action="Controller" method="Get">
            <button type="submit" name="command" value="GO_TO_INDEX_PAGE">Главная</button>
        </form>
    </div>
    <div class="buttons_block">
        <form action="Controller" method="Get">
            <button type="submit" name="command"
                    value="GO_TO_AUTHENTIFICATION_PAGE">Войти
            </button>
        </form>

        <form action="Controller" method="Get">
            <button type="submit" name="command" value="LOGOUT">Выйти</button>
        </form>

        <form action="Controller" method="Get">
            <button type="submit" name="command"
                    value="GO_TO_REGISTRATION_PAGE">Регистрация
            </button>
        </form>
    </div>
</header>
<body>
<div class="not_footer">
    <c:choose>
        <c:when test="${not empty news}">

            <div class="newsPage">
                <h1>
                    <c:out value="${news.title}"/>
                </h1>
                <img src="<c:out value='${news.imageUrl}'/>" alt="News Image"/>
                <p>
                    <c:out value="${news.newsText}"/>
                </p>

            </div>
        </c:when>
        <c:when test="${not empty errorMessage}">
            <p>
                <c:out value="${errorMessage}"/>
            </p>
        </c:when>
    </c:choose>
    <div class = "note">Если Вы хотите стать нашим автором, пожалуйста, пришлите образцы своих статей на адрес admin@news.by.</div>
</div>
<div class="footer">
    <%@ include file="page_elems/footer.jsp" %>
</div>
</body>
</html>