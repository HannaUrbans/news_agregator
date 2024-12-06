package by.urban.web_project.controller.concrete.impl;

import by.urban.web_project.bean.Auth;
import by.urban.web_project.controller.concrete.Command;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import static by.urban.web_project.controller.utils.UrlFormatterUtil.formatRedirectUrl;

public class GoToAuthentificationPage implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Auth auth = (Auth) request.getSession(false).getAttribute("auth");

        // Проверка, если уже есть авторизованный пользователь
        if (auth != null) {
            response.sendRedirect("Controller?command=" + formatRedirectUrl(auth.getRole()));
            System.out.println("Запрос кнопки войти, хотя уже зарегистрирован");
            return;
        }

        // Если в сети нет никого, то переходим на страницу авторизации
        RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/auth-page.jsp");
        dispatcher.forward(request, response);
    }
}
