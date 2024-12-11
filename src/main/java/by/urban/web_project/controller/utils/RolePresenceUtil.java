package by.urban.web_project.controller.utils;

import by.urban.web_project.bean.Auth;
import by.urban.web_project.bean.UserRole;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import static by.urban.web_project.controller.utils.UrlFormatterUtil.formatRedirectUrl;

public class RolePresenceUtil {
    public static void checkRolePresence(HttpServletRequest request, HttpServletResponse response, UserRole role) throws IOException {
        Auth auth = (Auth) request.getSession(false).getAttribute("auth");

        if (!role.equals(auth.getRole())) {
            System.out.println("Пользователь пытается войти на страницу добавления новостей не своей роли");
            request.getSession().setAttribute("authError", "У Вас недостаточно прав для посещения этой страницы");
            response.sendRedirect("Controller?command=" + formatRedirectUrl(auth.getRole()));
        }

    }
}
