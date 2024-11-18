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
    <div id="user_account_page">
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
                    <div id="userPic">
                        <img src="${pageContext.request.contextPath}/images/userPic.png" alt="Фото пользователя">
                        <br>
                        <a href="Controller?command=SHOW_STUB_PAGE">Заглушка</a>
                    </div>
                    <div class="logged_user_text_data">
                        <div>
                            <h4>Имя</h4>
                            <p>
                                <c:if test="${not empty sessionScope.user.name}">
                                    ${sessionScope.user.name}
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
                            <c:if test="${not (sessionScope.user eq null)}">
                                ${sessionScope.user.email}
                            </c:if>

                            <h4>Пароль</h4>
                            <p>
                                <c:if test="${not empty sessionScope.user.password}">
                                    <c:set var="passwordLength" value="${fn:length(sessionScope.user.password)}"/>
                                    <c:forEach var="i" begin="1" end="${passwordLength}">
                                        *
                                    </c:forEach>
                                </c:if>
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
    </div>
</div>
<div class="footer">
    <%@ include file="page_elems/footer.jsp" %>
</div>

</body>
</html>
