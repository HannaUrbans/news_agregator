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


<div id="author_account_page">
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
                <h4 class="greeting_message">
                    <c:if test="${not (sessionScope.authSuccess eq null)}">
                        ${sessionScope.authSuccess}
                        <c:remove var="authSuccess" scope="session"/>
                    </c:if>
                </h4>
                <c:if test="${not (sessionScope.regRedirectFail eq null)}">
                    <h4 class="alert alert-danger">
                            ${sessionScope.regRedirectFail}
                        <c:remove var="regRedirectFail" scope="session"/>
                    </h4>
                </c:if>
            </div>
            <div class="photo_text_align">
                <div id="authorPic">
                    <img src="${pageContext.request.contextPath}/images/authorPic.png" alt="Фото автора">
                    <br>
                    <a href="Controller?command=SHOW_STUB_PAGE">Заглушка</a>
                </div>
                <div class="logged_user_text_data">
                    <div>
                        <h4>Имя</h4>
                        <p>
                            <c:if test="${not empty sessionScope.author.name}">
                                ${sessionScope.author.name}
                            </c:if>
                        </p>
                        <a href="Controller?command=GO_TO_CHANGE_NAME_FORM">Изменить имя</a>
                        <c:if test="${not empty sessionScope.changeNameSuccess}">
                            <div class="alert alert-success">${sessionScope.changeNameSuccess}</div>
                            <c:remove var="changeNameSuccess" scope="session"/>
                        </c:if>

                        <c:if test="${not empty sessionScope.changeNameError}">
                            <div class="alert alert-danger">${sessionScope.changeNameError}</div>
                            <c:remove var="changeNameError" scope="session"/>
                        </c:if>
                        <h4>E-mail</h4>
                        <c:if test="${not (sessionScope.author eq null)}">
                            ${sessionScope.author.email}
                        </c:if>

                        <h4>Пароль</h4>
                        <p>
                            <c:if test="${not empty sessionScope.author.password}">
                                <c:set var="passwordLength" value="${fn:length(sessionScope.author.password)}"/>
                                <c:forEach var="i" begin="1" end="${passwordLength}">
                                    *
                                </c:forEach>
                            </c:if>
                        </p>
                        <a href="Controller?command=GO_TO_CHANGE_PASSWORD_FORM">Изменить пароль</a>
                        <c:if test="${not empty sessionScope.changePasswordSuccess}">
                            <div class="alert alert-success">${sessionScope.changePasswordSuccess}</div>
                            <c:remove var="changePasswordSuccess" scope="session"/>
                        </c:if>

                        <c:if test="${not empty sessionScope.changePasswordError}">
                            <div class="alert alert-danger">${sessionScope.changePasswordError}</div>
                            <c:remove var="changePasswordError" scope="session"/>
                        </c:if>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="bio_desc">
        <h4>Биография</h4>
        <c:choose>
            <c:when test="${not empty sessionScope.newBio}">
                ${sessionScope.newBio}
            </c:when>
            <c:otherwise>
                ${sessionScope.author.bio}
            </c:otherwise>
        </c:choose>
        <br>
        <a href="Controller?command=GO_TO_CHANGE_BIO_FORM">Изменить биографию</a>
        <c:if test="${not empty sessionScope.changeBioSuccess}">
            <div class="alert alert-success">${sessionScope.changeBioSuccess}</div>
            <c:remove var="changeBioSuccess" scope="session"/>
        </c:if>
        <c:if test="${not empty sessionScope.changeBioError}">
            <div class="alert alert-danger">${sessionScope.changeBioError}</div>
            <c:remove var="changeBioError" scope="session"/>
        </c:if>
    </div>
    <form>
        <div class="news_button">
            <button type="submit" name="command" value="GO_TO_ADD_NEWS_FORM_PAGE">Добавить новость</button>
        </div>
    </form>
    <div class="author_page_news_list">
        <h4>Список статей:</h4>

        <c:if test="${not empty sessionScope.newsAuthorsList}">
            <c:forEach var="news" items="${sessionScope.newsAuthorsList}">
                <h4>${news.title}</h4>
                <img src="${pageContext.request.contextPath}/images/${news.imageUrl}" alt="Image"/>
                <p>${news.brief}</p>
                <p>${news.newsText}</p>
                <hr>
            </c:forEach>
        </c:if>
    </div>
</div>
</body>
</html>
