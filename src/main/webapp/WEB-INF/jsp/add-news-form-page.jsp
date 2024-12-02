<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<%@ include file="page_elems/title.jsp" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title><%= title %>
    </title>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Bad+Script&family=Caveat:wght@400..700&family=Montserrat:ital,wght@0,100..900;1,100..900&family=Oswald:wght@200..700&display=swap"
          rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>

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
<div class="not_footer">
    <form action="Controller" method="post" enctype="multipart/form-data">
        <div class="wide_form">
            <h1>Для добавления новости, пожалуйста, заполните все поля</h1>

            <label for="newsImportance">Выберите категорию новости
                <select id="newsImportance" name="newsImportance">
                    <option value="breaking">Breaking</option>
                    <option value="top">Top</option>
                    <option value="regular">Regular</option>
                </select>
            </label>

            <label for="newsTitle">Заголовок новости
                <textarea id="newsTitle" name="newsTitle" required></textarea>
            </label>

            <label for="newsPic">Изображение
                <input type="file" id="newsPic" name="newsPic" accept="image/*" required>
            </label>

            <label for="newsBrief">Бриф статьи
                <textarea id="newsBrief" name="newsBrief" required></textarea>
            </label>

            <label for="newsContent">Статья
                <textarea id="newsContent" name="newsContent" required></textarea>
            </label>

            <label for="newsCategory">Выберите категорию новости
                <select id="newsCategory" name="newsCategory">
                    <option value="1">3D печать</option>
                    <option value="2">3D моделирование</option>
                    <option value="3">Литьё фотополимером</option>
                    <option value="4">Экология</option>
                    <option value="5">Косплей</option>
                    <option value="6">Материалы для работы</option>
                </select>
            </label>
            <button type="submit" name="command" value="ADD_NEWS">Добавить новость</button>
        </div>
    </form>


</div>
<div class="footer">
    <%@ include file="page_elems/footer.jsp" %>
</div>

</body>
</html>
