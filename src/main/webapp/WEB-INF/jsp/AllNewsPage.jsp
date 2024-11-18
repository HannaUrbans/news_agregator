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
<body>
<div class="not_footer">
    <header>
        <div class="buttons_block">
            <form action="Controller" method="Get">
                <button type="submit" name="command" value="GO_TO_INDEX_PAGE">Главная</button>
            </form>
        </div>
        <div>
            <div class="buttons_block">
                <form action="Controller" method="GET">
                    <button type="submit" name="command"
                            value="GO_TO_AUTHENTIFICATION_PAGE">Войти
                    </button>
                </form>

                <form action="Controller" method="Get">
                    <button type="submit" name="command" value="LOGOUT">Выйти</button>
                </form>

                <form action="Controller" method="GET">
                    <button type="submit" name="command"
                            value="GO_TO_REGISTRATION_PAGE">Регистрация
                    </button>
                </form>


            </div>
    </header>
</div>
<div class="all_news_page"><h2>Все новости</h2>

    <!-- Проверяем, есть ли новости -->
    <c:if test="${not empty newsList}">
        <ul>
            <c:forEach var="news" items="${newsList}">
                <li>
                    <strong>${news.newsId}</strong><br/>
                    <strong>${news.title}</strong><br/>
                    <em>${news.brief}</em><br/>
                    <!-- Отображаем полный текст новости только для зарегистрированных пользователей -->
                    <c:if test="${not empty sessionScope.email}">
                        <p>${news.newsText}</p>
                    </c:if>
                    <hr/>
                </li>
            </c:forEach>
        </ul>
    </c:if>

    <!-- Если нет новостей -->
    <c:if test="${empty newsList}">
        <p>Нет новостей для отображения.</p>
    </c:if></div>
<div class="footer">
    <%@ include file="page_elems/footer.jsp" %>
</div>
</body>
</html>
