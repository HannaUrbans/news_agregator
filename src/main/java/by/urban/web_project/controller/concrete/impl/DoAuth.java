package by.urban.web_project.controller.concrete.impl;

import by.urban.web_project.bean.Auth;
import by.urban.web_project.bean.ProfileDataField;
import by.urban.web_project.bean.UserRole;
import by.urban.web_project.controller.concrete.Command;
import by.urban.web_project.service.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import static by.urban.web_project.controller.utils.UrlFormatterUtil.formatRedirectUrl;

public class DoAuth implements Command {
    private final ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private final IAuthorizationService authorizationService = serviceFactory.getAuthorizationService();
    private final IChangeProfileService changeProfileService = serviceFactory.getChangeProfileService();
    private final ICookiesService cookiesService = serviceFactory.getCookiesService();

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
            Auth auth = authorizationService.checkAuth(email, password);
            if (auth == null) {
                request.getSession().setAttribute("authError", "Неверный email или пароль.");
                response.sendRedirect("Controller?command=GO_TO_AUTHENTIFICATION_PAGE");
                return;
            }

            request.getSession().setAttribute("auth", auth);
            request.getSession().setAttribute("id", auth.getId());
            request.getSession().setAttribute("role", auth.getRole().name().toLowerCase());
            request.getSession().setAttribute("name", auth.getName());
            if (auth.getRole() == UserRole.AUTHOR) {
                try {
                    // Пытаемся получить поле BIO из базы данных
                    String bio = changeProfileService.getFieldData(auth.getId(), ProfileDataField.BIO);

                    // Если поле BIO не null, сохраняем его, иначе сохраняем null
                    if (bio == null) {
                        request.getSession().setAttribute("bio", null);
                    } else {
                        request.getSession().setAttribute("bio", bio);
                    }
                } catch (ServiceException e) {
                    // В случае ошибки сохраняем null
                    request.getSession().setAttribute("bio", null);
                    System.out.println("Ошибка при получении BIO: " + e.getMessage());
                }
            } else {
                // Если не автор, устанавливаем bio в null
                request.getSession().setAttribute("bio", null);
            }

            //параметр из формы на странице авторизации
            String rememberMe = request.getParameter("rememberMe");

            //чекбокс "запомни меня" установлен
            if (rememberMe != null && rememberMe.equals("on")) {
                Cookie rememberMeCookie = cookiesService.createOrUpdateRememberMeCookie(request, auth);
                response.addCookie(rememberMeCookie);
                System.out.println("чекбокс 'Remember Me' установлен");

            }
            //чекбокс "запомни меня" не установлен
            else {
                System.out.println("Чекбокс 'Remember Me' не установлен.");
            }
            response.sendRedirect("Controller?command=" + formatRedirectUrl(auth.getRole()));

        } catch (ServiceException e) {
            request.getSession().setAttribute("authError", "Произошла ошибка при авторизации");
            response.sendRedirect("Controller?command=GO_TO_AUTHENTIFICATION_PAGE");
        }
    }
}
