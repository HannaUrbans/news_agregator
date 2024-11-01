<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ include file="page_elems/title.jsp"%>
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

	<div class="not_footer">
		<div id="user_account_page">
			<header>
				<div class="enter_button">
					<form action="Controller" method="Get">
						<button type="submit" name="command" value="SHOW_STUB_PAGE">Выйти</button>
					</form>
				</div>
			</header>
			<div class="body_flexbox">
				<h4 class="greeting_message">
					<c:if test="$ {not empty authSuccess}">
					${authSuccess}
				</c:if>
				</h4>
			</div>
		</div>
	</div>
	<div class="footer">
		<%@ include file="page_elems/footer.jsp"%>
	</div>

</body>
</html>
