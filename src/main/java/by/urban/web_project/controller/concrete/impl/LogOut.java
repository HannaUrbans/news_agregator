package by.urban.web_project.controller.concrete.impl;

import by.urban.web_project.bean.Auth;
import by.urban.web_project.controller.concrete.Command;
import by.urban.web_project.service.IAuthorizationService;
import by.urban.web_project.service.ServiceException;
import by.urban.web_project.service.ServiceFactory;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class LogOut implements Command {
    private final IAuthorizationService authorizationService;

    public LogOut() throws ServiceException {
        // Получаем экземпляр AuthorizationService для удаления токена
        this.authorizationService = ServiceFactory.getInstance().getAuthorizationService();
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServiceException {
        Cookie cookie = new Cookie("rememberMe", null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);

        Auth auth = (Auth) request.getSession(false).getAttribute("auth");
        System.out.println("команда логаут");
        // Проверяем, существует ли сессия
        if (auth != null) {
            if(authorizationService.deleteToken(auth.getId())){
                System.out.println("Токен был успешно удален из базы данных.");
            } else {
                System.out.println("Не удалось удалить токен из базы данных.");
            }

            request.getSession().invalidate();
                request.getSession().setAttribute("logoutSuccess", "Вы успешно вышли из системы");
            } else{
                request.getSession().setAttribute("logoutFail", "Вы не были зарегистрированы в системе");
        }
        response.sendRedirect("Controller?command=GO_TO_AUTHENTIFICATION_PAGE");
    }
}
