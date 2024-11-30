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
                <c:if test="${not (sessionScope.regRedirectFail eq null)}">
                    <div class="alert alert-danger">
                            ${sessionScope.regRedirectFail}
                        <c:remove var="regRedirectFail" scope="session"/>
                    </div>
                </c:if>
                <h4 class="greeting_message">
                    Добро пожаловать,
                    <c:choose>
                        <c:when test="${not empty sessionScope.author.name}">
                            ${sessionScope.author.name}
                        </c:when>
                        <c:otherwise>
                            ${sessionScope.nameFromDb}
                        </c:otherwise>
                    </c:choose>
                </h4>
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
                        <a href="Controller?command=GO_TO_CHANGE_FORM&formType=name">Изменить имя</a>
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
                            ${sessionScope.emailFromDb}
                        </c:if>

                        <h4>Пароль</h4>
                        <p>
                            <c:choose>
                                <c:when test="${not empty sessionScope.newPassword}">
                                    <c:set var="passwordLength" value="${fn:length(sessionScope.newPassword)}"/>
                                    <c:forEach var="i" begin="1" end="${passwordLength}">
                                        *
                                    </c:forEach>
                                </c:when>
                                <c:otherwise>
                                    <c:if test="${not empty sessionScope.passwordFromDb}">
                                        <c:set var="passwordLength" value="${fn:length(sessionScope.passwordFromDb)}"/>
                                        <c:forEach var="i" begin="1" end="${passwordLength}">
                                            *
                                        </c:forEach>
                                    </c:if>
                                </c:otherwise>
                            </c:choose>
                        </p>
                        <a href="Controller?command=GO_TO_CHANGE_FORM&formType=password">Изменить пароль</a>
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
                ${sessionScope.bioFromDb}
            </c:otherwise>
        </c:choose>
        <br>
        <a href="Controller?command=GO_TO_CHANGE_FORM&formType=bio">Изменить биографию</a>
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

        <c:forEach var="news" items="${sessionScope.authorNewsList}">
        <div class="news-item">
            <h4>
                <c:choose>
                    <c:when test="${not empty sessionScope.newNewsTitle}">${fn:escapeXml(sessionScope.newNewsTitle)}</c:when><c:otherwise>${fn:escapeXml(news.title)}</c:otherwise>
                </c:choose>
            </h4>
            <img src="${pageContext.request.contextPath}${news.imageUrl}" alt="Image"/>
            <img src="${news.imageUrl}" alt="Image"/>
            <p>
                <c:choose>
                    <c:when test="${not empty sessionScope.newNewsBrief}">${fn:escapeXml(sessionScope.newNewsBrief)}</c:when><c:otherwise>${fn:escapeXml(news.brief)}</c:otherwise></c:choose>
            </p>

            <p>
                <c:choose>
                    <c:when test="${not empty sessionScope.newNewsText}">${fn:escapeXml(sessionScope.newNewsText)}</c:when><c:otherwise>${fn:escapeXml(news.newsText)}</c:otherwise>
                </c:choose>
            </p>

            <a href="Controller?command=GO_TO_CHANGE_FORM&formType=newsArticle&newsId=${news.newsId}">Изменить
                новость</a>
            <hr>
            </c:forEach>
        </div>
    </div>
</body>
</html>
