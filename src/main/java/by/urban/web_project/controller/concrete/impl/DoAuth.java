package by.urban.web_project.controller.concrete.impl;

import by.urban.web_project.bean.Auth;
import by.urban.web_project.bean.ProfileDataField;
import by.urban.web_project.bean.User;
import by.urban.web_project.controller.concrete.Command;
import by.urban.web_project.service.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import static by.urban.web_project.controller.utils.UrlFormatterUtil.formatRedirectUrl;

public class DoAuth implements Command {
    private final ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private final IAuthorizationService authorizationService = serviceFactory.getAuthorizationService();
    private final IChangeProfileService changeProfileService = ServiceFactory.getInstance().getChangeProfileService();

    //idea заставила пробросить в конструкторе исключение из-за serviceFactory.getAuthorizationService()
    public DoAuth() throws ServiceException {
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //эти данные приходят методом пост
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        if (email == null || password == null || email.isEmpty() || password.isEmpty()) {
            request.getSession().setAttribute("authError", "Email и пароль не могут быть пустыми.");
            response.sendRedirect("Controller?command=GO_TO_AUTHENTIFICATION_PAGE");
            return;
        }

        try {
            //проверяем формат email + авторизован ли посетитель
            //пока оставлю так, впоследствии изменю checkAuth и уберу отсюда authorizedUser
            User authorizedUser = authorizationService.checkAuth(email, password);

            if (authorizedUser == null) {
                request.getSession().setAttribute("authError", "Неверный email или пароль.");
                response.sendRedirect("Controller?command=GO_TO_AUTHENTIFICATION_PAGE");
                return;
            }

            Auth auth = new Auth();
            auth.setId(authorizedUser.getId());
            auth.setRole(authorizedUser.getUserRole());
            auth.setName(authorizedUser.getName());

            //передаем в атрибуты для дальнейшего отображения на джсп страницах
            //если передавать на страницах "GO_To_....", то получается переписывание одного и того же кода
            //если передавать сразу объект, то получается, что на джсп странице ты используешь скриплет типа
            // <% Auth auth = (Auth) session.getAttribute("auth");
            //    String role = (auth != null) ? auth.getRole().name() : "Неизвестно";%>
            request.getSession().setAttribute("auth", auth);
            request.getSession().setAttribute("id", auth.getId());
            request.getSession().setAttribute("role", auth.getRole().name().toLowerCase());
            request.getSession().setAttribute("name", auth.getName());
            request.getSession().setAttribute("bio", changeProfileService.getFieldData(auth.getId(), ProfileDataField.BIO));


            response.sendRedirect("Controller?command=" + formatRedirectUrl(auth.getRole()));

        } catch (ServiceException e) {
            request.getSession().setAttribute("authError", "Произошла ошибка при авторизации");
            response.sendRedirect("Controller?command=GO_TO_AUTHENTIFICATION_PAGE");
        }
    }
}
