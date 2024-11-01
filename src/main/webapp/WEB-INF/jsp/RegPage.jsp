<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="page_elems/title.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title><%=title%></title>
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link
	href="https://fonts.googleapis.com/css2?family=Bad+Script&family=Caveat:wght@400..700&family=Montserrat:ital,wght@0,100..900;1,100..900&family=Oswald:wght@200..700&display=swap"
	rel="stylesheet">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<div class = "not_footer">
	<div class = "form_to_fill_in">
		<form action="Controller" method="post">
		<h1>Пожалуйста, зарегистрируйтесь</h1>
			<label> <input type="text" name="name"
				placeholder="Имя пользователя" required></label> <br> <label>
				<input type="email" name="email" placeholder="Email адрес" required>
			</label> <br> <label> <input type="password" name="password"
				placeholder="Пароль" required></label> <br> <label> <input
				type="password" name="confirmPassword"
				placeholder="Подтверждение пароля" required></label> <br>
				<c:if test="${not empty regError }"><div class="alert alert-danger">${regError}</div></c:if>
			<button type="submit" name="command" value="DO_REGISTRATION">Зарегистрироваться</button>
		</form>
	</div>
	</div>
	<div class = "footer">
	<%@ include file="page_elems/footer.jsp"%>
	</div>
</body>
</html>