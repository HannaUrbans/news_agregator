<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="page_elems/title.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title><%=title %>
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
<body>
<div class="not_footer">
    <div class="form_to_fill_in">

        <form action="Controller" method="post">
            <label for="newNewsTitle">Изменить заголовок статьи:</label>
            <input type="text" id="newNewsTitle" name="newNewsTitle" required value="${news.title}">
            <label for="newNewsCategory">Изменить категорию статьи:<br>
                <select id="newNewsCategory" name="newNewsCategory" required>
                    <option value="1" ${news.category == '1' ? 'selected' : ''}>3D печать</option>
                    <option value="2" ${news.category == '2' ? 'selected' : ''}>3D моделирование</option>
                    <option value="3" ${news.category == '3' ? 'selected' : ''}>Литьё фотополимером</option>
                    <option value="4" ${news.category == '4' ? 'selected' : ''}>Экология</option>
                    <option value="5" ${news.category == '5' ? 'selected' : ''}>Косплей</option>
                    <option value="6" ${news.category == '6' ? 'selected' : ''}>Материалы для работы</option>
                </select>
            </label>
            <label for="newNewsBrief">Изменить бриф статьи:</label>
            <textarea id="newNewsBrief" name="newNewsBrief" required>${news.brief}</textarea>
            <label for="newNewsContent">Изменить текст статьи:</label>
            <textarea id="newNewsContent" name="newNewsContent" required>${news.content}</textarea>
            <button type="submit" name="command" value="CHANGE_NEWS_ARTICLE">Готово</button>
        </form>
    </div>
</div>
<div class="footer">
    <%@ include file="page_elems/footer.jsp" %>
</div>
</body>
</html>